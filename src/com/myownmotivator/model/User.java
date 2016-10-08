package com.myownmotivator.model;

// Generated 11-Aug-2007 18:07:02 by Hibernate Tools 3.2.0.b9

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.Profile;

import flexjson.JSON;


@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Profile profile = new Profile();

	private String email;

	private String firstName;

	private String lastName;

	private String userName;

	private String gender;

	private String password;
	
	private String newPassword;
	
	private String newPasswordConfirm;

	private String country;
	
	private String state = "";
	
	private String role = "learner";

	private Integer age = -1;

	private Integer imageID = -1;	
	
	private String captcha;	
	
	private String avatarImage;	

	@JSON(include = false)
	@Transient
	public int getImageID() {

		Profile profile = getProfile();
		List<File> files = profile.getFiles();

		for (File file : files) {

			if (file.getFileName().equalsIgnoreCase("profilePhoto")) {

				setImageID(file.getId());
			}
		}

		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}	

	public User() {	
	}

	public User(String firstName, String password) {
		this.firstName = firstName;
		this.password = password;
	}

	public User(Profile profile, String email, String firstName,
			String lastName, String userName, String gender, String password) {
		this.profile = profile;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.gender = gender;
		this.password = password;		
	}

	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="PROFILE_ID", updatable = true)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	

	@JSON(include = false)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@JSON(include = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JSON(include = false)
	@Transient
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@JSON(include = false)
	@Transient
	public String getNewPasswordConfirm() {
		return newPasswordConfirm;
	}

	public void setNewPasswordConfirm(String newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
	}

	@JSON(include = false)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Transient
	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	@Transient
	public String getAvatarImage() {
		return avatarImage;
	}

	public void setAvatarImage(String avatarImage) {
		this.avatarImage = avatarImage;
	}
	
}
