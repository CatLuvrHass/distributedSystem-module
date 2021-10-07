import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import java.net.URL;
import java.text.NumberFormat;
import java.util.LinkedList;

import service.core.QuoterService;
import service.core.ClientInfo;
import service.core.Quotation;

public class Client{

    public static void main(String[] args) throws Exception {
        // making thread sleep so when docker runs - client waits for the broker and services to set up
        Thread.sleep(16000);
        String host = "broker";
        int port = 9000;

        URL wsdlUrl = new URL("http://" + host + ":" + port + "/broker?wsdl");
        QName serviceName =
                new QName("http://core.service/", "BrokerService");
        Service service = Service.create(wsdlUrl, serviceName);
        QName portName = new QName("http://core.service/", "BrokerPort");

        QuoterService brokeService = service.getPort(portName, QuoterService.class);

        LinkedList<Quotation> quotations;

        for (ClientInfo info : clients) {
            displayProfile(info);
            quotations = brokeService.getQuotations(info);

            for (Quotation quotation: quotations){
                displayQuotation(quotation);

            }
        }
    }


    /**
     * Display the client info nicely.
     *
     * @param info
     */
    public static void displayProfile(ClientInfo info) {
        System.out.println("|=================================================================================================================|");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println(
                "| Name: " + String.format("%1$-29s", info.name) +
                        " | Gender: " + String.format("%1$-27s", (info.gender==ClientInfo.MALE?"Male":"Female")) +
                        " | Age: " + String.format("%1$-30s", info.age)+" |");
        System.out.println(
                "| License Number: " + String.format("%1$-19s", info.licenseNumber) +
                        " | No Claims: " + String.format("%1$-24s", info.noClaims+" years") +
                        " | Penalty Points: " + String.format("%1$-19s", info.points)+" |");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println("|=================================================================================================================|");
    }

    /**
     * Display a quotation nicely - note that the assumption is that the quotation will follow
     * immediately after the profile (so the top of the quotation box is missing).
     *
     * @param quotation
     */

    public static void displayQuotation(Quotation quotation) {
        System.out.println(
                "| Company: " + String.format("%1$-26s", quotation.company) +
                        " | Reference: " + String.format("%1$-24s", quotation.reference) +
                        " | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
        System.out.println("|=================================================================================================================|");
    }
    /**
     * Test Data
     */
    public static final ClientInfo[] clients = {
            new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1"),
            new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 0, 2, "ABC123/4"),
            new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 16, 10, 0, "HMA304/9"),
            new ClientInfo("Rem Collier", ClientInfo.MALE, 44, 5, 3, "COL123/3"),
            new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 4, 7, "QUN987/4"),
            new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 5, 2, "XYZ567/9")
    };

}
