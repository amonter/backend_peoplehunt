package com.myownmotivator.model.gaming;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.myownmotivator.model.profile.Profile;

import flexjson.JSON;


@Entity
@Table(name = "score")
public class Score {

	private String latitude;
	
	private String longitude;
	
	private Integer score;
	
	private String bumpedProfiles;	

	private Profile profile;
	
	private Integer id;
	
	private Integer totalScore;

	@JSON(include= false)
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@JSON(include= false)
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getBumpedProfiles() {
		return bumpedProfiles;
	}

	public void setBumpedProfiles(String bumpedProfiles) {
		this.bumpedProfiles = bumpedProfiles;
	}

	@OneToOne
	@JoinColumn(name="PROFILE_ID", updatable = false, insertable = true)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Id
	@GeneratedValue
	@Column(name = "SCORE_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	
}
