Feature: Get Books EndPoint

  Scenario: given baseURI when we make a get call to /books endpoint verify response and status code
    Given baseURI
    When we want to make a get call to "/books" endpoint
    Then verify that response has all books
    And Verify status code is 200