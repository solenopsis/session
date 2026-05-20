package org.solenopsis.session.soap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test for ProxyPortEnum.
 */
class ProxyPortEnumTest {

    @Test
    void testEnumValues() {
        assertNotNull(ProxyPortEnum.APEX);
        assertNotNull(ProxyPortEnum.CUSTOM);
        assertNotNull(ProxyPortEnum.ENTERPRISE);
        assertNotNull(ProxyPortEnum.METADATA);
        assertNotNull(ProxyPortEnum.PARTNER);
        assertNotNull(ProxyPortEnum.TOOLING);
    }

    @Test
    void testGetPortEnum() {
        assertEquals(PortEnum.APEX, ProxyPortEnum.APEX.getPortEnum());
        assertEquals(PortEnum.CUSTOM, ProxyPortEnum.CUSTOM.getPortEnum());
        assertEquals(PortEnum.ENTERPRISE, ProxyPortEnum.ENTERPRISE.getPortEnum());
        assertEquals(PortEnum.METADATA, ProxyPortEnum.METADATA.getPortEnum());
        assertEquals(PortEnum.PARTNER, ProxyPortEnum.PARTNER.getPortEnum());
        assertEquals(PortEnum.TOOLING, ProxyPortEnum.TOOLING.getPortEnum());
    }

    @Test
    void testApex() {
        ProxyPortEnum apex = ProxyPortEnum.APEX;
        assertEquals(PortEnum.APEX, apex.getPortEnum());
    }

    @Test
    void testCustom() {
        ProxyPortEnum custom = ProxyPortEnum.CUSTOM;
        assertEquals(PortEnum.CUSTOM, custom.getPortEnum());
    }

    @Test
    void testEnterprise() {
        ProxyPortEnum enterprise = ProxyPortEnum.ENTERPRISE;
        assertEquals(PortEnum.ENTERPRISE, enterprise.getPortEnum());
    }

    @Test
    void testMetadata() {
        ProxyPortEnum metadata = ProxyPortEnum.METADATA;
        assertEquals(PortEnum.METADATA, metadata.getPortEnum());
    }

    @Test
    void testPartner() {
        ProxyPortEnum partner = ProxyPortEnum.PARTNER;
        assertEquals(PortEnum.PARTNER, partner.getPortEnum());
    }

    @Test
    void testTooling() {
        ProxyPortEnum tooling = ProxyPortEnum.TOOLING;
        assertEquals(PortEnum.TOOLING, tooling.getPortEnum());
    }

    @Test
    void testValueOf() {
        ProxyPortEnum port = ProxyPortEnum.valueOf("ENTERPRISE");
        assertEquals(ProxyPortEnum.ENTERPRISE, port);
    }

    @Test
    void testValues() {
        ProxyPortEnum[] values = ProxyPortEnum.values();
        assertEquals(6, values.length);
    }
}
