import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        LocalBrokerService lbService = new LocalBrokerService();
        try {
            // creating the registry
            Registry registry = null;
            try{
                registry = LocateRegistry.createRegistry(1099);
            
            } catch(Exception e){
                System.out.println("Trouble: " + e);
            }

            // Create the Remote Object
            BrokerService localBrokerService = (BrokerService) UnicastRemoteObject.exportObject(lbService, 0);
            // Register the object with the RMI Registry
            registry.bind(Constants.BROKER_SERVICE, localBrokerService);
            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
}