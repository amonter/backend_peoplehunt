package com.crowdscanner.controller.avatars;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myownmotivator.model.questions.Question;

public class RenzosGroupBundle extends AvantGardeCharacters {
	
	
	@Override
	public void computeGender(List<Question> theQuestions, HashMap<String, String> characterResult) {
		
		for (Question aQuestion : theQuestions) {	
			if (aQuestion.getQuestionPhoneId() == 9625816) {
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
	
	public void firstIteration(List<Question> theQuestions, Map<String, Integer> theCharactersScore) {
		
		for (Question question : theQuestions) {		
			switch (question.getQuestionPhoneId()) {
				case 7048825://openness			
					getOpenness(theCharactersScore, question); 			
					break;
				case 7311287://agreeableness			
					getAgreeableness(theCharactersScore, question); 			
					break;
				case 9943491:// concienciosness			
					getConcienciosness(theCharactersScore, question); 			
					break;
				case 6387473:// stability			
					getStability(theCharactersScore, question); 			
					break;
				case 8422869: //extroversion			
					getExtroversion(theCharactersScore, question);			
					break;			
			}		
		}
	}
	
	
	
	public void secondIteration(List<Question> theQuestions, Map<String, Integer> theCharactersScore, int repeat) {
		
		if (repeat > 1) {		
			outer:
			for (Question question : theQuestions) {	
				switch (question.getQuestionPhoneId()) {
					case 7048825://openness			
						getOpenness(theCharactersScore, question); 
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 7311287://agreeableness			
						getAgreeableness(theCharactersScore, question); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 9943491:// concienciosness			
						getConcienciosness(theCharactersScore, question); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 6387473:// stability			
						getStability(theCharactersScore, question); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 8422869: //extroversion			
						getExtroversion(theCharactersScore, question);			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}			
				}		
			}
		}
	}
	
}
