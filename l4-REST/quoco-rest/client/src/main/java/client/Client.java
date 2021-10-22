package client;


import org.springframework.web.client.RestTemplate;

import service.core.*;

import java.text.NumberFormat;

import org.springframework.http.HttpEntity;

public class Client {

      public static void main(String[] args) {

		// for(ClientApplication application: applications)
		ClientApplication clientApplication = new ClientApplication();
		for(ClientInfo client: clients){
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity <ClientInfo> request = new HttpEntity<>(client);
			clientApplication =
				  restTemplate.postForObject("http://localhost:8888/applications",
					    request, ClientApplication.class);
			displayProfile(client);
			for(Quotation quotation: clientApplication.getQuotations())
			displayQuotation(quotation);
		}

		
      }

	/**
	 * Display the client info nicely.
	 * 
	 * @param info
	 */
	public static void displayProfile(ClientInfo info) {
		System.out.println("|=================================================================================================================|");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println(
				"| Name: " + String.format("%1$-29s", info.getName()) + 
				" | Gender: " + String.format("%1$-27s", (info.getGender()==ClientInfo.MALE?"Male":"Female")) +
				" | Age: " + String.format("%1$-30s", info.getAge())+" |");
		System.out.println(
				"| License Number: " + String.format("%1$-19s", info.getLicenseNumber()) + 
				" | No Claims: " + String.format("%1$-24s", info.getClaims()+" years") +
				" | Penalty Points: " + String.format("%1$-19s", info.getPoints())+" |");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println("|=================================================================================================================|");
	}

	/**
	 * Display a quotation nicely - note that the assumption is that the quotation will follow
	 * immediately after the profile (so the top of the quotation box is missing).
	 * 
	 * @param quotation
	 */
	public static void displayQuotation(Quotation quotation) {
		System.out.println(
				"| Company: " + String.format("%1$-26s", quotation.getCompany()) + 
				" | Reference: " + String.format("%1$-24s", quotation.getReference()) +
				" | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.getprice()))+" |");
		System.out.println("|=================================================================================================================|");
	}
	
	/**
	 * Test Data
	 */
	public static final ClientInfo[] clients = {
		new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1"),
		new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 0, 2, "ABC123/4"),
		new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 16, 10, 0, "HMA304/9"),
		new ClientInfo("Rem Collier", ClientInfo.MALE, 44, 5, 3, "COL123/3"),
		new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 4, 7, "QUN987/4"),
		new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 5, 2, "XYZ567/9")		
	};
}
