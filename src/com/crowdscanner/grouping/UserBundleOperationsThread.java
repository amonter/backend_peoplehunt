package com.crowdscanner.grouping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class UserBundleOperationsThread extends Thread {
	
	final static Logger logger = Logger.getLogger(UserBundleOperationsThread.class);
	
	private Integer bundleId;
	
	private Integer profileId;
	
	private Integer action;
	
	
	public UserBundleOperationsThread(Integer bundleid, Integer profileId, Integer action) {
		this.bundleId = bundleid;
		this.profileId = profileId;
		this.action = action; 
	}
		
	
	@Override
	public void run() {
		
		try {
			
			//send request to huntserver		 
			String theUrl = String.format( "http://prod.crowdscanner.com/addusertobundle/?bundleid=%1$d&profileid=%2$d&action=%3$d",  bundleId, profileId, action);
			URL address = new URL(theUrl);		
			URLConnection conn = address.openConnection();			
			 
			 // Get the response -this has to be here
			 BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			 String line;
			 while ((line = rd.readLine()) != null) {
				 logger.info("User added to bundle "+line+" "+bundleId);
			  }	
			  rd.close();
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
