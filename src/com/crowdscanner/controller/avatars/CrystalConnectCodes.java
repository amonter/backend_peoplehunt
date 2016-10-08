package com.crowdscanner.controller.avatars;

import java.util.List;
import java.util.Map;

import com.myownmotivator.model.questions.Question;

public class CrystalConnectCodes extends ClassicAvatars  {
	
	

	public void firstIteration(List<Question> theQuestions, Map<String, Integer> theCharactersScore) {
		
		for (Question question : theQuestions) {		
			switch (question.getQuestionPhoneId()) {
				case 8416739://openness			
					getOpenness(theCharactersScore, question); 			
					break;
				case 1887326://agreeableness			
					getAgreeableness(theCharactersScore, question); 			
					break;
				case 7237688:// concienciosness			
					getConcienciosness(theCharactersScore, question); 			
					break;
				case 4503894:// stability			
					getStability(theCharactersScore, question); 			
					break;
				case 2593129: //extroversion			
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
					case 8416739://openness			
						getOpenness(theCharactersScore, question); 
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 1887326://agreeableness			
						getAgreeableness(theCharactersScore, question); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 7237688:// concienciosness			
						getConcienciosness(theCharactersScore, question); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 4503894:// stability			
						getStability(theCharactersScore, question); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 2593129: //extroversion			
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
