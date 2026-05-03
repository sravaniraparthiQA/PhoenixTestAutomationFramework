package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;
import com.api.request.model.Detail;
import com.api.services.DashboardService;
import com.api.utils.SpecUtil;

public class DetailsAPITest {
	
	private DashboardService dashboardService;
	private Detail detailPayload;
	
	@BeforeMethod(description = "Instantiating the Dashboard Service and creating detail payload")
	public void setup() {
		dashboardService = new DashboardService();
		detailPayload = new Detail("created_today");
	}
	
	@Test(description = "Verify if the deatils api is giving correct response", groups = {"api", "regression", "smoke"})
	
	public void detailAPITest() {
		dashboardService.details(FD, detailPayload)
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("message", equalTo("Success"));
			
	}
}
