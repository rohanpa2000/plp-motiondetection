package com.plp.motiondetection.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
 
public final class Configuration {
	
	public static String DB_URL_INCIDENT;
	public static String DB_USER_INCIDENT;
	public static String DB_PWD_INCIDENT;
	
 	static {
		try (InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream("config.properties")){
			Properties prop = new Properties();
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file config.properties not found in the classpath");
			}
 
			//define all the config properties to be loaded here
			DB_URL_INCIDENT = prop.getProperty("dburl_incident");
			DB_USER_INCIDENT = prop.getProperty("dbuser_incident");
			DB_PWD_INCIDENT = prop.getProperty("dbpwd_incident");
 
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
}