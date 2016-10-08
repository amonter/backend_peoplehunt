package com.myownmotivator.model.events;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.myownmotivator.model.profile.Profile;

@Entity
@Table(name = "event_history")
@NamedQueries({@NamedQuery(name = "EventHistory.findAll", query = "SELECT e FROM EventHistory e"), 
	           @NamedQuery(name = "EventHistory.findByEventHistoryId", query = "SELECT e FROM EventHistory e WHERE e.id = :eventHistoryId"), 
	           @NamedQuery(name = "EventHistory.findByCreationDate", query = "SELECT e FROM EventHistory e WHERE e.creationDate = :creationDate")})
public class EventHistory implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "EVENT_HISTORY_ID")
    private long id;
    
    @Column(name = "creationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @JoinColumn(name = "EVENT_ID", referencedColumnName = "EVENT_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Event eventId;
    
    @JoinColumn(name = "PROFILE_ID", referencedColumnName = "PROFILE_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profile profileId;
    
    public EventHistory() {
    }

    public EventHistory(long eventHistoryId) {
        this.id = eventHistoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long eventHistoryId) {
        this.id = eventHistoryId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Event getEventId() {
        return eventId;
    }

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }

    public Profile getProfileId() {
        return profileId;
    }

    public void setProfileId(Profile profileId) {
        this.profileId = profileId;
    }
}