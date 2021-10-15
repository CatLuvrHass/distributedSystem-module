package service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import service.auldfellas.AFQService;
import service.core.Quotation;
import service.message.QuotationRequestMessage;
import service.message.QuotationResponseMessage;

public class Receiver {
        
        // AFQ object to act as a reciever
        // slave to the broker.
        static AFQService service = new  AFQService();

        public static void main(String[] args){

                // specify the host as local host
                // but allow for args input for containerisation.
                String host = "localhost";
                if (args.length > 0) {
                        host = args[0];
                }
                try{
                        // 1.create a connection factory instance.
                        ConnectionFactory factory =
                        new ActiveMQConnectionFactory("failover://tcp://"+host+":61616");
                       
                        // 2.create the connection link
                        Connection connection = factory.createConnection();
                        // assigned the client name
                        connection.setClientID("auldfellas");
                        
                        // 3.session instance.
                        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

			/**code to pass in the host name for the ActiveMQ broker.
			 * two channels as the services are both consumers and producers
			 * the topic for pub/sub channel on a shared topic between the services
			 * "applications" from client.
			 * the other channel for queue on a p2p channel with service and master(broker) & broker 
                         * shares to the client.
			 * sending "quotations".
			 */

                         // 4.create/get queue/topic.
                        Queue queue = session.createQueue("QUOTATIONS");
                        MessageProducer producer = session.createProducer(queue);
                        
                        // 5. create a producer/consumer
                        Topic topic = session.createTopic("APPLICATIONS");
                        MessageConsumer consumer = session.createConsumer(topic);


                        connection.start();
                        // 6.send a message
                        while (true) {
                                // Get the next message from the APPLICATION topic
                                Message message = consumer.receive();
                                
                                // Check it is the right type of message
                                if (message instanceof ObjectMessage) {
                                        System.out.println("Hello, World");
                                        // It’s an Object Message
                                        Object content = ((ObjectMessage) message).getObject();
                                        if (content instanceof QuotationRequestMessage) {

                                                // It’s a Quotation Request Message
                                                QuotationRequestMessage request = (QuotationRequestMessage) content;

                                                // Generate a quotation and send a quotation response message…
                                                Quotation quotation = service.generateQuotation(request.info);
                                                Message response = session.createObjectMessage(
                                                new QuotationResponseMessage(request.id, quotation));
                                                producer.send(response);
                                                message.acknowledge();
                                        }
                                } else {
                                        System.out.println("Unknown message type: " + message.getClass().getCanonicalName());
                                }
                        }
                        
                }catch (JMSException e) {
                        e.printStackTrace();
                }


        }


}
