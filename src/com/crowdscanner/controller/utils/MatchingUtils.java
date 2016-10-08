package com.crowdscanner.controller.utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.crowdscanner.model.Match;
import com.crowdscanner.model.ProfileBuffer;
import com.myownmotivator.model.User;
import com.myownmotivator.model.gaming.HuntIdGuess;
import com.myownmotivator.model.gaming.PeopleHuntId;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;

public class MatchingUtils {

	final static Logger logger = Logger.getLogger(MatchingUtils.class);
	
	
	public static HuntIdGuess doCollection(Integer bundleId, Boolean isCollected,  User user, User collectedUser) {
		
		int herHuntId = 0;
		/*
		List<PeopleHuntId> huntIds = collectedUser.getProfile().getMyHuntId();
		for (PeopleHuntId peopleHuntId : huntIds) {
			if (peopleHuntId.getBundleId().intValue() == bundleId.intValue()){
				herHuntId = peopleHuntId.getHuntId();
			}
		}
		
		
		Profile myProfile = user.getProfile();
		PeopleHuntId peopleHuntIdOwner = null; 
		for (PeopleHuntId peopleHuntId : myProfile.getMyHuntId()) {
			if (peopleHuntId.getBundleId().intValue() == bundleId.intValue()){
				peopleHuntIdOwner = peopleHuntId;
			}
		}
		
		HuntIdGuess huntGuess = new HuntIdGuess();
		huntGuess.setInterrogatorId(peopleHuntIdOwner.getHuntId());
		huntGuess.setInterrogeeId(herHuntId);
		huntGuess.setBundleId(bundleId);
		String collection = "collected";
		if (!isCollected){
			collection = "not_collected";			
		}
		huntGuess.setCharacterGuess(collection);
		huntGuess.setProfile(myProfile);
		myProfile.getHuntIdGuess().add(huntGuess);
		
		Calendar cal  = Calendar.getInstance();
		huntGuess.setGuessTime(cal.getTime());
		*/
		return null;
	}
	
	
	public static void matchTheQuestions(HashMap<Integer, Match> matches, String theAnswer, List<Question> theQuestions, HashMap<Integer, ProfileBuffer> profileBuffer) {
			
			for (Question question2 : theQuestions) {			
				
				Profile theProfile = question2.getProfiles().get(0);
				Integer profileId = theProfile.getId();			
				if (!profileBuffer.containsKey(profileId)){	
					
					ProfileBuffer theBuffer = new ProfileBuffer();
					theBuffer.setName(theProfile.getUser().getLastName());
					theBuffer.setUserName(theProfile.getUser().getUserName());
					theBuffer.setUrlImage(RestUtils.extractImageUrl(question2, "http://images.crowdscanner.com/anon_nosmile.png"));
					profileBuffer.put(profileId, theBuffer);
				}
				
				ProfileBuffer buffer = profileBuffer.get(profileId);
				String profileName =  buffer.getName();
				String userName =  buffer.getUserName();
				String theFile = buffer.getUrlImage();			
				
				if (!question2.getParent()) {				
					for (Answer answer: question2.getAnswers()) {					
						
						theAnswer = theAnswer.replaceAll("\\s", "");
						String textualAnswer = answer.getTextualAnswer().replaceAll("\\s", "");
						if (theAnswer.equals(textualAnswer) && answer.getAnswerNumber() == 1) {		
							
							if (matches.containsKey(profileId)) {
								Match theMatch = matches.get(profileId);								
								theMatch.setName(profileName);
								//theMatch.setProfileId(profileId);							
								matches.put(profileId, theMatch);
								
							} else {
								Match theMatch = new Match();							
								theMatch.setUrl(theFile);
								theMatch.setName(profileName);
								//theMatch.setProfileId(profileId);
								String theEmail = userName;
								if (StringUtils.contains(userName, "facebook_" )) theEmail = "no_email";
								theMatch.setUsername(userName);
								theMatch.setEmail(theEmail);
								matches.put(profileId, theMatch);
							}					
							
					} else if (!matches.containsKey(profileId)) {	
						
							Match theMatch =new Match();
							theMatch.setUrl(theFile);							
							theMatch.setName(profileName);
							theMatch.setUsername(userName);
							String theEmail = userName;
							if (StringUtils.contains(userName, "facebook_" )) theEmail = "no_email";
							theMatch.setEmail(theEmail);
							matches.put(profileId, theMatch);								
						}		
					} 							
				}
		}
	}
	
	
	
	
	public static void matchTheQuestionsKersey(HashMap<Integer, Match> matches, String theAnswer, List<Question> theQuestions, HashMap<Integer, ProfileBuffer> profileBuffer) {
		
		for (Question question2 : theQuestions) {			
			
			Profile theProfile = question2.getProfiles().get(0);
			Integer profileId = theProfile.getId();			
			if (!profileBuffer.containsKey(profileId)){	
				
				ProfileBuffer theBuffer = new ProfileBuffer();
				theBuffer.setName(theProfile.getUser().getLastName());
				theBuffer.setUserName(theProfile.getUser().getUserName());
				theBuffer.setUrlImage(RestUtils.extractImageUrl(question2, "http://images.crowdscanner.com/anon_nosmile.png"));
				profileBuffer.put(profileId, theBuffer);
			}
			
			ProfileBuffer buffer = profileBuffer.get(profileId);
			String profileName =  buffer.getName();
			String userName =  buffer.getUserName();
			String theFile = buffer.getUrlImage();			
			
			if (!question2.getParent()) {				
				for (Answer answer: question2.getAnswers()) {					
					
					theAnswer = theAnswer.replaceAll("\\s", "");
					String textualAnswer = answer.getTextualAnswer().replaceAll("\\s", "");
					if (theAnswer.equals(textualAnswer) && answer.getAnswerNumber() == 1) {		
						
						if (matches.containsKey(profileId)) {
							Match theMatch = matches.get(profileId);							
							theMatch.setName(profileName);
							//theMatch.setProfileId(profileId);							
							matches.put(profileId, theMatch);
							
						} else {
							Match theMatch = new Match();							
							theMatch.setUrl(theFile);
							theMatch.setName(profileName);
							//theMatch.setProfileId(profileId);
							String theEmail = userName;
							if (StringUtils.contains(userName, "facebook_" )) theEmail = "no_email";
							theMatch.setUsername(userName);
							theMatch.setEmail(theEmail);
							matches.put(profileId, theMatch);
						}					
						
				} else if (!matches.containsKey(profileId)) {	
					
						Match theMatch =new Match();
						theMatch.setUrl(theFile);						
						theMatch.setName(profileName);
						theMatch.setUsername(userName);
						String theEmail = userName;
						if (StringUtils.contains(userName, "facebook_" )) theEmail = "no_email";
						theMatch.setEmail(theEmail);
						matches.put(profileId, theMatch);								
					}		
				} 							
			}
		}
	}
}
