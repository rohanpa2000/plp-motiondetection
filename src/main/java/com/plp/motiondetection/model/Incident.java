package com.plp.motiondetection.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Date;

public class Incident {
	
	//The JSON object looks like
	/*	"regionCoordinates" : [618, 317, 703, 493],
	"numberOfChanges" : 5446,
	"timestamp" : "1465894497",
	"microseconds" : "5-97451",
	"token" : 695,
	"pathToImage" : "1465894497_5-97451_frontdoor_618-317-703-493_5446_695.jpg",
	"instanceName" : "frontdoor"	
	*/
	
	private String regionCoordinates = "";
	private long numberOfChanges;
	private long timestamp;
	private String microseconds;
	private long token;
	private String pathToImage;
	private String instanceName;
	private Date createDate;
	
	public static Incident getFromJsonText(String jsonText) throws ParseException{
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(jsonText);
	      
		return getFromJsonObject(jsonObject);
	}
	
	public static Incident getFromJsonObject(JSONObject motionJson){
		
		Incident motion = new Incident();
		
		motion.setRegionCoordinates(((JSONArray) motionJson.get("regionCoordinates")));
		motion.setNumberOfChanges((Long) motionJson.get("numberOfChanges"));
		motion.setTimestamp((String)motionJson.get("timestamp"));
		motion.setMicroseconds((String) motionJson.get("microseconds"));
		motion.setToken((Long) motionJson.get("token"));
		motion.setPathToImage((String) motionJson.get("pathToImage"));
		motion.setInstanceName((String) motionJson.get("instanceName"));
        
		return motion;
	}

	
    @Override
    public String toString() {
    	
        return  "regionCoordinates: " + getRegionCoordinates() + "\r" +
        	    "numberOfChanges: " + numberOfChanges + "\r" +
                "timestamp: " + timestamp + "\r" +
                "microseconds: " + microseconds  + "\r" +
                "token: " + token + "\r" +
                "pathToImage: " + pathToImage + "\r" +
                "instanceName: " + instanceName  + "\r" + 
                "CreateDate: " + createDate;
    }
    
    public void setCreateDate(Date createDate){
    	this.createDate = createDate;
    }
    
    public Date getCreateDate(){
    	return this.createDate;
    }

	public long getNumberOfChanges() {
		return numberOfChanges;
	}

	public void setNumberOfChanges(long numberOfChanges) {
		this.numberOfChanges = numberOfChanges;
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp =  Long.parseLong(timestamp);
	}


	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMicroseconds() {
		return microseconds;
	}

	public void setMicroseconds(String microseconds) {
		this.microseconds = microseconds;
	}

	public long getToken() {
		return token;
	}

	public void setToken(long token) {
		this.token = token;
	}

	public String getPathToImage() {
		return pathToImage;
	}

	public void setPathToImage(String pathToImage) {
		this.pathToImage = pathToImage;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	
	public String getRegionCoordinates() {
		
		return this.regionCoordinates;
	}
	
	public void setRegionCoordinates(String regionCoordinates) {
		
		this.regionCoordinates = regionCoordinates;
		
	}

	public void setRegionCoordinates(JSONArray regionCoordinatesArray) {
		
		for (Object cordinateObj : regionCoordinatesArray){
			
			this.regionCoordinates += "[" + (Long) cordinateObj +"]";
		}
	}


}
