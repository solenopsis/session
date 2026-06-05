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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.solenopsis.session.Credentials;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.credentials.CredentialsRecord;
import org.solenopsis.session.login.LoginException;
import org.solenopsis.session.login.LogoutException;
import org.solenopsis.soap.partner.LoginResult;
import org.solenopsis.soap.partner.Soap;
import org.solenopsis.soap.service.ServiceEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for PartnerLoginService.
 */
@ExtendWith(MockitoExtension.class)
class PartnerLoginServiceTest {

    @Mock
    private Soap soapPort;

    @Mock
    private LoginResult loginResult;

    @Test
    void testToSession() {
        Credentials credentials = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password",
            "token",
            "58.0"
        );

        when(loginResult.getMetadataServerUrl()).thenReturn("https://test.salesforce.com/metadata");
        when(loginResult.isPasswordExpired()).thenReturn(false);
        when(loginResult.isSandbox()).thenReturn(true);
        when(loginResult.getServerUrl()).thenReturn("https://test.salesforce.com");
        when(loginResult.getSessionId()).thenReturn("partner-session123");
        when(loginResult.getUserId()).thenReturn("partner-user123");

        PartnerLoginService service = new PartnerLoginService();
        SessionContext session = service.toSession(loginResult, credentials);

        assertNotNull(session);
        assertEquals("https://test.salesforce.com/metadata", session.medataServerUrl());
        assertEquals(false, session.isPasswordExpired());
        assertEquals(true, session.isSandbox());
        assertEquals("https://test.salesforce.com", session.serverUrl());
        assertEquals("partner-session123", session.sessionId());
        assertEquals("partner-user123", session.userId());
        assertEquals(ServiceEnum.PARTNER, session.service());
        assertEquals(credentials, session.credentials());
    }

    @Test
    void testLogin_WithPort_Success() throws Exception {
        Credentials credentials = new CredentialsRecord(
            "https://login.salesforce.com",
            "user@company.com",
            "password123",
            "token456",
            "58.0"
        );

        when(loginResult.getMetadataServerUrl()).thenReturn("https://login.salesforce.com/metadata");
        when(loginResult.isPasswordExpired()).thenReturn(false);
        when(loginResult.isSandbox()).thenReturn(false);
        when(loginResult.getServerUrl()).thenReturn("https://login.salesforce.com");
        when(loginResult.getSessionId()).thenReturn("partner-session789");
        when(loginResult.getUserId()).thenReturn("partner-user789");

        when(soapPort.login(anyString(), anyString())).thenReturn(loginResult);

        PartnerLoginService service = new PartnerLoginService();
        SessionContext session = service.login(soapPort, credentials);

        assertNotNull(session);
        assertEquals("partner-session789", session.sessionId());
        assertEquals("partner-user789", session.userId());
        assertEquals(ServiceEnum.PARTNER, session.service());
        verify(soapPort).login(credentials.username(), credentials.securityPassword());
    }

    @Test
    void testLogin_WithPort_ThrowsLoginException() throws Exception {
        Credentials credentials = new CredentialsRecord(
            "https://login.salesforce.com",
            "user@company.com",
            "password123",
            "token456",
            "58.0"
        );

        when(soapPort.login(anyString(), anyString())).thenThrow(new RuntimeException("Authentication failed"));

        PartnerLoginService service = new PartnerLoginService();

        LoginException exception = assertThrows(LoginException.class, () -> {
            service.login(soapPort, credentials);
        });

        assertEquals("Could not login using the Partner service", exception.getMessage());
        assertNotNull(exception.getCause());
    }

    @Test
    void testLogout_WithPort_Success() throws Exception {
        PartnerLoginService service = new PartnerLoginService();
        service.logout(soapPort);

        verify(soapPort).logout();
    }

    @Test
    void testLogout_WithPort_ThrowsLogoutException() throws Exception {
        doThrow(new RuntimeException("Logout failed")).when(soapPort).logout();

        PartnerLoginService service = new PartnerLoginService();

        LogoutException exception = assertThrows(LogoutException.class, () -> {
            service.logout(soapPort);
        });

        assertEquals("Logout trouble from the Partner service", exception.getMessage());
        assertNotNull(exception.getCause());
    }

    @Test
    void testLogout_WithSession_NoOp() {
        Credentials credentials = new CredentialsRecord(
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
            "partner-session123",
            "partner-user123",
            ServiceEnum.PARTNER,
            credentials
        );

        PartnerLoginService service = new PartnerLoginService();
        // This is currently a no-op, just verify it doesn't throw
        service.logout(session);
    }
}
