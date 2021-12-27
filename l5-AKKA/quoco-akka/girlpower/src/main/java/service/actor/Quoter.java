package service.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.core.Quotation;
import service.core.QuotationService;
import service.girlpower.GPQService;
import service.messages.Init;
import service.messages.QuotationRequest;
import service.messages.QuotationResponse;

public class Quoter extends AbstractActor {
      private QuotationService service = new GPQService();
      
      public static void main(String[] args) {
      
            // start the actor system and initlalize the Quoter actor
            ActorSystem system = ActorSystem.create();
            ActorRef ref = system.actorOf(Props.create(Quoter.class), "girlpower");
            ref.tell(new Init(new GPQService()), null);
            
            // send the message to the broker to register
            ActorSelection selection =
            system.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
            selection.tell("register", ref);
      }

      @Override
      public Receive createReceive() {
            return receiveBuilder()
            .match(Init.class,
            msg -> {
                  service = msg.getService();
            })
            .match(QuotationRequest.class,
            msg -> {
                  Quotation quotation =
                  service.generateQuotation(msg.getClientInfo());
                  getSender().tell(
                        new QuotationResponse(msg.getId(), quotation), getSelf());
            }).build();
            
      }
}