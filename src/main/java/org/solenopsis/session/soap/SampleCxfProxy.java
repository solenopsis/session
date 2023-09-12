package org.solenopsis.session.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.headers.Header;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 *
 * @author sfloess
 */
public class SampleCxfProxy {
    public static void main(final String[] args) throws Exception {
        /* Create a ClientProxyFactoryBean reference and assign it an instance of
         * JaxWsProxyFactoryBean, a factory for creating JAX-WS proxies. This
         * class provides access to the internal properties used to set-up
         * proxies. Using it provides more control than the standard JAX-WS APIs.
         */

        ClientProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(singz.ws.cxf.sample.SampleServiceInterface.class);

        // Set the web service endpoint URL here
        factory.setAddress("http://xxx.xxx.com/services/SampleService/v1");

        SampleServiceInterface serviceClient = (SampleServiceInterface) factory.create();

        // Get the underlying Client object from the proxy object of service interface
        Client proxy = ClientProxy.getClient(serviceClient);

        // Creating SOAP headers to the web service request

        // Create a list for holding all SOAP headers
        List<Header> headersList = new ArrayList<Header>();

        Header testSoapHeader1 = new Header(new QName("uri:singz.ws.sample", "soapheader1"), "SOAP Header Message 1", new JAXBDataBinding(String.class));
        Header testSoapHeader2 = new Header(new QName("uri:singz.ws.sample", "soapheader2"), "SOAP Header Message 2", new JAXBDataBinding(String.class));

        headersList.add(testSoapHeader1);
        headersList.add(testSoapHeader2);

        // Add SOAP headers to the web service request
        proxy.getRequestContext().put(Header.HEADER_LIST, headersList);
        }
}
