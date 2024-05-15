package org.solenopsis.session.soap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.flossware.commons.util.MethodUtil;
import org.flossware.commons.util.SoapException;
import org.flossware.commons.util.SoapUtil;
import org.solenopsis.session.Credentials;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.soap.login.LoginServiceEnum;

/**
 *
 * @author sfloess
 */
public enum PortEnum {
    APEX(SoapUrlEnum.APEX),
    CUSTOM(SoapUrlEnum.CUSTOM),
    ENTERPRISE(SoapUrlEnum.ENTERPRISE),
    METADATA(SoapUrlEnum.METADATA),
    PARTNER(SoapUrlEnum.PARTNER),
    TOOLING(SoapUrlEnum.TOOLING);

    private static final Class[] DEFAULT_CONSTRCUCTOR_PARAMS_TYPES = new Class[0];
    private static final Object[] DEFAULT_CONSTRUCTOR_PARAMS = new Object[0];

    private final SoapUrlEnum url;


    private PortEnum(final SoapUrlEnum url) {
        this.url = url;
    }

    SoapUrlEnum getUrl() {
        return url;
    }

    List<Handler> createHandlerList() {
        return new ArrayList<Handler>(1);
    }

    <P> P createPortForTargetNamespace(final String targetNamespace, final Class<? extends Service> serviceClass, final SessionContext session) throws SoapException {
        try {
            final Constructor constructor = serviceClass.getConstructor(DEFAULT_CONSTRCUCTOR_PARAMS_TYPES);
            final Service service = (Service) constructor.newInstance(DEFAULT_CONSTRUCTOR_PARAMS);

            final Method method = MethodUtil.findMethodsForAnnotationClass(serviceClass, WebEndpoint.class).get(0);

            final P retVal = (P)  method.invoke(serviceClass, DEFAULT_CONSTRUCTOR_PARAMS);

            final String url = getUrl().computeUrl(serviceClass, session);

            final SOAPElement sessionId = SoapUtil.getSoapFactory().createElement(new QName(targetNamespace, SessionHeaderEnum.SESSION_ID.getString()));
            sessionId.addTextNode(session.sessionId());

            final SOAPElement sessionHeader = SoapUtil.getSoapFactory().createElement(new QName(targetNamespace, SessionHeaderEnum.SESSION_HEADER.getString()));
            sessionHeader.addChildElement(sessionId);





            final List<Header> headers = new ArrayList<Header>();
            final Header soapHeader = new Header(new QName(targetNamespace, SessionHeaderEnum.SESSION_HEADER.getString()), sessionHeader);
            headers.add(soapHeader);

            final Map<String, Object> ctx = ((BindingProvider) retVal).getRequestContext();
            ctx.put(Header.HEADER_LIST, headers);

            SoapUtil.setUrl(retVal, url);

            return retVal;
        } catch(final Throwable soapException) {
            throw new SoapException(soapException);
        }
    }

    <P> P createPortForTargetNamespace(final String targetNamespace, final Class<? extends Service> serviceClass, final Credentials credentials, final LoginServiceEnum loginService) throws SoapException {
        return createPortForTargetNamespace(targetNamespace, serviceClass, loginService.getLoginService().login(credentials));
    }

    <P> P createPortForTargetNamespace(final String targetNamespace, final Class<? extends Service> serviceClass, final Credentials creds) throws SoapException {
        return createPortForTargetNamespace(targetNamespace, serviceClass, creds, LoginServiceEnum.DEFAULT_LOGIN_SERVICE);
    }

    <P> P createPortForAnnotation(final WebServiceClient webServiceClientAnnotation, final Class<? extends Service> serviceClass, final SessionContext session) {
        return createPortForTargetNamespace(webServiceClientAnnotation.targetNamespace(), serviceClass, session);
    }

    <P> P createPortForAnnotation(final WebServiceClient webServiceClientAnnotation, final Class<? extends Service> serviceClass, final Credentials credentials, final LoginServiceEnum loginService) {
        return createPortForTargetNamespace(webServiceClientAnnotation.targetNamespace(), serviceClass, loginService.getLoginService().login(credentials));
    }

    public <P> P createPortForService(final Class<? extends Service> serviceClass, final SessionContext session, final LoginServiceEnum loginService) {
        return createPortForAnnotation(serviceClass.getAnnotation(WebServiceClient.class), serviceClass, session);
    }

    public <P> P createPortForService(final Class<? extends Service> serviceClass, final SessionContext session) {
        return createPortForService(serviceClass, session, LoginServiceEnum.DEFAULT_LOGIN_SERVICE);
    }

    public <P> P createPortForService(final Class<? extends Service> serviceClass, final Credentials credentials, final LoginServiceEnum loginService) {
        return createPortForAnnotation(serviceClass.getAnnotation(WebServiceClient.class), serviceClass, credentials, loginService);
    }

    public <P> P createPortForService(final Class<? extends Service> serviceClass, final Credentials credentials) {
        return createPortForService(serviceClass, credentials, LoginServiceEnum.DEFAULT_LOGIN_SERVICE);
    }
}
