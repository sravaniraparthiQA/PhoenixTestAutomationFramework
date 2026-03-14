package com.api.tests;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import static com.api.utils.DateTimeUtil.*;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITest() {
		
		//Creating the createJobPayload Object
		
		Customer customer = new Customer("Sravani", "Raparthi", "9618096697", "", "sravaniraparthis24@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("Flat 206", "Pushkardham", "Hanuman Nagar", "Morampudi", "Navaram", "533107", "India", "Rajahmundry");
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "10565811391521", "10565811391521", "10565811391521", getTimeWithDaysAgo(10), 1, 1);
		Problems problems = new Problems(2, "Battery issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsList);
		
		given()
			.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
			.post("/job/create")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message", equalTo("Job created successfully. "))
			.body("data.mst_service_location_id", equalTo(1))
			.body("data.job_number", startsWith("JOB_"));
		
	}

}
