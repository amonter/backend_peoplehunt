package com.crowdscanner.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.peoplehuntv2.model.AsyncMessage;
import com.peoplehuntv2.model.Location;

import flexjson.JSON;


public class HunterProfile {
   

	private Integer profileId;
    private String location;
    private String imageURL;
    private List<Location> locations;
    private String name;
    private Map<Integer, String> help;
    private Map<Integer, String> interested;
    private List<Map<String, Object>> connections;
    private List<AsyncMessage> asyncMessages;
    public Map<Integer, String> lastMessages;
    private Map<Integer, Integer> proficiency;
    private String username;
    private String number;
    private String udid;
    private String common;
    private String availableDay;
    private String availableTime;
    private List<String> matchList;
    public String matchItem;
    private String bio;
	private List<Map<String,Object>> personalInterests;
	private Map<String,Object> ratings;
	private Map<String,Object> paymentType;
	public Map<String, Map<Integer,String>> match_criteria;   
    

	public HunterProfile() {
        super();
        this.locations = new ArrayList<Location>();
        this.help = new HashMap<Integer, String>();
        this.interested = new HashMap<Integer, String>();
        this.connections = new ArrayList<Map<String, Object>>();
        this.asyncMessages = new ArrayList<AsyncMessage>();
        this.lastMessages = new HashMap<Integer, String>();
        this.matchList = new ArrayList<String>();
        this.proficiency = new HashMap<Integer, Integer>();
        this.personalInterests = new ArrayList<Map<String,Object>>();
        this.match_criteria = new HashMap<String, Map<Integer,String>>();
        this.ratings = new HashMap<String, Object>();
        this.paymentType = new HashMap<String, Object>();
    }
    
	
	public String getAvailableDay() {
		return availableDay;
	}

	public void setAvailableDay(String availableDay) {
		this.availableDay = availableDay;
	}

	public String getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(String availableTime) {
		this.availableTime = availableTime;
	}
	
	
    @JSON(include = true)
    public Map<Integer, Integer> getProficiency() {
		return proficiency;
	}

	public void setProficiency(Map<Integer, Integer> proficiency) {
		this.proficiency = proficiency;
	}

	@JSON(include = false)
    public List<String> getMatchList() {
        return this.matchList;
    }
    
    public void setMatchList(final List<String> matchList) {
        this.matchList = matchList;
    }
    
    public Integer getProfileId() {
        return this.profileId;
    }
    
    public void setProfileId(final Integer profileId) {
        this.profileId = profileId;
    }
    
    @JSON(include = false)
    public String getLocation() {
        return this.location;
    }
    
    public void setLocation(final String location) {
        this.location = location;
    }
    
    public String getImageURL() {
        return this.imageURL;
    }
    
    public void setImageURL(final String imageURL) {
        this.imageURL = imageURL;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @JSON(include = true)
    public Map<Integer, String> getHelp() {
        return this.help;
    }
    
    public void setHelp(final Map<Integer, String> help) {
        this.help = help;
    }
    
    @JSON(include = true)
    public Map<Integer, String> getInterested() {
        return this.interested;
    }
    
    public void setInterested(final Map<Integer, String> interested) {
        this.interested = interested;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    @JSON(include = false)
    public String getNumber() {
        return this.number;
    }
    
    public void setNumber(final String number) {
        this.number = number;
    }
    
    @JSON(include = false)
    public String getUdid() {
        return this.udid;
    }
    
    public void setUdid(final String udid) {
        this.udid = udid;
    }
    
    @JSON(include = false)
    public String getCommon() {
        return this.common;
    }
    
    public void setCommon(final String common) {
        this.common = common;
    }
    
    @JSON(include = false)
    public void addCommon(final String s) {
        if (!this.getMatchList().contains(s)) {
            this.getMatchList().add(s);
        }
    }
    
    public List<AsyncMessage> getAsyncMessages() {
        return this.asyncMessages;
    }
    
    public void setAsyncMessages(final List<AsyncMessage> asyncMessages) {
        this.asyncMessages = asyncMessages;
    }
    
    @JSON(include = true)
    public List<Location> getLocations() {
        return this.locations;
    }
    
    public void setLocations(final List<Location> locations) {
        this.locations = locations;
    }
    
    @JSON(include = true)
    public List<Map<String, Object>> getConnections() {
        return this.connections;
    }
    
    public void setConnections(final List<Map<String, Object>> connections) {
        this.connections = connections;
    }
    
    @JSON(include = false)
    public String getMatchItem() {
        final String addeData = Joiner.on(", ").join((Iterable)this.getMatchList());
        this.matchItem += addeData;
        return this.matchItem;
    }
    
    public void setMatchItem(final String matchItem) {
        this.matchItem = matchItem;
    }
    
    public Map<Integer, String> getLastMessages() {
        return this.lastMessages;
    }
    
    public void setLastMessages(final Map<Integer, String> lastMessages) {
        this.lastMessages = lastMessages;
    }
    
    public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	
	@JSON(include = true)
	public List<Map<String, Object>> getPersonalInterests() {
		return personalInterests;
	}

	public void setPersonalInterests(List<Map<String, Object>> personalInterests) {
		this.personalInterests = personalInterests;
	}
	
	public Map<String, Map<Integer, String>> getMatch_criteria() {
		
		match_criteria.put("help", getHelp());
		match_criteria.put("interested", getInterested());
		return match_criteria;
	}

	public void setMatch_criteria(Map<String, Map<Integer, String>> match_criteria) {
		this.match_criteria = match_criteria;
	}

	@JSON(include = true)
	public Map<String, Object> getRatings() {
		return ratings;
	}

	public void setRatings(Map<String, Object> ratings) {
		this.ratings = ratings;
	}

	@JSON(include = true)
	public Map<String, Object> getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Map<String, Object> paymentType) {
		this.paymentType = paymentType;
	}

	
}