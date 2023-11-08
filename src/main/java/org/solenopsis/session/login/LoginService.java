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
package org.solenopsis.session.login;

import org.solenopsis.session.Credentials;
import org.solenopsis.session.Session;

/**
 * Represents a way to login and out of SFDC for the enterprise, partner and tooling web services. Additionally, maintains a session
 * that can be shared (session id).
 *
 * @author Scot P. Floess
 */
public interface LoginService {
    /**
     * Request a new login.
     *
     * @param credentials the credentials to use for login - this includes the url to call, the API version, username and
     *                    password/token.
     *
     * @return a login result from SFDC.
     */
    Session login(Credentials credentials);

    /**
     * Force a logout.
     *
     * @param session contains the session id needed when calling out to SFDC
     *                to logout.
     */
    void logout(Session session);
}
