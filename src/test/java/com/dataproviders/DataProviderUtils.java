package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.services.AuthService;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.ExcelReaderUtil;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.database.dao.CreateJobPayloadDataDao;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {

	private static final Logger LOGGER = LogManager.getLogger(DataProviderUtils.class);
	
	@DataProvider(name = "LoginAPIDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {
		LOGGER.info("Loading Data from CSV file testData/LoginCreds.csv");
		
		return CSVReaderUtil.loadCSV("testData/LoginCreds.csv", UserBean.class);

	}

	// DataProvider needs to return something!! - 3things
	// [] - 1D Array
	// [][] - 2D Array
	// Iterator<>

	@DataProvider(name = "CreateJobAPIDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIDataProvider() {
		LOGGER.info("Loading Data from CSV file testData/CreateJobData.csv");
		
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
		LOGGER.info("Generating fake Create job data with the faker count {}",fakerCountInt);
		
		Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
		return payloadIterator;
	}

	@DataProvider(name = "LoginAPIJsonDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIJsonDataProvider() {
		LOGGER.info("Loading Data from JSON file testData/LoginAPITestData.json");
		
		return JsonReaderUtil.loadJson("testData/LoginAPITestData.json", UserBean[].class);

	}

	@DataProvider(name = "CreateJobAPIJsonDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> CreateJobAPIJsonDataProvider() {
		LOGGER.info("Loading Data from JSON file testData/CreateJobAPIData.json");
		
		return JsonReaderUtil.loadJson("testData/CreateJobAPIData.json", CreateJobPayload[].class);

	}

	@DataProvider(name = "LoginAPIExcelDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIExcelDataProvider() {
		LOGGER.info("Loading Data from Excel file testData/PhoenixTestData.xlsx and sheetName is LoginTestData");
		
		return ExcelReaderUtil.loadTestData("testData/PhoenixTestData.xlsx", "LoginTestData", UserBean.class);

	}

	@DataProvider(name = "CreateJobAPIExcelDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> CreateJobAPIExcelDataProvider() {
		LOGGER.info("Loading Data from Excel file testData/PhoenixTestData.xlsx and sheetName is CreateJobTestData");
		
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

	@DataProvider(name = "CreateJobAPIDBDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> CreateJobAPIDBDataProvider() {
		LOGGER.info("Loading Data from Database for CreateJobPayload");

		
		List<CreateJobBean> beanList = CreateJobPayloadDataDao.getCreateJobPayloadData();
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();

		for (CreateJobBean createJobBean : beanList) {
			CreateJobPayload payload = CreateJobBeanMapper.mapper(createJobBean);
			payloadList.add(payload);
		}

		return payloadList.iterator();
	}

}
