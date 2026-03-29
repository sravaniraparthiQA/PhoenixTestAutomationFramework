package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;
import com.poiji.bind.Poiji;

public class ExcelReaderUtil2 {

	public static <T> Iterator<T> loadTestData(String sheetName, Class<T> clazz) {
		// APACHE POI OOXML LIBRARY

		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/PhoenixTestData.xlsx");
		XSSFWorkbook myWorkbook = null ;

		try {
			myWorkbook = new XSSFWorkbook(is);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// focus on sheet now
		XSSFSheet mySheet = myWorkbook.getSheet(sheetName); //"LoginTestData"

		List<T> dataList = Poiji.fromExcel(mySheet, clazz);
		
		return dataList.iterator();
	}
}
