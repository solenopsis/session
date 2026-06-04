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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.solenopsis.session.Credentials;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.credentials.CredentialsRecord;
import org.solenopsis.session.login.LoginException;
import org.solenopsis.session.login.LogoutException;
import org.solenopsis.soap.service.ServiceEnum;
import org.solenopsis.soap.tooling.LoginResult;
import org.solenopsis.soap.tooling.SforceServicePortType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for ToolingLoginService.
 */
@ExtendWith(MockitoExtension.class)
class ToolingLoginServiceTest {

    @Mock
    private SforceServicePortType soapPort;

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
        when(loginResult.getSessionId()).thenReturn("tooling-session123");
        when(loginResult.getUserId()).thenReturn("tooling-user123");

        ToolingLoginService service = new ToolingLoginService();
        SessionContext session = service.toSession(loginResult, credentials);

        assertNotNull(session);
        assertEquals("https://test.salesforce.com/metadata", session.medataServerUrl());
        assertEquals(false, session.isPasswordExpired());
        assertEquals(true, session.isSandbox());
        assertEquals("https://test.salesforce.com", session.serverUrl());
        assertEquals("tooling-session123", session.sessionId());
        assertEquals("tooling-user123", session.userId());
        assertEquals(ServiceEnum.TOOLING, session.service());
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
        when(loginResult.getSessionId()).thenReturn("tooling-session789");
        when(loginResult.getUserId()).thenReturn("tooling-user789");

        when(soapPort.login(anyString(), anyString())).thenReturn(loginResult);

        ToolingLoginService service = new ToolingLoginService();
        SessionContext session = service.login(soapPort, credentials);

        assertNotNull(session);
        assertEquals("tooling-session789", session.sessionId());
        assertEquals("tooling-user789", session.userId());
        assertEquals(ServiceEnum.TOOLING, session.service());
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

        ToolingLoginService service = new ToolingLoginService();

        LoginException exception = assertThrows(LoginException.class, () -> {
            service.login(soapPort, credentials);
        });

        assertEquals("Could not login using the Tooling service", exception.getMessage());
        assertNotNull(exception.getCause());
    }

    @Test
    void testLogout_WithPort_Success() throws Exception {
        ToolingLoginService service = new ToolingLoginService();
        service.logout(soapPort);

        verify(soapPort).logout();
    }

    @Test
    void testLogout_WithPort_ThrowsLogoutException() throws Exception {
        doThrow(new RuntimeException("Logout failed")).when(soapPort).logout();

        ToolingLoginService service = new ToolingLoginService();

        LogoutException exception = assertThrows(LogoutException.class, () -> {
            service.logout(soapPort);
        });

        assertEquals("Logout trouble from the Tooling service", exception.getMessage());
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
            "tooling-session123",
            "tooling-user123",
            ServiceEnum.TOOLING,
            credentials
        );

        ToolingLoginService service = new ToolingLoginService();
        // This is currently a no-op, just verify it doesn't throw
        service.logout(session);
    }
}
