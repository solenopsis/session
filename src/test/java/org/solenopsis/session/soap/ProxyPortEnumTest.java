/*
 * Copyright (C) 2023-2026 Scot P. Floess
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
