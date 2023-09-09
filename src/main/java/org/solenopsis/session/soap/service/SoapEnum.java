package org.solenopsis.session.soap.service;

import com.sforce.soap._2006._08.apex.ApexPortType;

/**
 *
 * @author sfloess
 */
public enum SoapEnum {
    APEX(new Soap(SoapEnum.class.getClassLoader().getResource("wsdl/Session-apex.wsdl")), ApexPortType.class),
    ENTERPRISE(),
    PARTNER(),
    METADATA(),
    TOOLING();

    final Soap soap;

    SoapEnum(final Soap soap) {
        this.soap = soap;
    }

    public Soap getSoap() {
        return soap;
    }
}
