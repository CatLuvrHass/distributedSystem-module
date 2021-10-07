package quote;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.Endpoint;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

@WebService
@SOAPBinding(style = Style.RPC, use = Use.LITERAL)
public class StockPrice {
    public static void main(String[] args) throws IOException {
        Endpoint endpoint = Endpoint.create(new StockPrice());
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 5);
        server.setExecutor(Executors.newFixedThreadPool(5));
        HttpContext context = server.createContext("/quote");

        endpoint.publish(context);
        server.start();
    }

    private Map<String, Double> prices = new TreeMap<>();
    {
        prices.put("IBM", 143.79);
        prices.put("GOOGL", 1209.70);
        prices.put("MSFT", 137.44);
        prices.put("FB", 175.25);
        prices.put("TWTR", 40.22);
    }

    @WebMethod
    public double GetStockQuote(String StockName) {
        Double price = prices.get(StockName);
        return price == null ? -1 : price;
    }
}
