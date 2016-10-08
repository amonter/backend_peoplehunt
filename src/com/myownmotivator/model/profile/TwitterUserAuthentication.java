package com.myownmotivator.model.profile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "twitterUserAuthentication")
public class TwitterUserAuthentication implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String username;
	
	private String secret;
	
	private String theKey;
	
	private Profile profile;	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}


	public String getTheKey() {
		return theKey;
	}

	public void setTheKey(String theKey) {
		this.theKey = theKey;
	}

	@Id
	@GeneratedValue
	@Column(name = "TWITTERUSER_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
	
	@OneToOne
	@JoinColumn(name="PROFILE_ID", updatable = false, insertable = true)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
