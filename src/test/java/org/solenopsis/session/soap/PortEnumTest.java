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

import org.flossware.commons.util.SoapException;
import org.junit.jupiter.api.Test;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.credentials.CredentialsRecord;
import org.solenopsis.soap.service.ServiceEnum;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void testCreatePortForTargetNamespaceNoWebEndpointThrows() {
        var creds = new CredentialsRecord("https://test.salesforce.com", "user@test.com", "pass", "tok", "58.0");
        var session = new SessionContext(
            "https://test.salesforce.com/services/Soap/m/58.0",
            false, true,
            "https://test.salesforce.com/services/Soap/u/58.0",
            "sessionId", "userId", ServiceEnum.ENTERPRISE, creds
        );

        SoapException ex = assertThrows(SoapException.class, () -> {
            PortEnum.ENTERPRISE.createPortForTargetNamespace("urn:test", NoWebEndpointService.class, session);
        });
        assertTrue(ex.getMessage().contains("No methods annotated with @WebEndpoint"));
        assertTrue(ex.getMessage().contains("NoWebEndpointService"));
    }
}
