package com.myownmotivator.model.questions;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.lang.StringUtils;

import com.myownmotivator.model.profile.Profile;

import flexjson.JSON;

@Entity
@Table(name="question")
public class Question implements Serializable, Comparable<Question> {

	private static final long serialVersionUID = 1L;

	private List<Answer> answers = LazyList.decorate(new ArrayList<Answer>(),
			FactoryUtils.instantiateFactory(Answer.class));
	
	private Integer id;
	
	private Integer questionPhoneId;	

	private String questionType;
	
	private String questionDescription;
	
	private String createdBy;	
	
	private String latitude;
	
	private String longitude;
	
	private String helpWith;
	
	private String huntingFor;	

	private boolean parent;	

	private Date creationDate;		

	private Integer usersAggregate;
	
	private Integer answersAggregate;	

	private List<Profile> profiles = new ArrayList<Profile>();	
	
	private List<QuestionComment> comments = new ArrayList<QuestionComment>();	

	private Boolean hasAnswers;
	
	private Double distance;	
	
	private QuestionBundle questionBundle;	
	
	private String selectedAnswer;

	public Question() {
		
		this.usersAggregate = 1;
		this.answersAggregate = 0;
		this.distance = 0.0;
		hasAnswers = false;
	}		
	
	@Id
	@GeneratedValue
	@Column(name="QUESTION_ID")
	public Integer getId() {
		return id;
	}	

	public void setId(Integer id) {
		this.id = id;
	}

