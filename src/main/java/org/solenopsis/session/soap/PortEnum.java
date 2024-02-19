package org.solenopsis.session.soap;

import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
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

    public <P> P createPort(final Class<? extends Service> serviceClass, final SessionContext session) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(MethodUtil.findMethodsForAnnotationClass(serviceClass, WebEndpoint.class).get(0).getReturnType());

        final P retVal = (P) factory.create();
        final String url = getUrl().computeUrl(serviceClass, session);

        SoapUtil.setUrl(retVal, url);

        return retVal;
    }
//
//    public <P> P createProxyPort(final Class<? extends Service> serviceClass, final Session session) {
//
//    }
}
