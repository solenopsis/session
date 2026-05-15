package org.solenopsis.session;

import org.junit.jupiter.api.Test;
import org.solenopsis.session.credentials.CredentialsRecord;
import org.solenopsis.soap.service.ServiceEnum;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionContextTest {

    @Test
    void testConstructorWithAllParameters() {
        Credentials creds = new CredentialsRecord("https://test.salesforce.com", "user@test.com", "password123", "token", "58.0");

        SessionContext context = new SessionContext(
            "https://test.salesforce.com/services/Soap/m/58.0",
            false,
            true,
            "https://test.salesforce.com/services/Soap/u/58.0",
            "sessionId123",
            "userId123",
            ServiceEnum.METADATA,
            creds
        );

        assertNotNull(context);
        assertEquals("https://test.salesforce.com/services/Soap/m/58.0", context.medataServerUrl());
        assertFalse(context.isPasswordExpired());
        assertTrue(context.isSandbox());
        assertEquals("https://test.salesforce.com", context.serverUrl());  // URL normalized to host only
        assertEquals("sessionId123", context.sessionId());
        assertEquals("userId123", context.userId());
        assertEquals(ServiceEnum.METADATA, context.service());
        assertEquals(creds, context.credentials());
    }

    @Test
    void testCreateNewSessionContext() {
        Credentials creds = new CredentialsRecord("https://test.salesforce.com", "user@test.com", "password123", "token", "58.0");

        SessionContext original = new SessionContext(
            "https://test.salesforce.com/services/Soap/m/58.0",
            false,
            true,
            "https://test.salesforce.com/services/Soap/u/58.0",
            "oldSessionId",
            "userId123",
            ServiceEnum.METADATA,
            creds
        );

        SessionContext newContext = original.createNewSessionContext("newSessionId");

        assertNotNull(newContext);
        assertEquals("newSessionId", newContext.sessionId());
        // Other fields should be copied
        assertEquals(original.medataServerUrl(), newContext.medataServerUrl());
        assertEquals(original.isPasswordExpired(), newContext.isPasswordExpired());
        assertEquals(original.isSandbox(), newContext.isSandbox());
        assertEquals(original.serverUrl(), newContext.serverUrl());
        assertEquals(original.userId(), newContext.userId());
        assertEquals(original.service(), newContext.service());
        assertEquals(original.credentials(), newContext.credentials());
    }

    @Test
    void testServerUrlNormalization() {
        Credentials creds = new CredentialsRecord("https://test.salesforce.com", "user@test.com", "password123", "token", "58.0");

        // Full URL with path
        SessionContext context = new SessionContext(
            "https://test.salesforce.com/services/Soap/m/58.0",
            false,
            true,
            "https://test.salesforce.com/services/Soap/u/58.0/path",
            "sessionId123",
            "userId123",
            ServiceEnum.METADATA,
            creds
        );

        // Server URL should be normalized to just protocol and host
        assertEquals("https://test.salesforce.com", context.serverUrl());
    }

    @Test
    void testRecordEquality() {
        Credentials creds = new CredentialsRecord("https://test.salesforce.com", "user@test.com", "password123", "token", "58.0");

        SessionContext context1 = new SessionContext(
            "https://test.salesforce.com/services/Soap/m/58.0",
            false,
            true,
            "https://test.salesforce.com/services/Soap/u/58.0",
            "sessionId123",
            "userId123",
            ServiceEnum.METADATA,
            creds
        );

        SessionContext context2 = new SessionContext(
            "https://test.salesforce.com/services/Soap/m/58.0",
            false,
            true,
            "https://test.salesforce.com/services/Soap/u/58.0",
            "sessionId123",
            "userId123",
            ServiceEnum.METADATA,
            creds
        );

        assertEquals(context1, context2);
        assertEquals(context1.hashCode(), context2.hashCode());
    }

    @Test
    void testRecordInequality() {
        Credentials creds = new CredentialsRecord("https://test.salesforce.com", "user@test.com", "password123", "token", "58.0");

        SessionContext context1 = new SessionContext(
            "https://test.salesforce.com/services/Soap/m/58.0",
            false,
            true,
            "https://test.salesforce.com/services/Soap/u/58.0",
            "sessionId123",
            "userId123",
            ServiceEnum.METADATA,
            creds
        );

        SessionContext context2 = new SessionContext(
            "https://test.salesforce.com/services/Soap/m/58.0",
            false,
            true,
            "https://test.salesforce.com/services/Soap/u/58.0",
            "differentSessionId",  // Different session ID
            "userId123",
            ServiceEnum.METADATA,
            creds
        );

        assertNotEquals(context1, context2);
    }
}