	@JSON(include= false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name = "QUESTION_ID")	
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}	
	
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="BUNDLE_ID", updatable= false)
	public QuestionBundle getQuestionBundle() {
		return questionBundle;
	}

	public void setQuestionBundle(QuestionBundle questionBundle) {
		this.questionBundle = questionBundle;
	}
	
	@JSON(include= true)
	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionaireVersion) {
		this.questionType = questionaireVersion;
	}	
	
	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}	
	
	public Integer getQuestionPhoneId() {
		return questionPhoneId;
	}

	public void setQuestionPhoneId(Integer questionPhoneId) {
		this.questionPhoneId = questionPhoneId;
	}	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="profileQuestion",  
			joinColumns=@JoinColumn(name="QUESTION_ID", unique = true),
			inverseJoinColumns=@JoinColumn(name="PROFILE_ID"))
	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name = "QUESTION_ID")
	public List<QuestionComment> getComments() {
		return comments;
	}

	public void setComments(List<QuestionComment> comments) {
		this.comments = comments;
	}
	
	@JSON(include= false)
	@Transient
	public String getFormatedAnswerLabels () {
			
		StringBuffer buffer = new StringBuffer();
		for (Answer ans: getAnswers()) {
			
			buffer.append(ans.getTextualAnswer() +",");			
		}
				
		return StringUtils.chop(buffer.toString());
	}
	
	@JSON(include= false)
	@Transient
	public String getFormatedAnswerLabelsBar () {
			
		StringBuffer buffer = new StringBuffer();
		sortAnswersByName();		
		for (Answer ans: getAnswers()) {
			
			buffer.append(String.valueOf(ans.getAnswerNumber()) +"|");					
		}
				
		return StringUtils.chop(buffer.toString());
	}
	
	@JSON(include= false)
	@Transient
	public String getFormatedAnswers() {
		
		StringBuffer buffer = new StringBuffer();
		sortAnswersByName();	
		for (Answer ans: getAnswers()) {
			
			buffer.append(String.valueOf(ans.getAnswerNumber()) +",");			
		}
				
		return StringUtils.chop(buffer.toString());
	}
	
	@JSON(include= false)
	@Transient
	public void sortAnswers() {
		
		Collections.sort(getAnswers(), new Comparator<Answer>() {
			public int compare(Answer answer, Answer answer2) {
				return answer.getAnswerNumber().compareTo(answer2.getAnswerNumber());
			}
		});
	}			

	
	@JSON(include= false)
	@Transient
	public List<Answer> getSortedAnswers() {		
		List <Answer> allAnswers = getAnswers();
		Collections.sort(allAnswers, new Comparator<Answer>() {
			public int compare(Answer answer, Answer answer2) {
				return answer.getTextualAnswer().compareTo(answer2.getTextualAnswer());
			}
		});
		return allAnswers;
	}
	
	@JSON(include= true)
	@Transient
	public List<Answer> getSortedAnswersByDate() {		
		List <Answer> allAnswers = getAnswers();
		Collections.sort(allAnswers, new Comparator<Answer>() {
			public int compare(Answer answer, Answer answer2) {
				return answer2.getDateCreated().compareTo(answer.getDateCreated());
			}
		});		
		return allAnswers;
	}
	
	
	@JSON(include= false)
	@Transient
	public void sortAnswersByName() {
		
		
		Collections.sort(getAnswers(), new Comparator<Answer>() {

			public int compare(Answer answer, Answer answer2) {

				return answer2.getTextualAnswer().compareTo(answer.getTextualAnswer());
			}
		});
	}
	
	@JSON(include= false)
	@Transient
	public int getTotalAnswerCount() {
		
		int count = 0;
		for (Answer ans: getAnswers()) {
			
			 count = count + ans.getAnswerNumber();			
		}
				
		return count;
	}
	
	@JSON(include= false)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@JSON(include= false)
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@JSON(include= false)
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@JSON(include= false)
	public boolean getParent() {
		return parent;
	}

	public void setParent(boolean parent) {
		this.parent = parent;
	}
	
	@JSON(include= false)
	public String getHelpWith() {
		return helpWith;
	}

	public void setHelpWith(String helpWith) {
		this.helpWith = helpWith;
	}

	@JSON(include= false)
	public String getHuntingFor() {
		return huntingFor;
	}

	public void setHuntingFor(String huntingFor) {
		this.huntingFor = huntingFor;
	}
	
	@JSON(include= false)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}	

	@JSON(include= false)
	@Transient
	public Integer getUsersAggregate() {
		return usersAggregate;
	}

	public void setUsersAggregate(Integer users) {
		this.usersAggregate = users;
	}	
	
	@JSON(include= false)
	@Transient
	public Integer getAnswersAggregate() {
		return answersAggregate;
	}

	@JSON(include= false)
	public Boolean getHasAnswers() {
		return hasAnswers;
	}


	public void setHasAnswers(Boolean hasAnswers) {
		this.hasAnswers = hasAnswers;
	}
	
	public void setAnswersAggregate(Integer answersAggregate) {
		this.answersAggregate = answersAggregate;
	}
	
	
	/*
	@Override
	public boolean equals(Object obj) {
		
		Question theQuestion = (Question) obj;
		boolean isTrue = false;
		if (getQuestionPhoneId().equals(theQuestion.getQuestionPhoneId())) {
			isTrue = true;			
		}
		return isTrue;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 98767856;
	}
	*/
	
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@JSON(include= false)
	@Transient
	public Double getDistance() {
		return distance;
	}

	@Override
	public int compareTo(Question o) {
		// TODO Auto-generated method stub
		return getDistance().compareTo(o.getDistance());
	}	
	
	@JSON(include= false)
	@Transient
	public String getSelectedAnswer() {
		
		String theAnswer = this.selectedAnswer;
		for (Answer anAnswer: getAnswers()) {
			
			if (anAnswer.getAnswerNumber() == 1) {
				theAnswer = anAnswer.getTextualAnswer();					
			}			
		}
		
		return theAnswer;
	}

	public void setSelectedAnswer(String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

	
	public void addAnswer(Answer answer) {
		answers.add(answer);
	}

	public void removeAnswer(Answer answer) {
		answers.remove(answer);
		
	}

	public void removeProfile(Profile profile) {
		profiles.remove(profile);
	}

	public void addProfile(Profile profile) {
		profiles.add(profile);		
	}
	
}

