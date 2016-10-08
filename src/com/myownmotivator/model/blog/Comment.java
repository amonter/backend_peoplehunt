package com.myownmotivator.model.blog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Transient
	public Integer getBlogPostId() {
		return blogPostId;
	}

	public void setBlogPostId(Integer blogPostId) {
		this.blogPostId = blogPostId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	@Transient
	public String getHTMLContent () {
		
		String content = StringUtils.replace(getContent(), "\n", "<br>");				
		return content;
	}
}
