import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.xml.ws.Endpoint;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import quote.StockQuoteService_StockQuotePortImpl;

public class Main {
    public static void main(String[] args) throws IOException {
        Endpoint endpoint = Endpoint.create(new StockQuoteService_StockQuotePortImpl());
        HttpServer server = HttpServer.create(new InetSocketAddress(9000), 5);
        server.setExecutor(Executors.newFixedThreadPool(5));
        HttpContext context = server.createContext("/getQuote");

        endpoint.publish(context);
        server.start();
   }
    
}
