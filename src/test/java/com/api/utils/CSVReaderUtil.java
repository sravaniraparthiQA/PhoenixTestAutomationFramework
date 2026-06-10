package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	private static final Logger LOGGER = LogManager.getLogger(CSVReaderUtil.class);

	private CSVReaderUtil() {
		// No one can create object of CSVReaderUtil Outside the class
		// Singleton Class Constructors are private
	}

	public static <T> Iterator<T> loadCSV(String pathOfCSVFile, Class<T> bean) {
		
		LOGGER.info("Loading the CSV file from the path {}",pathOfCSVFile);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);
		
		LOGGER.info("Converting the CSV to the Bean class {}", bean);
		CsvToBean<T> csvToBean = new CsvToBeanBuilder(csvReader)
				.withType(bean)
				.withIgnoreEmptyLine(true)
				.build();

		List<T> list = csvToBean.parse();
		return list.iterator();
		
	}

}
