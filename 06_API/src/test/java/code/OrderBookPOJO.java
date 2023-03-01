package code;

public class OrderBookPOJO {
    public String bookId;
    public String customerName;

    public OrderBookPOJO(String bookId, String customerName){
        this.bookId=bookId;
        this.customerName=customerName;
    }

    public OrderBookPOJO( String customerName){
        this.bookId=bookId;
        this.customerName=customerName;
    }
}
