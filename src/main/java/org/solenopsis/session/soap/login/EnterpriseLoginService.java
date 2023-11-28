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
import org.solenopsis.session.Session;
import org.solenopsis.session.login.LoginException;
import org.solenopsis.session.login.LoginService;
import org.solenopsis.session.login.LogoutException;
import org.solenopsis.soap.enterprise.LoginResult;
import org.solenopsis.soap.enterprise.Soap;
import org.solenopsis.soap.port.factory.PortFactoryEnum;
import org.solenopsis.soap.SubUrlEnum;

/**
 * Uses the Enterprise service for login/logout.
 *
 * @author Scot P. Floess
 */
class EnterpriseLoginService implements LoginService {
    Session toSession(final LoginResult loginResult, final Credentials credentials) {
        return
            new Session(
                loginResult.getMetadataServerUrl(),
                loginResult.isPasswordExpired(),
                loginResult.isSandbox(),
                loginResult.getServerUrl(),
                SubUrlEnum.ENTERPRISE,
                loginResult.getSessionId(),
                loginResult.getUserId(),
                credentials
            );
    }

    Session login(final Soap port, final Credentials credentials) {
        try {
            return toSession(port.login(credentials.username(), credentials.password()), credentials);
        } catch (final Exception exception) {
            throw new LoginException("Could not login using the Enterprise service", exception);
        }
    }

    void logout(final Soap port) {
        try {
            port.logout();
        } catch(final Exception exception) {
            throw new LogoutException("Logout trouble from the Enterprise service", exception);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Session login(final Credentials credentials) {
        return login(PortFactoryEnum.ENTERPRISE.createPort(), credentials);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout(final Session session) {
//        logout(PortFactoryEnum.ENTERPRISE.createPort());
    }
}
