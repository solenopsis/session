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
package org.solenopsis.session.soap.login;


import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.credentials.CredentialsUtil;
import org.solenopsis.session.soap.ProxyPortEnum;
import org.solenopsis.soap.enterprise.SforceService;
import org.solenopsis.soap.enterprise.Soap;


/**
 *
 * @author sfloess
 */
public class Main {
    public static void main01(final String[] args) throws Exception {
//        System.out.println("QName = " + SoapUtil.computeQName(new SforceServiceService().getClass()));
//        System.out.println("QName = " + SoapUtil.computeQName(SforceServiceService.class));
//        System.out.println((Object) LoginServiceEnum.ENTERPRISE.getLoginPortFactory().createPort("https://test.salesforce.com"));
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(Soap.class);
//        System.out.println((Soap) factory.create());
//        Soap soap = (Soap) factory.create();
//        SoapUtil.setUrl(soap, "https://test.salesforce.com/" + SubUrlEnum.ENTERPRISE.getPartialUrl() + "/53.0");
//
//        soap.login(username, password);

//        Soap soap = LoginServiceEnum.ENTERPRISE.getLoginPortFactory().createPort("https://test.salesforce.com");

//        LoginServiceEnum.ENTERPRISE.getLoginService().login(CredentialsUtil.fromFile("/home/sfloess/.solenopsis/credentials/qa.properties"));
        final SessionContext session = LoginServiceEnum.PARTNER.getLoginService().login(CredentialsUtil.fromFile("/home/sfloess/.solenopsis/credentials/qa.properties"));
//        LoginServiceEnum.TOOLING.getLoginService().login(CredentialsUtil.fromFile("/home/sfloess/.solenopsis/credentials/qa.properties"));

    }

    public static void main(final String[] args) throws Exception {
        final SessionContext session = LoginServiceEnum.TOOLING.getLoginService().login(CredentialsUtil.fromFile("/home/sfloess/.solenopsis/credentials/qa.properties"));


//        SforceServicePortType port = PortEnum.TOOLING.TOOLING.createPortForService(SforceServiceService.class, session);
        Soap port = ProxyPortEnum.ENTERPRISE.createProxyPortForService(SforceService.class, session);

//                .createPortForService(SforceService.class, session);
        port.query("select foo from bar");

        System.out.println("Done");
    }


//    public static void main(final String[] args) throws Exception {
//        final SessionContext session = LoginServiceEnum.ENTERPRISE.getLoginService().login(CredentialsUtil.fromFile("/home/sfloess/.solenopsis/credentials/qa.properties"));
//
//
//        Soap s = PortEnum.ENTERPRISE.createPortForService(SforceService.class, session);
//        s.query("select foo from bar");
//
//        System.out.println("Done");
//    }
}