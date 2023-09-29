package com.microservices.java.movies.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
	private static final Logger logger = LogManager.getLogger(Utils.class);

	public static String executiontime(long startTime, long endTime) {
		String executiontime = null;
		try {			
			long difference = new Date(endTime).getTime() - new Date(startTime).getTime();
			
			long diffMiliSeconds = difference % 1000;  
			long diffSeconds = difference / 1000 % 60;  
			long diffMinutes = difference / (60 * 1000) % 60; 
			long diffHours = difference / (60 * 60 * 1000);  
			
			executiontime = String.format("%02d:%02d:%02d.%d", 
					diffHours, diffMinutes, diffSeconds, diffMiliSeconds);
		} catch (Exception e) {
			logger.error(starckTraceToString(e));
		}
		
		return executiontime;
	}
	
	public static String starckTraceToString(Exception ex) {
		String result = ex.getMessage() + "\n";
		result += ex.toString() + "\n";
		StackTraceElement[] trace = ex.getStackTrace();
		for (int i = 0; i < trace.length; i++) {
			result += trace[i].toString() + "\n";
		}
		return result;
	}
	
    public static boolean isValid(String dateStr) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    
    public static String convertDateToString(Date date) {
        try {
        	if(date != null) {
    	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	        sdf.setLenient(false);
                String newDateFormat = sdf.format(date);
                return newDateFormat;
        	} else {
                return null;
        	}
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Date convertStringToDate(String dateStr) {
        try {
        	if(dateStr != null) {
    	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	        sdf.setLenient(false);
    	        Date newDateFormat = sdf.parse(dateStr);
                return newDateFormat;
        	} else {
                return null;
        	}
        } catch (Exception e) {
            return null;
        }
    }
}
