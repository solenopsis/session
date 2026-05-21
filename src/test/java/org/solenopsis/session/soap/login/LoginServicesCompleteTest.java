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

    @Test
    void testEnterpriseLoginService_PublicLogout() {
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
            false,
            "https://test.salesforce.com",
            "session123",
            "user123",
            ServiceEnum.ENTERPRISE,
            credentials
        );

        EnterpriseLoginService service = new EnterpriseLoginService();
        // Public logout is currently a no-op
        service.logout(session);
    }

    @Test
    void testPartnerLoginService_PublicLogout() {
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
            false,
            "https://test.salesforce.com",
            "session123",
            "user123",
            ServiceEnum.PARTNER,
            credentials
        );

        PartnerLoginService service = new PartnerLoginService();
        // Public logout is currently a no-op
        service.logout(session);
    }

    @Test
    void testToolingLoginService_PublicLogout() {
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
            false,
            "https://test.salesforce.com",
            "session123",
            "user123",
            ServiceEnum.TOOLING,
            credentials
        );

        ToolingLoginService service = new ToolingLoginService();
        // Public logout is currently a no-op
        service.logout(session);
    }
}
