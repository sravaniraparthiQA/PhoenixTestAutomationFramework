package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.services.AuthService;
import com.database.DatabaseManager;
import com.database.model.JobHeadDBModel;

public class JobHeadDao {
	
	private static final Logger LOGGER = LogManager.getLogger(JobHeadDao.class);

	private static final String JOB_HEAD_QUERY = """
			SELECT *
			FROM tr_job_head
			where tr_customer_id = ?
			""";

	private JobHeadDao() {

	}

	public static JobHeadDBModel getDataFromJobHead(int tr_customer_id) {

		JobHeadDBModel jobHeadDBModel = null;
		try {
			LOGGER.info("Getting the Connection from the Database Manager");
			
			Connection conn = DatabaseManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(JOB_HEAD_QUERY);
			ps.setInt(1, tr_customer_id);
			LOGGER.info("Executing the SQL Query {}",JOB_HEAD_QUERY);
			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				jobHeadDBModel = new JobHeadDBModel(
						rs.getInt("id"), 
						rs.getString("job_number"),
						rs.getInt("tr_customer_id"), 
						rs.getInt("tr_customer_product_id"),
						rs.getInt("mst_service_location_id"), 
						rs.getInt("mst_platform_id"), 
						rs.getInt("mst_warrenty_status_id"), 
						rs.getInt("mst_oem_id"));
			}
		} 
		
		catch (SQLException e) {
			LOGGER.error("Cannot convert the result set to JobHeadDBModel bean",e);

			e.printStackTrace();
		}

		return jobHeadDBModel;
	}

}
