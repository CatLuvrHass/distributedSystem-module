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

	// All references are to be prefixed with an DD (e.g. DD001000)
	public static final String PREFIX = "GP";
	public static final String COMPANY = "Girl Power Inc.";
	
	public static void main(String[] args) {
		try{
			String host = "localhost";
			if (args.length > 0) {
				host = args[0];
			}
			//publish the quoter service
			Endpoint.publish("http://"+host+":9003/aQuote", new Quoter());

			//create the jmdns instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

			// register the service, hard coding host and port works
			ServiceInfo serviceInfo = ServiceInfo.create(
					"_http._tcp.local.", "gpq", 1234, "path=http://"+host+":9003/aQuote?wsdl"
			);
			// ServiceInfo serviceInfo = ServiceInfo.create(
			// 	"_http._tcp.local.", "ddq", 9003, "path=/getQuote?wsdl");
			jmdns.registerService(serviceInfo);

			// Wait a bit
			Thread.sleep(160000);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("Problem Advertising Service: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Quote generation:
	 * 50% discount for being female
	 * 20% discount for no penalty points
	 * 15% discount for < 3 penalty points
	 * no discount for 3-5 penalty points
	 * 100% penalty for > 5 penalty points
	 * 5% discount per year no claims
	 */
	@WebMethod
        public Quotation generateQuotation(ClientInfo info) {
		// Create an initial quotation between 600 and 1000
		double price = generatePrice(600, 400);
		
		// Automatic 50% discount for being female
		int discount = (info.gender == ClientInfo.FEMALE) ? 50:0;
		
		// Add a points discount
		discount += getPointsDiscount(info);
		
		// Add a no claims discount
		discount += getNoClaimsDiscount(info);
		
		// Generate the quotation and send it back
		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);
	}

	@WebMethod
	private int getPointsDiscount(ClientInfo info) {
		if (info.points == 0) return 20;
		if (info.points < 3) return 15;
		if (info.points < 6) return 0;
		return -100;
		
	}
        @WebMethod
        private int getNoClaimsDiscount(ClientInfo info) {
		return 5*info.noClaims;
	}
}
