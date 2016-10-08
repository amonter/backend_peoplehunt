package com.myownmotivator.model.profile;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "moderator")
public class Moderator {

	private String deviceId;
	
	private Integer id;
	
	private List<Device> devices = new ArrayList<Device>();
	
	private List<ActiveUser> activeUsers = new ArrayList<ActiveUser>();

	private Profile profile;	

	@OneToOne
	@JoinColumn(name="PROFILE_ID", updatable = false, insertable = true)
	public Profile getProfile() {
		return profile;	
	}
	
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Id
	@GeneratedValue
	@Column(name = "MODERATOR_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "MODERATOR_ID")
	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}	
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "MODERATOR_ID")
	public List<ActiveUser> getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(List<ActiveUser> activeUsers) {
		this.activeUsers = activeUsers;
	}	
}
