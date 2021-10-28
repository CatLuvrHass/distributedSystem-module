package service.core;

import javax.jws.WebService;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;

import java.util.LinkedList;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;



@WebService
@SOAPBinding(style=Style.DOCUMENT, use=Use.LITERAL)
public class Broker{

	static LinkedList<String> urls = new LinkedList<>();

        public static void main(String[] args) throws IOException {
		
			//Publish broker 
			Endpoint.publish("http://0.0.0.0:9000/broker", new Broker());
			// create the jmDNS instance to lock onto host 
			JmDNS jmDNS = JmDNS.create(InetAddress.getLocalHost());
			// service listener to wait for the three companies
			jmDNS.addServiceListener("_http._tcp.local.", new WSDLServiceListener());
			
			//wait some time to allow for connection
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static class WSDLServiceListener implements ServiceListener {
		@Override
		public void serviceAdded(ServiceEvent event) {
			System.out.println("Service added: " + event.getInfo());
		}
	
		@Override
		public void serviceRemoved(ServiceEvent event) {
			System.out.println("Service removed: " + event.getInfo());
		}
	
		@Override
		public void serviceResolved(ServiceEvent event) {
			String path = event.getInfo().getPropertyString("path");
			if (path != null) {
				try{
					urls.add(path);
				} catch (Exception e) {
					System.out.println("Problem with service: " + e.getMessage());
					e.printStackTrace();
				}	
			}
		}
	}

	@WebMethod
	public LinkedList<Quotation> getQuotations(ClientInfo info) throws Exception {

		LinkedList<Quotation> quotations = new LinkedList<Quotation>();
		QuoterService quotationService;
		
		for (String url : urls) {
			URL wsdlUrl = new URL(url);
			QName serviceName = new QName("http://core.service/", "QuoterService");
			Service service = Service.create(wsdlUrl, serviceName);
			QName portName = new QName("http://core.service/", "QuoterPort");

        		quotationService = service.getPort(portName, QuoterService.class);

			quotations.add(quotationService.generateQuotation(info));
		}

		return quotations;
	}

	@WebService
	public interface QuoterService {
		@WebMethod Quotation generateQuotation(ClientInfo info);
	}
	    
}