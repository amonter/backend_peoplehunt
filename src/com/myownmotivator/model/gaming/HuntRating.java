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
@Table(name = "huntrating")
public class HuntRating {

	private Integer id;
	
	private Integer bundleId;
	
	private Integer otherHuntId;

	private String typeRating;
	
	private String rating;
	
	private Profile profile;	
	
	private Date dateCalled;	

	private boolean opting;
	

	@Id
	@GeneratedValue
	@Column(name = "HUNTRATING_ID")
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

	public Integer getOtherHuntId() {
		return otherHuntId;
	}

	public void setOtherHuntId(Integer otherHuntId) {
		this.otherHuntId = otherHuntId;
	}

	public String getTypeRating() {
		return typeRating;
	}

	public void setTypeRating(String typeRating) {
		this.typeRating = typeRating;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROFILE_ID", updatable= false)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public boolean getOpting() {
		return opting;
	}

	public void setOpting(boolean opting) {
		this.opting = opting;
	}	
	
	public Date getDateCalled() {
		return dateCalled;
	}

	public void setDateCalled(Date dateCalled) {
		this.dateCalled = dateCalled;
	}	
}
