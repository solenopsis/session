package org.solenopsis.session.soap;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.headers.Header;
import org.apache.cxf.jaxb.JAXBDataBinding;
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

    private PortEnum(final UrlEnum url) {
        this.url = url;
    }

    UrlEnum getUrl() {
        return url;
    }

    public <P> P createPort(final Class<? extends Service> serviceClass, final SessionContext session) throws JAXBException {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(MethodUtil.findMethodsForAnnotationClass(serviceClass, WebEndpoint.class).get(0).getReturnType());

        final P retVal = (P) factory.create();
        final String url = getUrl().computeUrl(serviceClass, session);

        System.out.println("serviceClass:  "  + serviceClass);
        System.out.println("Annotation:    " + serviceClass.getAnnotation(WebServiceClient.class));

        final QName qname = new QName(serviceClass.getAnnotation(WebServiceClient.class).targetNamespace());

        Header sessionId = new Header(new QName(serviceClass.getAnnotation(WebServiceClient.class).targetNamespace(), "session-id"), session.credentials().securityPassword(), new JAXBDataBinding(String.class));

        List<Header> headers = new ArrayList<Header>();
        Header soapHeader = new Header(new QName(serviceClass.getAnnotation(WebServiceClient.class).targetNamespace(), "SessionHeader"), sessionId);
        headers.add(soapHeader);

        Map<String, Object> ctx = ((BindingProvider) retVal).getRequestContext();
        ctx.put(Header.HEADER_LIST, headers);

        SoapUtil.setUrl(retVal, url);

        return retVal;
    }
//
//    public <P> P createProxyPort(final Class<? extends Service> serviceClass, final Session session) {
//
//    }
}
