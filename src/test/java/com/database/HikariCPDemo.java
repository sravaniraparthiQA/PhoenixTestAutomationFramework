package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPDemo {

	public static void main(String[] args) throws SQLException {

		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(ConfigManager.getProperty("DB_URL"));
		hikariConfig.setUsername(ConfigManager.getProperty("DB_USER_NAME"));
		hikariConfig.setPassword(ConfigManager.getProperty("DB_PASSWORD"));
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMinimumIdle(2);
		hikariConfig.setConnectionTimeout(10000); // 10sec
		hikariConfig.setIdleTimeout(10000); // 10sec
		hikariConfig.setMaxLifetime(1800000); // 30mins 30*60*1000=1800000
		hikariConfig.setPoolName("Phoenix Test Automation Framework Pool"); // appears in logs

		HikariDataSource ds = new HikariDataSource(hikariConfig);
		Connection conn = ds.getConnection();
		System.out.println(conn);

		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT first_name,last_name,mobile_number from tr_customer;");
		while (rs.next()) {
			System.out.println(
					rs.getString("first_name") + " " + rs.getString("last_name") + " " + rs.getString("mobile_number"));
		}

		ds.close(); // removes the warning at DataSource
		// make sure to close the DataSource once after being used

	}

}
