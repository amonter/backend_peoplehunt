package com.myownmotivator.model.questions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import flexjson.JSON;

@Entity
@Table(name="answer")
public class Answer {

	private Integer id;
	
	private String textualAnswer;	
	
	private Integer answerNumber;	
	
	private Integer parentAnswerId;
	
	private Date dateCreated;
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateCreated() {
		if (dateCreated != null){
			Calendar theCal = Calendar.getInstance();
			theCal.setTime(dateCreated);
			theCal.setTimeZone(TimeZone.getTimeZone("UTC"));
			dateCreated = theCal.getTime();
		}
		
		return dateCreated;		
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}	
	
	public Integer getParentAnswerId() {
		return parentAnswerId;
	}

	public void setParentAnswerId(Integer parentAnswerId) {
		this.parentAnswerId = parentAnswerId;
	}

	public Integer getAnswerNumber() {
		return answerNumber;
	}

	public void setAnswerNumber(Integer answerNumber) {
		this.answerNumber = answerNumber;
	}

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	

	@JSON(include= false)
	public String getTextualAnswer() {
		return textualAnswer;
	}

	public void setTextualAnswer(String textualAnswer) {		
		this.textualAnswer = textualAnswer;
	}
	
	@JSON(include= true)
	@Transient
	public String getURLEncodedAnswer() {
		
		try {
			return URLEncoder.encode(textualAnswer, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return null;
	}	
	
	@Override
	public boolean equals(Object obj) {
		
		
		Answer theAnswer = (Answer) obj;
		boolean isTrue = false;
		if (getParentAnswerId().intValue() == theAnswer.getParentAnswerId().intValue()) {
			isTrue = true;			
		}		
		
		return isTrue;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 9866455;
	}
	
}
