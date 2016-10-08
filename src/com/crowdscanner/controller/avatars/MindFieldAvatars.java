package com.crowdscanner.controller.avatars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.myownmotivator.model.questions.Question;

@Component
public class MindFieldAvatars extends CharacterMaker {

	@Override
	public HashMap<String, String> characterAlg(List<Question> theQuestions) {
	
		//for loop
		int finalRange = 0;
		for (Question question : theQuestions) {
			//get Answer stringAnswer = stringAnswer.trim().replaceAll("\\s", "");
			String theAnswer = question.getSelectedAnswer().trim().replaceAll("\\s", "");
			//calculate the Number Agree/Disagree
			int answerDegree = 0;
			if (theAnswer.equalsIgnoreCase("a.stronglyagree")) {
				answerDegree = 5;
			} else if (theAnswer.equalsIgnoreCase("b.agree")) {
				answerDegree = 4;
			} else if (theAnswer.equalsIgnoreCase("c.neitheragreenordisagree")){
				answerDegree = 3;				
			} else if (theAnswer.equalsIgnoreCase("d.disagree")) {
				answerDegree = 2;				
			} else if (theAnswer.equalsIgnoreCase("e.stronglydisagree")){
				answerDegree = 1;				
			} 
					
			finalRange += answerDegree;			
		}
			
		
		//Find the character range 		
		HashMap<String, String> resHash = new HashMap<String, String>();		
		if (finalRange <= 11) {
			resHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/nerd.png");
			resHash.put("nerd_type", "Cyber Geek");
			resHash.put("nerd_key", "cyber_geek");
		} else if (finalRange >=12 && finalRange <= 16) {
			resHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/scientist.png");
			resHash.put("nerd_type", "Mad Hacker Scientist");
			resHash.put("nerd_key", "nerdy_hackerscientist");
		} else if (finalRange >=17 && finalRange <= 18){
			resHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/politician.png");
			resHash.put("nerd_type", "Political Poet");
			resHash.put("nerd_key", "nerdy_politicalpoet");				
		} else if (finalRange >= 19 && finalRange <= 21) {
			resHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/social.png");
			resHash.put("nerd_type", "Social Anarchist");
			resHash.put("nerd_key", "nerdy_socialanarchist");				
		} else if (finalRange >= 22 && finalRange <= 25){
			resHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/artist.png");
			resHash.put("nerd_type", "Artistic Genius");
			resHash.put("nerd_key", "nerdy_artisticgenius");
		} 				
		
		return resHash;
	}

