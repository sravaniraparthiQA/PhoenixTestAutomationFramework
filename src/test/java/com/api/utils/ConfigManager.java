package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {
	// WAP to read the Properties file from
	// src/test/resources/config/config.properties

	// Special class in java Properties, help us read the properties of project
	private static Properties prop = new Properties();
	private static String path = "config/config.properties";
	private static String env;
	private static final Logger LOGGER = LogManager.getLogger(ConfigManager.class);


	private ConfigManager() {
		// Private Constructor!!!
	}

	static {
		LOGGER.info("Reading env value passed from terminal");
		
		if(System.getProperty("env")==null) {
			LOGGER.warn("Env variable is not set...using qa as env");
		}
		env = System.getProperty("env", "qa");
		LOGGER.info("Running the test in env {}", env);
		env = env.toLowerCase().trim();

		switch (env) {
		case "dev" -> path = "config/config.dev.properties";

		case "qa" -> path = "config/config.qa.properties";

		case "uat" -> path = "config/config.uat.properties";

		default -> path = "config/config.qa.properties";
		}
		LOGGER.info("Using the Properties file from the path {}",path);
		
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

		if (input == null) {
			LOGGER.error("Cannot Find the File at the Path {}",path);
			throw new RuntimeException("Cannot Find the File at the Path " + path);
		}

		try {
			prop.load(input);
		} catch (IOException e) {
			LOGGER.info("Something went wrong... please check the file {}",path,e);
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {

		return prop.getProperty(key);
	}
}
