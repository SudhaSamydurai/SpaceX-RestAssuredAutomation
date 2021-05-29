package SpecBuilder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SpecBuilder {

	private static RequestSpecification req;

	public RequestSpecification requestSpecification() throws IOException {
		if (req == null) {
			PrintStream log = new PrintStream(new FileOutputStream("log.txt"));
			req = new RequestSpecBuilder().setBaseUri(getPropertyValue("baseURL"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();

			return req;
		}
		return req;
	}

	public static String getPropertyValue(String key) throws IOException {
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(
				"E:\\Workspace\\API_RestAssured\\SpaceX-API-Automation\\src\\test\\resources\\Global.property");
		props.load(fis);
		return props.getProperty(key);

	}

	public String getJsonPathValue(Response response, String key) {

		String responseString = response.asString();
		JsonPath js = new JsonPath(responseString);
		return js.get(key).toString();
	}
}
