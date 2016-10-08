package com.crowdscanner.controller.avatars;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myownmotivator.model.questions.Question;

public class SixteenthAnualEducationForum extends KeirseyAlgorithm {

	public void computeGender(List<Question> theQuestions, HashMap<String, String> characterResult) {

		for (Question aQuestion : theQuestions) {	
			if (aQuestion.getQuestionPhoneId() == 3609973) {
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


	public void firstIteration(List<Question> theQuestions, Map<String, Integer> theCharactersScore){

		/*
		for (Question question : theQuestions) {		
			switch (question.getQuestionPhoneId()) {
				case 7160983://thinking/feeling one		
					getTFOne(theCharactersScore, question); 			
					break;
				case 2865297://thinking/feeling two				
					getTFTwo(theCharactersScore, question); 			
					break;
				case 7099740://thinking/feeling three				
					getTFThree(theCharactersScore, question); 			
					break;
				case 1490600://Judging/perceiving one		
					getJPOne(theCharactersScore, question); 			
					break;
				case 2442284: //Judging/perceiving two			
					getJPTwo(theCharactersScore, question);			
					break;						
				case 247352: //Judging/perceiving three			
					getJPThree(theCharactersScore, question);				
					break;					
				case 833817: //Sensing/Intuition			
					getSNOne(theCharactersScore, question);			
					break;	
			}		
		}*/
	}	
}
