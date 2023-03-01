package code;

import code.hooks.bookApiHooks;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class POSTOrderBookReadingFile extends bookApiHooks{

    @Test(description = "Given a baseURI, token, headers and path params When user wants to Order a book Then Verify Order is success and status code is 201")
    void orderBookReadingJSONFile() {
        //GIven
        //request body, token, params and any headers

        //token
        String token = utils.generateBearerToken();

        //Create request payload
        String orderBookRequestPayload = utils.readFile("src/test/java/code/testData/orderBook.json");
        System.out.println(orderBookRequestPayload);

        //When
        RequestSpecification orderBookRequest = given()
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body(orderBookRequestPayload);


        //When
        Response orderBookResponse = orderBookRequest.when().post("/orders");

        //Then
        orderBookResponse.then().assertThat().statusCode(201);
        String orderId = orderBookResponse.jsonPath().getString("orderId");

//        if(orderBookResponse.equals(false)){
//            throw new Error("/Orders endpoint is down");
//        }
        System.out.println(orderBookResponse.getBody().asString());


        //Retrieve my order
        utils.retrieveOrder(token,"Amer");

        Faker faker = new Faker();
        String newCustomerName = faker.name().fullName();
        //update my order
        utils.updateMyOrder(newCustomerName,orderId,token);

        //Retrieve my order
        utils.retrieveOrder(token,newCustomerName);



    }



}
