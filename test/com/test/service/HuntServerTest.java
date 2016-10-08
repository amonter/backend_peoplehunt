package com.test.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class HuntServerTest  {
	
	
	public static void main(String[] args) {
		
		// All the pairs here match one another 
		final List<String> requests = new ArrayList<String>();
		//requests.add("http://dev.crowdscanner.com:80/rest/retrievehunterprofile/?profileid=80239&bundleid=162");
		//requests.add("http://prod.crowdscanner.com/addusertobundle/?bundleid=178&profileid=15338&action=0");
		//requests.add("http://prod.crowdscanner.com/addusertobundle/?bundleid=178&profileid=91964&action=0");
		//requests.add("http://prod.crowdscanner.com/addusertobundle/?bundleid=178&profileid=90023&action=0");
		
		requests.add("http://dev.crowdscanner.com/rest/retrievehunterprofile/?profileid=");//pair one match
		/*
		requests.add("http://74.50.62.220:7000/rest/pairinghuntmatching/?myhuntid=50786&bundleId=148&selectedtags=7482");
		requests.add("http://74.50.62.220:7000/rest/pairinghuntmatching/?myhuntid=32630&bundleId=148&selectedtags=7482");//pair two
		requests.add("http://74.50.62.220:7000/rest/pairinghuntmatching/?myhuntid=19003&bundleId=148&selectedtags=7483");
		requests.add("http://74.50.62.220:7000/rest/pairinghuntmatching/?myhuntid=79962&bundleId=148&selectedtags=7483");//pair three	
		*/
		
		for (final String theRequest : requests) {	
		
				new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							
							URL address = new URL(theRequest);							 						 
							URLConnection conn = address.openConnection();	
							conn.setDoOutput(true);
							//OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
							 BufferedReader rd = new BufferedReader( new InputStreamReader(conn.getInputStream(), "UTF-8"));
							 String line;
							 while ((line = rd.readLine()) != null) {
								 System.out.println(line);
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
			}).start();
				
				
				try {
					Thread.sleep(0);//Sleeping for just one second
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		}	
	}	
}
	
	
	
	
	

