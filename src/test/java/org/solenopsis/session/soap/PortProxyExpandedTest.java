package org.solenopsis.session.soap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.solenopsis.session.Credentials;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.credentials.CredentialsRecord;
import org.solenopsis.session.soap.login.LoginServiceEnum;
import org.solenopsis.soap.partner.SforceService;
import org.solenopsis.soap.partner.Soap;
import org.solenopsis.soap.service.ServiceEnum;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Additional tests for PortProxy class to increase coverage.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PortProxyExpandedTest {

    @Mock
    Soap soapMock;

    @Test
    public void testConstructor_WithAllParameters() {
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
            "session123",
            "user123",
            ServiceEnum.PARTNER,
            credentials
        );

        PortProxy proxy = new PortProxy(
            PortEnum.PARTNER,
            SforceService.class,
            soapMock,
            session,
            LoginServiceEnum.PARTNER
        );

        assertNotNull(proxy);
    }

    @Test
    public void testConstructor_WithDefaultLoginService() {
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
            "session123",
            "user123",
            ServiceEnum.PARTNER,
            credentials
        );

        PortProxy proxy = new PortProxy(
            PortEnum.PARTNER,
            SforceService.class,
            soapMock,
            session
        );

        assertNotNull(proxy);
    }

    @Test
    public void testConstructor_WithNullPortEnum() {
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
            "session123",
            "user123",
            ServiceEnum.PARTNER,
            credentials
        );

        assertThrows(NullPointerException.class, () -> {
            new PortProxy(
                null,
                SforceService.class,
                soapMock,
                session,
                LoginServiceEnum.PARTNER
            );
        });
    }

    @Test
    public void testConstructor_WithNullServiceClass() {
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
            "session123",
            "user123",
            ServiceEnum.PARTNER,
            credentials
        );

        assertThrows(NullPointerException.class, () -> {
            new PortProxy(
                PortEnum.PARTNER,
                null,
                soapMock,
                session,
                LoginServiceEnum.PARTNER
            );
        });
    }

    @Test
    public void testConstructor_WithNullProxy() {
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
            "session123",
            "user123",
            ServiceEnum.PARTNER,
            credentials
        );

        assertThrows(NullPointerException.class, () -> {
            new PortProxy(
                PortEnum.PARTNER,
                SforceService.class,
                null,
                session,
                LoginServiceEnum.PARTNER
            );
        });
    }

    @Test
    public void testConstructor_WithNullSession() {
        assertThrows(NullPointerException.class, () -> {
            new PortProxy(
                PortEnum.PARTNER,
                SforceService.class,
                soapMock,
                (SessionContext) null,
                LoginServiceEnum.PARTNER
            );
        });
    }

    @Test
    public void testConstructor_WithNullLoginService() {
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
            "session123",
            "user123",
            ServiceEnum.PARTNER,
            credentials
        );

        assertThrows(NullPointerException.class, () -> {
            new PortProxy(
                PortEnum.PARTNER,
                SforceService.class,
                soapMock,
                session,
                null
            );
        });
    }

    @Test
    public void testInvoke_SuccessfulCall() throws Throwable {
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
            "session123",
            "user123",
            ServiceEnum.PARTNER,
            credentials
        );

        when(soapMock.getUserInfo()).thenReturn(null);

        PortProxy proxy = new PortProxy(
            PortEnum.PARTNER,
            SforceService.class,
            soapMock,
            session
        );

        proxy.invoke(soapMock, Soap.class.getMethod("getUserInfo"), new Object[0]);
    }

    @Test
    public void testHandleException() throws Throwable {
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
            "session123",
            "user123",
            ServiceEnum.PARTNER,
            credentials
        );

        PortProxy proxy = new PortProxy(
            PortEnum.PARTNER,
            SforceService.class,
            soapMock,
            session,
            LoginServiceEnum.PARTNER
        );

        // handleException is a no-op method, just call it for coverage
        proxy.handleException(
            Soap.class.getMethod("getUserInfo"),
            new Object[0],
            new java.lang.reflect.InvocationTargetException(new RuntimeException("test"))
        );
    }
}
