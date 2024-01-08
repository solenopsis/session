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

import java.util.Objects;
import org.flossware.jcommons.AbstractBase;
import org.solenopsis.session.credentials.Credentials;
import org.solenopsis.session.login.LoginService;
import org.solenopsis.soap.port.factory.PortFactoryEnum;

/**
 * Uses the Enterprise service for login/logout.
 *
 * @author Scot P. Floess
 */
abstract class AbstractLoginService<T> extends AbstractBase implements LoginService {
    private final PortFactoryEnum portFactory;

    protected AbstractLoginService(final PortFactoryEnum portFactory) {
        this.portFactory = Objects.requireNonNull(portFactory, "Port factory cannot be null!");
    }

    /**
     * {@inheritDoc}
     */
    public void createPort(final Credentials credentials) {
        return login(portFactory.createPort(credentials.url() + "/" + "services/Soap/c/51.0"), credentials);
    }
}
