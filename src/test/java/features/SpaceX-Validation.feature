
@tag
Feature: Validate spaceX get API

  @tag1
  Scenario Outline: Verify the response is successful for getRequest
    Given User calls "GetPlacAPI" with http "get" request
    Then API call got success with status code 200
    And "success" in response body is "true"
    Then verify "<name>" is matching in "GetPlacAPI" response

    Examples: 
      | name  |
      |Starlink-28 (v1.0)|

      
  Scenario: Verify the response is failed for invalid getrequest
    Given User calls "InvalidPlacAPI" with http "get" request
    Then API call got success with status code 404
    
    
  Scenario Outline: Verify the count of ships
    Given User calls "GetPlacAPI" with http "get" request
    And "success" in response body is "true"
    Then verify total "ships" <count>
    
    Examples: 
      | count  |
      |5|