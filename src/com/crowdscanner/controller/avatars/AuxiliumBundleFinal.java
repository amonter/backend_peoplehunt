package com.crowdscanner.controller.avatars;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myownmotivator.model.questions.Question;

public class AuxiliumBundleFinal extends KeirseyAlgorithm {

	public void computeGender(List<Question> theQuestions, HashMap<String, String> characterResult) {

		for (Question aQuestion : theQuestions) {	
			if (aQuestion.getQuestionPhoneId() == 281355) {
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
				case 4869870://thinking/feeling one		
					getTFOne(theCharactersScore, question); 			
					break;
				case 7724497://thinking/feeling two				
					getTFTwo(theCharactersScore, question); 			
					break;
				case 5894445://thinking/feeling three				
					getTFThree(theCharactersScore, question); 			
					break;
				case 6278883://Judging/perceiving one		
					getJPOne(theCharactersScore, question); 			
					break;
				case 9166657: //Judging/perceiving two			
					getJPTwo(theCharactersScore, question);			
					break;						
				case 517985: //Judging/perceiving three			
					getJPThree(theCharactersScore, question);				
					break;					
				case 611496: //Sensing/Intuition			
					getSNOne(theCharactersScore, question);			
					break;	
			}		
		}*/
	}	
}
