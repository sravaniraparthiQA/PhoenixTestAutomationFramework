package com.api.tests;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.utils.SpecUtil;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginAPITest {

	@Test
	public void loginAPITest() {
		//Rest Assured Code!
		
		UserCredentials userCreds = new UserCredentials("iamfd", "password");
		
		given()
			.spec(SpecUtil.requestSpec(userCreds))
		.when()
			.post("login")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("message", equalTo("Success"))
			.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
