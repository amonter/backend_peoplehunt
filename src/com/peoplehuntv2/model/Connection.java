package com.peoplehuntv2.model;

import com.myownmotivator.model.profile.*;
import java.util.*;
import flexjson.*;
import javax.persistence.*;

@Entity
@Table(name = "connection")
public class Connection
{
    private Integer id;
    private Integer whoConnected;
    private boolean shares;
    private Integer status;
    private String matchItem;
    private String imageurl;
    private String name;
    private Date interactionTime;
    private Profile profile;
    private String lastMessage;
    
    @Id
    @GeneratedValue
    @Column(name = "CONNECTION_ID")
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public Integer getWhoConnected() {
        return this.whoConnected;
    }
    
    public void setWhoConnected(final Integer whoConnected) {
        this.whoConnected = whoConnected;
    }
    
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(final Integer status) {
        this.status = status;
    }
    
    public String getMatchItem() {
        return this.matchItem;
    }
    
    public void setMatchItem(final String matchItem) {
        this.matchItem = matchItem;
    }
    
    @Transient
    public String getImageurl() {
        return this.imageurl;
    }
    
    public void setImageurl(final String imageurl) {
        this.imageurl = imageurl;
    }
    
    @Transient
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    public Date getInteractionTime() {
        if (this.interactionTime != null) {
            final Calendar theCal = Calendar.getInstance();
            theCal.setTime(this.interactionTime);
            theCal.setTimeZone(TimeZone.getTimeZone("UTC"));
            this.interactionTime = theCal.getTime();
        }
        return this.interactionTime;
    }
    
    public void setInteractionTime(final Date interactionTime) {
        this.interactionTime = interactionTime;
    }
    
    @JSON(include = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinColumn(name = "PROFILE_ID", updatable = false)
    public Profile getProfile() {
        return this.profile;
    }
    
    public void setProfile(final Profile profile) {
        this.profile = profile;
    }
    
    @Transient
    public String getLastMessage() {
        return this.lastMessage;
    }
    
    public void setLastMessage(final String lastMessage) {
        this.lastMessage = lastMessage;
    }
    
    public boolean isShares() {
        return this.shares;
    }
    
    public void setShares(final boolean shares) {
        this.shares = shares;
    }
    
    public boolean equals(final Object obj) {
        final Connection theNotification = (Connection)obj;
        boolean isTrue = false;
        if (this.getWhoConnected() == (int)theNotification.getWhoConnected()) {
            isTrue = true;
        }
        return isTrue;
    }
    
    public int hashCode() {
        return 771234666;
    }
}