package org.solenopsis.session.soap.service;

/**
 *
 * @author sfloess
 */
public enum SoapEnum {
    ENTERPRISE_SERVICE(),
    PARTNER_SERVICE(),
    METADATA_SERVICE(),
    TOOLING_SERVICE();

    final Soap soap;

    SoapEnum(final Soap soap) {
        this.soap = soap;
    }

    public Soap getSoap() {
        return soap;
    }
}
