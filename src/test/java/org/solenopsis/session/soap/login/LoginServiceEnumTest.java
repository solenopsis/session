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
package org.solenopsis.session.soap.login;

import org.junit.jupiter.api.Test;
import org.solenopsis.session.login.LoginService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Test for LoginServiceEnum.
 */
class LoginServiceEnumTest {

    @Test
    void testEnumValues() {
        assertNotNull(LoginServiceEnum.ENTERPRISE);
        assertNotNull(LoginServiceEnum.PARTNER);
        assertNotNull(LoginServiceEnum.TOOLING);
    }

    @Test
    void testGetLoginService() {
        LoginService enterpriseService = LoginServiceEnum.ENTERPRISE.getLoginService();
        assertNotNull(enterpriseService);

        LoginService partnerService = LoginServiceEnum.PARTNER.getLoginService();
        assertNotNull(partnerService);

        LoginService toolingService = LoginServiceEnum.TOOLING.getLoginService();
        assertNotNull(toolingService);
    }

    @Test
    void testDefaultLoginService() {
        assertEquals(LoginServiceEnum.PARTNER, LoginServiceEnum.DEFAULT_LOGIN_SERVICE);
    }

    @Test
    void testGetLoginServiceReturnsConsistentInstance() {
        LoginService service1 = LoginServiceEnum.ENTERPRISE.getLoginService();
        LoginService service2 = LoginServiceEnum.ENTERPRISE.getLoginService();

        // Should return the same instance each time
        assertSame(service1, service2);
    }

    @Test
    void testValueOf() {
        LoginServiceEnum service = LoginServiceEnum.valueOf("ENTERPRISE");
        assertEquals(LoginServiceEnum.ENTERPRISE, service);
    }

    @Test
    void testValues() {
        LoginServiceEnum[] values = LoginServiceEnum.values();
        assertEquals(3, values.length);
    }

    @Test
    void testEnterpriseLoginService() {
        LoginServiceEnum enterprise = LoginServiceEnum.ENTERPRISE;
        assertNotNull(enterprise.getLoginService());
    }

    @Test
    void testPartnerLoginService() {
        LoginServiceEnum partner = LoginServiceEnum.PARTNER;
        assertNotNull(partner.getLoginService());
    }

    @Test
    void testToolingLoginService() {
        LoginServiceEnum tooling = LoginServiceEnum.TOOLING;
        assertNotNull(tooling.getLoginService());
    }
}
