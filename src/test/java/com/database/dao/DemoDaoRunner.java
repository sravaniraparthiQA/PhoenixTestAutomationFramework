package com.database.dao;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.request.model.Customer;
import com.database.model.CustomerDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {

		CustomerDBModel customerDBData = CustomerDao.getCustomerInfo();
		System.out.println(customerDBData);
		System.out.println(customerDBData.getFirst_name());
		System.out.println(customerDBData.getLast_name());
		System.out.println(customerDBData.getMobile_number());
		
		Customer customer = new Customer("Sravani", "Kumar", "9100127655", "", "RahulKumar123@gmail.com", "");
		System.out.println(customer.first_name());
		Assert.assertEquals(customerDBData.getFirst_name(), customer.first_name());
	}

}
