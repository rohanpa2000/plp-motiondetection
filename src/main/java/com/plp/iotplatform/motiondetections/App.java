package com.plp.iotplatform.motiondetections;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.plp.motiondetection.business.MotionBusiness;
import com.plp.motiondetection.model.CostedIncidentSeries;
import com.plp.motiondetection.model.IncidentSeries;
import com.plp.motiondetection.model.TimeSpacedIncident;

/**
 * Hello world!
 *
 */
public class App 
{
    static MotionBusiness motionBusiness = new MotionBusiness(); 
    
    public static void main( String[] args ) throws ParseException, InstantiationException, IllegalAccessException, java.text.ParseException{
        Test1();
    }
    
	private static void Test2() throws java.text.ParseException {
		
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
        
        Date startDate = df.parse("24/10/2018 00:00");
        Date endDate = df.parse("25/10/2018 00:00");
        
        List<CostedIncidentSeries> costedIncidentSerieses = motionBusiness.getCalculatedIncidents(
        																	startDate, 
        																	endDate, 
        																	30,
        																	2,
        																	8,
        																	60, 
        																	350, 
        																	200,
        																	14);
        
        SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sp = new SimpleDateFormat("h:mma");
        SimpleDateFormat sp1 = new SimpleDateFormat("h:mm:ssa");

        for (CostedIncidentSeries costedIncidentSeries : costedIncidentSerieses){
        	
			System.out.println(	
					spd.format(costedIncidentSeries.getStartDateTime()) + "," +
					costedIncidentSeries.getInstanceName() + "," +
					sp.format(costedIncidentSeries.getStartDateTime()) + "," +  
					sp.format(costedIncidentSeries.getEndtDateTime())  + "," +
					costedIncidentSeries.getTotalMinutes()   + "," +
					costedIncidentSeries.getTotalCost());
        }
	}

	private static void Test1() throws java.text.ParseException {
	    
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
        
        Date startDate = df.parse("24/10/2018 00:00");
        Date endDate = df.parse("25/10/2018 00:00");
        
        List<TimeSpacedIncident> Motions = motionBusiness.getIncidents(startDate, endDate, 60,2,8);

        SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sp = new SimpleDateFormat("h:mma");
        SimpleDateFormat sp1 = new SimpleDateFormat("h:mm:ssa");
        
        
        for (TimeSpacedIncident timeSpacedIncident: Motions){
        	
        	for (String instanceName : timeSpacedIncident.getInstanceNames()){
        		
        		       		
        		if (timeSpacedIncident.getIncidentSeriesesByInstanceName(instanceName) != null){
        			for (IncidentSeries incidentSeries : timeSpacedIncident.getIncidentSeriesesByInstanceName(instanceName)){
 	        			
        				
        				System.out.println(	
        									spd.format(timeSpacedIncident.getStartDateTime()) + "," +
        									sp.format(timeSpacedIncident.getStartDateTime()) + "," +  
        									sp.format(timeSpacedIncident.getEndDateTime())  + "," +
        									incidentSeries.getInstanceName() +","+sp1.format(incidentSeries.getStartDateTime()) + "," +
	        								sp1.format(incidentSeries.getEndtDateTime()) + "," +
	        								incidentSeries.getIncidents().size()
	        					);
        			}
        		}
        	}
        }
	}
}
