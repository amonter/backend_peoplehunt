package com.crowdscanner.controller.avatars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.myownmotivator.model.questions.Question;

public class AvantGardeCharacters extends CharacterMaker  {

	@Override
	public HashMap<String, String> characterAlg(List<Question> theQuestions) {
		
		//Find the character range 		
		HashMap<String, HashMap<String, String>> resHash = new HashMap<String, HashMap<String, String>>();	
		HashMap<String, String> mechantHash = new HashMap<String, String>();
		mechantHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/Transparent.png");
		mechantHash.put("nerd_type", "Transparent");
		mechantHash.put("nerd_key", "transparent");
		HashMap<String, String> blackNightHash = new HashMap<String, String>();
		blackNightHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/protector.png");
		blackNightHash.put("nerd_type", "Hero");
		blackNightHash.put("description", "Heroes are down to earth, practical and reliable. They are the glue that keeps systems running smoothly, but are rarely thanked for it. They are cool under pressure so they make fantastic leaders. Famous Heroes include Mother Teresa, Warren Buffet, and George Washington.");		
		blackNightHash.put("nerd_key", "hero");
		HashMap<String, String> jokerHash = new HashMap<String, String>();
		jokerHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/performer.png");
		jokerHash.put("nerd_type", "Charmer");
		jokerHash.put("nerd_key", "charmer");		
		jokerHash.put("description", "Charmers break the rules. They are delighted by spontaneous experiences so spending time with them is entertaining, if a little dangerous. They are relentless in their desire to express their inner dreams. Famous Charmers include Madonna and Woody Allen.");		
		HashMap<String, String> wizardHash = new HashMap<String, String>();
		wizardHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/rationalist.png");
		wizardHash.put("nerd_type", "Pragmatist");
		wizardHash.put("description", "Pragmatists are logical, strategic and natural problem solvers. Independent and self-contained, their insatiable curiosity about the world creates a never ending desire to fix all that is wrong with it. Famous Pragmatists include Steve Jobs, Charles Darwin and Hillary Clinton.");		
		wizardHash.put("nerd_key", "pragmatist");			
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
		
		firstIteration(theQuestions, theCharactersScore);			
		     
		int repeat = checkForRepeat(theCharactersScore);		
		secondIteration(theQuestions, theCharactersScore, repeat);
		
		HashMap<String, String>characterResult = null;
		List<Integer> allValues = sortCharacterCollec(theCharactersScore);
		Integer characterValue = allValues.get(0);
		for (Map.Entry<String, Integer> entries : theCharactersScore.entrySet()) {
			if (entries.getValue().equals(characterValue)) {
				characterResult = resHash.get(entries.getKey());
			}			
		}			
		
		//Check for gender characters
		computeGender(theQuestions, characterResult);		
		
		
		return characterResult;
	}


