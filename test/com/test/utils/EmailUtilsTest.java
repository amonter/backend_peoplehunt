package com.test.utils;

import junit.framework.TestCase;

import org.apache.commons.lang.StringEscapeUtils;

public class EmailUtilsTest extends TestCase {

	
	public DummyObject dummyObject;	
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();		
		dummyObject = new DummyObject("one","two",23);
	}	
	
	public void testBeanMap() {

		System.out.println(StringEscapeUtils.escapeJavaScript("<object stomp='aloha'>"));
		
	}		
}
