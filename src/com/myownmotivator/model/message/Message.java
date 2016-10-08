package com.myownmotivator.model.message;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.myownmotivator.model.profile.Profile;

@Entity
@Table(name = "message")
@NamedQueries(

	{ 
		@NamedQuery(name = "retrieveMessages", query = "from Message as m where m.profile.id =:profileid order by m.sendDate desc")	
	}
)
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String content;

	private String subject;

	private Date sendDate;

	private int senderId;
	
	private String senderName;
	
	private String feedBackEmail;	

	private Profile profile;
	
	private boolean readMessage = false;	

	
	public Message() {
		
		profile = new Profile();
	}	
	
	@ManyToOne
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
	@Column(name = "MESSAGE_ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int sender) {
		this.senderId = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public boolean getReadMessage() {
		return readMessage;
	}

	public void setReadMessage(boolean read) {
		this.readMessage = read;
	}
	
	@Transient
	public String getFeedBackEmail() {
		return feedBackEmail;
	}

	public void setFeedBackEmail(String feedBackEmail) {
		this.feedBackEmail = feedBackEmail;
	}
}
