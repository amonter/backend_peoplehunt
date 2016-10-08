package com.peoplehuntv2.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.myownmotivator.model.profile.Profile;

import flexjson.JSON;

@Entity
@Table(name = "asyncmessage")
public class AsyncMessage {
	
	
	@Id
	@GeneratedValue
	@Column(name="GROUP_ID")
	private Integer id;
	
	private String content;	

	private Date dateSent;
	
	private Date lastView;	

	private String sender;	
	
	private Integer senderId;	

	private String senderImageUrl;	
	
	private String profileImageUrl;	
	
	private String profileName;

	private Profile profile;
	
	private String reference;	

	@Id
	@GeneratedValue
	@Column(name="ASYNC_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@JSON(include= false)
	public String getContent() {		
		return content;		
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Transient
	public String getEncodedContent() {
		try {
			return URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}	

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateSent() {
		if (dateSent != null){
			Calendar theCal = Calendar.getInstance();
			theCal.setTime(dateSent);
			theCal.setTimeZone(TimeZone.getTimeZone("UTC"));
			dateSent = theCal.getTime();
		}
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}	
	
	@JSON(include= false)
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="PROFILE_ID", updatable= false)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public Date getLastView() {
		return lastView;
	}

	public void setLastView(Date lastView) {
		this.lastView = lastView;
	}
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	@Transient
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	@Transient
	public String getSenderImageUrl() {
		return senderImageUrl;
	}

	public void setSenderImageUrl(String senderImageUrl) {
		this.senderImageUrl = senderImageUrl;
	}
	
	@Transient
	public String getProfileImageUrl() {
		//this.profileImageUrl =  RestUtils.extractURLProfile(getProfile(), "http://images.crowdscanner.com/anon_nosmile.png");
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	
	@Transient
	public String getProfileName() {
		//this.profileName = getProfile().getUser().getLastName();
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	@Transient
	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}
}
