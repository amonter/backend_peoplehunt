package com.myownmotivator.model.video;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.myownmotivator.model.profile.Profile;

@Entity
@Table(name = "video_comment")
public class VideoComment implements Serializable{
	private static final long serialVersionUID = 2250260468981856110L;

	@Id
	@GeneratedValue
	@Column(name = "VIDEO_COMMENT_ID")
	private long id;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@JoinColumn(name = "VIDEO_ID", referencedColumnName = "VIDEO_ID")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Video video;
	
	@JoinColumn(name = "PROFILE_ID", referencedColumnName = "PROFILE_ID")
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Profile profile;
	
	@Column(name = "name")
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	    
	    String retValue = "";
	    
	    retValue = "VideoComment ( "
	        + super.toString() + TAB
	        + "id = " + this.id + TAB
	        + "content = " + this.content + TAB
	        + "creationDate = " + this.creationDate + TAB
	        + "name = " + this.name + TAB
	        + " )";
	
	    return retValue;
	}
	
}
