package com.myownmotivator.model.gaming;

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
@Table(name = "peoplehuntid")
public class PeopleHuntId {
	
	private Integer id;
	
	private Integer huntId;
	
	private Integer bundleId;
	
	private Profile profile;
	
	private	String pairedHuntIds;
	
	private String persona;
	
	private String url;	
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Id
	@GeneratedValue
	@Column(name = "PEOPLEHUNT_ID")
	public Integer getId() {
		return id;
	}

	public String getPairedHuntIds() {
		return pairedHuntIds;
	}

	public void setPairedHuntIds(String pairedHuntIds) {
		this.pairedHuntIds = pairedHuntIds;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHuntId() {
		return huntId;
	}

	public void setHuntId(Integer huntId) {
		this.huntId = huntId;
	}

	public Integer getBundleId() {
		return bundleId;
	}

	public void setBundleId(Integer bundleId) {
		this.bundleId = bundleId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROFILE_ID", updatable= false)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}	

}
