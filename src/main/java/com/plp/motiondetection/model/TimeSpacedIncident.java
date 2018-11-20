package com.plp.motiondetection.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

public class TimeSpacedIncident {
	
	private int noOfOccurances;
	private List<Incident> events;
	private Date startDateTime;
	private Date endDateTime;
	private int timeGapMunites;
	private List<String> instanceNames;
	private boolean hasIncidentSerieses;
	private HashMap<String,List<IncidentSeries>> instanceIncidentSerieses = new HashMap<String,List<IncidentSeries>>();
	
	private HashMap<String,List<Incident>> instanceIncidents = new HashMap<String,List<Incident>>();
	
	public int getNoOfOccurances() {
		return noOfOccurances;
	}
	
	public void setNoOfOccurances(int noOfOccurances) {
		this.noOfOccurances = noOfOccurances;
	}
	
	public List<Incident> getMotions() {
		return events;
	}
	
	public void setMotions(List<Incident> motions) {
		this.events = motions;
		
		for (Incident motion: motions){
			List<Incident> currentInstanceMotions = instanceIncidents.get(motion.getInstanceName());
			
			if (currentInstanceMotions == null)
				currentInstanceMotions = new ArrayList<Incident>();
			
			currentInstanceMotions.add(motion);
			instanceIncidents.put(motion.getInstanceName(), currentInstanceMotions);
		}
	}
	
	public  Date getStartDateTime() {
		return startDateTime;
	}
	
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	public  int getTimeGapMunites() {
		return timeGapMunites;
	}
	
	public  void setTimeGapMunites(int timeGapMunites) {
		this.timeGapMunites = timeGapMunites;
	}
	
	public  Date getEndDateTime() {
		return endDateTime;
	}
	
	public  void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public List<String> getInstanceNames() {
		return instanceNames;
	}

	public void setInstanceNames(List<String> instanceNames) {
		this.instanceNames = instanceNames;
	}

	public List<Incident> getIncidentsByInstanceName(String instanceName) {
		return instanceIncidents.get(instanceName);
	}

	public boolean isHasIncidentSerieses() {
		return hasIncidentSerieses;
	}

	public void setHasIncidentSerieses(boolean hasIncidentSerieses) {
		this.hasIncidentSerieses = hasIncidentSerieses;
	}

	public List<IncidentSeries> getIncidentSeriesesByInstanceName(String instanceName){
		return instanceIncidentSerieses.get(instanceName);
	}
	
	public long getTotalIncidentSeriesMinutesByIncidentName(String currentInstanceName){
		long totalMinutes = 0;
		for (IncidentSeries incidentSeries : this.getIncidentSeriesesByInstanceName(currentInstanceName)){
			 totalMinutes += (incidentSeries.getEndtDateTime().getTime() / 60000) - 
					 (incidentSeries.getStartDateTime().getTime()/60000);
		}
		return totalMinutes;
	}


	public void setIncidentSerieses(List<IncidentSeries> instanceIncidentSerieses) {
		
		for (IncidentSeries incidentSeries: instanceIncidentSerieses){
			List<IncidentSeries> currentIncidentSerieses = this.instanceIncidentSerieses.get(incidentSeries.getInstanceName());
			
			if (currentIncidentSerieses == null)
				currentIncidentSerieses = new ArrayList<IncidentSeries>();
			
			currentIncidentSerieses.add(incidentSeries);
			this.instanceIncidentSerieses.put(incidentSeries.getInstanceName(), currentIncidentSerieses);
		}
	}
}
