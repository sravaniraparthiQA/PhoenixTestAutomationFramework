package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import static com.api.constant.Role.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CountAPITest {
	
	@Test(description = "Verify if the count api is giving correct response", groups = {"api", "regression", "smoke"})
	public void verifyCountAPIResponse() {
		
		given()
			.spec(requestSpecWithAuth(FD))
		.when()
			.get("/dashboard/count")
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body("data", notNullValue())
			.body("data.size()", equalTo(3))
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", everyItem(not(blankOrNullString())))
			.body("data.key", containsInAnyOrder("pending_fst_assignment", "pending_for_delivery", "created_today"))
			.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));
		
	}
	
	@Test(description = "Verify if the count api is giving correct status code for invalid token", groups = {"api", "negative", "regression", "smoke"})
	public void countAPITest_MissingAuthToken() {
		
		given()
			.spec(requestSpec())
		.when()
			.get("/dashboard/count")
		.then()
			.spec(responseSpec_TEXT(401));
		
	}

}
