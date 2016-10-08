package com.myownmotivator.model.nfc;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import flexjson.JSON;

@Entity
@Table(name = "nfcobject")
public class NFCObject {

	private String title;

	private String tagId;
	
	private String description;
	
	private String jsonHistory;
	
	private Integer id;
	
	private String imageURL;
	
	private HashMap<String, Object> jsonHistoryObject = new HashMap<String, Object>();

	@Transient
	@JSON(include= true)
	public HashMap<String, Object> getJsonHistoryObject() {
		return jsonHistoryObject;
	}

	public void setJsonHistoryObject(HashMap<String, Object> jsonHistoryObject) {
		this.jsonHistoryObject = jsonHistoryObject;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JSON(include= false)
	public String getJsonHistory() {
		return jsonHistory;
	}

	public void setJsonHistory(String jsonHistory) {
		this.jsonHistory = jsonHistory;
	}

	@Id
	@GeneratedValue
	@Column(name = "NFCOBJECT_ID")
	@JSON(include= false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
