package com.crowdscanner.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import flexjson.JSON;

public class QueueElement {

	private String elementId;
	
	private Date queuedTime;
	
	private String theMatchName;	
	
	private String theMatchImageUrl;	
	
	private String name;
	
	private String message;		
	
	private Map<String, Map<String,String>> myAnswers = new HashMap<String, Map<String,String>>();
	
	@JSON(include= true)
	public Map<String, Map<String,String>> getMyAnswers() {
		return myAnswers;
	}

	public void setMyAnswers(Map<String, Map<String,String>> myAnswers) {
		this.myAnswers = myAnswers;
	}	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private Integer index;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTheMatchImageUrl() {
		return theMatchImageUrl;
	}
	
	public void setTheMatchImageUrl(String theMatchURL) {
		this.theMatchImageUrl = theMatchURL;
	}
	
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String element) {
		this.elementId = element;
	}

	public Date getQueuedTime() {
		return queuedTime;
	}

	public void setQueuedTime(Date queuedTime) {
		this.queuedTime = queuedTime;
	}
	
	public String getTheMatchName() {
		return theMatchName;
	}
	
	public void setTheMatchName(String matchedWith) {
		this.theMatchName = matchedWith;
	}	
}
