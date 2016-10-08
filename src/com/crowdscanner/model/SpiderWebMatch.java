package com.crowdscanner.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import flexjson.JSON;

public class SpiderWebMatch {

	private String url;
	
	private String name;
	
	private String email;
	
	private List<Map<Integer,String>> matchingAnswers = new ArrayList<Map<Integer,String>>();
	
	private List<Map<Integer,String>> selectedAnswers = new ArrayList<Map<Integer,String>>();

	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JSON(include= true)
	public List<Map<Integer,String>> getMatchingAnswers() {
		return matchingAnswers;
	}

	public void setMatchingAnswers(List<Map<Integer,String>> matchingAnswers) {
		this.matchingAnswers = matchingAnswers;
	}	
	
	@JSON(include= true)
	public List<Map<Integer, String>> getSelectedAnswers() {
		return selectedAnswers;
	}

	public void setSelectedAnswers(List<Map<Integer, String>> selectedAnswers) {
		this.selectedAnswers = selectedAnswers;
	}
}
