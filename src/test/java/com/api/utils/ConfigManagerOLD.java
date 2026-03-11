package com.api.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigManagerOLD {
	// WAP to read the Properties file from
	// src/test/resources/config/config.properties
	
	private ConfigManagerOLD() {
		//Private Constructor!!!
	}

	// Special class in java Properties, help us read the properties of project
	private static Properties prop = new Properties();

	static {
		// Load the Properties file using the load()
		File configFile = new File(System.getProperty("user.dir") +File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"config"+File.separator+"config.properties");

		FileReader fileReader = null;
		try {
			fileReader = new FileReader(configFile);
			prop.load(fileReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) throws IOException {

		return prop.getProperty(key);
	}
}
