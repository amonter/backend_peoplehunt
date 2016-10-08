package com.crowdscanner.controller.avatars;

import java.util.List;
import java.util.Map;

import com.myownmotivator.model.questions.Question;

public class CodesEventCampEU extends ClassicAvatars {
	
public void firstIteration(List<Question> theQuestions, Map<String, Integer> theCharactersScore) {
		
		for (Question question : theQuestions) {		
			switch (question.getQuestionPhoneId()) {
				case 4929238://openness			
					getOpenness(theCharactersScore, question); 			
					break;
				case 2445363://agreeableness			
					getAgreeableness(theCharactersScore, question); 			
					break;
				case 1499875:// concienciosness			
					getConcienciosness(theCharactersScore, question); 			
					break;
				case 6359501:// stability			
					getStability(theCharactersScore, question); 			
					break;
				case 8090512: //extroversion			
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
				case 4929238://openness			
					getOpenness(theCharactersScore, question); 
					if (checkForRepeat(theCharactersScore) > 1) {
						break;
					} else {
						break outer;
					}
				case 2445363://agreeableness			
					getAgreeableness(theCharactersScore, question); 			
					if (checkForRepeat(theCharactersScore) > 1) {
						break;
					} else {
						break outer;
					}
				case 1499875:// concienciosness			
					getConcienciosness(theCharactersScore, question); 			
					if (checkForRepeat(theCharactersScore) > 1) {
						break;
					} else {
						break outer;
					}
				case 6359501:// stability			
					getStability(theCharactersScore, question); 			
					if (checkForRepeat(theCharactersScore) > 1) {
						break;
					} else {
						break outer;
					}
				case 8090512: //extroversion			
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
