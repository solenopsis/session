/*[WARNING] WARNING: file:/home/sfloess/Development/github/solenopsis/Session/src/main/resources/wsdl/Session-enterprise.wsdl [36177,13]: Simple type "ExceptionCode" was not mapped to Enum due to EnumMemberSizeCap limit. Facets count: 263, current limit: 256. You can use customization attribute "typesafeEnumMaxMembers" to extend the limit.
 * Copyright (C) 2023 Scot P. Floess
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
package solenopsis.session.soap;

import org.apache.cxf.service.Service;
import org.solenopsis.session.Credentials;

/**
 * This interface denotes the built in API SFDC web services.
 *
 * @author Scot P. Floess
 */
public interface ApiService {
    /**
     * Return the web service type.
     *
     * @return the web service.
     */
    ServiceType getServiceType();

    /**
     * Return the SFDC web service.
     *
     * @return the SFDC web service.
     */
    Service getService();

    /**
     * Return the port for the web service.
     *
     * @return the port for the web service.
     */
    Class getPortType();

    /**
     * Will create a proxy port using the API services.
     *
     * @param <P>             the type of port to create.
     *
     * @param credentials     are the credentials to use when creating the API proxy port.
     * @param loginWebService used for logins and session ids.
     *
     * @return a proxy port
     */
    <P> P createProxyPort(Credentials credentials, LoginService loginWebService);

    /**
     * Will create a proxy port using the API services.
     *
     * @param <P>         the type of port to create.
     *
     * @param credentials are the credentials to use when creating the API proxy port.
     *
     * @return a proxy port
     */
    <P> P createProxyPort(Credentials credentials);
}
