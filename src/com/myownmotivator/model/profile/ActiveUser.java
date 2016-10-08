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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "active_user")
public class ActiveUser {

	private Long uid;	
	
	private Integer id;
	
	private String name;
	
	private String location;
	
	private String gender;
	
	private String url;
	
	private Moderator moderator;	
	
	private byte[] profilePhoto;	
	
	private String status;	
	
	private String accesstoken;

	private List<Friend> friends = new ArrayList<Friend>();	
	
	private List<ILiked> likes = new ArrayList<ILiked>();	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ACTIVEUSER_ID")
	public List<ILiked> getLikes() {
		return likes;
	}

	public void setLikes(List<ILiked> likes) {
		this.likes = likes;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	@Id
	@GeneratedValue
	@Column(name = "ACTIVEUSER_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@ManyToOne
	@JoinColumn(name = "MODERATOR_ID",updatable = false, insertable = true)
	public Moderator getModerator() {
		return moderator;
	}

	public void setModerator(Moderator moderator) {
		this.moderator = moderator;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ACTIVEUSER_ID")
	public List<Friend> getFriends() {
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}	
	
	public Long getUid() {
		return uid;
	}

	public String getUrl() {
		return url;
	}
	
	public byte[] getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(byte[] profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	
}
