package org.solenopsis.session.soap.login;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.flossware.jcommons.util.SoapUtil;
import org.solenopsis.soap.SubUrlEnum;
import com.sforce.soap.enterprise.Soap;
import org.solenopsis.credentials.properties.FilePropertiesCredentials;

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
        
        LoginServiceEnum.ENTERPRISE.getLoginService().login(new FilePropertiesCredentials("/home/sfloess/.solenopsis/credentials/qa.properties"));

    }
}