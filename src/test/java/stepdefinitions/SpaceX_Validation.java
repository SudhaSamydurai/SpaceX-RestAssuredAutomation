package stepdefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;

import Resources.APIResources;
import SpecBuilder.SpecBuilder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpaceX_Validation extends SpecBuilder {

	ResponseSpecification resspec;
	RequestSpecification res;
	Response response;
	String responseToString;

	@Given("User calls {string} with http {string} request")
	public void user_calls_with_http_post_request(String resourceName, String resourceType) throws IOException {

		APIResources apiResource = APIResources.valueOf(resourceName);
		apiResource.getResource();

		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		res = given().spec(requestSpecification());
		if (resourceType.equalsIgnoreCase("POST"))
			response = res.when().post(apiResource.getResource()).then().extract().response();
		else if (resourceType.equalsIgnoreCase("GET"))
			response = res.when().get(apiResource.getResource()).then().extract().response();

	}

	@Then("verify {string} is matching in {string} response")
	public void verify_is_matching_in_response(String expectedName, String resourceName) throws IOException {

		res = given().spec(requestSpecification());
		user_calls_with_http_post_request(resourceName, "GET");

		String name = getJsonPathValue(response, "name");
		Assert.assertEquals(name, expectedName);

	}

	@Then("API call got success with status code {int}")
	public void api_call_got_success_with_status_code(int statuscode) {
		Assert.assertEquals(response.getStatusCode(), statuscode);
	}

	@Then("{string} in response body is {string}")
	public void in_the_response_body_is(String keyValue, String expectedValue) {
		responseToString = response.asString();
		JsonPath path = new JsonPath(responseToString);
		Assert.assertEquals(path.get(keyValue).toString(), expectedValue);
	}

	@Then("verify total {string} {int}")
	public void verify_total(String key, int expectedCount) {
		JsonPath path = new JsonPath(responseToString);
		List<String> shipsCount = path.get(key);
		Assert.assertEquals(shipsCount.size(), expectedCount);
	}
}
