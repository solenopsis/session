package org.solenopsis.session.soap;

/**
 *
 * @author sfloess
 */
public enum SoapEnum {
    APEX(ServiceEnum.APEX, PortClassEnum.APEX, PartialUrlEnum.APEX),
    ENTERPRISE(ServiceEnum.ENTERPRISE, PortClassEnum.ENTERPRISE, PartialUrlEnum.ENTERPRISE),
    PARTNER(ServiceEnum.PARTNER, PortClassEnum.PARTNER, PartialUrlEnum.PARTNER),
    METADATA(ServiceEnum.METADATA, PortClassEnum.METADATA, PartialUrlEnum.METADATA),
    TOOLING(ServiceEnum.TOOLING, PortClassEnum.TOOLING, PartialUrlEnum.TOOLING);

    private final ServiceEnum service;
    private final PortClassEnum port;
    private final PartialUrlEnum partialUrl;

    SoapEnum(final ServiceEnum service, final PortClassEnum port ,final PartialUrlEnum partialUrl) {
        this.service = service;
        this.port = port;
        this.partialUrl = partialUrl;
    }

    public ServiceEnum getService() {
        return service;
    }

    public PortClassEnum getPort() {
        return port;
    }

    public PartialUrlEnum getPartialUrl() {
        return partialUrl;
    }
}
