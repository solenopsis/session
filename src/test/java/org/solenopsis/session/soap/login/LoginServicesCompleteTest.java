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
package org.solenopsis.session.soap.login;

import org.junit.jupiter.api.Test;
import org.solenopsis.session.Credentials;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.credentials.CredentialsRecord;
import org.solenopsis.session.login.LoginException;
import org.solenopsis.soap.service.ServiceEnum;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Complete coverage tests for all login services - testing public methods.
 */
class LoginServicesCompleteTest {

    @Test
    void testEnterpriseLoginService_PublicLogin() {
        Credentials credentials = new CredentialsRecord(
            "https://test.salesforce.com",
            "invalid@test.com",
            "invalid",
            "invalid",
            "58.0"
        );

        EnterpriseLoginService service = new EnterpriseLoginService();

        // This will fail because it tries to actually connect
        assertThrows(LoginException.class, () -> {
            service.login(credentials);
        });
    }

    @Test
    void testPartnerLoginService_PublicLogin() {
        Credentials credentials = new CredentialsRecord(
            "https://test.salesforce.com",
            "invalid@test.com",
            "invalid",
            "invalid",
            "58.0"
        );

        PartnerLoginService service = new PartnerLoginService();

        // This will fail because it tries to actually connect
        assertThrows(LoginException.class, () -> {
            service.login(credentials);
        });
    }

    @Test
    void testToolingLoginService_PublicLogin() {
        Credentials credentials = new CredentialsRecord(
            "https://test.salesforce.com",
            "invalid@test.com",
            "invalid",
            "invalid",
            "58.0"
        );

        ToolingLoginService service = new ToolingLoginService();

        // This will fail because it tries to actually connect
        assertThrows(LoginException.class, () -> {
            service.login(credentials);
        });
    }
}
