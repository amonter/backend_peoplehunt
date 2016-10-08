package com.test.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import junit.framework.TestCase;

import com.myownmotivator.model.User;

public class TestThreadSet extends TestCase {
	
	
	public void testSet(){
		
		User user = new User();
		user.setCountry("gratnas");
		user.setFirstName("carlitos");
		user.setNewPassword("duglas");
		user.setId(2);
		
		User user2 = new User();
		user2.setCountry("gratnas2");
		user2.setFirstName("carlitos2");
		user2.setNewPassword("duglas2");
		user.setId(4);
		
		User user3 = new User();
		user3.setCountry("gratnas3");
		user3.setFirstName("carlito3");
		user3.setNewPassword("duglas3");
		user.setId(5);
		
		List<User> users = new ArrayList<User>();
		users.add(user);
		users.add(user2);
		users.add(user3);
		
		System.out.println(20 % 5);
		
		//System.out.println(ChainedTilesApplicationContextFactory.DEFAULT_FACTORY_CLASS_NAMES);
	}

}
