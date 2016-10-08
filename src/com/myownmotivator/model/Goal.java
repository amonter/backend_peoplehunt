package com.myownmotivator.model;

import java.io.Serializable;

import com.myownmotivator.model.profile.Profile;

public class Goal implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String description;
	
	private int id;

	private Profile profileID;
	
	public Profile getProfileID() {
		return profileID;
	}

	public void setProfileID(Profile profileID) {
		this.profileID = profileID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
