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
@Table(name = "matchobject")
public class MatchObject {

	private Integer matchedWithHuntId;
	
	private Integer bundleId;
	
	private Integer matchRating;
	
	private Date matchTime;
	
	private Integer id;
	
	private Profile profile;
	
	private String matchedWithUsername;
	
	public String getMatchedWithUsername() {
		return matchedWithUsername;
	}

	public void setMatchedWithUsername(String matchedWithUsername) {
		this.matchedWithUsername = matchedWithUsername;
	}

	public Integer getMatchedWithHuntId() {
		return matchedWithHuntId;
	}

	public void setMatchedWithHuntId(Integer matchedWithHuntId) {
		this.matchedWithHuntId = matchedWithHuntId;
	}

	public Integer getBundleId() {
		return bundleId;
	}

	public void setBundleId(Integer bundleId) {
		this.bundleId = bundleId;
	}

	public Integer getMatchRating() {
		return matchRating;
	}

	public void setMatchRating(Integer matchRating) {
		this.matchRating = matchRating;
	}

	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	@Id
	@GeneratedValue
	@Column(name = "MATCH_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROFILE_ID", updatable= false)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}
