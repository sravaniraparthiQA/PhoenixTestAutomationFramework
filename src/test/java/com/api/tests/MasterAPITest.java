package com.api.tests;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;
import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class MasterAPITest {
	
	@Test(description = "Verify if the master api is giving correct response", groups = {"api", "regression", "smoke"})
	public void masterAPITest() {
		
		given()
			.spec(requestSpecWithAuth(FD))
		.when()
			.post("master") 	//Default Content-Type application/url-formenoded
		.then()
		.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body("data", notNullValue())
			.body("data", hasKey("mst_oem"))
			.body("data", hasKey("mst_model"))
			.body("$", hasKey("message"))
			.body("$", hasKey("data"))
			.body("data.mst_oem.size()", equalTo(2)) 	//Check the size of JSON Array with Matchers
			.body("data.mst_model.size()", greaterThan(0))
			.body("data.mst_oem.id", everyItem(notNullValue()))
			.body("data.mst_oem.name", everyItem(notNullValue()))
			.body(matchesJsonSchemaInClasspath("response-schema/MasterAPITResponseSchema.json"));
			
	}
	
	@Test(description = "Verify if the master api is giving correct status code or invalid token", groups = {"api", "negative", "regression", "smoke"})
	public void invalidTokenMasterAPITest() {
		
		given()
			.spec(requestSpec())
		.when()
			.post("master") 	
		.then()
			.spec(responseSpec_TEXT(401));
	}

}
