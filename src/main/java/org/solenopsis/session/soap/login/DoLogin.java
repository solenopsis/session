package org.solenopsis.session.soap.login;

import com.sforce.soap.enterprise.Soap;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.solenopsis.session.credentials.CredentialsUtil;

/**
 *
 * @author sfloess
 */
public class DoLogin {
    public static void main(final String[] args) throws Exception {
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
        LoginServiceEnum.PARTNER.getLoginService().login(CredentialsUtil.fromFile("/home/sfloess/.solenopsis/credentials/qa.properties"));
//        LoginServiceEnum.TOOLING.getLoginService().login(CredentialsUtil.fromFile("/home/sfloess/.solenopsis/credentials/qa.properties"));

    }
}