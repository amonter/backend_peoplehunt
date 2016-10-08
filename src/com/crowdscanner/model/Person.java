package com.crowdscanner.model;

import java.util.Map;
import java.util.HashMap;

import com.myownmotivator.model.profile.Profile;

public class Person {
	private Profile profile;
	private Map looking, providing;	//for faster matches
	
	
	public Person(){
		looking = null;
		providing = null;
		profile = null;
	}
	
	public Person(Profile p){
		looking = new HashMap();
		providing = new HashMap();
		profile = p;
		//now get the lookingFor and providingFor answers
		setup();
	}
	
	
	//PRIVATE
	private void setup(){
		
	}
}