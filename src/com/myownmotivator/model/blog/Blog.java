package com.myownmotivator.model.blog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

@Entity
@Table(name = "blog")
public class Blog implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<BlogPost> blogPosts = new ArrayList<BlogPost>();

	private Integer id;
	
	private Integer  views = 0;		

	@Id
	@GeneratedValue
	@Column(name = "BLOG_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "BLOG_ID")
	public List<BlogPost> getBlogPosts() {
		
		return blogPosts;
	}

	public void setBlogPosts(List<BlogPost> blogPosts) {
		this.blogPosts = blogPosts;		
	}	
	
	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}
}
