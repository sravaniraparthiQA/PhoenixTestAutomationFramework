package com.database.dao;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.constant.Model;
import com.api.constant.Product;
import com.api.request.model.Customer;
import com.api.request.model.CustomerProduct;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {

		CustomerProductDBModel customerProductData = CustomerProductDao.getProductInfoFromDB(244838);
		System.out.println(customerProductData);
		
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "12775811399831", "12775811399831", "12775811399831", 
				getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		System.out.println(customerProduct);
	}

}
