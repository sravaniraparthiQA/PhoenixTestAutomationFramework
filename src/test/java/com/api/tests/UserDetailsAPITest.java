package com.api.tests;

import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;

import static com.api.utils.AuthTokenProvider.*;

import static io.restassured.http.ContentType.*;
import io.restassured.http.Header;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class UserDetailsAPITest {
	
	@Test
	public void userDetailsAPITest() {
		Header authHeader = new Header("Authorization", getToken(SUP));
		
		given()
			.baseUri(getProperty("BASE_URI"))
			.header(authHeader)
			.contentType(JSON)
			.log().uri()
			.log().method()
			.log().headers()
			.log().body()
		.when()
			.get("userdetails")
		.then()
			.log().all()
			.statusCode(200)
			.time(lessThan(1500L))
			.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
			
	}

}
