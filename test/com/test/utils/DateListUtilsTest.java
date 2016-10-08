package com.test.utils;

import java.util.Calendar;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class DateListUtilsTest extends TestCase
{
	
	private static final Pattern loginPattern = Pattern.compile("[\\w]{6,25}");
	
	private static final Pattern loginPatternPassword = Pattern.compile("[\\w\\W]{6,25}");

	public void testUserDate(){
		
		Calendar dateTwo = Calendar.getInstance();
		dateTwo.set(Calendar.MONTH, Calendar.JANUARY);
		System.out.println(dateTwo.getTime());
		
		Calendar theCalendar = Calendar.getInstance();
		theCalendar.setTime(dateTwo.getTime());
		theCalendar.roll(Calendar.DAY_OF_MONTH, -7);
		
		System.out.println(theCalendar.getTime());
		
	}
	
}
