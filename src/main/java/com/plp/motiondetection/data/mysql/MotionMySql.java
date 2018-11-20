package com.plp.motiondetection.data.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.plp.motiondetection.config.Configuration;
import com.plp.motiondetection.data.MotionData;
import com.plp.motiondetection.model.Incident;

public class MotionMySql implements MotionData{
	
	static String url = Configuration.DB_URL_INCIDENT;
	static String user = Configuration.DB_USER_INCIDENT;
	static String pass = Configuration.DB_PWD_INCIDENT;
	
	

	@Override
	public void save(Incident motion){
		try(Connection conn = DriverManager.getConnection (url, user, pass);){
			
			PreparedStatement insertMotionStatement = conn.prepareStatement("CALL sp_motion_insert(?,?,?,?,?,?,?)");
			
			insertMotionStatement.setString(1,motion.getRegionCoordinates());
			insertMotionStatement.setFloat(2,motion.getNumberOfChanges());
			insertMotionStatement.setLong(3,motion.getTimestamp());
			insertMotionStatement.setString(4,motion.getMicroseconds());
			insertMotionStatement.setLong(5,motion.getToken());
			insertMotionStatement.setString(6,motion.getPathToImage());
			insertMotionStatement.setString(7,motion.getInstanceName());
			
			insertMotionStatement.executeUpdate();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Incident> getMotions(long startTimestamp, long endTimestamp){
		
		List<Incident> montions = new ArrayList<Incident>();
		
		try(Connection conn = DriverManager.getConnection (url, user, pass);){
		
			PreparedStatement seletMotionStatement = conn.prepareStatement("CALL sp_select_motion_by_timestamperange(?,?)");
			
			seletMotionStatement.setLong(1, startTimestamp);
			seletMotionStatement.setLong(2, endTimestamp);
			
			ResultSet results =  seletMotionStatement.executeQuery();
			
			while (results.next()) {
				Incident motion = new Incident();
				
				motion.setRegionCoordinates(results.getString("region_coordinates"));
				motion.setNumberOfChanges(results.getLong("number_of_changes"));
				motion.setTimestamp(results.getLong("timestamp"));
				motion.setMicroseconds(results.getString("microseconds"));
				motion.setInstanceName(results.getString("instance_name"));
				motion.setPathToImage(results.getString("path_to_image"));
				motion.setCreateDate(results.getDate("create_date"));
				
				montions.add(motion);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return montions;
	}

	@Override
	public List<Incident> getLastIncidents() {
		List<Incident> incidents = new ArrayList<Incident>();
		
		try(Connection conn = DriverManager.getConnection (url, user, pass);){
		
			PreparedStatement seletMotionStatement = conn.prepareStatement("CALL sp_select_last_incidents_image()");
						
			ResultSet results =  seletMotionStatement.executeQuery();
			
			while (results.next()) {
				Incident incident = new Incident();
				
				incident.setInstanceName(results.getString("instance_name").toLowerCase());
				incident.setCreateDate(results.getTimestamp("create_date"));
				incident.setPathToImage(results.getString("path_to_image"));
				
				incidents.add(incident);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return incidents;
	}
}
