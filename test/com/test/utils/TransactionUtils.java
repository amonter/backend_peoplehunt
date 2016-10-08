package com.test.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

public class TransactionUtils {

	
	//@Test
	public void testTokenixer () {
	
		List<String> counties = new ArrayList<String>();
		counties.add("Carlow");
		counties.add("Cavan");
		counties.add("Clare");
		counties.add("Cork");
		counties.add("Donegal");
		counties.add("Dublin");
		counties.add("Galway");
		counties.add("Kerry");
		counties.add("Kildare");
		counties.add("Kilkenny");
		counties.add("Laois");
		counties.add("Leitrim");
		counties.add("Limerick");
		counties.add("Longford");
		counties.add("Louth");
		counties.add("Mayo");
		counties.add("Meath");
		counties.add("Monaghan");
		counties.add("Offaly");
		counties.add("Roscommon");
		counties.add("Sligo");
		counties.add("Tipperary");
		counties.add("Waterford");
		counties.add("Westmeath");
		counties.add("Wexford");
		counties.add("Wicklow");
		
		String tolen = "hello|aloha|bye";
		
		String [] tokens = tolen.split("\\|");
		System.out.println(tokens[0] + " " +tokens[1]);
		
	}	
	
	
	@Test
	public void testCardDetails () { 
		
		 String ccnum = "6767000000000307"; 
		 String cardType = null;
		 
		   if (Pattern.compile("^4\\d{3}-?\\d{4}-?\\d{4}-?\\d{4}$").matcher(ccnum).matches()) {
		      // Visa: length 16, prefix 4, dashes optional.
			   cardType = "VISA";
		   } else if (Pattern.compile("^5[1-5]\\d{2}-?\\d{4}-?\\d{4}-?\\d{4}$").matcher(ccnum).matches()) {
		      // Mastercard: length 16, prefix 51-55, dashes optional.
			   cardType = "MC";
		   } else if (Pattern.compile("^3[4,7]\\d{13}$").matcher(ccnum).matches()) {
		      // American Express: length 15, prefix 34 or 37.
			   cardType = "AMEX";
		   } else if (Pattern.compile("^3[0,6,8]\\d{12}$").matcher(ccnum).matches()) {
		      // Diners: length 14, prefix 30, 36, or 38.
			   cardType = "DINERS";
		   }
		 
		   System.out.println(cardType);
		  
	}
	
}
