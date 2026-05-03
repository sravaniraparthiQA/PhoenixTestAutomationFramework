package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.services.UserService;

public class UserDetailsAPITest {
	
	private UserService userService;
	
	@BeforeMethod (description = "Setting up the UserService instance")
	public void setup() {
		userService = new UserService();
	}
	
	@Test(description = "Verify if the Userdetails API response is shown correctly", groups = {"api", "regression", "smoke"})
	public void userDetailsAPITest() {
		
		 
		userService.userDetails(Role.FD)
		.then()
			.spec(responseSpec_OK())
			.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
			
	}

}
