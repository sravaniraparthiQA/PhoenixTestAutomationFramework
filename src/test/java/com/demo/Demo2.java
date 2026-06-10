package com.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo2 {
	private static Logger logger = LogManager.getLogger(Demo2.class);

	public static void main(String[] args) {
		System.out.println("Inside the main method");
		logger.info("Inside the main method");

		int a = 10;
		logger.info("value of a is {}", a);

		int b = 0;
		if (b == 0) {
			logger.warn("Value of b is {}", b);
		} else {
			logger.info("value of b is {}", b);
		}
		try {
			int result = a / b;
			logger.info("value of result is {}", result);
		} catch (ArithmeticException e) {
			logger.error("operation cannot perfome", e);
		}
	}

}
