package com.plp.motiondetection.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.plp.motiondetection.data.MotionData;
import com.plp.motiondetection.data.mysql.MotionMySql;
import com.plp.motiondetection.model.TimeSpacedIncident;
import com.plp.motiondetection.model.CostedIncidentSeries;
import com.plp.motiondetection.model.Incident;
import com.plp.motiondetection.model.IncidentSeries;
import com.plp.motiondetection.util.EpochTimeConverter;

public class MotionBusiness {
	
    MotionData motionData = new MotionMySql();
	
	public void save (Incident motion){

		motionData.save(motion); 
	}
	
	public List<Incident> getLastIncidents(){
		
		return motionData.getLastIncidents();
	}
	 
	public List<CostedIncidentSeries> getCalculatedIncidents(	
										Date startDateTime, 
										Date endDateTime, 
										int spaceByMinutes, 
										int timegapIntervalLimit, 
										int postiveIncidentLimit,
			
										int maxTimeInterval, 
										int maxCost, 
										int minCost,
										int costableIncidentLimit
										){
		

		
		List<CostedIncidentSeries> costedIncidentSerieses = new ArrayList<CostedIncidentSeries>();
		
		List<TimeSpacedIncident> timeSpacedIncidents = 
				getIncidents (startDateTime, endDateTime, spaceByMinutes, timegapIntervalLimit, postiveIncidentLimit);
		
		Set<String> uniqueInstanceNames = getUniqueIncidentNames(timeSpacedIncidents);
		
		
		for (String currentInstanceName : uniqueInstanceNames){
			
			//currentInstanceName = "Court1";
			
			Date intervalBaseTime = null;
			long totalIntervalMinutes = 0;
			long minutesBetweenIntervals = 0;

			
			if (timeSpacedIncidents.size() > 0)
				intervalBaseTime = timeSpacedIncidents.get(0).getStartDateTime();
			
			for (int index = 0; index< timeSpacedIncidents.size(); ++index){
				
				TimeSpacedIncident timeSpacedIncident = timeSpacedIncidents.get(index);
				
				Date timeSpaceEndTime = timeSpacedIncident.getEndDateTime();
				
				long currentSeriesTotalMinutes = getTimeSpacedIncidentTotalMinutes(currentInstanceName, timeSpacedIncident);
				
				if (currentSeriesTotalMinutes >= costableIncidentLimit ){
					
					totalIntervalMinutes += currentSeriesTotalMinutes;
					
					minutesBetweenIntervals = ((timeSpaceEndTime.getTime()/60000) - (intervalBaseTime.getTime()/60000));
					
					boolean hasNext =  timeSpacedIncidents.size() > index + 1;
					
					if (minutesBetweenIntervals == maxTimeInterval ||
						!hasNext ||
						(hasNext && getTimeSpacedIncidentTotalMinutes(currentInstanceName, timeSpacedIncidents.get(index +1)) < costableIncidentLimit)
						){
							
						CostedIncidentSeries costedIncidentSeries = new CostedIncidentSeries();

						costedIncidentSeries.setCostableSeriesLimit(costableIncidentLimit);
						costedIncidentSeries.setEndtDateTime(timeSpaceEndTime);
						costedIncidentSeries.setStartDateTime(intervalBaseTime);
						costedIncidentSeries.setInstanceName(currentInstanceName);
						costedIncidentSeries.setTotalMinutes(totalIntervalMinutes);
						
						if (minutesBetweenIntervals == maxTimeInterval)
							costedIncidentSeries.setTotalCost(maxCost);
						else
							costedIncidentSeries.setTotalCost(minCost);
						
						costedIncidentSerieses.add(costedIncidentSeries);
						
						if (minutesBetweenIntervals == maxTimeInterval){
							minutesBetweenIntervals = 0;
							totalIntervalMinutes = 0;
							intervalBaseTime = timeSpaceEndTime;
						}
					}
				}
				else{
					minutesBetweenIntervals = 0;
					totalIntervalMinutes = 0;
					intervalBaseTime = timeSpaceEndTime;
				}
			}
		}
		
		return costedIncidentSerieses;
	}

	private long getTimeSpacedIncidentTotalMinutes(String currentInstanceName, TimeSpacedIncident timeSpacedIncident) {
		long totalMinutes;
		if (timeSpacedIncident.getIncidentSeriesesByInstanceName(currentInstanceName) != null){
			totalMinutes = timeSpacedIncident.getTotalIncidentSeriesMinutesByIncidentName(currentInstanceName);
		}
		else{
			totalMinutes = 0;
		}
		
		return totalMinutes;
	}

