package com.myownmotivator.model.gaming;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "interaction_session")
public class InteractionSession {

	private Integer id;	
	
	private Integer bundleId;	
	
	private String queue;	

	private Date lastUpdate;	
	
	private String status;
	
	private String type;

	@Id
	@GeneratedValue
	@Column(name = "INTERACTION_ID")
	public Integer getId() {
		return id;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}		
	
	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
