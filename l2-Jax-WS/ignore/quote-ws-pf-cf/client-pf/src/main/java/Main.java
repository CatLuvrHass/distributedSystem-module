import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import quote.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:9000/getQuote?wsdl");
        QName serviceName = new QName("http://quote/", "StockQuoteService");
        Service service = Service.create(url, serviceName);

        QName portName = new QName("http://quote/", "StockQuotePort");
        StockQuoteService serviceQuote = 
            service.getPort(portName, StockQuoteService.class);

        System.out.println("IBM: " + serviceQuote.getStockPrice("IBM"));
    }
}
