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
@Table(name = "profile_info")
public class ProfileInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String website;

	private String twitterId;	
	
	private Profile profile;
	
	private String languages;	
	
	private String interests;	

	private String selfPerception;	
	
	private String availableDay;
	
	private String availableTime;
	
	private Integer peopleHuntId;	

	public Integer getPeopleHuntId() {
		return peopleHuntId;
	}

	public void setPeopleHuntId(Integer peopleHuntId) {
		this.peopleHuntId = peopleHuntId;
	}

	@OneToOne
	@JoinColumn(name="PROFILE_ID")
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}	

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}	
	
	@Id
	@GeneratedValue
	@Column(name = "PROFILEINFO_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	

	public String getSelfPerception() {
		return selfPerception;
	}

	public void setSelfPerception(String selfPerception) {
		this.selfPerception = selfPerception;
	}	
	
	
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}
	
	public String getAvailableDay() {
		return availableDay;
	}

	public void setAvailableDay(String availableDay) {
		this.availableDay = availableDay;
	}

	public String getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(String availableTime) {
		this.availableTime = availableTime;
	}
}
