package org.solenopsis.session;

import org.junit.jupiter.api.Test;
import org.solenopsis.session.credentials.CredentialsRecord;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CredentialsTest {

    @Test
    void testConstructorWithAllParameters() {
        Credentials creds = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password123",
            "token456",
            "58.0"
        );

        assertNotNull(creds);
        assertEquals("https://test.salesforce.com", creds.url());
        assertEquals("58.0", creds.version());
        assertEquals("user@test.com", creds.username());
        assertEquals("password123", creds.password());
        assertEquals("token456", creds.token());
    }

    @Test
    void testSecurityPassword() {
        Credentials creds = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password123",
            "token456",
            "58.0"
        );

        // securityPassword() concatenates password + token
        assertEquals("password123token456", creds.securityPassword());
    }

    @Test
    void testRecordEquality() {
        Credentials creds1 = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password123",
            "token456",
            "58.0"
        );

        Credentials creds2 = new CredentialsRecord(
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
    void testRecordInequality() {
        Credentials creds1 = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password123",
            "token456",
            "58.0"
        );

        Credentials creds2 = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password123",
            "token456",
            "59.0"  // Different API version
        );

        assertNotEquals(creds1, creds2);
    }

    @Test
    void testToString() {
        Credentials creds = new CredentialsRecord(
            "https://test.salesforce.com",
            "user@test.com",
            "password123",
            "token456",
            "58.0"
        );

        String str = creds.toString();
        assertNotNull(str);
        // Records have automatic toString()
        assertTrue(str.contains("test.salesforce.com"));
        assertTrue(str.contains("58.0"));
        assertTrue(str.contains("user@test.com"));
    }

    @Test
    void testProductionUrl() {
        Credentials creds = new CredentialsRecord(
            "https://login.salesforce.com",
            "prod@company.com",
            "prodPassword123",
            "prodToken789",
            "58.0"
        );

        assertEquals("https://login.salesforce.com", creds.url());
        assertEquals("prodPassword123prodToken789", creds.securityPassword());
    }

    @Test
    void testSandboxUrl() {
        Credentials creds = new CredentialsRecord(
            "https://test.salesforce.com",
            "sandbox@company.com.dev",
            "sandboxPassword123",
            "sandboxToken789",
            "58.0"
        );

        assertEquals("https://test.salesforce.com", creds.url());
        assertEquals("sandboxPassword123sandboxToken789", creds.securityPassword());
    }
}

