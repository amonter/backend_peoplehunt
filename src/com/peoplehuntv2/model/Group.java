package com.peoplehuntv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import flexjson.JSON;

@Entity
@Table(name = "thegroup")
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<MemberShip> memberships = new ArrayList<MemberShip>();	
	
	private String description;
	
	private String name;	

	private Integer id;
	
	private String imageURL;	

	private Date date;
	
	private String location;
	
	private byte[] bundleImage;
	
	private String permaLink;
	
	private String latitude;

	private String longitude;
	
	private boolean inProgress;	
	
	private String type;
	
	private String facebookId;
	
	@Id
	@GeneratedValue
	@Column(name="GROUP_ID")
	public Integer getId() {
		return id;
	}

	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@JSON(include= true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JSON(include= false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JSON(include= false)
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	@JSON(include= false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {	
		if (date != null){
			Calendar theCal = Calendar.getInstance();
			theCal.setTime(date);
			theCal.setTimeZone(TimeZone.getTimeZone("UTC"));
			date = theCal.getTime();
		}
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}

	
	@JSON(include= false)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@JSON(include= false)
	public byte[] getBundleImage() {
		return bundleImage;
	}

	public void setBundleImage(byte[] bundleImage) {
		this.bundleImage = bundleImage;
	}

	@JSON(include= false)
	public String getPermaLink() {
		return permaLink;
	}

	public void setPermaLink(String permaLink) {
		this.permaLink = permaLink;
	}

	@JSON(include= false)
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@JSON(include= false)
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@JSON(include= false)
	public boolean isInProgress() {
		return inProgress;
	}

	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}

	@JSON(include= false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JSON(include= true)
	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	
	@JSON(include= false)
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="group_members",  
			joinColumns=@JoinColumn(name="GROUP_ID", unique = true,  updatable = false),
			inverseJoinColumns=@JoinColumn(name="MEMBER_ID",  updatable = false))
	public List<MemberShip> getMemberships() {
		return memberships;
	}

	public void setMemberships(List<MemberShip> memberships) {
		this.memberships = memberships;
	}
	
	@Override
	public boolean equals(Object obj) {	
		Group theGroup = (Group) obj;
		boolean isTrue = false;
		if (getId().intValue() == theGroup.getId().intValue()) {
			isTrue = true;			
		}				
		return isTrue;
	}
	
	@Override
	public int hashCode() {		
		return 771234710;
	}

}
