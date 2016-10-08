package com.myownmotivator.model.profile;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "testimonies")
public class Testimonies  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;

	private String content;	
	
	private Profile profile;	
	
	private Date date;
	
	private String event;
	

	@OneToOne
	@JoinColumn(name = "PROFILE_ID",updatable = false, insertable = true)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Id
	@GeneratedValue
	@Column(name = "TESTIMONY_ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
}
