package com.peoplehuntv2.model;

import java.io.*;
import com.myownmotivator.model.profile.*;
import java.util.*;
import flexjson.*;
import javax.persistence.*;

@Entity
@Table(name = "location")
public class Location implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private List<Profile> profiles;
    private String location;
    private Date creationDate;
    private Integer ranking;
    private String latitude;
    private String longitude;   
    private String matchingFeelers;	
    private Integer type;

	public Location() {
        super();
        this.profiles = new ArrayList<Profile>();
    }
    
    @Id
    @GeneratedValue
    @Column(name = "LOCATION_ID")
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    @JSON(include = false)
    @ManyToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
    @JoinTable(name = "profile_location", joinColumns = { @JoinColumn(name = "LOCATION_ID", unique = true) }, inverseJoinColumns = { @JoinColumn(name = "PROFILE_ID") })
    public List<Profile> getProfiles() {
        return this.profiles;
    }
    
    public void setProfiles(final List<Profile> profiles) {
        this.profiles = profiles;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public void setLocation(final String location) {
        this.location = location;
    }
    
    public Date getCreationDate() {
        return this.creationDate;
    }
    
    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }
    
    @JSON(include = false)
    public Integer getRanking() {
        return this.ranking;
    }
    
    public void setRanking(final Integer ranking) {
        this.ranking = ranking;
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
	
	@Transient
	public String getMatchingFeelers() {
		return matchingFeelers;
	}

	public void setMatchingFeelers(String matchingFeelers) {
		this.matchingFeelers = matchingFeelers;
	}
	
	@Transient
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}