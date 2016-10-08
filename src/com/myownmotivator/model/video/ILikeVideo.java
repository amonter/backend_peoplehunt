
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
@Table(name = "i_like_video")
public class ILikeVideo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "I_LIKE_VIDEO_ID")
	private long id;
	
	@Column(name = "createDate")
	private Date createDate;
	
	@JoinColumn(name = "PROFILE_ID", referencedColumnName = "PROFILE_ID")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Profile profileId;
	
	@JoinColumn(name = "VIDEO_ID", referencedColumnName = "VIDEO_ID")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Video video;
    
	public ILikeVideo() {
	}
	
	public ILikeVideo(Profile profileId) {
	   this.profileId = profileId;	
	}

	public ILikeVideo(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Profile getProfileId() {
		return profileId;
	}

	public void setProfileId(Profile profileId) {
		this.profileId = profileId;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
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
	    
	    retValue = "ILikeVideo ( "
	        + super.toString() + TAB
	        + "id = " + this.id + TAB
	        + "createDate = " + this.createDate + TAB
	        + " )";
	
	    return retValue;
	}

}
