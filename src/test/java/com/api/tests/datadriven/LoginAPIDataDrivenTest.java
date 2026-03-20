package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.dataproviders.api.bean.UserBean;

public class LoginAPIDataDrivenTest {

	@Test(description = "Verify if login api is working for FD user", groups = {"api", "regression", "datadriven"}, 
			dataProviderClass = com.dataproviders.DataProviderUtils.class, dataProvider = "LoginAPIDataProvider" )
	//After giving dataProviderClass & dataProvider the @Test methods become parameterized, hence we need to pass the parameters

	public void loginAPITest(UserBean userbean) {
		//inside a list of dataProviderClass, each one in list is nothing but an ref variable of an UserBean object hence passing ref variable of UserBean type as parameter here
		
		given()
			.spec(requestSpec(userbean))
		.when()
			.post("login")
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
