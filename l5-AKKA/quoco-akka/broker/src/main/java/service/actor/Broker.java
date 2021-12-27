package service.actor;

import akka.actor.*;
import scala.concurrent.duration.Duration;
import service.core.ClientInfo;
import service.core.Quotation;
import service.messages.*;
import service.messages.QuotationResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Broker extends AbstractActor {
      private static int SEED_ID = 0;
      private static ActorRef clientActor;
      private ArrayList<ActorRef> actorRefs = new ArrayList<>();
      private static HashMap<Integer, ArrayList<Quotation>> cache = new HashMap<>();
      private static HashMap<Integer, ClientInfo> clientsMap = new HashMap<>();

      public static void main(String[] args) {
            //start broker
            ActorSystem system = ActorSystem.create();
            system.actorOf(Props.create(Broker.class), "broker");
      }

      @Override
      public Receive createReceive() {
      return receiveBuilder().match(String.class,
      msg -> {

            if (msg.equals("register")){
                  actorRefs.add(getSender());
            }else if(msg.equals("client_register")){
                  clientActor = getSender();
            }

            })

            .match(ApplicationRequest.class,
            msg -> {
                  // Listen for ApplicationRequest from client and publish to services
                  // Increment seed id for new application
                  // Store this id and the client in a hash map
                  ClientInfo client = msg.getClientInfo();

                  clientsMap.put(SEED_ID, client);
                  for (ActorRef actorRef : actorRefs) {
                        QuotationRequest quotationRequest = new QuotationRequest(SEED_ID, client);
                        actorRef.tell(quotationRequest, getSelf());
                  }
                  
                  // wait 2 seconds for services response.
                  getContext().system().scheduler().scheduleOnce(
                  Duration.create(2, TimeUnit.SECONDS),
                  getSelf(),
                  new RequestDeadline(SEED_ID++),
                  getContext().dispatcher(), null);
            })

            .match(QuotationResponse.class,
            msg -> {
                  
                  // Listen for QuotationResponse and deal with it
                  Quotation quote = msg.getQuotation();

                  if (cache.containsKey(msg.getId())) {
                        // If the id exists append quotes
                        ArrayList<Quotation> quotations = cache.get(msg.getId()); 
                        quotations.add(quote);
                        cache.put(msg.getId(), quotations);
                  } else {
                        // make new
                        ArrayList<Quotation> quotations = new ArrayList<>();
                        quotations.add(quote);
                        cache.put(msg.getId(), quotations);
                  }
            })

            .match(RequestDeadline.class,
            msg -> {

                  // Get the list of quotations for this SEED
                  ArrayList<Quotation> quotations = cache.get(msg.getSEED_ID());
                  // Get the ClientInfo object 
                  ClientInfo client = clientsMap.get(msg.getSEED_ID());

                  // Make an instance of ApplicationResponse to send to client.
                  ApplicationResponse applicationResponse = new ApplicationResponse(client, quotations);
                  
                  clientActor.tell(applicationResponse, getSelf());


            }).build();
              
      }

}