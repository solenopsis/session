package org.solenopsis.session.soap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test for PortEnum.
 */
class PortEnumTest {

    @Test
    void testEnumValues() {
        assertNotNull(PortEnum.APEX);
        assertNotNull(PortEnum.CUSTOM);
        assertNotNull(PortEnum.ENTERPRISE);
        assertNotNull(PortEnum.METADATA);
        assertNotNull(PortEnum.PARTNER);
        assertNotNull(PortEnum.TOOLING);
    }

    @Test
    void testGetUrl() {
        assertEquals(SoapUrlEnum.APEX, PortEnum.APEX.getUrl());
        assertEquals(SoapUrlEnum.CUSTOM, PortEnum.CUSTOM.getUrl());
        assertEquals(SoapUrlEnum.ENTERPRISE, PortEnum.ENTERPRISE.getUrl());
        assertEquals(SoapUrlEnum.METADATA, PortEnum.METADATA.getUrl());
        assertEquals(SoapUrlEnum.PARTNER, PortEnum.PARTNER.getUrl());
        assertEquals(SoapUrlEnum.TOOLING, PortEnum.TOOLING.getUrl());
    }

    @Test
    void testApex() {
        PortEnum apex = PortEnum.APEX;
        assertEquals(SoapUrlEnum.APEX, apex.getUrl());
    }

    @Test
    void testCustom() {
        PortEnum custom = PortEnum.CUSTOM;
        assertEquals(SoapUrlEnum.CUSTOM, custom.getUrl());
    }

    @Test
    void testEnterprise() {
        PortEnum enterprise = PortEnum.ENTERPRISE;
        assertEquals(SoapUrlEnum.ENTERPRISE, enterprise.getUrl());
    }

    @Test
    void testMetadata() {
        PortEnum metadata = PortEnum.METADATA;
        assertEquals(SoapUrlEnum.METADATA, metadata.getUrl());
    }

    @Test
    void testPartner() {
        PortEnum partner = PortEnum.PARTNER;
        assertEquals(SoapUrlEnum.PARTNER, partner.getUrl());
    }

    @Test
    void testTooling() {
        PortEnum tooling = PortEnum.TOOLING;
        assertEquals(SoapUrlEnum.TOOLING, tooling.getUrl());
    }

    @Test
    void testValueOf() {
        PortEnum port = PortEnum.valueOf("ENTERPRISE");
        assertEquals(PortEnum.ENTERPRISE, port);
    }

    @Test
    void testValues() {
        PortEnum[] values = PortEnum.values();
        assertEquals(6, values.length);
    }
}
