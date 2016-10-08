package com.peoplehuntv2.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import flexjson.JSON;

@Entity
@Table(name = "feeler")
public class Feeler implements Serializable {			

	private static final long serialVersionUID = 1L;
    private Integer id;
    private String description;
    private Date dateCreated;
    private Integer lookingRank;
    private Integer providingRank;
    private List<Status> statuses;
    private List<Integer> proficiencies = new ArrayList<Integer>();
    
    @Transient
    public List<Integer> getProficiencies() {
		return proficiencies;
	}

	public void setProficiencies(List<Integer> proficiencies) {
		this.proficiencies = proficiencies;
	}

	public Feeler() {
        super();
        this.statuses = new ArrayList<Status>();
    }
    
    @Id
    @GeneratedValue
    @Column(name = "FEELER_ID")
    public Integer getId() {
        return this.id;
    }
    
    @ManyToMany(mappedBy = "feelers", targetEntity = Status.class, cascade = { CascadeType.ALL })
    public List<Status> getStatuses() {
        return this.statuses;
    }
    
    public void setStatuses(final List<Status> statuses) {
        this.statuses = statuses;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public Integer getLookingRank() {
        return this.lookingRank;
    }
    
    public void setLookingRank(final Integer lookingRank) {
        this.lookingRank = lookingRank;
    }
    
    public Integer getProvidingRank() {
        return this.providingRank;
    }
    
    public void setProvidingRank(final Integer providingRank) {
        this.providingRank = providingRank;
    }
    
    @JSON(include = false)
    @Transient
    public String getURLEncodedDescription() {
        try {
            return URLEncoder.encode(this.description, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateCreated() {
        if (this.dateCreated != null) {
            final Calendar theCal = Calendar.getInstance();
            theCal.setTime(this.dateCreated);
            theCal.setTimeZone(TimeZone.getTimeZone("UTC"));
            this.dateCreated = theCal.getTime();
        }
        return this.dateCreated;
    }
    
    public void setDateCreated(final Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public boolean equals(final Object obj) {
        final Feeler theFeeler = (Feeler)obj;
        boolean isTrue = false;
        if (this.getId() == (int)theFeeler.getId()) {
            isTrue = true;
        }
        return isTrue;
    }
    
    public int hashCode() {
        return 55632742;
    }
}
