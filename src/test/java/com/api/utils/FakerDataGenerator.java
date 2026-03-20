package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDataGenerator {
	// Util class

	private static Faker faker = new Faker(new Locale("en-IND"));
	private final static String COUNTRY = "India";
	private final static Random random = new Random();
	private final static int MST_SERVICE_LOCATION_ID = 0;
	private final static int MST_PLATFORM_ID = 2;
	private final static int MST_WARRENTY_STATUS_ID = 1;
	private final static int MST_OEM_ID = 1;
	private final static int PRODUCT_ID = 1;
	private final static int MST_MODEL_ID = 1;
	
	private final static int VALID_PROBLEMS_ID[] = {1,2,3,4,5,6,7,8,9,10,11,12,15,16,17,19,20,22,24,26,28,29};

	private FakerDataGenerator() {
		// private constructor!
	}

	public static CreateJobPayload generateFakeCreateJobData() {
		Customer customer = generateFakeCustomerData();
		CustomerAddress customerAddress = generateFakeCustomerAddressData();
		CustomerProduct customerProduct = generateFakeCustomerProductData();
		List<Problems> problemsList = generateFakeProblemsList();
		CreateJobPayload payload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
				MST_WARRENTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemsList);
		return payload;
	}

	public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {

		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();

		for (int i = 1; i <= count; i++) {
			Customer customer = generateFakeCustomerData();
			CustomerAddress customerAddress = generateFakeCustomerAddressData();
			CustomerProduct customerProduct = generateFakeCustomerProductData();
			List<Problems> problemsList = generateFakeProblemsList();
			CreateJobPayload payload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
					MST_WARRENTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemsList);
			payloadList.add(payload);
		}
		
		return payloadList.iterator();
	}

	private static List<Problems> generateFakeProblemsList() {

		int randomIndex = random.nextInt(VALID_PROBLEMS_ID.length);
		String fakeRemark = faker.lorem().sentence(10);

		Problems problems = new Problems(VALID_PROBLEMS_ID[randomIndex], fakeRemark);

		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);

		return problemsList;
	}

	private static CustomerProduct generateFakeCustomerProductData() {

		String dop = DateTimeUtil.getTimeWithDaysAgo(10);
		String imeiSerialNumber = faker.numerify("##############");
		String popUrl = faker.internet().url();

		CustomerProduct customerProduct = new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber,
				popUrl, PRODUCT_ID, MST_MODEL_ID);

		return customerProduct;
	}

	private static CustomerAddress generateFakeCustomerAddressData() {

		String flatNumber = faker.numerify("###");
		String apartmentName = faker.address().streetName();
		String streetName = faker.address().streetName();
		String landmark = faker.address().streetName();
		String area = faker.address().streetName();
		String pincode = faker.numerify("#####");
		String state = faker.address().state();

		CustomerAddress customerAddress = new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area,
				pincode, COUNTRY, state);

		return customerAddress;
	}

	private static Customer generateFakeCustomerData() {

		String fName = faker.name().firstName();
		String lName = faker.name().lastName();
		String mobileNumber = faker.numerify("91########");
		String altMobileNumber = faker.numerify("96########");
		String customerEmailAddress = faker.internet().emailAddress();
		String altCustomerEmailAddress = faker.internet().emailAddress();

		Customer customer = new Customer(fName, lName, mobileNumber, altMobileNumber, customerEmailAddress,
				altCustomerEmailAddress);

		return customer;
	}

}
