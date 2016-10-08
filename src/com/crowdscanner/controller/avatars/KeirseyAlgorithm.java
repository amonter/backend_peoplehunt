	package com.crowdscanner.controller.avatars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.myownmotivator.model.questions.Question;

public class KeirseyAlgorithm extends CharacterMaker  {

	@Override
	public HashMap<String, String> characterAlg(List<Question> theQuestions) {
		
		//Find the character range 		
		HashMap<String, HashMap<String, String>> resHash = new HashMap<String, HashMap<String, String>>();	
		HashMap<String, String> mechantHash = new HashMap<String, String>();
		mechantHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/Transparent.png");
		mechantHash.put("nerd_type", "Transparent");
		mechantHash.put("nerd_key", "transparent");
		HashMap<String, String> blackNightHash = new HashMap<String, String>();
		blackNightHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/hero.png");
		blackNightHash.put("nerd_type", "Hero");
		blackNightHash.put("nerd_key", "hero");
		blackNightHash.put("description", "Heroes are down to earth, practical and reliable. They are the glue that keeps systems running smoothly, but are rarely thanked for it. They are cool under pressure so they make fantastic leaders. Famous Heroes include Mother Teresa, Warren Buffet, and George Washington.");
		HashMap<String, String> jokerHash = new HashMap<String, String>();
		jokerHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/charmer.png");
		jokerHash.put("nerd_type", "Charmer");
		jokerHash.put("nerd_key", "charmer");		
		jokerHash.put("description", "Charmers break the rules. They are delighted by spontaneous experiences so spending time with them is entertaining, if a little dangerous. They are relentless in their desire to express their inner dreams. Famous Charmers include Madonna and Woody Allen.");
		HashMap<String, String> wizardHash = new HashMap<String, String>();
		wizardHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/pragmatist.png");
		wizardHash.put("nerd_type", "Pragmatist");
		wizardHash.put("nerd_key", "pragmatist");	
		wizardHash.put("description", "Pragmatists are logical, strategic and natural problem solvers. Independent and self-contained, their insatiable curiosity about the world creates a never ending desire to fix all that is wrong with it. Famous Pragmatists include Steve Jobs, Charles Darwin and Hillary Clinton.");		
		HashMap<String, String> outlawHash = new HashMap<String, String>();
		outlawHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/idealist.png");
		outlawHash.put("nerd_type", "Idealist");
		outlawHash.put("nerd_key", "idealist");
		outlawHash.put("description", "Idealists strive for the highest ideals in life, often to the chagrin of those around them. Their spiritual natures, enthusiasm and desire to find their true self lead them on fascinating life journeys. Famous Idealists include Oprah, Nelson Mandela, Carl Jung and Margaret Mead.");
						
		
		resHash.put("transparent", mechantHash);
		resHash.put("hero", blackNightHash);
		resHash.put("charmer", jokerHash);
		resHash.put("pragmatist", wizardHash);
		resHash.put("idealist", outlawHash);
		
		Map<String,Integer> theCharactersScore = new HashMap<String,Integer>();
		theCharactersScore.put("hero", 0);
		theCharactersScore.put("charmer", 0);		
		theCharactersScore.put("idealist", 0);
		theCharactersScore.put("pragmatist", 0);
		
		Map<String, Integer> theLettersScore = getTheLetterScore();			
		firstIteration(theQuestions, theCharactersScore, theLettersScore);			
	
		
		HashMap<String, String>characterResult = null;
		List<Integer> allValues = sortCharacterCollec(theCharactersScore);
		Integer characterValue = allValues.get(0);
		for (Map.Entry<String, Integer> entries : theCharactersScore.entrySet()) {
			if (entries.getValue().equals(characterValue)) {
				characterResult = resHash.get(entries.getKey());
			}			
		}			
		
		List<Integer> letterValues = sortCharacterCollec(theLettersScore);
		Integer letterValue = letterValues.get(0);
		for (Map.Entry<String, Integer> entries : theLettersScore.entrySet()) {
			if (entries.getValue().equals(letterValue)) {
				characterResult.put("code", entries.getKey());		
				System.out.println(entries.getKey()+" "+entries.getValue());
			}			
		}			
		
		//Check for gender characters
		computeGender(theQuestions, characterResult);		
		
		
		return characterResult;
	}


	public Map<String, Integer> getTheLetterScore() {
		
		Map<String,Integer> theLettersScore = new HashMap<String,Integer>();
		theLettersScore.put("estj", 0);
		theLettersScore.put("istj", 0);
		theLettersScore.put("esfj", 0);
		theLettersScore.put("isfj", 0);
		theLettersScore.put("estp", 0);
		theLettersScore.put("istp", 0);
		theLettersScore.put("esfp", 0);
		theLettersScore.put("isfp", 0);
		theLettersScore.put("enfj", 0);
		theLettersScore.put("infj", 0);
		theLettersScore.put("enfp", 0);
		theLettersScore.put("infp", 0);
		theLettersScore.put("entj", 0);
		theLettersScore.put("intj", 0);
		theLettersScore.put("entp", 0);
		theLettersScore.put("intp", 0);
		return theLettersScore;
	}


