package com.api.tests;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class MasterAPITest {
	
	@Test
	public void masterAPITest() {
		
		given()
			.spec(SpecUtil.requestSpecWithAuth(FD))
		.when()
			.post("master") 	//Default Content-Type application/url-formenoded
		.then()
		.spec(SpecUtil.responseSpec_OK())
			.body("message", Matchers.equalTo("Success"))
			.body("data", Matchers.notNullValue())
			.body("data", Matchers.hasKey("mst_oem"))
			.body("data", Matchers.hasKey("mst_model"))
			.body("$", Matchers.hasKey("message"))
			.body("$", Matchers.hasKey("data"))
			.body("data.mst_oem.size()", Matchers.equalTo(2)) 	//Check the size of JSON Array with Matchers
			.body("data.mst_model.size()", Matchers.greaterThan(0))
			.body("data.mst_oem.id", Matchers.everyItem(Matchers.notNullValue()))
			.body("data.mst_oem.name", Matchers.everyItem(Matchers.notNullValue()))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPITResponseSchema.json"));
			
	}
	
	@Test
	public void invalidTokenMasterAPITest() {
		
		given()
			.spec(SpecUtil.requestSpec())
		.when()
			.post("master") 	
		.then()
			.spec(SpecUtil.responseSpec_TEXT(401));
	}

}
