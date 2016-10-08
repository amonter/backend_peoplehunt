package com.myownmotivator.model.profile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "efactordata")
public class EfactorData {

	private Integer id;
	
	private Integer efactorId;
	
	private String efactorUrl;
	
	private String email;
	
	private Profile profile;

	
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
	@Column(name = "EFACTORDATA_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEfactorId() {
		return efactorId;
	}

	public void setEfactorId(Integer efactorId) {
		this.efactorId = efactorId;
	}

	public String getEfactorUrl() {
		return efactorUrl;
	}

	public void setEfactorUrl(String efactorUrl) {
		this.efactorUrl = efactorUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
