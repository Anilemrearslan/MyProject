package code;

public class serializationAndDeserialization {
    //Specific to Rest Assured. It is related to programing language -JAVA
    //What is Serialization and Deserialization
    // S is conversion of JSON object, D is the reverse flow
    //POJO - Plain Old Java Object
    //Converting a Java Object into a JSON object >>> Serialization
    //Converting a JSOn object into a JAVA object >>> Deserialization
    //Jackson, Gson etc.

    //bookId and customerName

    //First is to declare variables
    private String bookId;
    private String customerName;


    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
