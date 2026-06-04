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

import jakarta.xml.ws.WebServiceException;
import java.lang.reflect.InvocationTargetException;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.solenopsis.session.Credentials;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.credentials.CredentialsRecord;
import org.solenopsis.session.login.LoginService;
import org.solenopsis.session.soap.login.LoginServiceEnum;
import org.solenopsis.session.soap.util.SoapFailureMsgEnum;
import org.solenopsis.soap.partner.SforceService;
import org.solenopsis.soap.partner.Soap;
import org.solenopsis.soap.partner.UnexpectedErrorFault_Exception;
import org.solenopsis.soap.service.ServiceEnum;

/**
 * Test the PortProxy class.
 *
 * @author sfloess
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PortProxyTest {
    @Mock
    Soap soap;

    @Mock
    LoginServiceEnum loginServiceEnum;

    @Mock
    LoginService loginService;

    Credentials credentials;

    SessionContext session;

    // Tests relogins
    @Test
    public void test_invoke_invalidSessionId() throws Throwable {
        credentials = new CredentialsRecord("http://foobar.com", "user", "password", "token", "75.0");

        session = new SessionContext("http://meta/data/url", false, false, "http://foobar.com", "1234567890", "user", ServiceEnum.ENTERPRISE, credentials);

        when(loginServiceEnum.getLoginService()).thenReturn(loginService);
        when(loginService.login(any(Credentials.class))).thenReturn(session.createNewSessionContext("0987654321"));

        // Forcing a new port for calls to make.
        when(soap.describeAllTabs()).thenThrow(new UnexpectedErrorFault_Exception(SoapFailureMsgEnum.INVALID_SESSION_ID.getMsg()));

        final PortProxy proxy = new PortProxy(PortEnum.PARTNER, SforceService.class, soap, session, loginServiceEnum);

        // Login should not be called...yet.
        verify(loginService, times(0)).login(any(CredentialsRecord.class));


        // Little kludgy as the created port will be used to attempt a web service
        // call.
        final Throwable throwable = assertThrows(InvocationTargetException.class, () -> proxy.invoke(soap, Soap.class.getMethod("describeAllTabs", new Class[0]), new Object[0]));

        // The failed web service call.
        assertSame(WebServiceException.class, throwable.getCause().getClass());

        // Make sure we've only tried login once.
        verify(loginService, times(1)).login(any(CredentialsRecord.class));

        // Should only be called once.
        verify(soap, times(1)).describeAllTabs();
    }
}
