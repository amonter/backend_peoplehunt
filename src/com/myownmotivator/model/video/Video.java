package com.myownmotivator.model.video;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.myownmotivator.model.category.Category;
import com.myownmotivator.model.profile.File;

@Entity
@Table(name = "Video")
public class Video implements Serializable {
	private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue
	@Column(name = "VIDEO_ID")
	private long id;
	
	@Column(name = "videoDescription")
	private String videoDescription;
	
	@JoinColumn(name = "IMAGEFILE_ID", referencedColumnName = "FILE_ID")
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private File file;		

	@JoinColumn(name = "IMAGEFILE_ID_THUMB", referencedColumnName = "FILE_ID")
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private File thumbnailFile;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@Column(name = "views")
	private int views;
	
	@Column(name = "videoPath")
	private String videoPath;
	
	@Column(name = "videoTitle")
	private String videoTitle;
	
	@JoinColumn(name = "CATEGORY_TYPE", referencedColumnName = "CATEGORY_ID")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Category category;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "video", fetch = FetchType.LAZY)
	private Set<VideoComment> videoCommentCollection;
		
	private String shortLink;		

	@Transient
	private MultipartFile multipartFile;

	@Transient
	private long categoryId;
	
	@Transient
	private Integer fileId;
	
	@Transient
	private Integer fileIdThumbnail;	

	@Transient
	private boolean updateThumbnail;
	

	public Video(long videoId) {
		this.id = videoId;
	}

	public Video() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}
	

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public Set<VideoComment> getVideoCommentCollection() {
		return videoCommentCollection;
	}

	public void setVideoCommentCollection(Set<VideoComment> videoCommentCollection) {
		this.videoCommentCollection = videoCommentCollection;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	
	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	
	public File getThumbnailFile() {
		return thumbnailFile;
	}

	public void setThumbnailFile(File thumbnailFile) {
		this.thumbnailFile = thumbnailFile;
	}	
	
	public Integer getFileIdThumbnail() {
		return fileIdThumbnail;
	}

	public void setFileIdThumbnail(Integer fileIdThumbnail) {
		this.fileIdThumbnail = fileIdThumbnail;
	}
	
	public boolean getUpdateThumbnail() {
		return updateThumbnail;
	}

	public void setUpdateThumbnail(boolean updateThumbnail) {
		this.updateThumbnail = updateThumbnail;
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
	    
	    retValue = "Video ( "
	        + super.toString() + TAB
	        + "id = " + this.id + TAB
	        + "videoDescription = " + this.videoDescription + TAB
	        + "creationDate = " + this.creationDate + TAB
	        + "views = " + this.views + TAB
	        + "videoPath = " + this.videoPath + TAB
	        + "videoTitle = " + this.videoTitle + TAB
	        + "fileId = " + this.fileId + TAB
	        + " )";
	
	    return retValue;
	}
}
