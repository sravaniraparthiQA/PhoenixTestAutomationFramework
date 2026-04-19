package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.MapJobProblemDBModel;

public class MapJobProblemDao {
	
	private static final String PROBLEM_QUERY = """
			select * 
			from map_job_problem 
			where tr_job_head_id = ?
			""";
	
	private MapJobProblemDao() {
		
	}
	
	public static MapJobProblemDBModel getProblemDetails(int tr_job_head_id) {
		MapJobProblemDBModel mapJobProblemDBModel = null;
		try {
			Connection conn = DatabaseManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(PROBLEM_QUERY);
			ps.setInt(1, tr_job_head_id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				mapJobProblemDBModel = new MapJobProblemDBModel(rs.getInt("id"), rs.getInt("tr_job_head_id"), rs.getInt("mst_problem_id"), rs.getString("remark"));
			}
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mapJobProblemDBModel;
	}

}
