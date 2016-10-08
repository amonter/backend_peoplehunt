package com.myownmotivator.model.profile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;


// Generated 11-Aug-2007 18:07:02 by Hibernate Tools 3.2.0.b9


@Entity
@Table(name = "file")
public class File implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;

	private int id;

	private byte[] fileContent;
	
	private MultipartFile multipartFile;

	private String fileName;
	
	private Integer userProfile;	

	private Profile profile;
	

	public File() {
	}
	
	public File(int id) {
	  this.id = id;
	}	

	public File(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public File(byte[] fileContent, String fileName) {
		this.fileContent = fileContent;
		this.fileName = fileName;
	}

	@Id
	@GeneratedValue
	@Column(name = "FILE_ID")
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getFileContent() {
		return this.fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@ManyToOne
	@JoinColumn(name = "PROFILE_ID",updatable = false, insertable = false)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Transient
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	
	@Transient
	public Integer getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(Integer userProfile) {
		this.userProfile = userProfile;
	}

}
