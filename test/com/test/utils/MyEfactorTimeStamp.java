package com.test.utils;

import java.util.Calendar;
import java.util.Random;

import org.scribe.services.TimestampService;

public class MyEfactorTimeStamp implements TimestampService {

	@Override
	public String getNonce() {
		// TODO Auto-generated method stub
		String theNonce = String.valueOf(new Random().nextInt());		
		return theNonce;
	}

	@Override
	public String getTimestampInSeconds() {
		// TODO Auto-generated method stub
		Calendar theCalendar = Calendar.getInstance();		
		return String.valueOf(theCalendar.getTimeInMillis());
	}

}
