package com.crowdscanner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "blogpost_crowd")
public class TheBlogPost  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	
	private Date publishedDate;	
	
	private String description = "";	

	private String content;	

	private Integer id;	

	List<TheComment> comments = new ArrayList<TheComment>();	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)	
	@JoinColumn(name = "BLOGPOST_ID")
	public List<TheComment> getComments() {
		return comments;
	}

	public void setComments(List<TheComment> comments) {
		this.comments = comments;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Id
	@GeneratedValue
	@Column(name = "BLOG_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Transient
	public List<TheComment> getSortedComments() {
		
		List<TheComment> comments = getComments();
		
		if (!getComments().isEmpty()) {	
		
			Collections.sort(comments, new Comparator<TheComment>() {
	
				public int compare(TheComment comment, TheComment comment2) {
	
					return comment2.getDate().compareTo(comment.getDate());
				}
			});		
		}
		
		Collections.reverse(comments);
		
		return comments;
	}
	
}
