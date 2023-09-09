package org.solenopsis.session.soap.api;

import org.apache.cxf.service.Service;
import org.solenopsis.session.Credentials;
import solenopsis.session.soap.LoginService;

/**
 *
 * @author sfloess
 */
abstract class AbstractService<P> implements ApiService<P> {

    protected AbstractService(final ServiceType serviceType, final Service service, final Class portType) {
        this.serviceType = serviceType;
        this.service = service;
        this.portType = portType;
    }

    @Override
    public ServiceType getServiceType() {
        return serviceType;
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public Class getPortType() {
        return portType;
    }

    @Override
    public P createPort(Credentials credentials, LoginService loginWebService) {
    }

    @Override
    public P createPort(Credentials credentials) {
    }

    @Override
    public P createProxyPort(Credentials credentials, LoginService loginWebService) {
    }

    @Override
    public P createProxyPort(Credentials credentials) {
    }
}
