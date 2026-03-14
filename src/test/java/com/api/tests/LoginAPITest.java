package com.api.tests;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginAPITest {
	
	private UserCredentials userCreds;
	
	@BeforeMethod(description = "Create the Request Payload for the login API")
	public void setup() {
		
		userCreds = new UserCredentials("iamfd", "password");
	}

	@Test(description = "Verify if login api is working for FD user", groups = {"api", "regression", "smoke"})
	public void loginAPITest() {
		//Rest Assured Code!
		
		given()
			.spec(requestSpec(userCreds))
		.when()
			.post("login")
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
