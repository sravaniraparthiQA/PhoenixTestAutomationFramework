package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;

public class ExcelReaderUtil2 {

	public static Iterator<UserCredentials> loadTestData() {
		// APACHE POI OOXML LIBRARY

		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/PhoenixTestData.xlsx");
		XSSFWorkbook myWorkbook = null;
		
		try {
			myWorkbook = new XSSFWorkbook(is);
		
		} catch (IOException e) {
			e.printStackTrace();
		}

		// focus on sheet now
		XSSFSheet mySheet = myWorkbook.getSheet("LoginTestData");

		// Read the Excel file ----> Stored in he ArrayList<UserCredentials>

		// I want to know the indexes for the username and password in our sheet!

		XSSFRow headerRows = mySheet.getRow(0);

		int usernameIndex = -1;
		int passwordIndex = -1;

		for (Cell cell : headerRows) {

			if (cell.getStringCellValue().trim().equalsIgnoreCase("username")) {
				usernameIndex = cell.getColumnIndex();
			}

			if (cell.getStringCellValue().trim().equalsIgnoreCase("password")) {
				passwordIndex = cell.getColumnIndex();
			}
		}

		System.out.println(usernameIndex + " " + passwordIndex);

		int lastRowIndex = mySheet.getLastRowNum();
		XSSFRow rowData;
		UserCredentials userCredentials;
		ArrayList<UserCredentials> userList = new ArrayList<UserCredentials>();

		for (int rowIndex = 1; rowIndex <= lastRowIndex; rowIndex++) {
			rowData = mySheet.getRow(rowIndex);
			userCredentials = new UserCredentials(rowData.getCell(usernameIndex).toString().trim(),
					rowData.getCell(passwordIndex).toString().trim());
			userList.add(userCredentials);
		}

		return userList.iterator();

	}
}
