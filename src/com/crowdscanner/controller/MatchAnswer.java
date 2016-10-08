package com.crowdscanner.controller;

import java.util.ArrayList;
import java.util.List;

import com.crowdscanner.model.Match;

import flexjson.JSON;

public class MatchAnswer {

	private String questionDescription;	

	private String textualAnswer;
	
	private Integer questionPhoneId;
	
	private String encodedTextualAnswer;	
	
	private List<Match> matchProfiles = new ArrayList<Match>();

	@JSON(include= false)
	public String getTextualAnswer() {
		return textualAnswer;
	}

	public void setTextualAnswer(String textualAnswer) {
		this.textualAnswer = textualAnswer;
	}

	public Integer getQuestionPhoneId() {
		return questionPhoneId;
	}

	public void setQuestionPhoneId(Integer questionPhoneId) {
		this.questionPhoneId = questionPhoneId;
	}	
	
	@JSON(include= true)
	public List<Match> getMatchProfiles() {
		return matchProfiles;
	}

	public void setMatchProfiles(List<Match> matchProfiles) {
		this.matchProfiles = matchProfiles;
	}	
	
	@JSON(include= true)
	public String getEncodedTextualAnswer() {
		return encodedTextualAnswer;
	}

	public void setEncodedTextualAnswer(String encodedTextualAnswer) {
		this.encodedTextualAnswer = encodedTextualAnswer;
	}
	
	@JSON(include= true)
	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

}
