package org.solenopsis.session.soap.cxf.login;

import org.solenopsis.soap.wsimport.port.factory.PortFactoryEnum;

/**
 *
 * @author sfloess
 */
public enum LoginServiceEnum {
    ENTERPRISE(PortFactoryEnum.ENTERPRISE),
    PARTNER(PortFactoryEnum.PARTNER),
    TOOLING(PortFactoryEnum.TOOLING);

    private final PortFactoryEnum portFactory;

    private LoginServiceEnum(final PortFactoryEnum portFactory) {
        this.portFactory = portFactory;
    }

    public PortFactoryEnum getLoginPortFactory() {
        return portFactory;
    }
}
