package com.crowdscanner.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myownmotivator.model.questions.Question;

import flexjson.JSON;

public class ProfileBuffer {

	private Integer profileId;
	
	private String name;
	
	private String urlImage;
	
	private String userName;	
	
	private String twitterHandle;
	
	private String linkedInUrl;
	
	private String facebookUrl;

	private String character;
	
	private String topic;
	
	private String lookingFor;

	private Integer score;
	
	private Date createdDate;
	
	private	HashMap<String, Date>  guessedTimes = new HashMap<String, Date>();
	
	private	HashMap<String, Integer>  guessIds = new HashMap<String, Integer>();

	private Map<Integer, MatchQuestions> matchedQuestions = new HashMap<Integer, MatchQuestions>();
	
	private Collection<MatchQuestions> matchFinalQuestions = new ArrayList<MatchQuestions>();		
	
	private List<HashMap<String, String>> bumpedProfiles = new ArrayList<HashMap<String,String>>();

	private List<Question> questions = new ArrayList<Question>();	

	
	public ProfileBuffer() {
		// TODO Auto-generated constructor stub
	}
	
	public ProfileBuffer(ProfileBuffer buffer) {
		this.userName = buffer.getUserName();
		this.createdDate = buffer.getCreatedDate();
		this.name = buffer.getName();
		this.urlImage = buffer.getUrlImage();
		
	}
	
	

	@JSON(include= false)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	public HashMap<String, Date> getGuessedTimes() {
		return guessedTimes;
	}

	public void setGuessedTimes(HashMap<String, Date> guessedTimes) {
		this.guessedTimes = guessedTimes;
	}

	@JSON(include= true)
	public List<HashMap<String, String>> getBumpedProfiles() {
		return bumpedProfiles;
	}

	public void setBumpedProfiles(List<HashMap<String, String>> bumpedProfiles) {
		this.bumpedProfiles = bumpedProfiles;
	}

	@JSON(include= false)
	public String getTwitterHandle() {
		return twitterHandle;
	}

	public void setTwitterHandle(String twitterHandle) {
		this.twitterHandle = twitterHandle;
	}
	
	@JSON(include= false)
	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@JSON(include= false)
	public Map<Integer, MatchQuestions> getMatchedQuestions() {
		return matchedQuestions;
	}

	public void setMatchedQuestions(Map<Integer, MatchQuestions> matchedQuestions) {
		this.matchedQuestions = matchedQuestions;
	}
	
	@JSON(include= false)
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}	
	
	@JSON(include= false)
	public Collection<MatchQuestions> getMatchFinalQuestions() {		
		
		List<MatchQuestions> sortedQuestions = new ArrayList<MatchQuestions>(getMatchedQuestions().values());	
		Collections.sort(sortedQuestions, new Comparator<MatchQuestions>() {
			@Override
			public int compare(MatchQuestions m1, MatchQuestions m2) {
				// TODO Auto-generated method stub
				return m1.getQuestionPhoneId().compareTo(m2.getQuestionPhoneId());
			}
		});
		
		
		return sortedQuestions;
	};
	

	public void setMatchFinalQuestions(List<MatchQuestions> matchFinalQuestions) {
		this.matchFinalQuestions = matchFinalQuestions;
	}
	
	@JSON(include= false)
	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}
	
	@JSON(include= false)
	public Date getLatestGuess(){
		
		List<Date> listDataGuesses = new ArrayList<Date>(getGuessedTimes().values());
		Collections.sort(listDataGuesses,  new Comparator<Date>() {
			public int compare(Date profile, Date profile2) {				
				return profile2.compareTo(profile);
			}
		});		
		
		return listDataGuesses.get(0);
	}
	
	public HashMap<String, Integer> getGuessIds() {
		return guessIds;
	}

	public void setGuessIds(HashMap<String, Integer> guessIds) {
		this.guessIds = guessIds;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public String getLinkedInUrl() {
		return linkedInUrl;
	}

	public void setLinkedInUrl(String linkedInUrl) {
		this.linkedInUrl = linkedInUrl;
	}
	
	public String getLookingFor() {
		return lookingFor;
	}

	public void setLookingFor(String lookingFor) {
		this.lookingFor = lookingFor;
	}

	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}	
	
	@Override
	public String toString() {
		
		String data = getName();
		data+="  username: "+getUserName();		
		for (HashMap<String, String> hash : getBumpedProfiles()) {
			data+="  bumpedUsers: "+hash.get("userName");
		}
		
		return data;
	}	
	
}
