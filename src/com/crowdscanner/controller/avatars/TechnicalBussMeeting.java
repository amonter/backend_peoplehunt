package com.crowdscanner.controller.avatars;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myownmotivator.model.questions.Question;

public class TechnicalBussMeeting extends KeirseyAlgorithm  {

	public void computeGender(List<Question> theQuestions, HashMap<String, String> characterResult) {
		
		for (Question aQuestion : theQuestions) {	
			if (aQuestion.getQuestionPhoneId() == 86454629) {
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
	
	
	public void firstIteration(List<Question> theQuestions, Map<String, Integer> theCharactersScore, Map<String, Integer> theLettersScore){
		
		for (Question question : theQuestions) {		
			switch (question.getQuestionPhoneId()) {
				case 93418937://thinking/feeling one		
					getTFOne(theCharactersScore, question, theLettersScore); 			
					break;
				case 6093812://Judging/perceiving one		
					getJPOne(theCharactersScore, question, theLettersScore); 			
					break;
				case 4233002: //Sensing/Intuition			
					getSNOne(theCharactersScore, question, theLettersScore);			
					break;	
				case 7993631://thinking/feeling two				
					getTFTwo(theCharactersScore, question, theLettersScore); 			
					break;
				case 9172982: //Judging/perceiving two			
					getJPTwo(theCharactersScore, question, theLettersScore);			
					break;
				case 6342635://thinking/feeling three				
					getTFThree(theCharactersScore, question, theLettersScore); 			
					break;										
				case 2401987: //Judging/perceiving three			
					getJPThree(theCharactersScore, question, theLettersScore);				
					break;										
				case 69293372: //Extraversion INtroversion			
					getEIOne(theCharactersScore, question, theLettersScore);			
					break;	
			}		
		}
	}
}
