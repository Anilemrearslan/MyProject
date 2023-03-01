package code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Serialization {
    public static void main(String[] args) throws JsonProcessingException {
        //Step 1 - Create an Object of POJO class and set required values

        serializationAndDeserialization serialization = new serializationAndDeserialization();
        serialization.setBookId("1");
        serialization.setCustomerName("Benjamin");

        //ObjectMAPPER class to serialize POJO object to JSON
        //create an object of ObjectMAPPER using jackson dependency
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonPayload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(serialization);
        System.out.println("JSON object we created >>> "+jsonPayload);











    }


}
