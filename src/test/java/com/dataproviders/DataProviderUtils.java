package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.ExcelReaderUtil;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {

	@DataProvider(name = "LoginAPIDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {

		return CSVReaderUtil.loadCSV("testData/LoginCreds.csv", UserBean.class);

	}

	// DataProvider needs to return something!! - 3things
	// [] - 1D Array
	// [][] - 2D Array
	// Iterator<>

	@DataProvider(name = "CreateJobAPIDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIDataProvider() {

		Iterator<CreateJobBean> createJobBeanIterator = CSVReaderUtil.loadCSV("testData/CreateJobData.csv",
				CreateJobBean.class);

		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();

		CreateJobBean tempbean;
		CreateJobPayload tempPayload;

		while (createJobBeanIterator.hasNext()) {
			tempbean = createJobBeanIterator.next();
			tempPayload = CreateJobBeanMapper.mapper(tempbean);
			payloadList.add(tempPayload);
		}
		return payloadList.iterator();
	}

	@DataProvider(name = "CreateJobAPIFakerDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> CreateJobAPIFakerDataProvider() {
		String fakerCount = System.getProperty("fakerCount", "5");
		int fakerCountInt = Integer.parseInt(fakerCount);
		Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
		return payloadIterator;
	}

	@DataProvider(name = "LoginAPIJsonDataProvider", parallel = true)
	public static Iterator<UserCredentials> loginAPIJsonDataProvider() {

		return JsonReaderUtil.loadJson("testData/LoginAPITestData.json", UserCredentials[].class);

	}

	@DataProvider(name = "CreateJobAPIJsonDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> CreateJobAPIJsonDataProvider() {

		return JsonReaderUtil.loadJson("testData/CreateJobAPIData.json", CreateJobPayload[].class);

	}

	@DataProvider(name = "LoginAPIExcelDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIExcelDataProvider() {

		return ExcelReaderUtil.loadTestData("testData/PhoenixTestData.xlsx", "LoginTestData", UserBean.class);

	}

	@DataProvider(name = "CreateJobAPIExcelDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> CreateJobAPIExcelDataProvider() {

		Iterator<CreateJobBean> iterator = ExcelReaderUtil.loadTestData("testData/PhoenixTestData.xlsx",
				"CreateJobTestData", CreateJobBean.class);

		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();

		CreateJobBean tempbean;
		CreateJobPayload tempPayload;

		while (iterator.hasNext()) {
			tempbean = iterator.next();
			tempPayload = CreateJobBeanMapper.mapper(tempbean);
			payloadList.add(tempPayload);
		}
		return payloadList.iterator();

	}

}
