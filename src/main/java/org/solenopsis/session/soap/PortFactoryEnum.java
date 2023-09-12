package org.solenopsis.session.soap;

/**
 *
 * @author sfloess
 */
public enum PortFactoryEnum {
    APEX(ServicePortMethodEnum.APEX),
    ENTERPRISE(ServicePortMethodEnum.ENTERPRISE),
    PARTNER(ServicePortMethodEnum.PARTNER),
    METADATA(ServicePortMethodEnum.METADATA),
    TOOLING(ServicePortMethodEnum.TOOLING),
    ;

    private final ServicePortMethodEnum servicePortMethod;

    private PortFactoryEnum(final ServicePortMethodEnum servicePortMethod) {
        this.servicePortMethod = servicePortMethod;
    }

    public <P> P createPort() {
        return (P) servicePortMethod.getPortMethod().invoke(this, args);
    }
}
