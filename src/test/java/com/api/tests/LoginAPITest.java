package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;

import io.restassured.response.Response;

public class LoginAPITest {
	
	private UserCredentials userCreds;
	private AuthService authService;
	
	@BeforeMethod(description = "Create the Request Payload for the login API")
	public void setup() {
		
		userCreds = new UserCredentials("iamfd", "password");
		authService = new AuthService();
	}

	@Test(description = "Verify if login api is working for FD user", groups = {"api", "regression", "smoke"})
	public void loginAPITest() {
		//Rest Assured Code!
		
		authService.login(userCreds)
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
