package com.crowdscanner.model;

public class MatchQuestions {

	private String questionDescription;
	
	private Integer questionPhoneId;
	
	private Integer numberMatch;
	
	private String selectedAnswer;
	
	public String getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	public Integer getQuestionPhoneId() {
		return questionPhoneId;
	}

	public void setQuestionPhoneId(Integer questionPhoneId) {
		this.questionPhoneId = questionPhoneId;
	}

	public Integer getNumberMatch() {
		return numberMatch;
	}

	public void setNumberMatch(Integer matchedQuestions) {
		this.numberMatch = matchedQuestions;
	}	
	
}
