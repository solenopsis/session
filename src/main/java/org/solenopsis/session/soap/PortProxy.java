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
    private final PortEnum portEnum;
    private final Class<? extends Service> serviceClass;
    private Object proxy;
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

    void handleException(final Method method, final Object[] args, final InvocationTargetException exception) throws Throwable {

    }

    public PortProxy(final PortEnum portEnum, final Class<? extends Service> serviceClass, final Object toProxy, final SessionContext session, final LoginServiceEnum loginService) {
        this.portEnum = Objects.requireNonNull(portEnum, "Must provide an instance of port enum!");
        this.serviceClass = Objects.requireNonNull(serviceClass, "Must provide a service class!");
        this.proxy = Objects.requireNonNull(toProxy, "Must provide an instance to a proxy!");
        this.session = Objects.requireNonNull(session, "Must provide a session!");
        this.loginService = Objects.requireNonNull(loginService, "Must provide a login service!");
    }

    public PortProxy(final PortEnum portEnum, final Class<? extends Service> serviceClass, final Object toProxy, final SessionContext session) {
        this(portEnum, serviceClass, toProxy, session, LoginServiceEnum.PARTNER);
    }


    public PortProxy(final PortEnum portEnum, final Class<? extends Service> serviceClass, final Object toProxy, final Credentials credentials, final LoginServiceEnum loginService) {
        this(portEnum, serviceClass, toProxy, Objects.requireNonNull(loginService, "Must provide a login service!").getLoginService().login(credentials), loginService);
    }

    public PortProxy(final PortEnum portEnum, final Class<? extends Service> serviceClass, final Object toProxy, final Credentials credentials) {
        this(portEnum, serviceClass, toProxy, credentials, LoginServiceEnum.DEFAULT_LOGIN_SERVICE);
    }


    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        // Run forever...
        while(true) {
            try {
                return method.invoke(getProxy(), args);
            } catch(final InvocationTargetException exception) {
                if (SoapExceptionUtil.isInvalidSessionId(exception)) {
                    log(Level.WARNING, exception,"Session ID [" + getSession().sessionId() + "] is stale, will relogin");

                    this.proxy = getPortEnum().createPortForService(getServiceClass(), getSession(), getLoginService());

                    continue;
                } else if (SoapExceptionUtil.isRetry(exception)) {
                    continue;
                }

                log(Level.SEVERE, exception, "Problem  Invoking " + getProxy().getClass().getName() + " -> " + method.getName());

                throw exception;
            }
        }
    }
}
