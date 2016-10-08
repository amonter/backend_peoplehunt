package com.test.service;

import org.junit.Test;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class MatchMadeTwitterPostTest {
	
	@Test
	public void matchMade(){
		try{
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.updateStatus("Testing our new service :-)");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
