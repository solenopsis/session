package org.solenopsis.session.soap;

import com.sforce.soap._2006._04.metadata.MetadataService;
import com.sforce.soap._2006._08.apex.ApexService;
import com.sforce.soap.enterprise.SforceService;
import com.sforce.soap.tooling.SforceServiceService;
import jakarta.xml.ws.Service;

/**
 *
 * @author sfloess
 */
public enum ServiceEnum {
    APEX(new ApexService(ServiceEnum.class.getClassLoader().getResource("wsdl/Session-apex.wsdl"))),
    ENTERPRISE(new SforceService(ServiceEnum.class.getClassLoader().getResource("wsdl/Session-enterprise.wsdl"))),
    METADATA(new MetadataService(ServiceEnum.class.getClassLoader().getResource("wsdl/Session-metadata.wsdl"))),
    PARTNER(new com.sforce.soap.partner.SforceService(ServiceEnum.class.getClassLoader().getResource("wsdl/Session-partner.wsdl"))),
    TOOLING(new SforceServiceService(ServiceEnum.class.getClassLoader().getResource("wsdl/Session-tooling.wsdl")));

    final Service service;

    ServiceEnum(final Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }
}
