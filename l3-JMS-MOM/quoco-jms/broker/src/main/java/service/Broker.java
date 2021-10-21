package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import service.core.ClientInfo;
import service.core.Quotation;
import service.message.ClientApplicationMessage;
import service.message.QuotationRequestMessage;
import service.message.QuotationResponseMessage;

public class Broker {


      // Map to help associate the int SEED_ID with the Clientinfo
      static String host;
      static Map<Long, ClientInfo> cache = new HashMap<>();
      private static Connection connection;
      static ArrayList<Quotation> quotations = new ArrayList<>();
      static long id = -1;


      public static void main(String[] args){
            
            // specify the host as local host
            // but allow for args input for containerisation.
            String host = "localhost";
            if (args.length > 0) {
                    host = args[0];
            }

            try{
                  // 1.create a connection factory instance.
                  System.out.println("Starting Broker on: " +host);
                  ConnectionFactory factory = new ActiveMQConnectionFactory("failover://tcp://" + host + ":61616");

                  // 2.create the connection link
                  connection = factory.createConnection();

                  // assigned the client name has to be unique
                  connection.setClientID("broker");

                  /**create 2 threads -
                   * Thread 1 is client-broker-service. 
                   * Thread 2 is service-broker-client.
                  
                   two channels as the services are both consumers and producers
                   * the topic for pub/sub channel on a shared topic between the services
                   * "applications" from client.
                   * the other channel for queue on a p2p channel 
                   * with service and master(broker) & 
                   * broker shares to the client.
                   * sending "quotations".
                   */

                  ReqThread requestThread = new ReqThread();
                  ResponseThread responseThread = new ResponseThread();
                  new Thread(requestThread).start();

                  new Thread(responseThread).start();
            
            } catch (JMSException e){
                  System.out.println(e);
            }
      }
      
      //The thread Client -> Broker -> Services -> Client
      public static class ReqThread implements Runnable{

            /**
             * here we need a consumer to handle a REQUEST QUEUE  and publish
             * to APPLICATIONS TOPIC
             * ie the CLIENT has sent a message to REQUEST QUEUE  of ClientInfo
             * the Broker on this thread will consume
             * Contents of the QuotationRequestMessage
             * and produce.send(message) to APPLICATIONS.
             * 
             * then 
             * the broker will consume the Queue from the response Thread and create
             * RESPONSE topic here for the client to consume and print results. using the cache id
             * to link the ClientApplication
             * 
             *  */ 

            @Override
            public void run() {
                  try {
                        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
                        Queue queue = session.createQueue("REQUEST");
                        MessageConsumer consumer = session.createConsumer(queue);

                        Topic topic = session.createTopic("APPLICATIONS");
                        MessageProducer producer = session.createProducer(topic);

                        Topic responseTopic = session.createTopic("RESPONSE");
                        MessageProducer responsProducer = session.createProducer(responseTopic);
                        
                        connection.start();

                        while (true){
                              Message message = consumer.receive();
                              if (message instanceof ObjectMessage) {
                                    Object content = ((ObjectMessage) message).getObject();
                                    if (content instanceof QuotationRequestMessage) {
                                    QuotationRequestMessage request = (QuotationRequestMessage) content;
                                    producer.send(message);

                                    cache.put(request.id,request.info);
                                    }
                              } else {
                                    System.out.println("Unknown message type: " +
                                          message.getClass().getCanonicalName());
                              }

                              try {
                                    Thread.sleep(5000);
                              } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                              }

                              System.out.println("this is the quotaions size" + quotations.size());
                  
                              Message application = session.createObjectMessage(new ClientApplicationMessage(id, cache.get(id), quotations));
                        
                              System.out.println("this is cached id" + id);
                              System.out.println("THIS IS THE APP");
                              responsProducer.send(application);

                              quotations.clear();
                        }

                  } catch (JMSException e){
                        System.out.println(e);
                  }
            }
      }

      //The thread Services -> Broker 
      public static class ResponseThread implements Runnable{

            /**In this thread we have the QUOTATIONS Queue where the 
             * different services produce their quotes.
             */

            @Override
            public void run() {
                try {

                    Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

                    Queue queue = session.createQueue("QUOTATIONS");
                    MessageConsumer consumer = session.createConsumer(queue);

                    connection.start();
                    
                    while (true) {
                        
                        Message message = consumer.receive();
                        if (message instanceof ObjectMessage) {
                        Object content = ((ObjectMessage) message).getObject();
                              if (content instanceof QuotationResponseMessage) {
                                    QuotationResponseMessage response = (QuotationResponseMessage) content;
                                    System.out.println("this is the response id" + response.id);

                                    message.acknowledge();
                                    quotations.add(response.quotation);
                                    id=response.id;
                              }
                        } else {
                              System.out.println("Unknown message type: " +
                                          message.getClass().getCanonicalName());
                        }
                  }

            } catch (JMSException e){
           
                  System.out.println(e);
       
            }
       
      }
   
}

}     
