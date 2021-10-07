import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.junit.*;

import static org.junit.Assert.*;


public class brokerUnitTest {

    private static Registry registry;

    @BeforeClass
    public static void setup() {
        LocalBrokerService lbService = new LocalBrokerService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            BrokerService localBrokerService = (BrokerService) UnicastRemoteObject.exportObject(lbService, 0);
            registry.bind(Constants.BROKER_SERVICE, localBrokerService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    @Test
    public void connectionTest() throws Exception {
        BrokerService localBroker = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
        assertNotNull(localBroker);
    }

    @Test
    public void brokerTest() throws Exception {
        BrokerService localBroker = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);

        ClientInfo testClient = new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1");

        List<Quotation> quotations = localBroker.getQuotations(testClient);
        // System.out.println(quotations);

        Assert.assertTrue(quotations.isEmpty());

    }

}
