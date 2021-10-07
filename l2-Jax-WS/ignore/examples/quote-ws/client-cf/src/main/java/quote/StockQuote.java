
package quote;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebService(name = "StockQuote", targetNamespace = "http://quote/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface StockQuote {


    /**
     * 
     * @param arg0
     * @return
     *     returns double
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://quote/StockQuote/getStockPriceRequest", output = "http://quote/StockQuote/getStockPriceResponse")
    public double getStockPrice(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0);

}
