package code;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class POSTOrderBook {

    String baseURI = RestAssured.baseURI="https://simple-books-api.glitch.me";

    @Test(description = "Given a baseURI and token When user wants to POST to /orders Tehn verify Status Code is 201")
    void orderBook(){

        // Order Book Call
        // Needed Information : request payload, token, endpoint and header(content-type)

        //Given
        // Get a Token
        String token=utils.generateBearerToken();

        //Create Payload for the request

        Faker faker=new Faker();
        String customerName = faker.name().fullName();
        String bookId = utils.getABookId();

        JSONObject object = new JSONObject();
        object.put("bookId",bookId);
        object.put("customerName",customerName);

        String requestPayload = object.toString();

        RequestSpecification orderBookRequest= given()
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body(requestPayload);

        //When
        Response orderBookResponse = orderBookRequest.when().post("/orders");

        //Then
        orderBookResponse.then().assertThat().statusCode(201);
        System.out.println("Order Book Response Payload "+orderBookResponse.getBody().asString());

        String orderId = orderBookResponse.jsonPath().getString("orderId");

        //Retrieve Order(s)
        //Lets Make the Second call: List of Ordered Book(s)

        //Given
        RequestSpecification listOfOrdersRequest = given()
                .headers("Authorization",token);

        //When
        Response listOfOrdersResponse = listOfOrdersRequest.when().get("/orders");

        //Then
        listOfOrdersResponse.then().assertThat().statusCode(200);
        System.out.println(listOfOrdersResponse.getBody().asString());

        String actualCustomerName=listOfOrdersResponse.jsonPath().getString("customerName");
        Assert.assertTrue(actualCustomerName.contains(customerName));

        //Update Order - patch
        // Token, Content-Type, Path param, requestBody

        String newCustomerName="Onder";

        JSONObject objectNewName= new JSONObject();
        objectNewName.put("customerName",newCustomerName);
        String updateOrderRequestPayload = objectNewName.toString();

        //Given
        RequestSpecification updateOrderRequest = given()
                .pathParam("orderId",orderId)
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body(updateOrderRequestPayload);

        //when
        Response updateOrderResponse = updateOrderRequest.when().patch("/orders/{orderId}");

        updateOrderResponse.then().assertThat().statusCode(204);

        //MAKE ANOTHER CALL TO : List of the Ordered Book(s)
        listOfOrdersRequest= given()
                .header("Authorization",token);
        listOfOrdersResponse = listOfOrdersRequest.when().get("/orders");

        listOfOrdersResponse.then().assertThat().statusCode(200);

        System.out.println(listOfOrdersResponse.getBody().asString());

        String actualNewCustomerName = listOfOrdersResponse.jsonPath().getString("customerName");

        Assert.assertTrue(actualNewCustomerName.contains(newCustomerName));


        //DELETE order

        //Given
        //token, path parameter, Content-Type Delete(HTTP) body(if required)

        RequestSpecification deleteOrderRequest = given()
                .pathParam("orderId",orderId)
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{}");

        //When
        Response deleteOrderResponse = deleteOrderRequest.when().delete("/orders/{orderId}");

        //Then
        deleteOrderResponse.then().assertThat().statusCode(204);



        //LAST CALL TO VERIFY IF IT GOT DELETED - List of the Ordered Books
        listOfOrdersRequest= given()
                .header("Authorization",token);
        listOfOrdersResponse=listOfOrdersRequest.when().get("/orders");
        listOfOrdersResponse.then().assertThat().statusCode(200);

        System.out.println(listOfOrdersResponse.getBody().asString());
        String listOftheOrdersResponseBody=listOfOrdersResponse.getBody().asString();
        Assert.assertTrue(!listOftheOrdersResponseBody.contains(orderId));


    }


}