	public String searchAvatarURL(String avatar){
		
		String url = null;
		if (avatar.equals("Cyber Geek")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/nerd.png";
			
		} else if (avatar.equals("Mad Hacker Scientist")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/scientist.png";		
			
		} else if (avatar.equals("Political Poet")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/politician.png";				
			
		} else if (avatar.equals("Social Anarchist")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/social.png";				
						
		} else if (avatar.equals("Artistic Genius")) {
			url = "http://s3.amazonaws.com/crowdscanner_images/artist.png";			
		}
		
		return url;
	}
	
	
	@Override
	public List<HashMap<String, Object>> getAvatarsInfo() {
		
	List<HashMap<String, Object>> personArrays = new ArrayList<HashMap<String,Object>>();
		
		//Merchant
		HashMap<String, Object> theMerchantHash = new HashMap<String, Object>();
		theMerchantHash.put("nerd_type", "Cyber Geek");
		theMerchantHash.put("nerd_key", "cyber_geek");
		theMerchantHash.put("description", "Cyber Geeks are smart and savvy, especially with people, having a remarkable ability to understand and respond to their needs, hence their magnificent wealth. They are flexible, good at negotiating, yet indulgent, feasting on the finest life has to offer. They are eager to find a bargain, and wont miss an opportunity.");
		theMerchantHash.put("url", "http://s3.amazonaws.com/crowdscanner_images/nerd.png");
		
		List<HashMap<String, String>> arrayClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> cluesHash = new HashMap<String, String>();
		cluesHash.put("question", "In a very competitive environment, how do you react?");
		cluesHash.put("clue", "Cyber Geek tend to be driven");
		HashMap<String, String> cluesHash2 = new HashMap<String, String>();
		cluesHash2.put("question", "If a fire were to break out, right here, what would you do?");
		cluesHash2.put("clue", "Cyber Geek tend to be relaxed and adaptive in a crisis");			
		HashMap<String, String> cluesHash3 = new HashMap<String, String>();
		cluesHash3.put("question", "What would you miss most if you were left alone for a year in the woods?");
		cluesHash3.put("clue", "Cyber Geek tend to love to be around people");
		//add has cues
		arrayClues.add(cluesHash);
		arrayClues.add(cluesHash2);
		arrayClues.add(cluesHash3);
		//add array clues into meta hash
		theMerchantHash.put("clues", arrayClues);
		
		//WIzard
		HashMap<String, Object> theWizardHash = new HashMap<String, Object>();
		theWizardHash.put("nerd_type", "Mad Hacker Scientist");
		theWizardHash.put("nerd_key", "nerdy_hackerscientist");
		theWizardHash.put("description", "Wizards can be logical and are known for possessing much greater physical and mental power than ordinary humans. Abstract and theoretical, they are naturally inventive, often on a quest to find the solution to a lofty problem. They are adept at spotting patterns.");
		theWizardHash.put("url", "http://s3.amazonaws.com/crowdscanner_images/scientist.png");
		
		List<HashMap<String, String>> wizardClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> wizardHash = new HashMap<String, String>();
		wizardHash.put("question", "Do you like libraries?");
		wizardHash.put("clue", "Mad Hacker Scientist tend to be on a quest for knowledge");
		HashMap<String, String> wizardHash2 = new HashMap<String, String>();
		wizardHash2.put("question", "If something breaks, do you like to try to fix it yourself?");
		wizardHash2.put("clue", "Mad Hacker Scientist tend to be efficient problem solvers");			
		HashMap<String, String> wizardHash3 = new HashMap<String, String>();
		wizardHash3.put("question", "How do you work best? Alone or in a team?");
		wizardHash3.put("clue", "Mad Hacker Scientist tend to like their own company");
		HashMap<String, String> wizardHash4 = new HashMap<String, String>();
		wizardHash4.put("question", "What future discovery do you anticipate the most?");
		wizardHash4.put("clue", "Mad Hacker Scientist tend to be on a quest for knowledge");
		HashMap<String, String> wizardHash5 = new HashMap<String, String>();
		wizardHash5.put("question", "What talent would you like to develop?");
		wizardHash5.put("clue", "Mad Hacker Scientist tend to be on a quest for knowledge");
		//add has cues
		wizardClues.add(wizardHash);
		wizardClues.add(wizardHash2);
		wizardClues.add(wizardHash3);
		wizardClues.add(wizardHash4);
		wizardClues.add(wizardHash5);
		//add array clues into meta hash
		theWizardHash.put("clues", wizardClues);
		
		//Black Knight
		HashMap<String, Object> blackNightHashMeta = new HashMap<String, Object>();
		blackNightHashMeta.put("nerd_type", "Political Poet");
		blackNightHashMeta.put("nerd_key", "nerdy_politicalpoet");	
		blackNightHashMeta.put("description", "Black Knights are trustworthy, reliable individuals. They enjoy when everything is done according to plan, and are fair and kind in their approach to others. They are cool under pressure. When leading a team, they are reliable, diligent, and industrious, so their sword is just for show.");
		blackNightHashMeta.put("url", "http://s3.amazonaws.com/crowdscanner_images/politician.png");
		
		List<HashMap<String, String>> blackNightHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> blackNightHash = new HashMap<String, String>();
		blackNightHash.put("question", "Are you ever late for important meetings?");
		blackNightHash.put("clue", "Political Poet tend to be trustworthy");
		HashMap<String, String> blackNightHash2 = new HashMap<String, String>();
		blackNightHash2.put("question", "If you won the lottery, would you spend most of it on yourself?");
		blackNightHash2.put("clue", "Political Poet tend to be generous and caring towards others");			
		HashMap<String, String> blackNightHash3 = new HashMap<String, String>();
		blackNightHash3.put("question", "Do you prefer to work for yourself, or for someone else?");
		blackNightHash3.put("clue", "Political Poet tend to be realistic, and like security");
		//add has cues
		blackNightHashClues.add(blackNightHash);
		blackNightHashClues.add(blackNightHash2);
		blackNightHashClues.add(blackNightHash3);
		//add array clues into meta hash
		blackNightHashMeta.put("clues", blackNightHashClues);
		
		//OutLaw
		HashMap<String, Object> outlawHashMeta = new HashMap<String, Object>();
		outlawHashMeta.put("nerd_type", "Social Anarchist");
		outlawHashMeta.put("nerd_key", "nerdy_socialanarchist");		
		outlawHashMeta.put("description", "Outlaws follow their gut and strive for the highest ideals in life. They want to make the world a better place, and have a huge appreciation and curiosity for the world around them. They are decisive, inventive with clothing and will most likely be passionately pursuing some worthy cause.");
		outlawHashMeta.put("url", "http://s3.amazonaws.com/crowdscanner_images/social.png");
		
		List<HashMap<String, String>> outlawHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> outlawHash = new HashMap<String, String>();
		outlawHash.put("question", "Where would you like to travel to right now?");
		outlawHash.put("clue", "Social Anarchist tend to have travelled to unusual places");
		HashMap<String, String> outlawHash2 = new HashMap<String, String>();
		outlawHash2.put("question", "When you wake up, and think about work, how do you feel?");
		outlawHash2.put("clue", "Social Anarchist tend to be passionate about their career");			
		HashMap<String, String> outlawHash3 = new HashMap<String, String>();
		outlawHash3.put("question", "If you could be president of your country, would you be?");
		outlawHash3.put("clue", "Social Anarchist tend to like to lead");
		HashMap<String, String> outlawHash4 = new HashMap<String, String>();
		outlawHash4.put("question", "What do you like to do with a free hour?");
		outlawHash4.put("clue", "Social Anarchist tend to hate wasting time");
		//add has cues
		outlawHashClues.add(outlawHash);
		outlawHashClues.add(outlawHash2);
		outlawHashClues.add(outlawHash3);
		outlawHashClues.add(outlawHash4);
		//add array clues into meta hash
		outlawHashMeta.put("clues", outlawHashClues);
		
		
		//Joker
		HashMap<String, Object> jokerHashMeta = new HashMap<String, Object>();
		jokerHashMeta.put("nerd_type", "Artistic Genius");
		jokerHashMeta.put("nerd_key", "nerdy_artisticgenius");
		jokerHashMeta.put("description", "Jokers are observant, delighted by experiences and live in the here and now. Their spontaneity can get them into trouble, but they wont miss an opportunity. Either wildly rich, or desperately poor, jokers are relentless in their desire to express their inner dreams. Spending time with them will always be entertaining.");
		jokerHashMeta.put("url", "http://s3.amazonaws.com/crowdscanner_images/artist.png");

		
		List<HashMap<String, String>> jokerHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> jokerHash = new HashMap<String, String>();
		jokerHash.put("question", "Do you think art or science is more important to mankind?");
		jokerHash.put("clue", "Artistic Genius tend to love anything to do with creation or crafting");
		HashMap<String, String> jokerHash2 = new HashMap<String, String>();
		jokerHash2.put("question", "Whats the first thing you notice about someone new?");
		jokerHash2.put("clue", "Artistic Genius tend to be observant");			
		HashMap<String, String> jokerHash3 = new HashMap<String, String>();
		jokerHash3.put("question", "Have you ever run out of gas?");
		jokerHash3.put("clue", "Artistic Genius tend to be scatterbrained");
		HashMap<String, String> jokerHash4 = new HashMap<String, String>();
		jokerHash4.put("question", "What do you consider a waste in our society?");
		jokerHash4.put("clue", "Artistic Genius tend to be observant");
		HashMap<String, String> jokerHash5 = new HashMap<String, String>();
		jokerHash5.put("question", "What are you most proud of creating in your life?");
		jokerHash5.put("clue", "Artistic Genius tend to love anything to do with creation or crafting");
		//add has cues
		jokerHashClues.add(jokerHash);
		jokerHashClues.add(jokerHash2);
		jokerHashClues.add(jokerHash3);
		jokerHashClues.add(jokerHash4);
		jokerHashClues.add(jokerHash5);
		//add array clues into meta hash
		jokerHashMeta.put("clues", jokerHashClues);
		
		personArrays.add(theMerchantHash);
		personArrays.add(theWizardHash);
		personArrays.add(blackNightHashMeta);
		personArrays.add(outlawHashMeta);
		personArrays.add(jokerHashMeta);
		
		return personArrays;
	}

	@Override
	public List<HashMap<String, Object>> getAvatarsInfoByGender(String gender) {
		// TODO Auto-generated method stub
		return null;
	}

}
