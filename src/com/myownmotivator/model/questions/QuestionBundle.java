package com.myownmotivator.model.questions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.myownmotivator.model.crowdmodule.Visual;

import flexjson.JSON;

@Entity
@Table(name="question_bundle")
public class QuestionBundle {
	
	private long coolOffPeriod;
	
	private int threshold;

	private String description;
	
	private String topic;	

	private Integer id;		

	private String productId;	

	private List<Question> questions = new ArrayList<Question>();
	
	private boolean freeBundle;	
	
	private String imageURL;
	
	private Visual visual;
	
	private Date date;
	
	private String location;
	
	private byte[] bundleImage;
	
	private String permaLink;
	
	private String latitude;

	private String longitude;
	
	private boolean inProgress;	
	
	private String version;
	
	@JSON(include= false)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}	
	
	public QuestionBundle() {
		// TODO Auto-generated constructor stub
	}
	
	@JSON(include= false)
	public boolean getInProgress() {
		return inProgress;
	}

	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}

	public QuestionBundle(Integer bundleId) {
		this.id = bundleId;
	}
	
	@JSON(include= false)
	public boolean getFreeBundle() {
		return freeBundle;
	}

	public void setFreeBundle(boolean freeBundle) {
		this.freeBundle = freeBundle;
	}
	
	@Id
	@GeneratedValue
	@Column(name="BUNDLE_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JSON(include= false)
	@OneToMany(mappedBy="questionBundle")	
	public List<Question> getQuestions() {
		return questions;
	}
	
	@JSON(include= true)
	@Transient
	public List <Question> getParentQuestions() {
		
		List<Question> parentList = new ArrayList<Question>();
		for (Question aQuestion: getQuestions()){
			
			if (aQuestion.getParent()) {
				parentList.add(aQuestion);
			}
		}
		
		Collections.sort(parentList, new Comparator<Question>() {
			public int compare(Question question, Question question2) {
				return question.getQuestionPhoneId().compareTo(question2.getQuestionPhoneId());
			}
		});
		
		
		return parentList;		
	}
	
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}	
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	@JSON(include= true)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	@JSON(include= false)
	@ManyToOne
	@JoinColumn(name="VISUAL_ID")
	public Visual getVisual() {
		return visual;
	}

	public void setVisual(Visual visual) {
		this.visual = visual;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		Calendar theCal = Calendar.getInstance();
		theCal.setTime(date);
		theCal.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		return theCal.getTime();
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public byte[] getBundleImage() {
		return bundleImage;
	}

	public void setBundleImage(byte[] bundleImage) {
		this.bundleImage = bundleImage;
	}

	public void addQuestion(Question question) {
		questions.add(question);
		question.setQuestionBundle(this);
	}
	
	@JSON(include= true)
	public String getPermaLink() {		
		return permaLink;
	}

	public void setPermaLink(String permaLink) {
		this.permaLink = permaLink;
	}

	public void removeQuestion(Question question) {
		questions.remove(question);
		question.setQuestionBundle(null);
	}	
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public void setCoolOffPeriod(long coolOffPeriod) {
		this.coolOffPeriod = coolOffPeriod;
	}
	
	@JSON(include= false)
	public long getCoolOffPeriod() {
		return coolOffPeriod;
	}

	public int getThreshold() {
		return threshold;
	}
	
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
}
