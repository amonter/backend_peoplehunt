package com.crowdscanner.controller.utils;

import java.util.ArrayList;
import java.util.List;

public enum KerseyTypesMatch {

	//hero
	Supervisor("Supervisor", "Provider", "Inspector", "Protector", "e,s,t,j", "Hero"),
	Provider("Provider", "Supervisor", "Inspector", "Protector", "e,s,f,j", "Hero"),
	Inspector("Inspector", "Protector", "Provider", "Supervisor", "i,s,t,j", "Hero"),
	Protector("Protector", "Inspector", "Supervisor", "Provider", "i,s,f,j", "Hero"),

	//charmers
	Promoter("Promoter", "Performer", "Crafter", "Composer","e,s,t,p", "Charmer"),
	Performer("Performer", "Promoter", "Crafter", "Composer","e,s,f,p", "Charmer"),
	Crafter("Crafter", "Composer", "Performer", "Performer", "i,s,t,p", "Charmer"),
	Composer("Composer", "Crafter", "Promoter", "Performer", "i,s,f,p", "Charmer"),

	//Idealists
	Teacher("Teacher", "Champion", "Counselor", "Healer","e,n,f,j", "Idealist"),
	Counselor("Counselor", "Healer", "Teacher", "Champion","i,n,f,j", "Idealist"),
	Champion("Champion", "Teacher", "Healer", "Counselor","e,n,f,p", "Idealist"),
	Healer("Healer", "Counselor", "Champion", "Teacher","i,n,f,p", "Idealist"),

	//Pragmatist
	FieldMarshal("FieldMarshal", "Inventor", "Mastermind", "Architect", "e,n,t,j", "Pragmatist"),
	Inventor("Inventor", "FieldMarshal", "Mastermind", "Architect", "e,n,t,p", "Pragmatist"),
	Mastermind("Mastermind", "Architect", "Inventor", "FieldMarshal", "i,n,t,j", "Pragmatist"),
	Architect("Architect", "Mastermind", "Inventor", "FieldMarshal", "i,n,t,p", "Pragmatist");


	private List<String> primaryMatches = new ArrayList<String>();
	private List<String> secondaryMatches = new ArrayList<String>();
	private String[] letters;
	private String stringLetters;
	private String peopleHuntType;
	private KerseyTypesMatch(String type1, String type2, String type3, String type4, String myLetters,
	String peopleHuntString) {

	stringLetters = myLetters.replaceAll(",", "");
	letters = myLetters.split(",");
	primaryMatches.add(type1);
	primaryMatches.add(type2);
	secondaryMatches.add(type3);
	secondaryMatches.add(type4);
	peopleHuntType = peopleHuntString;
	
	}	


	public static KerseyTypesMatch findCharacterByLetters(String code) {
		
		KerseyTypesMatch resultKerseyTypesMatch = null;
		KerseyTypesMatch[] theKerseyTypes = values();
		for (int i = 0; i < theKerseyTypes.length; i++) {
			 String theLetters = theKerseyTypes[i].stringLetters;
			 if (theLetters.equals(code)){
				 resultKerseyTypesMatch = theKerseyTypes[i];
			 }
		}
		
		return resultKerseyTypesMatch;
	}
	

	public String[] getLetters() {
		return letters;
	}


	public void setLetters(String[] letters) {
		this.letters = letters;
	}


	public String getPeopleHuntType() {
	return peopleHuntType;
	}


	public void setPeopleHuntType(String peopleHuntType) {
	this.peopleHuntType = peopleHuntType;
	}


	public List<String> getPrimaryMatches(){
	return primaryMatches;
	}


	public List<String> getSecondaryMatched(){
	return secondaryMatches;
	}

	public String createMessage(KerseyTypesMatch kerseyMatch){

	String positionOne = letters[0]+""+kerseyMatch.letters[0];
	String positionTwo = letters[1]+""+kerseyMatch.letters[1];
	String positionThree = letters[2]+""+kerseyMatch.letters[2];
	String positionFour = letters[3]+""+kerseyMatch.letters[3];
	StringBuffer message = new StringBuffer();

	        message.append("You might like each other because you ");
	        boolean addAnd = false;
	        
	if (positionTwo.equals("ss")){
	            addAnd = true;
	            message.append("both get stuff done, ");
	        } else if (positionTwo.equals("nn")){
	            addAnd = true;
	            message.append("both love to think big, ");
	        }

	        if (positionThree.equals("tt")){
	            addAnd = true;
	            message.append("see things clearly, ");
	        } else if (positionThree.equals("ff")){
	            addAnd = true;
	            message.append("both care about the people around you, ");
	        }

	        if (positionFour.equals("jj")){
	            addAnd = true;
	            message.append("both love schedules, ");
	        } else if (positionFour.equals("pp")){
	            addAnd = true;
	            message.append("would both hop on a plane to Mali in a heartbeat, ");
	        }

	        if (addAnd)message.append("and ");

	        if (positionOne.equals("ii")){
	            message.append("both know what you want from life.");
	        } else if (positionOne.equals("ee")){
	            message.append("both don't mind being the center of attention.");
	        } else if (positionOne.equals("ei")||positionOne.equals("ie")){
	            message.append("make a good team.");
	        }
	        

	        return message.toString();
	}	
	
}
