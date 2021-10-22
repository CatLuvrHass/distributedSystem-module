package service.broker;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import org.springframework.web.client.RestTemplate;

import service.core.ClientApplication;
import service.core.ClientInfo;
import service.core.Quotation;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 * @author edit by Hassan
 *
 */

@RestController
public class LocalBrokerService{

	// we need to get the quotations
	// map with client info an unique number

	private int id = 0;
	static Map<Integer, ClientApplication> applications = new HashMap<Integer, ClientApplication>();


	static String[] uris = {"http://localhost:8081/quotations","http://localhost:8082/quotations","http://localhost:8083/quotations"};

	@RequestMapping(value="/applications",method=RequestMethod.POST)
	public ResponseEntity<ClientApplication> createApplications(@RequestBody ClientInfo info) throws Exception, URISyntaxException {

		LinkedList<Quotation> quotesFromServices = getQuotations(info);

		// create instance of the client application for response entity
		ClientApplication clientApplication = new ClientApplication();
		clientApplication.setId(id);
		clientApplication.setClientInfo(info);
		clientApplication.setQuotations(quotesFromServices);

		applications.put(id++, clientApplication);

		String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+ "/applications/"+clientApplication.getId();

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(new URI(path));
		return new ResponseEntity<>(clientApplication, headers, HttpStatus.CREATED);

	}

	@RequestMapping(value="/applications/{reference}",method=RequestMethod.GET)
	public ClientApplication getResource(@PathVariable("reference") int id) {
	
		ClientApplication application = applications.get(id);
	
		if (application == null) throw new NoSuchQuotationException();
		return application;
	}

	@RequestMapping(value="/applications", method=RequestMethod.GET)
	public ArrayList<ClientApplication> listApplications() {
		
		// list of applications in an arraylist for client
		ArrayList<ClientApplication> listaApplications = new ArrayList<ClientApplication>();
		
		listaApplications.addAll(applications.values());
		return listaApplications;
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class NoSuchQuotationException extends RuntimeException {
		static final long serialVersionUID = -6516152229878843037L;
	}

	public LinkedList<Quotation> getQuotations(ClientInfo info) throws Exception {

		LinkedList<Quotation> quotations = new LinkedList<Quotation>();
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<ClientInfo> request = new HttpEntity<>(info);

		for (String uri : uris) {
			Quotation quotation = restTemplate.postForObject(uri, request, Quotation.class);
			quotations.add(quotation); 
		  }

		return quotations;
	}



}
