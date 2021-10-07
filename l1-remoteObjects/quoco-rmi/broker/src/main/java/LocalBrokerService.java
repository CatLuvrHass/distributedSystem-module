import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.LinkedList;
import java.util.List;

// implements BrokerService that extends Remote exposing as a distributed object.
public class LocalBrokerService implements BrokerService {

    public List<Quotation> getQuotations(ClientInfo info) throws RemoteException {

        List<Quotation> quotations = new LinkedList<Quotation>();

        Registry registry = LocateRegistry.getRegistry("localhost", 1099);

        for (String name : registry.list()) {
            if (name.startsWith("qs-")) {
                QuotationService service;
                
                try{
                    service =(QuotationService) registry.lookup(name);
                    quotations.add(service.generateQuotation(info));
                } catch(NotBoundException e){
                    e.printStackTrace();
                }

            }
        }

        return quotations;
    }

}
