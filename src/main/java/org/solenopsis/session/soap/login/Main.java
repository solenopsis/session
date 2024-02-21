package org.solenopsis.session.soap.login;


import com.sforce.soap.enterprise.SforceService;
import com.sforce.soap.enterprise.Soap;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.solenopsis.session.SessionContext;
import org.solenopsis.session.credentials.CredentialsUtil;
import org.solenopsis.session.soap.PortEnum;


/**
 *
 * @author sfloess
 */
public class Main {
    public static void oldMain(final String[] args) throws Exception {
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
        final SessionContext session = LoginServiceEnum.PARTNER.getLoginService().login(CredentialsUtil.fromFile("/home/sfloess/.solenopsis/credentials/qa.properties"));


        Soap s = PortEnum.ENTERPRISE.createPortForService(SforceService.class, session);
        s.query("select foo from bar");

        System.out.println("Done");
    }
}