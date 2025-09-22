package org.solenopsis.session.soap;

import jakarta.xml.ws.Service;
import java.lang.reflect.Proxy;
import org.solenopsis.session.Credentials;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.soap.login.LoginServiceEnum;

/**
 *
 * @author sfloess
 */
public enum ProxyPortEnum {
    APEX(PortEnum.APEX),
    CUSTOM(PortEnum.CUSTOM),
    ENTERPRISE(PortEnum.ENTERPRISE),
    METADATA(PortEnum.METADATA),
    PARTNER(PortEnum.PARTNER),
    TOOLING(PortEnum.TOOLING);

    private final PortEnum portEnum;

    private ProxyPortEnum(final PortEnum portEnum) {
        this.portEnum = portEnum;
    }


    <P> P createProxiedPort(final Class<? extends Service> serviceClass, final Object toProxy, final SessionContext session, final LoginServiceEnum loginService) {
        return (P) Proxy.newProxyInstance(getClass().getClassLoader(), toProxy.getClass().getInterfaces(), new PortProxy(getPortEnum(), serviceClass, toProxy, session, loginService));
    }

    <P> P createProxiedPort(final Class<? extends Service> serviceClass, final Object toProxy, final Credentials credentials, final LoginServiceEnum loginService) {
        return (P) Proxy.newProxyInstance(getClass().getClassLoader(), toProxy.getClass().getInterfaces(), new PortProxy(getPortEnum(), serviceClass, toProxy, credentials, loginService));
    }

    <P> P createProxyPort(final Class<? extends Service> serviceClass, final SessionContext session, final LoginServiceEnum loginService) {
        // The created port has all the interface definitions.
        return createProxiedPort(serviceClass, getPortEnum().createPortForService(serviceClass, session), session, loginService);
    }

    <P> P createProxyPort(final Class<? extends Service> serviceClass, final Credentials credentials, final LoginServiceEnum loginService) {
        // The created port has all the interface definitions.
        return createProxiedPort(serviceClass, getPortEnum().createPortForService(serviceClass, credentials), credentials, loginService);
    }

    public PortEnum getPortEnum() {
        return portEnum;
    }

    public <P> P createProxyPortForService(final Class<? extends Service> serviceClass, final SessionContext session, final LoginServiceEnum loginService) {
         return createProxyPort(serviceClass, session, loginService);
    }

    public <P> P createProxyPortForService(final Class<? extends Service> serviceClass, final SessionContext session) {
        return createProxyPortForService(serviceClass, session, LoginServiceEnum.DEFAULT_LOGIN_SERVICE);
    }

    public <P> P createProxyPortForService(final Class<? extends Service> serviceClass, final Credentials credentials, final LoginServiceEnum loginService) {
        return createProxyPort(serviceClass, credentials, loginService);
    }

    public <P> P createProxyPortForService(final Class<? extends Service> serviceClass, final Credentials credentials) {
        return createProxyPortForService(serviceClass, credentials, LoginServiceEnum.DEFAULT_LOGIN_SERVICE);
    }
}
