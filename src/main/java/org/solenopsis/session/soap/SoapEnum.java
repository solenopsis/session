package org.solenopsis.session.soap;

import org.flossware.soap.Soap;

/**
 *
 * @author sfloess
 */
public enum SoapEnum {
    APEX(ServiceEnum.APEX, PortEnum.APEX, PartialUrlEnum.APEX),
    ENTERPRISE(ServiceEnum.ENTERPRISE, PortEnum.ENTERPRISE, PartialUrlEnum.ENTERPRISE),
    PARTNER(ServiceEnum.PARTNER, PortEnum.PARTNER, PartialUrlEnum.PARTNER),
    METADATA(ServiceEnum.METADATA, PortEnum.METADATA, PartialUrlEnum.METADATA),
    TOOLING(ServiceEnum.TOOLING, PortEnum.TOOLING, PartialUrlEnum.TOOLING);

    private final ServiceEnum service;
    private final PortEnum port;
    private final PartialUrlEnum partialUrl;

    private final Soap soap;

    SoapEnum(final ServiceEnum service, final PortEnum port ,final PartialUrlEnum partialUrl) {
        this.service = service;
        this.port = port;
        this.partialUrl = partialUrl;

        this.soap = new Soap(service.getService(), port.getPortType());
    }

    public ServiceEnum getService() {
        return service;
    }

    public PortEnum getPort() {
        return port;
    }

    public PartialUrlEnum getPartialUrl() {
        return partialUrl;
    }

    public Soap getSoap() {
        return soap;
    }
}
