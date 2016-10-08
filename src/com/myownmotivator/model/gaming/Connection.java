package com.myownmotivator.model.gaming;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "connection")
public class Connection {

	private Integer id;
	
	private Integer connectionOne;
	
	private Integer connectionTwo;
	
	private String topic;
	
	private String location;
	
	private Date connectionMade;
	
	private Integer bundleId;

	@Id
	@GeneratedValue
	@Column(name = "CONNECTION_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getConnectionOne() {
		return connectionOne;
	}

	public void setConnectionOne(Integer connectionOne) {
		this.connectionOne = connectionOne;
	}

	public Integer getConnectionTwo() {
		return connectionTwo;
	}

	public void setConnectionTwo(Integer connectionTwo) {
		this.connectionTwo = connectionTwo;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getConnectionMade() {
		return connectionMade;
	}

	public void setConnectionMade(Date connectionMade) {
		this.connectionMade = connectionMade;
	}

	public Integer getBundleId() {
		return bundleId;
	}

	public void setBundleId(Integer bundleId) {
		this.bundleId = bundleId;
	}
}