	public void computeGender(List<Question> theQuestions, HashMap<String, String> characterResult) {
		
		for (Question aQuestion : theQuestions) {	
			if (aQuestion.getQuestionPhoneId() == 20384231) {
				String theAnswer = aQuestion.getSelectedAnswer().trim().replaceAll("\\s", "");	
				String characterURL = characterResult.get("nerd_image");
				int slashIndex = characterURL.lastIndexOf('.');
				if (theAnswer.equalsIgnoreCase("b.Female")) {					
					characterURL = characterURL.substring(0, slashIndex) + "_female.png";
				}				
				characterResult.put("nerd_image", characterURL);
				
			}						
		}
	}	

	
	public Question findQuestionById(List<Question> theQuestions, Integer questionId){
		
		for (Question question : theQuestions) {
			if (questionId.equals(question.getQuestionPhoneId())) {
				return question;
			}
		}
		
		return null;
	}
	
	
	
	

	public void firstIteration(List<Question> theQuestions, Map<String, Integer> theCharactersScore, Map<String, Integer> theLettersScore ){
		
		for (Question question : theQuestions) {		
			switch (question.getQuestionPhoneId()) {
				case 36387384://thinking/feeling one		
					getTFOne(theCharactersScore, question, theLettersScore); 			
					break;
				case 82058253://thinking/feeling two				
					getTFTwo(theCharactersScore, question, theLettersScore); 			
					break;
				case 64943376://thinking/feeling three				
					getTFThree(theCharactersScore, question, theLettersScore); 			
					break;
				case 36507925://Judging/perceiving one		
					getJPOne(theCharactersScore, question, theLettersScore); 			
					break;
				case 12424026: //Judging/perceiving two			
					getJPTwo(theCharactersScore, question, theLettersScore);			
					break;						
				case 67742148: //Judging/perceiving three			
					getJPThree(theCharactersScore, question, theLettersScore);			
					break;					
				case 48625091: //Sensing/Intuition			
					getSNOne(theCharactersScore, question, theLettersScore);			
					break;	
				case 44075500: //Extraversion/ INtroversion		
					getEIOne(theCharactersScore, question, theLettersScore);			
					break;	
			}		
		}
	}			

	


	@Override
	public List<HashMap<String, Object>> getAvatarsInfo() {		
			
		return getAvatarsInfoByGender("");
	}
	
	protected void getEIOne(Map<String, Integer> theCharactersScore, Question question, Map<String, Integer> theLettersScore) {
		String theAnswer4 = question.getSelectedAnswer().trim().replaceAll("\\s", "");	
		//E answer
		if (theAnswer4.equalsIgnoreCase("a.Stronglyagree") || theAnswer4.equalsIgnoreCase("b.Agree")) {
			//theCharactersScore.put("pragmatist", theCharactersScore.get("pragmatist") + 1);	
			theLettersScore.put("estj", theLettersScore.get("estj") +1);
			theLettersScore.put("esfj", theLettersScore.get("esfj") +1);
			theLettersScore.put("estp", theLettersScore.get("estp") +1);
			theLettersScore.put("esfp", theLettersScore.get("esfp") +1);
			theLettersScore.put("entj", theLettersScore.get("entj") +1);
			theLettersScore.put("enfj", theLettersScore.get("enfj") +1);
			theLettersScore.put("entp", theLettersScore.get("entp") +1);
			theLettersScore.put("enfp", theLettersScore.get("enfp") +1);
			
			
		} else if (theAnswer4.equalsIgnoreCase("c.Neitheragreenordisagree") ||  theAnswer4.equalsIgnoreCase("d.Disagree")
				|| theAnswer4.equalsIgnoreCase("e.Stronglydisagree")) { //I answer
			//theCharactersScore.put("idealist", theCharactersScore.get("idealist") + 1);					
			theLettersScore.put("istj", theLettersScore.get("istj") +1);	
			theLettersScore.put("istp", theLettersScore.get("istp") +1);	
			theLettersScore.put("isfj", theLettersScore.get("isfj") +1);
			theLettersScore.put("intj", theLettersScore.get("intj") +1);
			theLettersScore.put("intp", theLettersScore.get("intp") +1);				
			theLettersScore.put("isfp", theLettersScore.get("isfp") +1);			
			theLettersScore.put("infj", theLettersScore.get("infj") +1);			
			theLettersScore.put("infp", theLettersScore.get("infp") +1);			
		}		
		
	}
	
	protected void getTFOne(Map<String, Integer> theCharacters, Question question, Map<String, Integer> theLettersScore) {
		
		String theAnswer4 = question.getSelectedAnswer().trim().replaceAll("\\s", "");	
		//T answer
		if (theAnswer4.equalsIgnoreCase("a.Stronglyagree") || theAnswer4.equalsIgnoreCase("b.Agree")) {
			//theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);	
			theLettersScore.put("estj", theLettersScore.get("estj") +1);
			theLettersScore.put("istj", theLettersScore.get("istj") +1);		
			theLettersScore.put("estp", theLettersScore.get("estp") +1);
			theLettersScore.put("istp", theLettersScore.get("istp") +1);			
			theLettersScore.put("entj", theLettersScore.get("entj") +1);
			theLettersScore.put("intj", theLettersScore.get("intj") +1);
			theLettersScore.put("entp", theLettersScore.get("entp") +1);
			theLettersScore.put("intp", theLettersScore.get("intp") +1);		
			
			
		} else if (theAnswer4.equalsIgnoreCase("c.Neitheragreenordisagree") ||  theAnswer4.equalsIgnoreCase("d.Disagree")
				|| theAnswer4.equalsIgnoreCase("e.Stronglydisagree")) { //F answer
			//theCharacters.put("idealist", theCharacters.get("idealist") + 1);			
			theLettersScore.put("esfj", theLettersScore.get("esfj") +1);
			theLettersScore.put("isfj", theLettersScore.get("isfj") +1);
			theLettersScore.put("esfp", theLettersScore.get("esfp") +1);
			theLettersScore.put("isfp", theLettersScore.get("isfp") +1);
			theLettersScore.put("enfj", theLettersScore.get("enfj") +1);
			theLettersScore.put("infj", theLettersScore.get("infj") +1);
			theLettersScore.put("enfp", theLettersScore.get("enfp") +1);
			theLettersScore.put("infp", theLettersScore.get("infp") +1);			
		}		
	}

