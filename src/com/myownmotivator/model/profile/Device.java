package com.myownmotivator.model.profile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class Device {

	private Integer id;	
	
	private String deviceId;
	
	private String deviceName;
	
	private Moderator moderator;

	@Id
	@GeneratedValue
	@Column(name = "DEVICE_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@ManyToOne
	@JoinColumn(name = "MODERATOR_ID",updatable = false, insertable = true)
	public Moderator getModerator() {
		return moderator;
	}

	public void setModerator(Moderator moderator) {
		this.moderator = moderator;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
}
