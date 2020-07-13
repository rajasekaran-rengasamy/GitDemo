package stepDefinations;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import static io.restassured.RestAssured.given;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class StepDefination extends Utils {

	ResponseSpecification resspec;
	RequestSpecification res;
	RequestSpecification req;
	Response response;
	public static String place_id;
	
	TestDataBuild data= new TestDataBuild();
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_Place_Payload_with(String name, String language, String address) throws IOException
     {
		//RestAssured.baseURI="https://rahulshettyacademy.com";
		res=given().spec(requestSpecification())
		    .body(data.addPlacePayLoad(name,language,address));	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_Post_http_request(String resource, String method) {
		//Constructor will be called with the value of resource which you pass
		APIResources resourceAPI= APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		//response =res.when().post("/maps/api/place/add/json").
		if(method.equalsIgnoreCase("POST"))
		{
		response =res.when().post(resourceAPI.getResource());
				//.then().spec(resspec).extract().response();
		}
		if(method.equalsIgnoreCase("GET"))
		{
			response=res.when().get(resourceAPI.getResource());
		}
	}

	@Then("the API call got success with status code {int}")
	public void the_API_call_got_success_with_status_code(Integer int1) {
	   assertEquals(response.statusCode(),200);
	}

	@And("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
		
		assertEquals(getJsonPath(response,keyValue),expectedValue);
	   
	}
	@Then("verify place_id is mapped to {string} using {string}")
	public void verify_place_id_is_mapped_to_using(String expectedName, String resource) throws IOException {

		place_id= getJsonPath(response,"place_id");
		res.given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_Post_http_request(resource, "GET");
		String name=getJsonPath(response,"name");
		assertEquals(name,expectedName);
		
	}

	@Given("DeletePlace Payload")
	public void deleteplace_Payload() throws IOException {
		
		res=given().spec(requestSpecification())
			    .body(data.deletePlacePayLoad(place_id));
		
	}
}
