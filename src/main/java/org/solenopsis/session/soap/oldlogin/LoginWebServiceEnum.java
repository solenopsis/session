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
package org.solenopsis.session.soap.oldlogin;

import org.solenopsis.session.Credentials;
import org.solenopsis.session.Login;
import org.solenopsis.session.soap.port.ApiServiceEnum;
import org.solenopsis.session.soap.session.SessionPortFactory;
import org.solenopsis.session.soap.ApiService;
import org.solenopsis.session.soap.LoginService;

/**
 * Represents all login SOAP web service: enterprise, partner and tooling. Additionally provides the ability to create a usable
 * login port for logins.
 *
 * @author Scot P. Floess
 */
public enum LoginWebServiceEnum implements LoginService {
    ENTERPRISE_LOGIN_SERVICE(ApiServiceEnum.ENTERPRISE_SERVICE, LoginMgr.ENTERPRISE_LOGIN_MGR),
    PARTNER_LOGIN_SERVICE(ApiServiceEnum.PARTNER_SERVICE, LoginMgr.PARTNER_LOGIN_MGR),
    TOOLING_LOGIN_SERVICE(ApiServiceEnum.TOOLING_SERVICE, LoginMgr.TOOLING_LOGIN_MGR);

    /**
     * The actual web service type.
     */
    private final ApiService apiWebService;

    /**
     * The login mgr.
     */
    private final LoginMgr loginMgr;

    private LoginMgr getLoginMgr() {
        return loginMgr;
    }

    /**
     * This constructor sets the SFDC web service, port type and partial URL (as defined in the Java doc header).
     *
     * @param webService the SFDC web service.
     * @param portType   the port for the web service.
     */
    private LoginWebServiceEnum(final ApiService apiWebService, final LoginMgr loginMgr) {
        this.apiWebService = apiWebService;
        this.loginMgr = loginMgr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiService getApiWebService() {
        return apiWebService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Login login(final Credentials credentials) {
        return getLoginMgr().login(SoapUtils.createPort(getApiWebService().getService(), getApiWebService().getPortType(), getApiWebService().getServiceType().getSessionUrlFactory().computeUrl(credentials, getApiWebService().getService())), credentials);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout(final Login loginContext) {
        getLoginMgr().logout(SessionPortFactory.createSessionPort(getApiWebService(), loginContext));
    }
}
