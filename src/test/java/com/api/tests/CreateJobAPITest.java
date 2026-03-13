package com.api.tests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.SpecUtil;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITest() {
		
		//Creating the createJobPayload Object
		
		Customer customer = new Customer("Sravani", "Raparthi", "9618096697", "", "sravaniraparthis24@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("Flat 206", "Pushkardham", "Hanuman Nagar", "Morampudi", "Navaram", "533107", "India", "Rajahmundry");
		CustomerProduct customerProduct = new CustomerProduct("2025-04-13T18:30:00.000Z", "10565811391577", "10565811391577", "10565811391577", "2025-04-13T18:30:00.000Z", 1, 1);
		Problems problems = new Problems(2, "Battery issue");
		Problems problemsArray[] = new Problems[1];
		problemsArray[0]=problems;
		
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsArray);
		
		given()
			.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
			.post("/job/create")
		.then()
			.spec(SpecUtil.responseSpec_OK());
	}

}
