package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.JobHeadDao;
import com.database.dao.MapJobProblemDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.JobHeadDBModel;
import com.database.model.MapJobProblemDBModel;

public class CreateJobAPITestWithFakerData {

	private CreateJobPayload createJobPayload;
	private final static String COUNTRY = "India";

	@BeforeMethod(description = "Creating the create job api request payload")
	public void setup() {

		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();

	}

	@Test(description = "Verify if the create job api is able to create Inwarranty job", groups = { "api", "regression",
			"smoke" })
	public void createJobAPITest() {

		int customerId = given()
			.spec(requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
			.post("/job/create")
		.then()
			.spec(responseSpec_OK())
			.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
			.body("data.job_number", startsWith("JOB_"))
			.extract().body().jsonPath().getInt("data.tr_customer_id");
		
		Customer expectedCustomerData = createJobPayload.customer();
		CustomerDBModel actualCustomerDataInDB = CustomerDao.getCustomerInfo(customerId);
		
		Assert.assertEquals(expectedCustomerData.first_name(), actualCustomerDataInDB.getFirst_name());
		Assert.assertEquals(expectedCustomerData.last_name(), actualCustomerDataInDB.getLast_name());
		Assert.assertEquals(expectedCustomerData.mobile_number(), actualCustomerDataInDB.getMobile_number());
		Assert.assertEquals(expectedCustomerData.mobile_number_alt(), actualCustomerDataInDB.getMobile_number_alt());
		Assert.assertEquals(expectedCustomerData.email_id(), actualCustomerDataInDB.getEmail_id());
		Assert.assertEquals(expectedCustomerData.email_id_alt(), actualCustomerDataInDB.getEmail_id_alt());	
		
		CustomerAddressDBModel customerAddressDataFromDB =  CustomerAddressDao.getCustomerAddressInfo(actualCustomerDataInDB.getTr_customer_address_id());
		
		Assert.assertEquals(createJobPayload.customer_address().flat_number(), customerAddressDataFromDB.getFlat_number());
		Assert.assertEquals(createJobPayload.customer_address().apartment_name(), customerAddressDataFromDB.getApartment_name());
		Assert.assertEquals(createJobPayload.customer_address().street_name(), customerAddressDataFromDB.getStreet_name());
		Assert.assertEquals(createJobPayload.customer_address().landmark(), customerAddressDataFromDB.getLandmark());
		Assert.assertEquals(createJobPayload.customer_address().area(), customerAddressDataFromDB.getArea());
		Assert.assertEquals(createJobPayload.customer_address().pincode(), customerAddressDataFromDB.getPincode());
		Assert.assertEquals(createJobPayload.customer_address().country(), customerAddressDataFromDB.getCountry());
		Assert.assertEquals(createJobPayload.customer_address().state(), customerAddressDataFromDB.getState());
		
		JobHeadDBModel jobHeadDataFromDB = JobHeadDao.getDataFromJobHead(customerId);
		Assert.assertEquals(createJobPayload.mst_service_location_id(), jobHeadDataFromDB.getMst_service_location_id());
		Assert.assertEquals(createJobPayload.mst_platform_id(), jobHeadDataFromDB.getMst_platform_id());
		Assert.assertEquals(createJobPayload.mst_warrenty_status_id(), jobHeadDataFromDB.getMst_warrenty_status_id());
		Assert.assertEquals(createJobPayload.mst_oem_id(), jobHeadDataFromDB.getMst_oem_id());
		
	}

}
