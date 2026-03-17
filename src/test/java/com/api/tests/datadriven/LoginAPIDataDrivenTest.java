package com.api.tests.datadriven;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginAPIDataDrivenTest {

	@Test(description = "Verify if login api is working for FD user", groups = {"api", "regression", "smoke"}, 
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