	public void computeGender(List<Question> theQuestions, HashMap<String, String> characterResult) {
		
		for (Question aQuestion : theQuestions) {	
			if (aQuestion.getQuestionPhoneId() == 5996728) {
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
	
	
	
	public void secondIteration(List<Question> theQuestions, Map<String, Integer> theCharactersScore, int repeat) {
		
		if (repeat > 1) {			
			outer:			
			for (int i = 0; i < 5 ; i++) {					
				switch (i) {
				
					case 4853302: //extroversion		
					//4620022
						getExtroversion(theCharactersScore,  findQuestionById(theQuestions, 4853302));			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}						
					case 925552:// concienciosness	
						//5396313
						getConcienciosness(theCharactersScore,  findQuestionById(theQuestions, 925552)); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 9682743://openness		
						//27807
						getOpenness(theCharactersScore, findQuestionById(theQuestions, 9682743)); 
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 2506224://agreeableness		
						//8943874
						getAgreeableness(theCharactersScore,  findQuestionById(theQuestions, 2506224)); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}					
					case 9729692:// stability	
						//6903753
						getStability(theCharactersScore,  findQuestionById(theQuestions, 9729692)); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
							
				}								
			}
		}
	}

	public void firstIteration(List<Question> theQuestions, Map<String, Integer> theCharactersScore){
		
		for (Question question : theQuestions) {		
			switch (question.getQuestionPhoneId()) {
				case 9682743://openness			
					getOpenness(theCharactersScore, question); 			
					break;
				case 2506224://agreeableness			
					getAgreeableness(theCharactersScore, question); 			
					break;
				case 925552:// concienciosness			
					getConcienciosness(theCharactersScore, question); 			
					break;
				case 9729692:// stability			
					getStability(theCharactersScore, question); 			
					break;
				case 4853302: //extroversion			
					getExtroversion(theCharactersScore, question);			
					break;			
			}		
		}
	}		
	

	@Override
	public List<HashMap<String, Object>> getAvatarsInfo() {		
			
		return getAvatarsInfoByGender("");
	}
	
	protected void getStability(Map<String, Integer> theCharacters, Question question) {
		
		String theAnswer4 = question.getSelectedAnswer().trim().replaceAll("\\s", "");				
		if (theAnswer4.equalsIgnoreCase("a.stronglyagree")) {
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			//merchant += 1;
			//black_knight += 1;
		} else if (theAnswer4.equalsIgnoreCase("b.agree")) {
			theCharacters.put("idealist", theCharacters.get("idealist") + 1);
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			//merchant += 1;
			//black_knight += 1;
		} else if (theAnswer4.equalsIgnoreCase("c.neitheragreenordisagree")){
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);
			//idealist += 1;		
			//charmer +=1;
			//pragmatist +=1;
		} else if (theAnswer4.equalsIgnoreCase("d.disagree")) {			
			theCharacters.put("idealist", theCharacters.get("idealist") + 1);
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);
			//idealist += 1;		
			//charmer +=1;
			//pragmatist +=1;				
		} else if (theAnswer4.equalsIgnoreCase("e.stronglydisagree")){
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);
			//charmer +=1;				
		}
	}

	protected void getConcienciosness(Map<String, Integer> theCharacters, Question question) {
		
		String theAnswer3 = question.getSelectedAnswer().trim().replaceAll("\\s", "");				
		if (theAnswer3.equalsIgnoreCase("a.stronglyagree")) {
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			theCharacters.put("idealist", theCharacters.get("idealist") + 1);
			//charmer +=1;
		} else if (theAnswer3.equalsIgnoreCase("b.agree")) {
			theCharacters.put("idealist", theCharacters.get("idealist") + 1);
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			//charmer +=1;
		} else if (theAnswer3.equalsIgnoreCase("c.neitheragreenordisagree")){
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			//theCharacters.put("merchant", theCharacters.get("merchant") + 1);
			//idealist += 1;		
			//charmer +=1;
			//merchant += 1;
		} else if (theAnswer3.equalsIgnoreCase("d.disagree")) {
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);
			//theCharacters.put("merchant", theCharacters.get("merchant") + 1);
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			//idealist += 1;		
			//merchant += 1;
			//black_knight += 1;
			//pragmatist +=1;
		} else if (theAnswer3.equalsIgnoreCase("e.stronglydisagree")){			
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			//theCharacters.put("merchant", theCharacters.get("merchant") + 1);
			//black_knight += 1;
			//pragmatist +=1;	
			//idealist += 1;
		}
	}

	public String searchAvatarURL(String avatar) {
		
		String url = null;
		if (avatar.equals("Transparent")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/Transparent.png";
			
		} else if (avatar.equals("Hero")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/protector.png";		
			
		} else if (avatar.equals("Charmer")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/performer.png";				
			
		} else if (avatar.equals("Pragmatist")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/rationalist.png";				
						
		} else if (avatar.equals("Idealist")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/idealist.png";			
		}
		
		return url;
	}
	
	
	
	protected void getAgreeableness(Map<String, Integer> theCharacters, Question question) {
		
		String theAnswer2 = question.getSelectedAnswer().trim().replaceAll("\\s", "");				
		if (theAnswer2.equalsIgnoreCase("a.stronglyagree")) {
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			
		} else if (theAnswer2.equalsIgnoreCase("b.agree")) {
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);		
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			//charmer +=1;
			//black_knight += 1;
			//pragmatist +=1;
			//merchant += 1;
		} else if (theAnswer2.equalsIgnoreCase("c.neitheragreenordisagree")){
			theCharacters.put("idealist", theCharacters.get("idealist") + 1);
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);
			
		} else if (theAnswer2.equalsIgnoreCase("d.disagree")) {
			theCharacters.put("idealist", theCharacters.get("idealist") + 1);
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);
			//idealist += 1;		
			//pragmatist +=1;				
		} else if (theAnswer2.equalsIgnoreCase("e.stronglydisagree")){
			theCharacters.put("idealist", theCharacters.get("idealist") + 1);
			//idealist += 1;					
		}
	}

	protected void getOpenness(Map<String, Integer> theCharacters, Question question) {
		
		String theAnswer = question.getSelectedAnswer().trim().replaceAll("\\s", "");				
		if (theAnswer.equalsIgnoreCase("a.stronglyagree")) {
			theCharacters.put("idealist", theCharacters.get("idealist") + 1);
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);
			//idealist += 1;
			//charmer +=1;
			//pragmatist +=1;
		} else if (theAnswer.equalsIgnoreCase("b.agree")) {
			theCharacters.put("idealist", theCharacters.get("idealist") + 1);
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);		
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			//idealist += 1;
			//charmer +=1;
			//pragmatist +=1;
			//merchant += 1;
			//black_knight += 1;
		} else if (theAnswer.equalsIgnoreCase("c.neitheragreenordisagree")){
			//theCharacters.put("merchant", theCharacters.get("merchant") + 1);
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			//black_knight += 1;	
			//merchant += 1;
		} else if (theAnswer.equalsIgnoreCase("d.disagree")) {
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			//black_knight += 1;				
		} else if (theAnswer.equalsIgnoreCase("e.stronglydisagree")){
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			//black_knight += 1;				
		}
	}
	
	protected void getExtroversion(Map<String, Integer> theCharacters, Question question) {
		
		String theAnswer5 = question.getSelectedAnswer().trim().replaceAll("\\s", "");				
		if (theAnswer5.equalsIgnoreCase("a.stronglyagree")) {
			theCharacters.put("charmer", theCharacters.get("charmer") + 1);
			theCharacters.put("idealist", theCharacters.get("idealist") + 1);
			//charmer +=1;
			//merchant += 1;
		} else if (theAnswer5.equalsIgnoreCase("b.agree")) {
			theCharacters.put("idealist", theCharacters.get("idealist") + 1);
			theCharacters.put("hero", theCharacters.get("hero") + 1);
			//idealist += 1;
			//black_knight += 1;
		} else if (theAnswer5.equalsIgnoreCase("c.neitheragreenordisagree")){
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);
			//pragmatist +=1;				
		} else if (theAnswer5.equalsIgnoreCase("d.disagree")) {
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);
			//pragmatist +=1;				
		} else if (theAnswer5.equalsIgnoreCase("e.stronglydisagree")){
			theCharacters.put("pragmatist", theCharacters.get("pragmatist") + 1);
			//pragmatist +=1;				
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
		theWizardHash.put("description", "Pragmatists are logical, strategic and natural problem solvers. Independent and self-contained, their insatiable curiosity about the world creates a never ending desire to fix all that is wrong with it. Famous Pragmatists include Steve Jobs, Charles Darwin and Hillary Clinton.");
		theWizardHash.put("gender", theGender);
		String rationalistURL =  "http://s3.amazonaws.com/crowdscanner_images/rationalist.png";
		
		rationalistURL = checkGenderURL(gender, rationalistURL);
		theWizardHash.put("url", rationalistURL);
		
		List<HashMap<String, String>> wizardClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> wizardHash = new HashMap<String, String>();
		wizardHash.put("question", "Find out: Do they like libraries?");
		wizardHash.put("clue", "Pragmatists tend to be on a quest for knowledge");
		HashMap<String, String> wizardHash2 = new HashMap<String, String>();
		wizardHash2.put("question", "Ask them: If something breaks, do you like to try to fix it yourself?");
		wizardHash2.put("clue", "Pragmatists tend to be efficient problem solvers");			
		HashMap<String, String> wizardHash3 = new HashMap<String, String>();
		wizardHash3.put("question", "Find out: How do they work best? Alone or in a team?");
		wizardHash3.put("clue", "Pragmatists tend to like their own company");
		HashMap<String, String> wizardHash4 = new HashMap<String, String>();
		wizardHash4.put("question", "Ask them: What future discovery do you anticipate the most?");
		wizardHash4.put("clue", "Pragmatists tend to be perceptive");
		HashMap<String, String> wizardHash5 = new HashMap<String, String>();
		wizardHash5.put("question", "Ask them: What talent would you like to develop?");
		wizardHash5.put("clue", "Pragmatists tend to be on a quest for knowledge");
		HashMap<String, String> wizardHash6 = new HashMap<String, String>();
		wizardHash6.put("question", "Play: Start to talk about the weather, how do they react?");
		wizardHash6.put("clue", "Pragmatists tend to despise small talk");
		HashMap<String, String> wizardHash7 = new HashMap<String, String>();
		wizardHash7.put("question", "Reflect: When did they seem most at ease?");
		wizardHash7.put("clue", "Pragmatists tend to feel most at ease talking about hobbies");			
		HashMap<String, String> wizardHash8 = new HashMap<String, String>();
		wizardHash8.put("question", "Play: Give them a hug. (Remember to ask them for permission first).");
		wizardHash8.put("clue", "Pragmatists like to know the reason behind actions.");
        HashMap<String, String> wizardHash9 = new HashMap<String, String>();
		wizardHash9.put("question", "Ask them: What are you most afraid of?");
		wizardHash9.put("clue", "Pragmatists tend to have worries that slow them down");
        HashMap<String, String> wizardHash10 = new HashMap<String, String>();
		wizardHash10.put("question", "Test them: Give them one of your ideas, how do they react? Impressed?");
		wizardHash10.put("clue", "Pragmatists tend to admire new ideas");
        HashMap<String, String> wizardHash11 = new HashMap<String, String>();
		wizardHash11.put("question", "Ask them: What would you like to achieve in your life?");
		wizardHash11.put("clue", "Pragmatists tend to be driven to improve things");
        HashMap<String, String> wizardHash12 = new HashMap<String, String>();
		wizardHash12.put("question", "Reflect: Do they seem confident and sure of themselves?");
		wizardHash12.put("clue", "Pragmatists tend to be a little confused by life");
		//add has cues
		wizardClues.add(wizardHash);
		wizardClues.add(wizardHash2);
		wizardClues.add(wizardHash3);
		wizardClues.add(wizardHash4);
		wizardClues.add(wizardHash5);
		wizardClues.add(wizardHash6);
		wizardClues.add(wizardHash7);
		wizardClues.add(wizardHash8);
		wizardClues.add(wizardHash9);
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
		String protectorURL = "http://s3.amazonaws.com/crowdscanner_images/protector.png";
		protectorURL = checkGenderURL(gender, protectorURL);
		blackNightHashMeta.put("url", protectorURL);
		
		List<HashMap<String, String>> blackNightHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> blackNightHash = new HashMap<String, String>();
		blackNightHash.put("question", "Reflect: Would you trust them to watch your stuff while you go to the restroom?");
		blackNightHash.put("clue", "Heroes tend to be stable and trustworthy");
		HashMap<String, String> blackNightHash2 = new HashMap<String, String>();
		blackNightHash2.put("question", "Ask them: If you won the lottery, would you spend most of it on yourself?");
		blackNightHash2.put("clue", "Heroes tend to be generous and caring towards others");			
		HashMap<String, String> blackNightHash3 = new HashMap<String, String>();
		blackNightHash3.put("question", "Find out: Do they prefer to work for themselves, or someone else?");
		blackNightHash3.put("clue", "Heroes tend to be realistic, and like security");
        HashMap<String, String> blackNightHash4 = new HashMap<String, String>();
		blackNightHash4.put("question", "Test them: Do they remember _____________?");
		blackNightHash4.put("clue", "Heroes tend to be attentive to details");
        HashMap<String, String> blackNightHash5 = new HashMap<String, String>();
		blackNightHash5.put("question", "Play: Give them a hug - how do they react?");
		blackNightHash5.put("clue", "Heroes tend to trust others");
        HashMap<String, String> blackNightHash6 = new HashMap<String, String>();
		blackNightHash6.put("question", "Test them: Ask them to ____________");
		blackNightHash6.put("clue", "Heroes tend to like helping others");
        HashMap<String, String> blackNightHash7 = new HashMap<String, String>();
		blackNightHash7.put("question", "Reflect: If something happened, do you think they would handle it well?");
		blackNightHash7.put("clue", "Heroes tend to be reliable in an emergency");
		//add has cues
		blackNightHashClues.add(blackNightHash);
		blackNightHashClues.add(blackNightHash2);
		blackNightHashClues.add(blackNightHash3);
		blackNightHashClues.add(blackNightHash4);
		blackNightHashClues.add(blackNightHash5);
		blackNightHashClues.add(blackNightHash6);
		blackNightHashClues.add(blackNightHash7);
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
		outlawHash.put("question", "Find out: Where would they like to travel to right now?");
		outlawHash.put("clue", "Idealists tend to have travelled to unusual places");
		HashMap<String, String> outlawHash2 = new HashMap<String, String>();
		outlawHash2.put("question", "Ask them: When you wake up, and think about work, how do you feel?");
		outlawHash2.put("clue", "Idealists tend to be passionate about their career");			
		HashMap<String, String> outlawHash3 = new HashMap<String, String>();
		outlawHash3.put("question", "Inquire: If they could be _________________, would they be?");
		outlawHash3.put("clue", "Idealists tend to like to lead");
		HashMap<String, String> outlawHash4 = new HashMap<String, String>();
		outlawHash4.put("question", "Discover: What do they like to do with a free hour?");
		outlawHash4.put("clue", "Idealists tend to hate wasting time");
		HashMap<String, String> outlawHash5 = new HashMap<String, String>();
		outlawHash5.put("question", "Play: Tell them something horrible that happened to you - do you think they care?");
		outlawHash5.put("clue", "Idealists tend to be less than empathic");
		HashMap<String, String> outlawHash6 = new HashMap<String, String>();
		outlawHash6.put("question", "Play: Act as though wasting time is a good plan, how do they react?");
		outlawHash6.put("clue", "Idealists tend to be on a mission");	
		//add has cues
		outlawHashClues.add(outlawHash);
		outlawHashClues.add(outlawHash2);
		outlawHashClues.add(outlawHash3);
		outlawHashClues.add(outlawHash4);
		outlawHashClues.add(outlawHash5);
		outlawHashClues.add(outlawHash6);
		//add array clues into meta hash
		outlawHashMeta.put("clues", outlawHashClues);
		
		
		//Charmer
		HashMap<String, Object> jokerHashMeta = new HashMap<String, Object>();
		jokerHashMeta.put("nerd_type", "Charmer");
		jokerHashMeta.put("nert_key", "charmer");
		jokerHashMeta.put("description", "Charmers break the rules. They are delighted by spontaneous experiences so spending time with them is entertaining, if a little dangerous. They are relentless in their desire to express their inner dreams. Famous Charmers include Madonna and Woody Allen.");
		jokerHashMeta.put("gender", theGender);
		String performerURL = "http://s3.amazonaws.com/crowdscanner_images/performer.png";
		performerURL = checkGenderURL(gender, performerURL);
		jokerHashMeta.put("url", performerURL);

		
		List<HashMap<String, String>> jokerHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> jokerHash = new HashMap<String, String>();
		jokerHash.put("question", "Ask them: Do you think art or science is more important to mankind?");
		jokerHash.put("clue", "Charmers tend to love anything to do with creation or crafting");
		HashMap<String, String> jokerHash2 = new HashMap<String, String>();
		jokerHash2.put("question", "Ask them: Whats the first thing you notice about someone new?");
		jokerHash2.put("clue", "Charmers tend to be observant");			
		HashMap<String, String> jokerHash3 = new HashMap<String, String>();
		jokerHash3.put("question", "Ask them: Have you ever _____________?");
		jokerHash3.put("clue", "Charmers tend to be scatterbrained");
		HashMap<String, String> jokerHash4 = new HashMap<String, String>();
		jokerHash4.put("question", "Discover: What do they consider a waste in society?");
		jokerHash4.put("clue", "Charmers tend to be observant");
		HashMap<String, String> jokerHash5 = new HashMap<String, String>();
		jokerHash5.put("question", "Find out: What are they most proud of creating?");
		jokerHash5.put("clue", "Charmers tend to love anything to do with creation or crafting");
		HashMap<String, String> jokerHash6 = new HashMap<String, String>();
		jokerHash6.put("question", "Investigate: How would they feel if they lost _____________?");
		jokerHash6.put("clue", "Charmers tend to not get attached to things");
        HashMap<String, String> jokerHash7 = new HashMap<String, String>();
		jokerHash7.put("question", "Investigate: What are their hopes for the future?");
		jokerHash7.put("clue", "Charmers tend to be optimistic");
        HashMap<String, String> jokerHash8 = new HashMap<String, String>();
		jokerHash8.put("question", "Imagine: Would you like to hang out with them?");
		jokerHash8.put("clue", "Charmers tend to be fun to hang around");
        HashMap<String, String> jokerHash9 = new HashMap<String, String>();
		jokerHash9.put("question", "Reflect: Are they relaxed around you?");
		jokerHash9.put("clue", "Charmers tend to like being around people");
        HashMap<String, String> jokerHash10 = new HashMap<String, String>();
		jokerHash10.put("question", "Test them: Do they remember ___________?");
		jokerHash10.put("clue", "Charmers tend to be forgetful");
        HashMap<String, String> jokerHash11 = new HashMap<String, String>();
		jokerHash11.put("question", "Find out: Have they ever spent time at the lost and found?");
		jokerHash11.put("clue", "Charmers tend to lose things");
		HashMap<String, String> jokerHash12 = new HashMap<String, String>();
		jokerHash12.put("question", "Reflect: Did they make up a lot of their own questions?");
		jokerHash12.put("clue", "Charmers tend to be great conversationalists");
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
		jokerHashClues.add(jokerHash11);
		jokerHashClues.add(jokerHash12);
		//add array clues into meta hash
		jokerHashMeta.put("clues", jokerHashClues);
		
		personArrays.add(theMerchantHash);
		personArrays.add(theWizardHash);
		personArrays.add(blackNightHashMeta);
		personArrays.add(outlawHashMeta);
		personArrays.add(jokerHashMeta);
		
		return personArrays;
	}


	private String checkGenderURL(String gender, String rationalistURL) {
		if (gender.equals("b. Female")) {
			int slashIndex = rationalistURL.lastIndexOf('.');
			rationalistURL = rationalistURL.substring(0, slashIndex) + "_female.png";
		}
		return rationalistURL;
	}

}