	private Set<String> getUniqueIncidentNames(List<TimeSpacedIncident> timeSpacedIncidents) {
		Set<String> uniqueInstanceNames = new HashSet<String>();
		for (TimeSpacedIncident timeSpacedIncident : timeSpacedIncidents){
			for (String instanceName : timeSpacedIncident.getInstanceNames())
				uniqueInstanceNames.add(instanceName);
		}
		return uniqueInstanceNames;
	}
	
	
	public List<TimeSpacedIncident> getIncidents (	Date startDateTime, 
												Date endDateTime, 
												int spaceByMinutes, 
												int timegapIntervalLimit, 
												int postiveIncidentLimit)
	{
		List<Incident> allMotions = 
				motionData.getMotions(	EpochTimeConverter.DateTimeToEpoch(startDateTime), 
										EpochTimeConverter.DateTimeToEpoch(endDateTime));
		
		//create date objects spaced by the specified minutes
		List<TimeSpacedIncident> timeSpacedEvents = new ArrayList<TimeSpacedIncident>();
		
		long startTimeInMinutes = (startDateTime.getTime() / 1000L) / 60L;
		long endTimeInMinutes = (endDateTime.getTime() / 1000L) / 60L;
		
		long numberOfIntervals = (endTimeInMinutes - startTimeInMinutes) / spaceByMinutes;
		
		Date startTimeRange = startDateTime;
		Calendar c = Calendar.getInstance();
		c.setTime(startTimeRange);
		c.add(Calendar.MINUTE, spaceByMinutes);
	
		Date endTimeRange = c.getTime();
		
		for (int index = 0; index < numberOfIntervals; ++index){
			TimeSpacedIncident timeSpacedIncident = new TimeSpacedIncident();
			
			timeSpacedIncident.setMotions(getMatchingMotions(startTimeRange, endTimeRange, allMotions));
			timeSpacedIncident.setInstanceNames(getInstanceNames(allMotions));
			timeSpacedIncident.setStartDateTime(startTimeRange);
			timeSpacedIncident.setEndDateTime(endTimeRange);
			timeSpacedIncident.setNoOfOccurances(timeSpacedIncident.getMotions().size());
			timeSpacedIncident.setTimeGapMunites(spaceByMinutes);
			
			
			timeSpacedIncident.setIncidentSerieses(getPositiveIncidents(	timeSpacedIncident, 
																		timegapIntervalLimit, 
																		postiveIncidentLimit));
			
			timeSpacedEvents.add(timeSpacedIncident);
			
			startTimeRange = endTimeRange;
			
			c.setTime(startTimeRange);
			c.add(Calendar.MINUTE, spaceByMinutes);
		
			endTimeRange = c.getTime();
		}
		
		return timeSpacedEvents;
	}
	
	//Private methods
	
	private List<IncidentSeries> getPositiveIncidents(TimeSpacedIncident timeSpaceIncident, int timegapIntervalLimit, int postiveIncidentLimit){
		
		List<IncidentSeries> incidentSerieses = new ArrayList<IncidentSeries>();
		
		for (String instanceName : timeSpaceIncident.getInstanceNames()){
			
			int numberOfIncidents = 0;
			Date footTime = timeSpaceIncident.getStartDateTime();
			Date prevIncidentTime = footTime;
			List<Incident> incidents = new ArrayList<Incident>();
			
			if (timeSpaceIncident.getIncidentsByInstanceName(instanceName) != null){

				for (Incident incident : timeSpaceIncident.getIncidentsByInstanceName(instanceName)){
					Date incidentTime = EpochTimeConverter.EpockToDateTime(incident.getTimestamp());
					
					long timegap = ((incidentTime.getTime()/60000) - (prevIncidentTime.getTime()/60000));
					
					if (timegap <= timegapIntervalLimit){
						numberOfIncidents++;
						prevIncidentTime = incidentTime;
						incidents.add(incident);
						
						if (numberOfIncidents == postiveIncidentLimit){
							IncidentSeries positiveIncident = new IncidentSeries();
							
							positiveIncident.setStartDateTime(footTime);
							positiveIncident.setIncidents(incidents);
							positiveIncident.setInstanceName(instanceName);
							positiveIncident.setIntervalLimit(timegapIntervalLimit);
							positiveIncident.setEndtDateTime(incidentTime);
							
							incidentSerieses.add(positiveIncident);
						}
						if (numberOfIncidents > postiveIncidentLimit){
							IncidentSeries positiveIncident = incidentSerieses.get(incidentSerieses.size()-1);
							positiveIncident.setEndtDateTime(incidentTime);
						
						}
				
					}
					else{
						numberOfIncidents = 1;
						footTime = incidentTime;
						prevIncidentTime = incidentTime;
						incidents = new ArrayList<Incident>();
						incidents.add(incident);
					}
				}
			}
		}
		
		return incidentSerieses;
	}

	
	private List<Incident> getMatchingMotions(Date startTimeRange,Date endTimeRange, List<Incident> allMotions){
		
		List<Incident> matchingMotions = new ArrayList<Incident>();
		
		long epochStart = EpochTimeConverter.DateTimeToEpoch(startTimeRange);
		long epochEnd = EpochTimeConverter.DateTimeToEpoch(endTimeRange);
		
		for (Incident motion : allMotions){
			if (motion.getTimestamp() >= epochStart && motion.getTimestamp() <= epochEnd)
				matchingMotions.add(motion);
				
		}
		
		return matchingMotions;
	}
	
	private List<String> getInstanceNames(List<Incident> allMotions){
		
		List<String> instanceNames = new ArrayList<String>();
		
		for (Incident motion : allMotions){
			if (!instanceNames.contains(motion.getInstanceName()))
				instanceNames.add(motion.getInstanceName());
		}
		
		return instanceNames;
	}
}
