package com.crowdscanner.controller.avatars;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myownmotivator.model.questions.Question;

public class TechBreakFastConnect extends KeirseyAlgorithm {

	
	public void computeGender(List<Question> theQuestions, HashMap<String, String> characterResult) {
		
		for (Question aQuestion : theQuestions) {	
			if (aQuestion.getQuestionPhoneId() == 2073252) {
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
				case 2570053://thinking/feeling one		
					getTFOne(theCharactersScore, question); 			
					break;
				case 4720419://thinking/feeling two				
					getTFTwo(theCharactersScore, question); 			
					break;
				case 4087566://thinking/feeling three				
					getTFThree(theCharactersScore, question); 			
					break;
				case 8936529://Judging/perceiving one		
					getJPOne(theCharactersScore, question); 			
					break;
				case 1820153: //Judging/perceiving two			
					getJPTwo(theCharactersScore, question);			
					break;						
				case 1489078: //Judging/perceiving three			
					getJPThree(theCharactersScore, question);				
					break;					
				case 3587492: //Sensing/Intuition			
					getSNOne(theCharactersScore, question);			
					break;	
			}		
		}*/
	}		
}
