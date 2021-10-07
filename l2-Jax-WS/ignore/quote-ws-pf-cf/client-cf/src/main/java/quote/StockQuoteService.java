
package quote;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "StockQuoteService", targetNamespace = "http://quote/", wsdlLocation = "http://localhost:9000/getQuote?wsdl")
public class StockQuoteService
    extends Service
{

    private final static URL STOCKQUOTESERVICE_WSDL_LOCATION;
    private final static WebServiceException STOCKQUOTESERVICE_EXCEPTION;
    private final static QName STOCKQUOTESERVICE_QNAME = new QName("http://quote/", "StockQuoteService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:9000/getQuote?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        STOCKQUOTESERVICE_WSDL_LOCATION = url;
        STOCKQUOTESERVICE_EXCEPTION = e;
    }

    public StockQuoteService() {
        super(__getWsdlLocation(), STOCKQUOTESERVICE_QNAME);
    }

    public StockQuoteService(WebServiceFeature... features) {
        super(__getWsdlLocation(), STOCKQUOTESERVICE_QNAME, features);
    }

    public StockQuoteService(URL wsdlLocation) {
        super(wsdlLocation, STOCKQUOTESERVICE_QNAME);
    }

    public StockQuoteService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, STOCKQUOTESERVICE_QNAME, features);
    }

    public StockQuoteService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public StockQuoteService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns StockQuote
     */
    @WebEndpoint(name = "StockQuotePort")
    public StockQuote getStockQuotePort() {
        return super.getPort(new QName("http://quote/", "StockQuotePort"), StockQuote.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StockQuote
     */
    @WebEndpoint(name = "StockQuotePort")
    public StockQuote getStockQuotePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://quote/", "StockQuotePort"), StockQuote.class, features);
    }

    private static URL __getWsdlLocation() {
        if (STOCKQUOTESERVICE_EXCEPTION!= null) {
            throw STOCKQUOTESERVICE_EXCEPTION;
        }
        return STOCKQUOTESERVICE_WSDL_LOCATION;
    }

}