package org.solenopsis.session.credentials;

import org.junit.jupiter.api.Test;
import org.solenopsis.session.Credentials;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for CredentialsRecord.
 */
class CredentialsRecordTest {

    @Test
    void testConstructorWithAllParameters() {
        CredentialsRecord creds = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password123",
            "token456",
            "58.0"
        );

        assertNotNull(creds);
        assertEquals("https://test.salesforce.com", creds.url());
        assertEquals("user@test.com", creds.username());
        assertEquals("password123", creds.password());
        assertEquals("token456", creds.token());
        assertEquals("58.0", creds.version());
    }

    @Test
    void testSecurityPassword() {
        CredentialsRecord creds = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "myPassword",
            "myToken",
            "58.0"
        );

        assertEquals("myPasswordmyToken", creds.securityPassword());
    }

    @Test
    void testSecurityPasswordEmptyToken() {
        CredentialsRecord creds = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password123",
            "",
            "58.0"
        );

        assertEquals("password123", creds.securityPassword());
    }

    @Test
    void testEquality() {
        CredentialsRecord creds1 = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password123",
            "token456",
            "58.0"
        );

        CredentialsRecord creds2 = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password123",
            "token456",
            "58.0"
        );

        assertEquals(creds1, creds2);
        assertEquals(creds1.hashCode(), creds2.hashCode());
    }

    @Test
    void testInequality() {
        CredentialsRecord creds1 = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password123",
            "token456",
            "58.0"
        );

        CredentialsRecord creds2 = new CredentialsRecord(
            "https://login.salesforce.com",
            "user@test.com",
            "password123",
            "token456",
            "58.0"
        );

        assertNotEquals(creds1, creds2);
    }

    @Test
    void testToString() {
        CredentialsRecord creds = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password123",
            "token456",
            "58.0"
        );

        String str = creds.toString();
        assertNotNull(str);
        assertTrue(str.contains("test.salesforce.com"));
        assertTrue(str.contains("user@test.com"));
        assertTrue(str.contains("58.0"));
    }

    @Test
    void testProductionCredentials() {
        CredentialsRecord creds = new CredentialsRecord(
            "https://login.salesforce.com",
            "prod@company.com",
            "prodPass123",
            "prodToken789",
            "59.0"
        );

        assertEquals("https://login.salesforce.com", creds.url());
        assertEquals("prod@company.com", creds.username());
        assertEquals("prodPass123prodToken789", creds.securityPassword());
        assertEquals("59.0", creds.version());
    }

    @Test
    void testSandboxCredentials() {
        CredentialsRecord creds = new CredentialsRecord(
            "https://test.salesforce.com",
            "sandbox@company.com.sandbox",
            "sandboxPass",
            "sandboxToken",
            "58.0"
        );

        assertEquals("https://test.salesforce.com", creds.url());
        assertEquals("sandbox@company.com.sandbox", creds.username());
        assertEquals("sandboxPasssandboxToken", creds.securityPassword());
    }

    @Test
    void testImplementsCredentialsInterface() {
        Credentials creds = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password",
            "token",
            "58.0"
        );

        assertNotNull(creds);
        assertEquals("https://test.salesforce.com", creds.url());
        assertEquals("user@test.com", creds.username());
    }
}
