package com.peoplehuntv2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.myownmotivator.model.profile.Profile;

import flexjson.JSON;

@Entity
@Table(name = "membership")
public class MemberShip implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String membershipType;	
	
	private Profile profile;	
	
	private List <Group> groups = new ArrayList<Group>();	
	
	@JSON(include= false)
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="PROFILE_ID", updatable= false)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@JSON(include= false)
	@Id
	@GeneratedValue
	@Column(name = "MEMBER_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JSON(include= true)
	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}
	
	@JSON(include= true)
	@ManyToMany(mappedBy = "memberships",
    targetEntity = Group.class, cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	
}
