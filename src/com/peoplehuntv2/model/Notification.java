package com.peoplehuntv2.model;

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
@Table(name = "notification")
public class Notification {
	
	private Integer id;
	
	private Integer senderId;
	
	private boolean callBack;
	
	private String matchItem;
	
	private String imageurl;
	
	private String name;
	
	private Date notificationTime;	

	private Profile profile;
	
	private String lastMessage;

	@JSON(include= false)
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="PROFILE_ID", updatable= false)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Transient
	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String senderImageUrl) {
		this.imageurl = senderImageUrl;
	}

	@Transient
	public String getName() {
		return name;
	}

	public void setName(String senderName) {
		this.name = senderName;
	}

	@Id
	@GeneratedValue
	@Column(name="NOTIFICATION_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getNotificationTime() {
		if (notificationTime != null){
			Calendar theCal = Calendar.getInstance();
			theCal.setTime(notificationTime);
			theCal.setTimeZone(TimeZone.getTimeZone("UTC"));
			notificationTime = theCal.getTime();
		}
		return notificationTime;
	}

	public void setNotificationTime(Date notificationTime) {
		this.notificationTime = notificationTime;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public Integer getSenderId() {
		return senderId;
	}	

	public boolean isCallBack() {
		return callBack;
	}

	public void setCallBack(boolean callBack) {
		this.callBack = callBack;
	}

	public String getMatchItem() {
		return matchItem;
	}

	public void setMatchItem(String matchItem) {
		this.matchItem = matchItem;
	}
	@Transient	
	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}
	
	@Override
	public boolean equals(Object obj) {	
		Notification theNotification = (Notification) obj;
		boolean isTrue = false;
		if (getSenderId().intValue() == theNotification.getSenderId().intValue()) {
			isTrue = true;			
		}				
		return isTrue;
	}
	
	@Override
	public int hashCode() {		
		return 233391311;
	}	
}
