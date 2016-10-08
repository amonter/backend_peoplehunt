package com.myownmotivator.model.questions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


public class AnswerNominal {

	private Integer id;
	
	private String textualAnswer;	
	
	private Integer answerNumber;
	
	private Integer weight;
	
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTextualAnswer() {
		return textualAnswer;
	}

	public void setTextualAnswer(String textualAnswer) {
		this.textualAnswer = textualAnswer;
	}

	public Integer getAnswerNumber() {
		return answerNumber;
	}

	public void setAnswerNumber(Integer answerNumber) {
		this.answerNumber = answerNumber;
	}	
	
}
