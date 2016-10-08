package com.myownmotivator.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;

import com.myownmotivator.model.profile.Profile;

@Entity
@Table(name = "thread")
@NamedQueries(
	
	{ @NamedQuery(name = "searchThread", query = " Select  DISTINCT(th.post.id),th  From Thread as th where th.content LIKE ? OR th.post.title LIKE ? OR th.post.forum.topic LIKE ? GROUP BY th.post.id"),
	  @NamedQuery(name = "countThreads", query = " Select  count(th.id) From Thread as th where th.post.forum.topic=:topic" )		
	}
)
public class Thread  implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;

	private Profile profile;

	private String content;

	private Date creationDate;

	private Post post;

	public Thread() {

		Calendar calendar = Calendar.getInstance();
		setCreationDate(calendar.getTime());

	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Transient
	public String getIdAsString() {

		return String.valueOf(getId());

	}

	@ManyToOne
	@JoinColumn(name = "POST_ID")
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@ManyToOne
	@JoinColumn(name = "PROFILE_ID")
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Id
	@GeneratedValue
	@Column(name = "THREAD_ID")
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

}
