package service.core;

import javax.jws.WebService;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.ws.Endpoint;

@WebService
@SOAPBinding(style=Style.RPC, use=Use.LITERAL)
public class Quoter extends AbstractQuotationService {
	
	// All references are to be prefixed with an AF (e.g. AF001000)
	public static final String PREFIX = "AF";
	public static final String COMPANY = "Auld Fellas Ltd.";
	public static void main(String[] args) {

		try{
			String host = "localhost";
			if (args.length > 0) {
				host = args[0];
			}
			//publish the quoter service
			Endpoint.publish("http://"+host+":9001/aQuote", new Quoter());

			//create the jmdns instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

			// register the service, hard coding host and port works 
			ServiceInfo serviceInfo = ServiceInfo.create(
				"_http._tcp.local.", "afq", 1234, "path=http://"+host+":9001/aQuote?wsdl"
			);
			//register
			jmdns.registerService(serviceInfo);     
			
			// Wait a bit
			Thread.sleep(8000);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("Problem Advertising Service: " + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * Quote generation:
	 * 30% discount for being male
	 * 2% discount per year over 60
	 * 20% discount for less than 3 penalty points
	 * 50% penalty (i.e. reduction in discount) for more than 60 penalty points 
	 */

	@WebMethod
	public Quotation generateQuotation(ClientInfo info) {
		// Create an initial quotation between 600 and 1200
		double price = generatePrice(600, 600);
		
		// Automatic 30% discount for being male
		int discount = (info.gender == service.core.ClientInfo.MALE) ? 30:0;
		
		// Automatic 2% discount per year over 60...
		discount += (info.age > 60) ? (2*(info.age-60)) : 0;
		
		// Add a points discount
		discount += getPointsDiscount(info);
		
		// Generate the quotation and send it back
		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);
	}
	@WebMethod
	private int getPointsDiscount(ClientInfo info) {
		if (info.points < 3) return 20;
		if (info.points <= 6) return 0;
		return -50;
		
	}

}
