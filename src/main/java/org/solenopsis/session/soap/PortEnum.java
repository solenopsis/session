/*
 * Copyright (C) 2023-2026 Scot P. Floess
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

import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.headers.Header;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.flossware.jcommons.util.MethodUtil;
import org.flossware.jcommons.util.SoapException;
import org.flossware.jcommons.util.SoapUtil;
import org.solenopsis.session.Credentials;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.soap.login.LoginServiceEnum;

/**
 *
 * @author sfloess
 */
public enum PortEnum {
    APEX(SoapUrlEnum.APEX),
    CUSTOM(SoapUrlEnum.CUSTOM),
    ENTERPRISE(SoapUrlEnum.ENTERPRISE),
    METADATA(SoapUrlEnum.METADATA),
    PARTNER(SoapUrlEnum.PARTNER),
    TOOLING(SoapUrlEnum.TOOLING);

    private final SoapUrlEnum url;

    private PortEnum(final SoapUrlEnum url) {
        this.url = url;
    }

    SoapUrlEnum getUrl() {
        return url;
    }

    <P> P createPortForTargetNamespace(final String targetNamespace, final Class<? extends Service> serviceClass, final SessionContext session) throws SoapException {
        try {
            final var methods = MethodUtil.findMethodsForAnnotationClass(serviceClass, WebEndpoint.class);
            if (methods.isEmpty()) {
                throw new SoapException("No methods annotated with @WebEndpoint found on " + serviceClass.getName());
            }

            final P retVal = (P) new JaxWsProxyFactoryBean().create(methods.get(0).getReturnType());

            final SOAPElement sessionId = SoapUtil.getSoapFactory().createElement(new QName(targetNamespace, SessionHeaderEnum.SESSION_ID.getString()));
            sessionId.addTextNode(session.sessionId());

            final SOAPElement sessionHeader = SoapUtil.getSoapFactory().createElement(new QName(targetNamespace, SessionHeaderEnum.SESSION_HEADER.getString()));
            sessionHeader.addChildElement(sessionId);

            ((BindingProvider) retVal).getRequestContext().put(
                Header.HEADER_LIST,
                List.of(new Header(new QName(targetNamespace, SessionHeaderEnum.SESSION_HEADER.getString()), sessionHeader))
            );

            return SoapUtil.setUrl(retVal, getUrl().computeUrl(serviceClass, session));
        } catch (final SOAPException soapException) {
            throw new SoapException(soapException);
        }
    }

    <P> P createPortForTargetNamespace(final String targetNamespace, final Class<? extends Service> serviceClass, final Credentials credentials, final LoginServiceEnum loginService) throws SoapException {
        return createPortForTargetNamespace(targetNamespace, serviceClass, loginService.getLoginService().login(credentials));
    }

    <P> P createPortForTargetNamespace(final String targetNamespace, final Class<? extends Service> serviceClass, final Credentials creds) throws SoapException {
        return createPortForTargetNamespace(targetNamespace, serviceClass, creds, LoginServiceEnum.DEFAULT_LOGIN_SERVICE);
    }

    <P> P createPortForAnnotation(final WebServiceClient webServiceClientAnnotation, final Class<? extends Service> serviceClass, final SessionContext session) {
        return createPortForTargetNamespace(webServiceClientAnnotation.targetNamespace(), serviceClass, session);
    }

    <P> P createPortForAnnotation(final WebServiceClient webServiceClientAnnotation, final Class<? extends Service> serviceClass, final Credentials credentials, final LoginServiceEnum loginService) {
        return createPortForTargetNamespace(webServiceClientAnnotation.targetNamespace(), serviceClass, loginService.getLoginService().login(credentials));
    }

    public <P> P createPortForService(final Class<? extends Service> serviceClass, final SessionContext session, final LoginServiceEnum loginService) {
        return createPortForAnnotation(serviceClass.getAnnotation(WebServiceClient.class), serviceClass, session.credentials(), loginService);
    }

    public <P> P createPortForService(final Class<? extends Service> serviceClass, final SessionContext session) {
        return createPortForService(serviceClass, session, LoginServiceEnum.DEFAULT_LOGIN_SERVICE);
    }

    public <P> P createPortForService(final Class<? extends Service> serviceClass, final Credentials credentials, final LoginServiceEnum loginService) {
        return createPortForAnnotation(serviceClass.getAnnotation(WebServiceClient.class), serviceClass, credentials, loginService);
    }

    public <P> P createPortForService(final Class<? extends Service> serviceClass, final Credentials credentials) {
        return createPortForService(serviceClass, credentials, LoginServiceEnum.DEFAULT_LOGIN_SERVICE);
    }
}
