package com.myownmotivator.model;

import java.io.Serializable;


public class UserLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;

	private String password;
	
	private String authentication;
	
	private Integer profileId;
	
	private boolean rememberMe = false;	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
	
	public UserLogin newUser () {
		
		return new UserLogin();
	}

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}
}
