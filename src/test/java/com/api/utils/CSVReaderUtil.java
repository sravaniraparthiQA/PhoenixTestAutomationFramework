package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtil {
	/*
	 * Constructor is Private
	 * 
	 * static- static methods! Job: Help me read the CSV File and Map it to Bean
	 * 
	 */

	private CSVReaderUtil() {
		// No one can create object of CSVReaderUtil Outside the class
		// Singleton Class Constructors are private
	}

	public static <T> Iterator<T> loadCSV(String pathOfCSVFile, Class<T> bean) {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);
		
		CsvToBean<T> csvToBean = new CsvToBeanBuilder(csvReader)
				.withType(bean)
				.withIgnoreEmptyLine(true)
				.build();

		List<T> list = csvToBean.parse();
		return list.iterator();
		
	}

}
