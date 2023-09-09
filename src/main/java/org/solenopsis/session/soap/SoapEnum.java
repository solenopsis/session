package org.solenopsis.session.soap;

import org.flossware.soap.Soap;

/**
 *
 * @author sfloess
 */
public enum SoapEnum {
    APEX(ServiceEnum.APEX, PortEnum.APEX),
    ENTERPRISE(ServiceEnum.ENTERPRISE, PortEnum.ENTERPRISE),
    PARTNER(ServiceEnum.PARTNER, PortEnum.PARTNER),
    METADATA(ServiceEnum.METADATA, PortEnum.METADATA),
    TOOLING(ServiceEnum.TOOLING, PortEnum.TOOLING);

    final ServiceEnum service;
    final PortEnum port;

    final Soap soap;

    SoapEnum(final ServiceEnum service, PortEnum port) {
        this.service = service;
        this.port = port;

        this.soap = new Soap(service.getService(), port.getPortType());
    }

    public ServiceEnum getService() {
        return service;
    }

    public PortEnum getPort() {
        return port;
    }

    public Soap getSoap() {
        return soap;
    }
}
