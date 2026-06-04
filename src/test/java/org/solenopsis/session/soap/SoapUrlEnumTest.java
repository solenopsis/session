/* Copyright (C) 2023 Scot P. Floess
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
import org.solenopsis.session.Credentials;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.credentials.CredentialsRecord;
import org.solenopsis.soap.service.ServiceEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test for SoapUrlEnum.
 */
class SoapUrlEnumTest {

    @Test
    void testEnumValues() {
        assertNotNull(SoapUrlEnum.APEX);
        assertNotNull(SoapUrlEnum.CUSTOM);
        assertNotNull(SoapUrlEnum.ENTERPRISE);
        assertNotNull(SoapUrlEnum.METADATA);
        assertNotNull(SoapUrlEnum.PARTNER);
        assertNotNull(SoapUrlEnum.TOOLING);
    }

    @Test
    void testGetPartialUrl() {
        assertEquals("services/Soap/s", SoapUrlEnum.APEX.getPartialUrl());
        assertEquals("services/Soap/class", SoapUrlEnum.CUSTOM.getPartialUrl());
        assertEquals("services/Soap/c", SoapUrlEnum.ENTERPRISE.getPartialUrl());
        assertEquals("services/Soap/m", SoapUrlEnum.METADATA.getPartialUrl());
        assertEquals("services/Soap/u", SoapUrlEnum.PARTNER.getPartialUrl());
        assertEquals("services/Soap/T", SoapUrlEnum.TOOLING.getPartialUrl());
    }

    @Test
    void testGetUrlFunction() {
        assertNotNull(SoapUrlEnum.APEX.getUrlFunction());
        assertNotNull(SoapUrlEnum.ENTERPRISE.getUrlFunction());
        assertNotNull(SoapUrlEnum.PARTNER.getUrlFunction());
        assertNotNull(SoapUrlEnum.TOOLING.getUrlFunction());
        assertNotNull(SoapUrlEnum.METADATA.getUrlFunction());
        assertNotNull(SoapUrlEnum.CUSTOM.getUrlFunction());
    }

    @Test
    void testComputeUrlNullServiceClass() {
        Credentials creds = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password",
            "token",
            "58.0"
        );

        SessionContext session = new SessionContext(
            "https://test.salesforce.com/metadata",
            false,
            true,
            "https://test.salesforce.com",
            "session123",
            "user123",
            ServiceEnum.ENTERPRISE,
            creds
        );

        assertThrows(NullPointerException.class, () -> {
            SoapUrlEnum.ENTERPRISE.computeUrl(null, session);
        });
    }

    @Test
    void testComputeUrlNullSession() {
        assertThrows(NullPointerException.class, () -> {
            SoapUrlEnum.ENTERPRISE.computeUrl(org.solenopsis.soap.enterprise.SforceService.class, null);
        });
    }

    @Test
    void testValueOf() {
        SoapUrlEnum url = SoapUrlEnum.valueOf("ENTERPRISE");
        assertEquals(SoapUrlEnum.ENTERPRISE, url);
    }

    @Test
    void testValues() {
        SoapUrlEnum[] values = SoapUrlEnum.values();
        assertEquals(6, values.length);
    }
}
