package com.plp.motiondetection.model;

import java.util.Date;
import java.util.List;

public class IncidentSeries {
	
	private Date startDateTime;
	private Date endtDateTime;
	private String instanceName;
	private List<Incident> incidents;
	private int intervalLimit;
	
	public Date getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	public Date getEndtDateTime() {
		return endtDateTime;
	}
	public void setEndtDateTime(Date endtDateTime) {
		this.endtDateTime = endtDateTime;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public List<Incident> getIncidents() {
		return incidents;
	}
	public void setIncidents(List<Incident> incidents) {
		this.incidents = incidents;
	}
	public int getIntervalLimit() {
		return intervalLimit;
	}
	public void setIntervalLimit(int intervalLimit) {
		this.intervalLimit = intervalLimit;
	}
	
}
