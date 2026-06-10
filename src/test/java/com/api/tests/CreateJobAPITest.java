package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.services.JobService;

public class CreateJobAPITest {
	
	private JobService jobService;
	private CreateJobPayload createJobPayload;
	
	@BeforeMethod(description = "Creating the create job api request payload and instantiating the Job Service")
	public void setup() {
		
		Customer customer = new Customer("Sravani", "Raparthi", "9618096697", "", "sravaniraparthis24@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("Flat 206", "Pushkardham", "Hanuman Nagar", "Morampudi", "Navaram", "533107", "India", "Rajahmundry");
		
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "10665811401122", "10665811401122", "10665811401122", 
				getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), 
				Warranty_Status.IN_WARRENTY.getCode(), OEM.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemsList);
		jobService = new JobService();
		
	}
	
	@Test(description = "Verify if the create job api is able to create Inwarranty job", groups = {"api", "regression", "smoke"})
	public void createJobAPITest() {
	
		jobService.createJob(Role.FD, createJobPayload)
		.then()
			.spec(responseSpec_OK())
			.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message", equalTo("Job created successfully. "))
			.body("data.mst_service_location_id", equalTo(1))
			.body("data.job_number", startsWith("JOB_"));
		
	}

}
