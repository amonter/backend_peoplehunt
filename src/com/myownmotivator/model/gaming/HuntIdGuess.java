package com.myownmotivator.model.gaming;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.myownmotivator.model.profile.Profile;

@Entity
@Table(name = "huntguess")
public class HuntIdGuess {

	private Integer id;	

	private Integer interrogatorId;
	
	private Integer interrogeeId;
	
	private Integer bundleId;	

	private String characterGuess;
	
	private Date guessTime;
	
	private Profile profile;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROFILE_ID", updatable= false)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Integer getInterrogatorId() {
		return interrogatorId;
	}

	public void setInterrogatorId(Integer interrogatorId) {
		this.interrogatorId = interrogatorId;
	}

	public Integer getInterrogeeId() {
		return interrogeeId;
	}

	public void setInterrogeeId(Integer interrogeeId) {
		this.interrogeeId = interrogeeId;
	}

	public String getCharacterGuess() {
		return characterGuess;
	}

	public void setCharacterGuess(String characterGuess) {
		this.characterGuess = characterGuess;
	}

	public Date getGuessTime() {
		return guessTime;
	}

	public void setGuessTime(Date guessTime) {
		this.guessTime = guessTime;
	}	
	
	@Id
	@GeneratedValue
	@Column(name = "HUNT_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getBundleId() {
		return bundleId;
	}

	public void setBundleId(Integer bundleId) {
		this.bundleId = bundleId;
	}
}
