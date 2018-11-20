package com.plp.motiondetection.model;

import java.util.Date;
import java.util.List;

public class CostedIncidentSeries {
		
	private Date startDateTime;
	private Date endtDateTime;
	private String instanceName;
	private List<IncidentSeries> incidentSerieses;
	private int costableSeriesLimit;
	private long totalMinutes;
	private int totalCost;
	
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
	public List<IncidentSeries> getIncidentSerieses() {
		return incidentSerieses;
	}
	public void setIncidentSerieses(List<IncidentSeries> incidentSerieses) {
		this.incidentSerieses = incidentSerieses;
	}
	public int getCostableSeriesLimit() {
		return costableSeriesLimit;
	}
	public void setCostableSeriesLimit(int costableSeriesLimit) {
		this.costableSeriesLimit = costableSeriesLimit;
	}
	public long getTotalMinutes() {
		return totalMinutes;
	}
	public void setTotalMinutes(long totalMinutes) {
		this.totalMinutes = totalMinutes;
	}
	public int getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
}
