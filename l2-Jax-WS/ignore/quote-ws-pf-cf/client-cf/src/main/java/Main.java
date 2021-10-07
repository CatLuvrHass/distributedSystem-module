import quote.*;

public class Main {
    public static void main(String[] args) {
        StockQuoteService service = new StockQuoteService();
        StockQuote stockQuote = service.getStockQuotePort();
        System.out.println("IBM: " + stockQuote.getStockPrice("IBM"));
    }
}
