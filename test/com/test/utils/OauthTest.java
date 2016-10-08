package com.test.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;


public class OauthTest {
	
	public static void main(String[] args){
		
		
		final String requestTokenUrl = "https://api.efactor.com/oauth/request_token";
		final String authorizeUrl = "https://api.efactor.com/oauth/authorize";
		final String accessTokenUrl = "https://api.efactor.com/oauth/access_token";
		
		// All the pairs here match one another 
		final List<String> requests = new ArrayList<String>();
		
		//these will just match
		//requests.add("http://127.0.0.1:7000/rest/pairinghuntmatching/?myhuntid=54108&bundleId=162&selectedtags=10039");
		requests.add("https://api.efactor.com/oauth/request_token");
		//requests.add("http://127.0.0.1:7000/rest/pairinghuntmatching/?myhuntid=5995&bundleId=162&selectedtags=10041");//pair one match	
		
		for (final String theRequest : requests) {	
		
				new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							
						
							
							OAuthService service = new ServiceBuilder()
	                           .provider(EfactorApi.class)	                        
	                           .apiKey("a52bf1268fea0843b3859e221aed586a04fd600d2")
	                           .apiSecret("6ab9789a2e17103812215a04070fa72d")
	                           .callback("http://crowdscanner.com")
	                           .debug() // here!
	                           .build();
							
							Token requestToken = service.getRequestToken();
							String authUrl = service.getAuthorizationUrl(requestToken);
							System.out.println(authUrl+"?oauth_token="+requestToken.getToken());
							 
							/*
							 URL address = new URL(theRequest);							 
							 URLConnection conn = address.openConnection();		
							 
							 // Get the response -this has to be here
							 BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							 String line;
							 while ((line = rd.readLine()) != null) {
								 System.out.println(line);
							  }	
							  rd.close();
							*/
						
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}				
						

				}				
			}).start();
				
		}	
	}
}
