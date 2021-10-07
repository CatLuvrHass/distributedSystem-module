import java.net.InetAddress;
import java.net.URL;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import quote.StockQuoteService;

public class Main {

    public static void main(String[] args) throws Exception {

        JmDNS jmDNS = JmDNS.create(InetAddress.getLocalHost());
        jmDNS.addServiceListener("_http._tcp.local.", new WSDLServiceListener());
    }

    public static class WSDLServiceListener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent event) {
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            String path = event.getInfo().getPropertyString("path");
            if (path != null) {
                String url =event.getInfo().getURLs()[0];
                connectToService(url);
            }
        }
    }

    private static void connectToService(String url) {
        try {
            URL wsdlUrl = new URL(url);
            QName serviceName = new QName("http://quote/", "StockQuoteService");
            Service service = Service.create(wsdlUrl, serviceName);

            QName portName = new QName("http://quote/", "StockQuotePort");
            StockQuoteService serviceQuote = service.getPort(portName, StockQuoteService.class);

            System.out.println("IBM: " + serviceQuote.getStockPrice("IBM"));
        } catch (Exception e) {
            System.out.println("Trouble: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
