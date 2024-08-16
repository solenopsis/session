/*
 * Copyright (C) 2015 Scot P. Floess
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

import org.solenopsis.session.Credentials;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.login.LoginException;
import org.solenopsis.session.login.LoginService;
import org.solenopsis.session.login.LogoutException;
import org.solenopsis.session.soap.SoapUrlEnum;
import org.solenopsis.soap.partner.LoginResult;
import org.solenopsis.soap.partner.Soap;
import org.solenopsis.soap.port.factory.PortFactoryEnum;
import org.solenopsis.soap.service.ServiceEnum;

/**
 * Uses the Enterprise service for login/logout.
 *
 * @author Scot P. Floess
 */
class PartnerLoginService implements LoginService {
    SessionContext toSession(final LoginResult loginResult, final Credentials credentials) {
        return
            new SessionContext(
                loginResult.getMetadataServerUrl(),
                loginResult.isPasswordExpired(),
                loginResult.isSandbox(),
                loginResult.getServerUrl(),
                loginResult.getSessionId(),
                loginResult.getUserId(),
                ServiceEnum.PARTNER,
                credentials
            );
    }

    SessionContext login(final Soap port, final Credentials credentials) {
        try {
            return toSession(port.login(credentials.username(), credentials.securityPassword()), credentials);
        } catch (final Exception exception) {
            throw new LoginException("Could not login using the Partner service", exception);
        }
    }

    void logout(final Soap port) {
        try {
            port.logout();
        } catch(final Exception exception) {
            throw new LogoutException("Logout trouble from the Partner service", exception);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionContext login(final Credentials credentials) {
        return login(PortFactoryEnum.PARTNER.createPort(credentials.url() + "/" + SoapUrlEnum.PARTNER.getPartialUrl() + "/" + credentials.version()), credentials);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout(final SessionContext session) {
//        logout(PortFactoryEnum.PARTNER.createPort());
    }
}
