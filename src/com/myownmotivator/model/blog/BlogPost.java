package com.myownmotivator.model.blog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.Profile;

@Entity
@Table(name = "blog_post")
public class BlogPost implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	
	private Date publishedDate;
	
	private Profile publishedBy;
	
	private String description = "";	

	private String content;	

	private Integer id;
	
	public BlogPost() {
		
		publishedBy = new Profile();
	}
	
	private Set<Comment> comments = new LinkedHashSet<Comment>();

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


	@Transient
	public Profile getPublishedBy() {
		
		Profile profile = new Profile();
		User user = new User();
		user.setUserName("admin");
		profile.setUser(user);
		
		return profile;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)	
	@JoinColumn(name = "BLOGPOST_ID")
	public Set<Comment> getComments() {		
		return comments;
	}
	
	@Transient
	public List<Comment> getSortedComments() {
		
		List<Comment> listComments = new ArrayList<Comment>(comments);
		if (!listComments.isEmpty()) {		
					
			Collections.sort(listComments, new Comparator<Comment>() {
	
				public int compare(Comment comment, Comment comment2) {
	
					return comment2.getDate().compareTo(comment.getDate());
				}
			});		
		}
		
		Collections.reverse(listComments);
		
		return listComments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public void setPublishedBy(Profile publishedBy) {
		this.publishedBy = publishedBy;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "BLOGPOST_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
}
