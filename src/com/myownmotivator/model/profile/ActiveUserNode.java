package com.myownmotivator.model.profile;

import java.util.ArrayList;
import java.util.List;

import flexjson.JSON;

public class ActiveUserNode {

	private Long uid;
	
	private String name;
	
	private String url;
	
	private List<Long> connections = new ArrayList<Long>();
	
	private Integer numberConnections;
	
	private String status;	

	public Integer getNumberConnections() {
		
		numberConnections = connections.size();
		return numberConnections;
	}

	public void setNumberConnections(Integer numberConnections) {
		this.numberConnections = numberConnections;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@JSON(include= true)
	public List<Long> getConnections() {
		return connections;
	}

	public void setConnections(List<Long> connections) {
		this.connections = connections;
	}	
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
 }
