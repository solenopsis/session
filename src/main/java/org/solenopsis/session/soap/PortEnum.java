package org.solenopsis.session.soap;

import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.headers.Header;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.flossware.jcommons.util.MethodUtil;
import org.flossware.jcommons.util.SoapUtil;
import org.solenopsis.session.SessionContext;

/**
 *
 * @author sfloess
 */
public enum PortEnum {
    APEX(UrlEnum.APEX),
    CUSTOM(UrlEnum.CUSTOM),
    ENTERPRISE(UrlEnum.ENTERPRISE),
    METADATA(UrlEnum.METADATA),
    PARTNER(UrlEnum.PARTNER),
    TOOLING(UrlEnum.TOOLING);

    private final UrlEnum url;

    public static final String SESSION_ID = "sessionId";
    public static final String SESSION_HEADER = "SessionHeader";

    private PortEnum(final UrlEnum url) {
        this.url = url;
    }

    UrlEnum getUrl() {
        return url;
    }

    <P> P createPortForTargetNamespace(final String targetNamespace, final Class<? extends Service> serviceClass, final SessionContext session) throws SOAPException {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(MethodUtil.findMethodsForAnnotationClass(serviceClass, WebEndpoint.class).get(0).getReturnType());

        final P retVal = (P) factory.create();
        final String url = getUrl().computeUrl(serviceClass, session);

        SOAPElement sessionId = SoapUtil.getSoapFactory().createElement(new QName(targetNamespace, SESSION_ID));
        sessionId.addTextNode(session.sessionId());

        SOAPElement sessionHeader = SoapUtil.getSoapFactory().createElement(new QName(targetNamespace, SESSION_HEADER));
        sessionHeader.addChildElement(sessionId);

        List<Header> headers = new ArrayList<Header>();
        Header soapHeader = new Header(new QName(targetNamespace, SESSION_HEADER), sessionHeader);
        headers.add(soapHeader);

        Map<String, Object> ctx = ((BindingProvider) retVal).getRequestContext();
        ctx.put(Header.HEADER_LIST, headers);

        SoapUtil.setUrl(retVal, url);

        return retVal;
    }

    <P> P createPortForAnnotation(final WebServiceClient webServiceClientAnnotation, final Class<? extends Service> serviceClass, final SessionContext session) throws Exception {
        return createPortForTargetNamespace(webServiceClientAnnotation.targetNamespace(), serviceClass, session);
    }

    public <P> P createPortForService(final Class<? extends Service> serviceClass, final SessionContext session) throws Exception {
        return createPortForAnnotation(serviceClass.getAnnotation(WebServiceClient.class), serviceClass, session);
    }
//
//    public <P> P createProxyPort(final Class<? extends Service> serviceClass, final Session session) {
//
//    }
}
