package com.myownmotivator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@NamedQueries(

{
		@NamedQuery(name = "findByTopic", query = "select f from Forum as f where f.topic =:topic"),
		@NamedQuery(name = "findByPrimaryKey", query = "select f from Forum as f where f.id =:id") }

)
@Entity
@Table(name = "forum")
public class Forum implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Post> post = new ArrayList<Post>();

	private String topic;
	
	private String description;

	private Date creationDate;

	private int id;

	public Forum() {

		Calendar calendar = Calendar.getInstance();
		setCreationDate(calendar.getTime());
	}

	@Id
	@GeneratedValue
	@Column(name = "FORUM_ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "FORUM_ID")
	public List<Post> getPost() {
		return post;
	}

	public void setPost(List<Post> post) {
		this.post = post;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Transient
	public Integer getPostCount() {
		return post.size();
	}

	@Transient
	public Integer getCalculateTotalReplies() {
		
		int replies = 0;
		for (Post postValue : post) {
			replies = replies + postValue.getNumberReplies();
		}
		return replies;
	}

	@Transient
	public String getIdAsString() {

		return String.valueOf(getId());

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
