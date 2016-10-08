package com.crowdscanner.controller.avatars;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myownmotivator.model.questions.Question;

public class NewJustPlaying extends KeirseyAlgorithm {

	
	public void computeGender(List<Question> theQuestions, HashMap<String, String> characterResult) {
		
		for (Question aQuestion : theQuestions) {	
			if (aQuestion.getQuestionPhoneId() == 2345768) {
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
				case 9641699://thinking/feeling one		
					getTFOne(theCharactersScore, question, theLettersScore); 			
					break;
				case 7493989://thinking/feeling two				
					getTFTwo(theCharactersScore, question, theLettersScore); 			
					break;
				case 3042560://thinking/feeling three				
					getTFThree(theCharactersScore, question, theLettersScore); 			
					break;
				case 6793998://Judging/perceiving one		
					getJPOne(theCharactersScore, question, theLettersScore); 			
					break;
				case 9572377: //Judging/perceiving two			
					getJPTwo(theCharactersScore, question, theLettersScore);			
					break;						
				case 2801579: //Judging/perceiving three			
					getJPThree(theCharactersScore, question, theLettersScore);				
					break;					
				case 4293988: //Sensing/Intuition			
					getSNOne(theCharactersScore, question, theLettersScore);			
					break;						
				case 29093566: //Extraversion INtroversion			
					getEIOne(theCharactersScore, question, theLettersScore);			
					break;	
			}		
		}
	}
}