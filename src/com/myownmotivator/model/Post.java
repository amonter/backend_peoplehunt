package com.myownmotivator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.myownmotivator.model.profile.Profile;


@Entity
@Table(name = "post")
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;

	private int id;

	private Date creationDate;

	private Forum forum = new Forum();

	private Thread newThread = new Thread();

	private User postUserProfile;
	
	private Integer views = 0;

	private Set<Thread> thread = new LinkedHashSet<Thread>();

	@Id
	@GeneratedValue
	@Column(name = "POST_ID")
	public int getId() {
		return id;
	}

	public Post() {
		Calendar calendar = Calendar.getInstance();
		setCreationDate(calendar.getTime());
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

	@ManyToOne
	@JoinColumn(name = "FORUM_ID")
	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "POST_ID")
	public Set<Thread> getThread() {
		return thread;
	}

	public void setThread(Set<Thread> thread) {
		this.thread = thread;
	}

	@Transient
	public Thread getNewThread() {
		return newThread;
	}

	@Transient
	public void setNewThread(Thread newThread) {
		this.newThread = newThread;
	}

	@Transient
	public void addThreadToList() {
		getThread().add(getNewThread());
	}

	@Transient
	public List<Thread> sortThreads() {

		List<Thread> listThreads = new ArrayList<Thread>();
		if (!thread.isEmpty()) {

			listThreads.addAll(thread);
			Collections.sort(listThreads, new Comparator<Thread>() {

				public int compare(Thread thread1, Thread thread2) {

					return thread1.getCreationDate().compareTo(
							thread2.getCreationDate());
				}
			});

			thread.clear();
			thread.addAll(listThreads);
		}

		return listThreads;
	}

	@Transient
	public List<Thread> getSortedThreads() {
		return sortThreads();
	}

	@Transient
	public User getPostUserProfile() {
		return postUserProfile;
	}

	@Transient
	public void populateUserProfile() {
		List<Thread> sortedThreads = sortThreads();
		if (!sortedThreads.isEmpty()) {
			Profile profile = sortedThreads.get(0).getProfile();
			if (profile != null) {
				postUserProfile = profile.getUser();
			}
		}
	}

	@Transient
	public Integer getNumberReplies() {

		return thread.size();

	}
	
	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

}
