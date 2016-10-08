package com.peoplehuntv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.myownmotivator.model.profile.Profile;

import flexjson.JSON;

@Entity
@Table(name = "foundtarget")
public class FoundTarget implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Integer ownerId;	

	private List<Profile> foundProfiles = new ArrayList<Profile>();	

	private List<Status> statuses = new ArrayList<Status>();

	@Id
	@GeneratedValue
	@Column(name="FOUND_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@JSON(include= false)
	@OneToMany(mappedBy="foundTarget", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)	
	public List<Status> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}
	
	@ManyToMany(cascade = CascadeType.DETACH,  fetch = FetchType.LAZY)
	@JoinTable(name="profile_foundtarget",  
		joinColumns=@JoinColumn(name="FOUND_ID", unique = true),
			inverseJoinColumns=@JoinColumn(name="PROFILE_ID"))
	public List<Profile> getFoundProfiles() {
		return foundProfiles;
	}

	public void setFoundProfiles(List<Profile> foundProfiles) {
		this.foundProfiles = foundProfiles;
	}
	
	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	
	@Transient
	public Status getStatusType(String statusType){
		
		for (Status theStatus : getStatuses()) {
			if (theStatus.getFoundStatus().equals(statusType)){
				return theStatus;
			}
		}
		
		return null;		
	}

}