	protected void getTFTwo(Map<String, Integer> theCharacters, Question question, Map<String, Integer> theLettersScore) {
		
		String theAnswer3 = question.getSelectedAnswer().trim().replaceAll("\\s", "");	
		// F answer
		if (theAnswer3.equalsIgnoreCase("a.Stronglyagree") || theAnswer3.equalsIgnoreCase("b.Agree")) {			
			//theCharacters.put("idealist", theCharacters.get("idealist") + 1);
			theLettersScore.put("esfj", theLettersScore.get("esfj") +1);
			theLettersScore.put("isfj", theLettersScore.get("isfj") +1);
			theLettersScore.put("esfp", theLettersScore.get("esfp") +1);
			theLettersScore.put("isfp", theLettersScore.get("isfp") +1);
			theLettersScore.put("enfj", theLettersScore.get("enfj") +1);
			theLettersScore.put("infj", theLettersScore.get("infj") +1);
			theLettersScore.put("enfp", theLettersScore.get("enfp") +1);
			theLettersScore.put("infp", theLettersScore.get("infp") +1);			
			
		} else if (theAnswer3.equalsIgnoreCase("c.Neitheragreenordisagree") ||  theAnswer3.equalsIgnoreCase("d.Disagree")
				|| theAnswer3.equalsIgnoreCase("e.Stronglydisagree")) { //T answer
			//theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);
			theLettersScore.put("estj", theLettersScore.get("estj") +1);
			theLettersScore.put("istj", theLettersScore.get("istj") +1);		
			theLettersScore.put("estp", theLettersScore.get("estp") +1);
			theLettersScore.put("istp", theLettersScore.get("istp") +1);			
			theLettersScore.put("entj", theLettersScore.get("entj") +1);
			theLettersScore.put("intj", theLettersScore.get("intj") +1);
			theLettersScore.put("entp", theLettersScore.get("entp") +1);
			theLettersScore.put("intp", theLettersScore.get("intp") +1);		
			
		}
	}	
	
	
	protected void getTFThree(Map<String, Integer> theCharacters, Question question, Map<String, Integer> theLettersScore) {
		
		String theAnswer2 = question.getSelectedAnswer().trim().replaceAll("\\s", "");	
		// T answer
		if (theAnswer2.equalsIgnoreCase("c.Neitheragreenordisagree") ||  theAnswer2.equalsIgnoreCase("d.Disagree")
				|| theAnswer2.equalsIgnoreCase("e.Stronglydisagree")) {
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);
			theLettersScore.put("estj", theLettersScore.get("estj") +1);
			theLettersScore.put("istj", theLettersScore.get("istj") +1);		
			theLettersScore.put("estp", theLettersScore.get("estp") +1);
			theLettersScore.put("istp", theLettersScore.get("istp") +1);			
			theLettersScore.put("entj", theLettersScore.get("entj") +1);
			theLettersScore.put("intj", theLettersScore.get("intj") +1);
			theLettersScore.put("entp", theLettersScore.get("entp") +1);
			theLettersScore.put("intp", theLettersScore.get("intp") +1);			
			
		} else if (theAnswer2.equalsIgnoreCase("a.Stronglyagree") || theAnswer2.equalsIgnoreCase("b.Agree")) { //F answer
			theCharacters.put("idealist", theCharacters.get("idealist") + 1);
			theLettersScore.put("esfj", theLettersScore.get("esfj") +1);
			theLettersScore.put("isfj", theLettersScore.get("isfj") +1);
			theLettersScore.put("esfp", theLettersScore.get("esfp") +1);
			theLettersScore.put("isfp", theLettersScore.get("isfp") +1);
			theLettersScore.put("enfj", theLettersScore.get("enfj") +1);
			theLettersScore.put("infj", theLettersScore.get("infj") +1);
			theLettersScore.put("enfp", theLettersScore.get("enfp") +1);
			theLettersScore.put("infp", theLettersScore.get("infp") +1);		
			
		} 
	}

	protected void getJPOne(Map<String, Integer> theCharacters, Question question, Map<String, Integer> theLettersScore) {
		
		String theAnswer = question.getSelectedAnswer().trim().replaceAll("\\s", "");		
		// J answer
		if (theAnswer.equalsIgnoreCase("a.Stronglyagree") || theAnswer.equalsIgnoreCase("b.Agree")) {
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			theLettersScore.put("estj", theLettersScore.get("estj") +1);
			theLettersScore.put("istj", theLettersScore.get("istj") +1);		
			theLettersScore.put("esfj", theLettersScore.get("esfj") +1);
			theLettersScore.put("isfj", theLettersScore.get("isfj") +1);			
			theLettersScore.put("enfj", theLettersScore.get("enfj") +1);
			theLettersScore.put("infj", theLettersScore.get("infj") +1);
			theLettersScore.put("entj", theLettersScore.get("entj") +1);
			theLettersScore.put("intj", theLettersScore.get("intj") +1);		
			
		} else if (theAnswer.equalsIgnoreCase("c.Neitheragreenordisagree") ||  theAnswer.equalsIgnoreCase("d.Disagree")
				|| theAnswer.equalsIgnoreCase("e.Stronglydisagree")) { //P answer			
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			theLettersScore.put("estp", theLettersScore.get("estp") +1);
			theLettersScore.put("istp", theLettersScore.get("istp") +1);		
			theLettersScore.put("esfp", theLettersScore.get("esfp") +1);
			theLettersScore.put("isfp", theLettersScore.get("isfp") +1);			
			theLettersScore.put("enfp", theLettersScore.get("enfp") +1);
			theLettersScore.put("infp", theLettersScore.get("infp") +1);
			theLettersScore.put("entp", theLettersScore.get("entp") +1);
			theLettersScore.put("intp", theLettersScore.get("intp") +1);		
			
		} 
	}
	
	protected void getJPTwo(Map<String, Integer> theCharacters, Question question, Map<String, Integer> theLettersScore) {
		
		String theAnswer5 = question.getSelectedAnswer().trim().replaceAll("\\s", "");		
		//P answer
		if (theAnswer5.equalsIgnoreCase("a.Stronglyagree") || theAnswer5.equalsIgnoreCase("b.Agree")) {
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			theLettersScore.put("estp", theLettersScore.get("estp") +1);
			theLettersScore.put("istp", theLettersScore.get("istp") +1);		
			theLettersScore.put("esfp", theLettersScore.get("esfp") +1);
			theLettersScore.put("isfp", theLettersScore.get("isfp") +1);			
			theLettersScore.put("enfp", theLettersScore.get("enfp") +1);
			theLettersScore.put("infp", theLettersScore.get("infp") +1);
			theLettersScore.put("entp", theLettersScore.get("entp") +1);
			theLettersScore.put("intp", theLettersScore.get("intp") +1);		
			
			
		} else if (theAnswer5.equalsIgnoreCase("c.Neitheragreenordisagree") ||  theAnswer5.equalsIgnoreCase("d.Disagree")
				|| theAnswer5.equalsIgnoreCase("e.Stronglydisagree")) { //J answer
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			theLettersScore.put("estj", theLettersScore.get("estj") +1);
			theLettersScore.put("istj", theLettersScore.get("istj") +1);		
			theLettersScore.put("esfj", theLettersScore.get("esfj") +1);
			theLettersScore.put("isfj", theLettersScore.get("isfj") +1);			
			theLettersScore.put("enfj", theLettersScore.get("enfj") +1);
			theLettersScore.put("infj", theLettersScore.get("infj") +1);
			theLettersScore.put("entj", theLettersScore.get("entj") +1);
			theLettersScore.put("intj", theLettersScore.get("intj") +1);		
		
		} 
	}

	
	protected void getJPThree(Map<String, Integer> theCharacters, Question question, Map<String, Integer> theLettersScore) {
		
		String theAnswer = question.getSelectedAnswer().trim().replaceAll("\\s", "");		
		// J answer
		if (theAnswer.equalsIgnoreCase("c.Neitheragreenordisagree") ||  theAnswer.equalsIgnoreCase("d.Disagree")
				|| theAnswer.equalsIgnoreCase("e.Stronglydisagree")) {
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			theLettersScore.put("estj", theLettersScore.get("estj") +1);
			theLettersScore.put("istj", theLettersScore.get("istj") +1);		
			theLettersScore.put("esfj", theLettersScore.get("esfj") +1);
			theLettersScore.put("isfj", theLettersScore.get("isfj") +1);			
			theLettersScore.put("enfj", theLettersScore.get("enfj") +1);
			theLettersScore.put("infj", theLettersScore.get("infj") +1);
			theLettersScore.put("entj", theLettersScore.get("entj") +1);
			theLettersScore.put("intj", theLettersScore.get("intj") +1);		
			
		} else if (theAnswer.equalsIgnoreCase("a.Stronglyagree") || theAnswer.equalsIgnoreCase("b.Agree")) { //P answer			
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			theLettersScore.put("estp", theLettersScore.get("estp") +1);
			theLettersScore.put("istp", theLettersScore.get("istp") +1);		
			theLettersScore.put("esfp", theLettersScore.get("esfp") +1);
			theLettersScore.put("isfp", theLettersScore.get("isfp") +1);			
			theLettersScore.put("enfp", theLettersScore.get("enfp") +1);
			theLettersScore.put("infp", theLettersScore.get("infp") +1);
			theLettersScore.put("entp", theLettersScore.get("entp") +1);
			theLettersScore.put("intp", theLettersScore.get("intp") +1);		
			
		} 
		
	}
	

	protected void getSNOne(Map<String, Integer> theCharacters, Question question, Map<String, Integer> theLettersScore) {
		
		String theAnswer = question.getSelectedAnswer().trim().replaceAll("\\s", "");		
		// S answer
		if (theAnswer.equalsIgnoreCase("c.Neitheragreenordisagree") ||  theAnswer.equalsIgnoreCase("d.Disagree")
				|| theAnswer.equalsIgnoreCase("e.Stronglydisagree")) {
			theCharacters.put("hero", theCharacters.get("hero") + 2);
			theCharacters.put("charmer", theCharacters.get("charmer") + 2);
			theLettersScore.put("estj", theLettersScore.get("estj") +2);
			theLettersScore.put("istj", theLettersScore.get("istj") +2);		
			theLettersScore.put("esfj", theLettersScore.get("esfj") +2);
			theLettersScore.put("isfj", theLettersScore.get("isfj") +2);			
			theLettersScore.put("estp", theLettersScore.get("estp") +2);
			theLettersScore.put("istp", theLettersScore.get("istp") +2);
			theLettersScore.put("esfp", theLettersScore.get("esfp") +2);
			theLettersScore.put("isfp", theLettersScore.get("isfp") +2);		
			
		} else if (theAnswer.equalsIgnoreCase("a.Stronglyagree") || theAnswer.equalsIgnoreCase("b.Agree")) { //N answer			
			theCharacters.put("idealist", theCharacters.get("idealist") + 2);
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 2);
			theLettersScore.put("entj", theLettersScore.get("entj") +2);
			theLettersScore.put("intj", theLettersScore.get("intj") +2);		
			theLettersScore.put("enfj", theLettersScore.get("enfj") +2);
			theLettersScore.put("infj", theLettersScore.get("infj") +2);			
			theLettersScore.put("entp", theLettersScore.get("entp") +2);
			theLettersScore.put("intp", theLettersScore.get("intp") +2);
			theLettersScore.put("enfp", theLettersScore.get("enfp") +2);
			theLettersScore.put("infp", theLettersScore.get("infp") +2);		
			
		} 
		
	}
	
	
	protected int checkForRepeat(Map<String, Integer> theCharacters) {
		
		List<Integer> allValues = sortCharacterCollec(theCharacters);		
		Integer maxValue = allValues.get(0);
		int repeat = 0;
		for (Integer integer : allValues) {
			if(maxValue.equals(integer)){
				repeat++;
			}
		}
		return repeat;
	}
	
	
	private List<Integer> sortCharacterCollec(Map<String, Integer> theCharacters) {
		List<Integer> allValues = new ArrayList<Integer>(theCharacters.values());
		Collections.sort(allValues, new Comparator<Integer>() {
			public int compare(Integer one, Integer two) {				
				return two.compareTo(one);
			}
		});
		return allValues;
	}


	@Override
	public List<HashMap<String, Object>> getAvatarsInfoByGender(String gender) {
		
		List<HashMap<String, Object>> personArrays = new ArrayList<HashMap<String,Object>>();		
		String theGender = "male";
		if (StringUtils.contains(gender, "Female")){
			theGender = "female";
		}
		
		//Merchant
		HashMap<String, Object> theMerchantHash = new HashMap<String, Object>();
		theMerchantHash.put("nerd_type", "Transparent");
		theMerchantHash.put("nert_key", "transparent");
		theMerchantHash.put("description", "");
		theMerchantHash.put("gender", theGender);
		theMerchantHash.put("url", "http://s3.amazonaws.com/crowdscanner_images/Transparent.png");
		
		List<HashMap<String, String>> arrayClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> cluesHash = new HashMap<String, String>();
		cluesHash.put("question", "");
		cluesHash.put("clue", "");
		
		/*
		HashMap<String, String> cluesHash2 = new HashMap<String, String>();
		cluesHash2.put("question", "Reflect: Did they convince you of anything?");
		cluesHash2.put("clue", "Merchants tend to be great at convincing people");
		HashMap<String, String> cluesHash3 = new HashMap<String, String>();
		cluesHash3.put("question", "Ask them: If a fire were to break out, right here, what would you do?");
		cluesHash3.put("clue", "Merchants tend to be relaxed and adaptive in a crisis");			
		HashMap<String, String> cluesHash4 = new HashMap<String, String>();
		cluesHash4.put("question", "Investigate: What would they miss most if left alone for a year in the woods?");
		cluesHash4.put("clue", "Merchants tend to love to be around people");
		HashMap<String, String> cluesHash5 = new HashMap<String, String>();
		cluesHash5.put("question", "Reflect: Did you get a word in edgeways?");
		cluesHash5.put("clue", "Merchants tend to like talking a lot");
        HashMap<String, String> cluesHash6 = new HashMap<String, String>();
		cluesHash6.put("question", "Investigate: What was the best present they ever gave someone?");
		cluesHash6.put("clue", "Merchants tend to be generous");
        HashMap<String, String> cluesHash7 = new HashMap<String, String>();
		cluesHash7.put("question", "Ask them: What was the best present you ever received?");
		cluesHash7.put("clue", "Merchants tend to have a lot of admirers");
		//add has cues
		 
		 */
		arrayClues.add(cluesHash);		
		/*
		arrayClues.add(cluesHash2);
		arrayClues.add(cluesHash3);
		arrayClues.add(cluesHash4);
		arrayClues.add(cluesHash5);
		arrayClues.add(cluesHash6);
		arrayClues.add(cluesHash7);
		//add array clues into meta hash
		 */
		theMerchantHash.put("clues", arrayClues);
		
		//WIzard
		HashMap<String, Object> theWizardHash = new HashMap<String, Object>();
		theWizardHash.put("nerd_type", "Pragmatist");
		theWizardHash.put("nert_key", "pragmatist");
		theWizardHash.put("description", "Pragmatists are logical, strategic and natural problem solvers. Independent and self-contained, their insatiable curiosity about the world creates a never ending desire to fix all that is wrong with it. Famous Pragmatists include Steve Jobs, Charles Darwin and Hillary Clinton. ");
		theWizardHash.put("gender", theGender);
		String rationalistURL =  "http://s3.amazonaws.com/crowdscanner_images/pragmatist.png";
		
		rationalistURL = checkGenderURL(gender, rationalistURL);
		theWizardHash.put("url", rationalistURL);
		
		List<HashMap<String, String>> wizardClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> wizardHash = new HashMap<String, String>();
		wizardHash.put("question", "Find out if they like libraries.");
		wizardHash.put("clue", "Pragmatists tend to be on a quest for knowledge");
		HashMap<String, String> wizardHash2 = new HashMap<String, String>();
		wizardHash2.put("question", "Ask: If something breaks, do you like to try to fix it yourself?");
		wizardHash2.put("clue", "Pragmatists tend to be efficient problem solvers");			
		HashMap<String, String> wizardHash3 = new HashMap<String, String>();
		wizardHash3.put("question", "Find out how they work best - alone or in a team?");
		wizardHash3.put("clue", "Pragmatists tend to like their own company");
		HashMap<String, String> wizardHash4 = new HashMap<String, String>();
		wizardHash4.put("question", "Ask: What future discovery do you anticipate the most?");
		wizardHash4.put("clue", "Pragmatists tend to be perceptive");
		HashMap<String, String> wizardHash6 = new HashMap<String, String>();
		wizardHash6.put("question", "Ask: What quality or ability would you love to wake up with tomorrow?");
		wizardHash6.put("clue", "Pragmatists tend to love learning");
		HashMap<String, String> wizardHash7 = new HashMap<String, String>();
		wizardHash7.put("question", "Ask: Whats your favourite thing to do on a Sunday afternoon?");
		wizardHash7.put("clue", "Pragmatists tend to feel most at ease talking about hobbies");			
		HashMap<String, String> wizardHash8 = new HashMap<String, String>();
		wizardHash8.put("question", "Play: Ask to give them a hug. Do they want to know why?");
		wizardHash8.put("clue", "Pragmatists like to know the reason behind actions.");       
        HashMap<String, String> wizardHash10 = new HashMap<String, String>();
		wizardHash10.put("question", "Ask: How do you see us solving the economic crisis?");
		wizardHash10.put("clue", "Pragmatists tend to love complex problems");
        HashMap<String, String> wizardHash11 = new HashMap<String, String>();
		wizardHash11.put("question", "Find out if they would change anything about how they were raised.");
		wizardHash11.put("clue", "Pragmatists tend to be driven to improve things");
        HashMap<String, String> wizardHash12 = new HashMap<String, String>();
		wizardHash12.put("question", "Ask: Do you ever rehearse before a phonecall?");
		wizardHash12.put("clue", "Pragmatists tend to be a little confused by life");
		//add has cues
		wizardClues.add(wizardHash);
		wizardClues.add(wizardHash2);
		wizardClues.add(wizardHash3);
		wizardClues.add(wizardHash4);
		wizardClues.add(wizardHash6);
		wizardClues.add(wizardHash7);
		wizardClues.add(wizardHash8);		
		wizardClues.add(wizardHash10);
		wizardClues.add(wizardHash11);
		wizardClues.add(wizardHash12);
		//add array clues into meta hash
		theWizardHash.put("clues", wizardClues);
		
		//Black Knight
		HashMap<String, Object> blackNightHashMeta = new HashMap<String, Object>();
		blackNightHashMeta.put("nerd_type", "Hero");
		blackNightHashMeta.put("nert_key", "hero");
		blackNightHashMeta.put("description", "Heroes are down to earth, practical and reliable. They are the glue that keeps systems running smoothly, but are rarely thanked for it. They are cool under pressure so they make fantastic leaders. Famous Heroes include Mother Teresa, Warren Buffet, and George Washington.");
		blackNightHashMeta.put("gender", theGender);
		String protectorURL = "http://s3.amazonaws.com/crowdscanner_images/hero.png";
		protectorURL = checkGenderURL(gender, protectorURL);
		blackNightHashMeta.put("url", protectorURL);
		
		List<HashMap<String, String>> blackNightHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> blackNightHash = new HashMap<String, String>();
		blackNightHash.put("question", "Find out if they own a car. If so, why did they choose that model?");
		blackNightHash.put("clue", "Heroes tend to be stable and trustworthy");
		HashMap<String, String> blackNightHash2 = new HashMap<String, String>();
		blackNightHash2.put("question", "Ask: For what in your life do you feel most grateful?");
		blackNightHash2.put("clue", "Heroes tend to be generous and caring towards others");			
		HashMap<String, String> blackNightHash3 = new HashMap<String, String>();
		blackNightHash3.put("question", "Find out if they prefer to work for themselves, or someone else.");
		blackNightHash3.put("clue", "Heroes tend to be realistic, and like security");
        HashMap<String, String> blackNightHash4 = new HashMap<String, String>();
		blackNightHash4.put("question", "Test them: Do they remember your name or your job?");
		blackNightHash4.put("clue", "Heroes tend to be attentive to details");
        HashMap<String, String> blackNightHash5 = new HashMap<String, String>();
		blackNightHash5.put("question", "Play: Give them a hug - how do they react?");
		blackNightHash5.put("clue", "Heroes tend to trust others easily");
        HashMap<String, String> blackNightHash6 = new HashMap<String, String>();
		blackNightHash6.put("question", "Find out if a perfect day for them would include helping someone.");
		blackNightHash6.put("clue", "Heroes tend to like helping others");
        HashMap<String, String> blackNightHash7 = new HashMap<String, String>();
		blackNightHash7.put("question", "Investigate what happened the last time they experienced an emergency.");
		blackNightHash7.put("clue", "Heroes tend to be reliable in an emergency");
        HashMap<String, String> blackNightHash8 = new HashMap<String, String>();
		blackNightHash8.put("question", "Ask: When did you last sing to yourself? To someone else?");
		blackNightHash8.put("clue", "Heroes tend to take good care of people");     
        HashMap<String, String> blackNightHash9 = new HashMap<String, String>();
		blackNightHash9.put("question", "Find out what they are most afraid of.");
		blackNightHash9.put("clue", "Heroes tend to protect those around them");
	    HashMap<String, String> blackNightHash10 = new HashMap<String, String>();
	    blackNightHash10.put("question", "Find out if they would like to be famous. And in what way?");
	    blackNightHash10.put("clue", "Heroes tend to put others needs before their own");
		//add has cues
		blackNightHashClues.add(blackNightHash);
		blackNightHashClues.add(blackNightHash2);
		blackNightHashClues.add(blackNightHash3);
		blackNightHashClues.add(blackNightHash4);
		blackNightHashClues.add(blackNightHash5);
		blackNightHashClues.add(blackNightHash6);
		blackNightHashClues.add(blackNightHash7);
        blackNightHashClues.add(blackNightHash8);
        blackNightHashClues.add(blackNightHash9);
        blackNightHashClues.add(blackNightHash10);
		//add array clues into meta hash
		blackNightHashMeta.put("clues", blackNightHashClues);
		
		//OutLaw
		HashMap<String, Object> outlawHashMeta = new HashMap<String, Object>();
		outlawHashMeta.put("nerd_type", "Idealist");
		outlawHashMeta.put("nert_key", "idealist");
		outlawHashMeta.put("description", "Idealists strive for the highest ideals in life, often to the chagrin of those around them. Their spiritual natures, enthusiasm and desire to find their true self lead them on fascinating life journeys. Famous Idealists include Oprah, Nelson Mandela, Carl Jung and Margaret Mead.");
		outlawHashMeta.put("gender", theGender);
		String idealistURL = "http://s3.amazonaws.com/crowdscanner_images/idealist.png";
		idealistURL = checkGenderURL(gender, idealistURL);
		outlawHashMeta.put("url" ,idealistURL);
		
		List<HashMap<String, String>> outlawHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> outlawHash = new HashMap<String, String>();
		outlawHash.put("question", "Ask: What foreign country would you most like to visit?");
		outlawHash.put("clue", "Idealists tend to have travelled to unusual places");
		HashMap<String, String> outlawHash2 = new HashMap<String, String>();
		outlawHash2.put("question", "Ask: When you wake up, and think about work, how do you feel?");
		outlawHash2.put("clue", "Idealists tend to be passionate about their career");			
		HashMap<String, String> outlawHash3 = new HashMap<String, String>();
		outlawHash3.put("question", "Inquire: If you could be a leader, what kind of leader would you be?");
		outlawHash3.put("clue", "Idealists tend to like to lead");
		HashMap<String, String> outlawHash4 = new HashMap<String, String>();
		outlawHash4.put("question", "Discover: What do they like to do with a free hour?");
		outlawHash4.put("clue", "Idealists tend to hate wasting time");
		HashMap<String, String> outlawHash5 = new HashMap<String, String>();
		outlawHash5.put("question", "Ask: What, if anything, is too serious to be joked about?");
		outlawHash5.put("clue", "Idealists tend to like doing the right thing");
		HashMap<String, String> outlawHash6 = new HashMap<String, String>();
		outlawHash6.put("question", "Ask: What is the greatest accomplishment of your life?");
		outlawHash6.put("clue", "Idealists tend to be on a mission");
        HashMap<String, String> outlawHash7 = new HashMap<String, String>();
		outlawHash7.put("question", "Discover: What do they consider a waste in society?");
		outlawHash7.put("clue", "Idealists tend to have strong opinions on world issues");
        HashMap<String, String> outlawHash8 = new HashMap<String, String>();
		outlawHash8.put("question", "Discover: What do they consider a waste in society?");
		outlawHash8.put("clue", "Idealists tend to have strong opinions on world issues");
        HashMap<String, String> outlawHash9 = new HashMap<String, String>();
        outlawHash9.put("question", "Ask: At an ideal dinner party, whom would you want as a guest?");
        outlawHash9.put("clue", "Idealists tend to have idea driven conversation");
		//add has cues
		outlawHashClues.add(outlawHash);
		outlawHashClues.add(outlawHash2);
		outlawHashClues.add(outlawHash3);
		outlawHashClues.add(outlawHash4);
		outlawHashClues.add(outlawHash5);
		outlawHashClues.add(outlawHash6);
        outlawHashClues.add(outlawHash7);
        outlawHashClues.add(outlawHash8);
        outlawHashClues.add(outlawHash9);
		//add array clues into meta hash
		outlawHashMeta.put("clues", outlawHashClues);
		
		
		//Charmer
		HashMap<String, Object> jokerHashMeta = new HashMap<String, Object>();
		jokerHashMeta.put("nerd_type", "Charmer");
		jokerHashMeta.put("nert_key", "charmer");
		jokerHashMeta.put("description", "Charmers break the rules. They are delighted by spontaneous experiences so spending time with them is entertaining, if a little dangerous. They are relentless in their desire to express their inner dreams. Famous Charmers include Madonna and Woody Allen.");
		jokerHashMeta.put("gender", theGender);
		String performerURL = "http://s3.amazonaws.com/crowdscanner_images/charmer.png";
		performerURL = checkGenderURL(gender, performerURL);
		jokerHashMeta.put("url", performerURL);

		
		List<HashMap<String, String>> jokerHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> jokerHash = new HashMap<String, String>();
		jokerHash.put("question", "Ask: Do you think art or science is more important to mankind?");
		jokerHash.put("clue", "Charmers tend to love to create beautiful things");
		HashMap<String, String> jokerHash2 = new HashMap<String, String>();
		jokerHash2.put("question", "Ask: Whats the first thing you notice about someone new?");
		jokerHash2.put("clue", "Charmers tend to be observant");			
		HashMap<String, String> jokerHash3 = new HashMap<String, String>();
		jokerHash3.put("question", "Ask: Have you ever spent time in Lost and Found?");
		jokerHash3.put("clue", "Charmers tend to be scatterbrained");
		HashMap<String, String> jokerHash4 = new HashMap<String, String>();
		jokerHash4.put("question", "Ask: What could you not live without - music or exercise?");
		jokerHash4.put("clue", "Charmers tend to appreciate sensory input");
		HashMap<String, String> jokerHash5 = new HashMap<String, String>();
		jokerHash5.put("question", "Find out what they are most proud of creating.");
		jokerHash5.put("clue", "Charmers tend to love anything to do with creation or crafting");
		HashMap<String, String> jokerHash6 = new HashMap<String, String>();
		jokerHash6.put("question", "Investigate if theyve ever lost anything dear to them.");
		jokerHash6.put("clue", "Charmers tend to not get attached to things");
        HashMap<String, String> jokerHash7 = new HashMap<String, String>();
		jokerHash7.put("question", "Investigate their hopes for the future.");
		jokerHash7.put("clue", "Charmers tend to be optimistic");
        HashMap<String, String> jokerHash8 = new HashMap<String, String>();
		jokerHash8.put("question", "Imagine: Would you like to hang out with them?");
		jokerHash8.put("clue", "Charmers tend to be fun to hang around");
        HashMap<String, String> jokerHash9 = new HashMap<String, String>();
		jokerHash9.put("question", "Ask: What would be your ideal day?");
		jokerHash9.put("clue", "Charmers tend to like being around people");
        HashMap<String, String> jokerHash10 = new HashMap<String, String>();
		jokerHash10.put("question", "Test them: Do they remember your name or job?");
		jokerHash10.put("clue", "Charmers tend to be forgetful and scatterbrained");
		HashMap<String, String> jokerHash12 = new HashMap<String, String>();
		jokerHash12.put("question", "Reflect: Did they make up a lot of their own questions?");
		jokerHash12.put("clue", "Charmers tend to be great conversationalists");
        HashMap<String, String> jokerHash13 = new HashMap<String, String>();
		jokerHash13.put("question", "Ask: Which is more important in kids - imagination or diligence?");
		jokerHash13.put("clue", "Charmers tend to value imagination");	
		//add has cues
		jokerHashClues.add(jokerHash);
		jokerHashClues.add(jokerHash2);
		jokerHashClues.add(jokerHash3);
		jokerHashClues.add(jokerHash4);
		jokerHashClues.add(jokerHash5);
		jokerHashClues.add(jokerHash6);
		jokerHashClues.add(jokerHash7);
		jokerHashClues.add(jokerHash8);
		jokerHashClues.add(jokerHash9);
		jokerHashClues.add(jokerHash10);
		jokerHashClues.add(jokerHash12);
        jokerHashClues.add(jokerHash13);
		//add array clues into meta hash
		jokerHashMeta.put("clues", jokerHashClues);
		
		personArrays.add(theMerchantHash);
		personArrays.add(theWizardHash);
		personArrays.add(blackNightHashMeta);
		personArrays.add(outlawHashMeta);
		personArrays.add(jokerHashMeta);
		
		return personArrays;
	}
	
	
	public String searchAvatarURL(String avatar) {
		
		String url = null;
		if (avatar.equals("Transparent")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/Transparent.png";
			
		} else if (avatar.equals("Hero")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/hero.png";		
			
		} else if (avatar.equals("Charmer")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/charmer.png";				
			
		} else if (avatar.equals("Pragmatist")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/pragmatist.png";				
						
		} else if (avatar.equals("Idealist")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/idealist.png";			
		}
		
		return url;
	}
	


	private String checkGenderURL(String gender, String rationalistURL) {
		if (gender.equals("b. Female")) {
			int slashIndex = rationalistURL.lastIndexOf('.');
			rationalistURL = rationalistURL.substring(0, slashIndex) + "_female.png";
		}
		return rationalistURL;
	}

}
