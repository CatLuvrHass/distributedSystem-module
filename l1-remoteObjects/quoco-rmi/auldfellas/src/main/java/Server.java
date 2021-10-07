import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;


public class Server {
    public static void main(String[] args) {
        QuotationService afqService = new AFQService();
        try {
            
            // Connect to the RMI Registry
            Registry registry = null;
            String host = args[0];
            try{
                registry = LocateRegistry.getRegistry(host, 1099);
            }catch (Exception e){
                System.out.println("Trouble: " + e);
            }
            // Create the Remote Object
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(afqService,0);
            // Register the object with the RMI Registry
            registry.bind(Constants.AULD_FELLAS_SERVICE, quotationService);
            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {Thread.sleep(1000); }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
}