package service.core;

import java.io.Serializable;
import java.util.*;

/**
 * Class to store the quotations returned by the the broker service to client.
 * 
 * @author Hassan
 * 
 */

public class ClientApplication implements Serializable {
   
      private int id;
      private ClientInfo clientInfo;
      private List<Quotation> quotations = new ArrayList<Quotation>();

      //default constructor
      public ClientApplication(){}


      public ClientApplication(int id, ClientInfo clientInfo, List<Quotation> quotations) {
            this.id = id;
            this.clientInfo = clientInfo;
            this.quotations = quotations;
      }
      

      public long getId() {
            return id;
      }

      public void setId(int id) {
            this.id = id;
      }

      public ClientInfo getClientInfo() {
            return clientInfo;
      }

      public void setClientInfo(ClientInfo clientInfo) {
            this.clientInfo = clientInfo;
      }

      public List<Quotation> getQuotations() {
            return quotations;
      }

      public void setQuotations(List<Quotation> quotations) {
            this.quotations = quotations;
      }

}