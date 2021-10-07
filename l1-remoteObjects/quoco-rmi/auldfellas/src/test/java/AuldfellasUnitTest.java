import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.junit.*;

public class AuldfellasUnitTest {
            private static Registry registry;

            @BeforeClass
            public static void setup() {
                        QuotationService afqService = new AFQService();
                        try {
                                    registry = LocateRegistry.createRegistry(1099);
                                    QuotationService quotationService = (QuotationService) UnicastRemoteObject
                                                            .exportObject(afqService, 0);
                                    registry.bind(Constants.AULD_FELLAS_SERVICE, quotationService);
                        } catch (Exception e) {
                                    System.out.println("Trouble: " + e);
                        }
            }

            @Test
            public void connectionTest() throws Exception {
                        QuotationService service = (QuotationService) registry.lookup(Constants.AULD_FELLAS_SERVICE);
                        assertNotNull(service);
            }

            @Test
            public void quotationTest() throws Exception {

                        QuotationService service = (QuotationService) registry.lookup(Constants.AULD_FELLAS_SERVICE);

                        ClientInfo info = new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1");

                        Quotation quotation = service.generateQuotation(info);

                        //assert there is an object and it is an instance of Quotation
                        // and finally check if the quotation Company name is generated from the correct comapany.
                        assertNotNull(quotation);
                        assertTrue(quotation instanceof Quotation);
                        assertEquals(quotation.company ,"Auld Fellas Ltd.");
            }

}