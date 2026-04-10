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

import org.testng.Assert;
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
import com.api.response.model.CreateJobResponseModel;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;

import io.restassured.response.Response;

public class CreateJobAPIWithDBValidationTest2 {
	
	private CreateJobPayload createJobPayload;
	private Customer customer;
	private CustomerAddress customerAddress;
	private CustomerProduct customerProduct;
	
	@BeforeMethod(description = "Creating the create job api request payload")
	public void setup() {
		
		customer = new Customer("Sravani", "Raparthi", "9618096697", "", "sravaniraparthis24@gmail.com", "");
		customerAddress = new CustomerAddress("Flat 206", "Pushkardham", "Hanuman Nagar", "Morampudi", "Navaram", "533107", "India", "Rajahmundry");
		
		customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "12775811399033", "12775811399033", "12775811399033", 
				getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), 
				Warranty_Status.IN_WARRENTY.getCode(), OEM.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemsList);
		
	}
	
	@Test(description = "Verify if the create job api is able to create Inwarranty job", groups = {"api", "regression", "smoke"})
	public void createJobAPITest() {
	
		CreateJobResponseModel createJobResponseModel = given()
			.spec(requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
			.post("/job/create")
		.then()
			.spec(responseSpec_OK())
			.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message", equalTo("Job created successfully. "))
			.body("data.mst_service_location_id", equalTo(1))
			.body("data.job_number", startsWith("JOB_"))
			.extract().as(CreateJobResponseModel.class);
		
		System.out.println(createJobResponseModel);
		
		int customerId = createJobResponseModel.getData().getTr_customer_id();
		
		CustomerDBModel customerDataFromDB = CustomerDao.getCustomerInfo(customerId);
		System.out.println(customerDataFromDB);
		
		Assert.assertEquals(customer.first_name(), customerDataFromDB.getFirst_name());
		Assert.assertEquals(customer.last_name(), customerDataFromDB.getLast_name());
		Assert.assertEquals(customer.mobile_number(), customerDataFromDB.getMobile_number());
		Assert.assertEquals(customer.mobile_number_alt(), customerDataFromDB.getMobile_number_alt());
		Assert.assertEquals(customer.email_id(), customerDataFromDB.getEmail_id());
		Assert.assertEquals(customer.email_id_alt(), customerDataFromDB.getEmail_id_alt());
		
		CustomerAddressDBModel customerAddressDataFromDB =  CustomerAddressDao.getCustomerAddressInfo(customerDataFromDB.getTr_customer_address_id());
		
		Assert.assertEquals(customerAddress.flat_number(), customerAddressDataFromDB.getFlat_number());
		Assert.assertEquals(customerAddress.apartment_name(), customerAddressDataFromDB.getApartment_name());
		Assert.assertEquals(customerAddress.street_name(), customerAddressDataFromDB.getStreet_name());
		Assert.assertEquals(customerAddress.landmark(), customerAddressDataFromDB.getLandmark());
		Assert.assertEquals(customerAddress.area(), customerAddressDataFromDB.getArea());
		Assert.assertEquals(customerAddress.pincode(), customerAddressDataFromDB.getPincode());
		Assert.assertEquals(customerAddress.country(), customerAddressDataFromDB.getCountry());
		Assert.assertEquals(customerAddress.state(), customerAddressDataFromDB.getState());
		
		int productId = createJobResponseModel.getData().getTr_customer_product_id();
		
		CustomerProductDBModel customerProductDataFromDB = CustomerProductDao.getProductInfoFromDB(productId);
		
		Assert.assertEquals(customerProduct.dop(), customerProductDataFromDB.getDop());
		Assert.assertEquals(customerProduct.serial_number(), customerProductDataFromDB.getSerial_number());
		Assert.assertEquals(customerProduct.imei1(), customerProductDataFromDB.getImei1());
		Assert.assertEquals(customerProduct.imei2(), customerProductDataFromDB.getImei2());
		Assert.assertEquals(customerProduct.popurl(), customerProductDataFromDB.getPopurl());
		Assert.assertEquals(customerProduct.mst_model_id(), customerProductDataFromDB.getMst_model_id());
		
	}

}
