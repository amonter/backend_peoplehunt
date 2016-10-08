package com.crowdscanner.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "comment_crowd")
public class TheComment  implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String content;
	
	private String name;

	private Date date;
	
	private String website;	

	private Integer blogPostId;		

	@Id
	@GeneratedValue
	@Column(name = "COMMENT_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}	
	
	@Transient
	public Integer getBlogPostId() {
		return blogPostId;
	}

	public void setBlogPostId(Integer blogPostId) {
		this.blogPostId = blogPostId;
	}
	
}
