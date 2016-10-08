package com.crowdscanner.controller.avatars;

import java.util.List;
import java.util.Map;

import com.myownmotivator.model.questions.Question;

public class QuestionCodesEventCamp extends ClassicAvatars {

	public void firstIteration(List<Question> theQuestions, Map<String, Integer> theCharactersScore) {
		
		for (Question question : theQuestions) {		
			switch (question.getQuestionPhoneId()) {
				case 4481495://openness			
					getOpenness(theCharactersScore, question); 			
					break;
				case 2804151://agreeableness			
					getAgreeableness(theCharactersScore, question); 			
					break;
				case 6205614:// concienciosness			
					getConcienciosness(theCharactersScore, question); 			
					break;
				case 756736:// stability			
					getStability(theCharactersScore, question); 			
					break;
				case 1447467: //extroversion			
					getExtroversion(theCharactersScore, question);			
					break;			
			}		
		}
	}
	
	
	
	public void secondIteration(List<Question> theQuestions, Map<String, Integer> theCharactersScore, int repeat) {
		
		if (repeat > 1) {		
			outer:
			for (Question question : theQuestions) {	
				System.out.println(question.getQuestionPhoneId());
				switch (question.getQuestionPhoneId()) {
					case 4481495://openness			
						getOpenness(theCharactersScore, question); 
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 2804151://agreeableness			
						getAgreeableness(theCharactersScore, question); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 6205614:// concienciosness			
						getConcienciosness(theCharactersScore, question); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 756736:// stability			
						getStability(theCharactersScore, question); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 1447467: //extroversion			
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
