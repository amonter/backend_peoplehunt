package com.crowdscanner.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flexjson.JSON;

public class Match {

	private String url;
	
	private String name;
	
	private String email;
	
	private String username;	
	
	private Integer profileId;
	
	private Integer huntId;	
	
	private String matchingMessage;	
	
	private Map<String, List<String>> thingsInCommon = new HashMap<String, List<String>>();
	

	@JSON(include= true)
	public Map<String, List<String>> getThingsInCommon() {
		return thingsInCommon;
	}

	public void setThingsInCommon(Map<String, List<String>> thingsInCommon) {
		this.thingsInCommon = thingsInCommon;
	}
	

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
	
	@JSON(include= false)
	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}	
	
	public Integer getHuntId() {
		return huntId;
	}

	public void setHuntId(Integer huntId) {
		this.huntId = huntId;
	}
	
	public String getMatchingMessage() {
		return matchingMessage;
	}

	public void setMatchingMessage(String matchingMessage) {
		this.matchingMessage = matchingMessage;
	}	
}
