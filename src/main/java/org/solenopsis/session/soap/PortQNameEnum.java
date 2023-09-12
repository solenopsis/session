package org.solenopsis.session.soap;

import jakarta.xml.ws.WebServiceClient;
import javax.xml.namespace.QName;

/**
 *
 * @author sfloess
 */
public enum PortQNameEnum {
    APEX(ServiceEnum.APEX),
    ENTERPRISE(ServiceEnum.ENTERPRISE),
    METADATA(ServiceEnum.METADATA),
    PARTNER(ServiceEnum.PARTNER),
    TOOLING(ServiceEnum.TOOLING)
    ;

    private final QName qname;

    private PortQNameEnum(final WebServiceClient webServiceClient) {
        qname = new QName(webServiceClient.targetNamespace(), webServiceClient.name());
    }

    private PortQNameEnum(final ServiceEnum service) {
        this(service.getService().getClass().getAnnotation(WebServiceClient.class));
    }

    public QName getQName() {
        return qname;
    }
}
