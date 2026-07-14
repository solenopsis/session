/*
 * Copyright (C) 2023-2026 Scot P. Floess
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.solenopsis.session.soap;

import jakarta.xml.ws.Service;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.logging.Level;
import org.flossware.commons.AbstractBase;
import org.solenopsis.session.Credentials;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.soap.login.LoginServiceEnum;
import org.solenopsis.session.soap.util.SoapExceptionUtil;

/**
 * Proxies calls to methods.
 *
 * @author sfloess
 */
public class PortProxy extends AbstractBase implements InvocationHandler {
    static final int MAX_RETRIES = 5;
    static final int MAX_RELOGINS = 3;
    static final long MAX_BACKOFF_MS = 30_000L;

    private final PortEnum portEnum;
    private final Class<? extends Service> serviceClass;
    private volatile Object proxy;
    private final SessionContext session;
    private final LoginServiceEnum loginService;

    private PortEnum getPortEnum() {
        return portEnum;
    }

    private Object getProxy() {
        return proxy;
    }

    private Class<? extends Service> getServiceClass() {
        return serviceClass;
    }

    private SessionContext getSession() {
        return session;
    }

    private LoginServiceEnum getLoginService() {
        return loginService;
    }

    public PortProxy(final PortEnum portEnum, final Class<? extends Service> serviceClass, final Object toProxy, final SessionContext session, final LoginServiceEnum loginService) {
        this.portEnum = Objects.requireNonNull(portEnum, "Must provide an instance of port enum!");
        this.serviceClass = Objects.requireNonNull(serviceClass, "Must provide a service class!");
        this.proxy = Objects.requireNonNull(toProxy, "Must provide an instance to a proxy!");
        this.session = Objects.requireNonNull(session, "Must provide a session!");
        this.loginService = Objects.requireNonNull(loginService, "Must provide a login service!");
    }

    public PortProxy(final PortEnum portEnum, final Class<? extends Service> serviceClass, final Object toProxy, final SessionContext session) {
        this(portEnum, serviceClass, toProxy, session, LoginServiceEnum.DEFAULT_LOGIN_SERVICE);
    }

    public PortProxy(final PortEnum portEnum, final Class<? extends Service> serviceClass, final Object toProxy, final Credentials credentials, final LoginServiceEnum loginService) {
        this(portEnum, serviceClass, toProxy, Objects.requireNonNull(loginService, "Must provide a login service!").getLoginService().login(credentials), loginService);
    }

    public PortProxy(final PortEnum portEnum, final Class<? extends Service> serviceClass, final Object toProxy, final Credentials credentials) {
        this(portEnum, serviceClass, toProxy, credentials, LoginServiceEnum.DEFAULT_LOGIN_SERVICE);
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        int retryCount = 0;
        int reloginCount = 0;

        while (true) {
            try {
                return method.invoke(getProxy(), args);
            } catch (final InvocationTargetException exception) {
                if (SoapExceptionUtil.isInvalidSessionId(exception)) {
                    if (++reloginCount > MAX_RELOGINS) {
                        log(Level.SEVERE, exception, "Exhausted " + MAX_RELOGINS + " relogin attempts for " + method.getName());
                        throw exception;
                    }

                    log(Level.WARNING, exception, "Session is stale, relogin attempt " + reloginCount + "/" + MAX_RELOGINS);
                    this.proxy = getPortEnum().createPortForService(getServiceClass(), getSession(), getLoginService());
                    continue;
                } else if (SoapExceptionUtil.isRetry(exception)) {
                    if (++retryCount > MAX_RETRIES) {
                        log(Level.SEVERE, exception, "Exhausted " + MAX_RETRIES + " retry attempts for " + method.getName());
                        throw exception;
                    }

                    final long backoffMs = Math.min(1000L * (1L << (retryCount - 1)), MAX_BACKOFF_MS);
                    log(Level.WARNING, exception, "Transient error, retry " + retryCount + "/" + MAX_RETRIES + " after " + backoffMs + "ms");
                    Thread.sleep(backoffMs);
                    continue;
                }

                log(Level.SEVERE, exception, "Problem invoking " + getProxy().getClass().getName() + " -> " + method.getName());
                throw exception;
            }
        }
    }
}
