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
package solenopsis.session.soap.port;

import com.sforce.soap.enterprise.SforceService;
import com.sforce.soap.enterprise.Soap;
import org.apache.cxf.service.Service;
import org.solenopsis.session.Credentials;
import solenopsis.session.soap.ApiService;
import solenopsis.session.soap.LoginService;
import solenopsis.session.soap.ServiceType;

/**
 * This enum denotes the built in SFDC API web services.
 *
 * @author Scot P. Floess
 */
public enum ApiServiceEnum implements ApiService {
    APEX_SERVICE(ServiceTypeEnum.APEX_SERVICE_TYPE, new ApexService(ApiServiceEnum.class.getClassLoader().getResource("wsdl/Session-apex.wsdl")), ApexPortType.class),
    ENTERPRISE_SERVICE(ServiceTypeEnum.ENTERPRISE_SERVICE_TYPE, new SforceService(ApiServiceEnum.class.getClassLoader().getResource("wsdl/Session-enterprise.wsdl")), Soap.class),
    METADATA_SERVICE(ServiceTypeEnum.METADATA_SERVICE_TYPE, new MetadataService(ApiServiceEnum.class.getClassLoader().getResource("wsdl/Session-metadata.wsdl")), MetadataPortType.class),
    PARTNER_SERVICE(ServiceTypeEnum.PARTNER_SERVICE_TYPE, new org.solenopsis.keraiai.wsdl.partner.SforceService(ApiServiceEnum.class.getClassLoader().getResource("wsdl/Keraiai-partner.wsdl")), org.solenopsis.keraiai.wsdl.partner.Soap.class),
    TOOLING_SERVICE(ServiceTypeEnum.TOOLING_SERVICE_TYPE, new SforceServiceService(ApiServiceEnum.class.getClassLoader().getResource("wsdl/Session-tooling.wsdl")), SforceServicePortType.class);

    /**
     * The web service type.
     */
    private final ServiceType webServiceType;

    /**
     * The SFDC web service.
     */
    private final Service service;

    /**
     * The port for the web service.
     */
    private final Class portType;

    /**
     * This constructor sets the SFDC web service, port type and partial URL (as defined in the Java doc header).
     *
     * @param service  the SFDC web service.
     * @param portType the port for the web service.
     */
    private ApiServiceEnum(final ServiceType webServiceType, final Service service, final Class portType) {
        this.webServiceType = webServiceType;
        this.service = service;
        this.portType = portType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceType getServiceType() {
        return webServiceType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Service getService() {
        return service;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class getPortType() {
        return portType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <P> P createProxyPort(Credentials credentials, LoginService loginWebService) {
        return (P) getServiceType().createProxyPort(credentials, loginWebService, getService(), getPortType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <P> P createProxyPort(final Credentials credentials) {
        return (P) getServiceType().createProxyPort(credentials, getService(), getPortType());
    }
}
