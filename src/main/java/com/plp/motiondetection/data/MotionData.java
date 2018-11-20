package com.plp.motiondetection.data;

import java.util.List;

import com.plp.motiondetection.model.Incident;

public interface MotionData {
	
	public void save (Incident motion);
	public List<Incident> getMotions(long startTimestamp, long endTimestamp);
	
	public List<Incident> getLastIncidents();
}
