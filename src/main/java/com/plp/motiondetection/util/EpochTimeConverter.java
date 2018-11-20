package com.plp.motiondetection.util;

import java.util.Date;

public class EpochTimeConverter {
	
	public static long DateTimeToEpoch(Date dateTime){
		
		return dateTime.getTime() / 1000;
		
	}
	
	public static Date EpockToDateTime(long epoch){
		
		return new java.util.Date (epoch*1000);
	
	}
}
