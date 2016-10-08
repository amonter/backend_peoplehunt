package com.myownmotivator.model.profile;

import java.io.*;

import com.myownmotivator.model.*;

import org.hibernate.bytecode.javassist.*;

import com.peoplehuntv2.model.*;

import org.apache.commons.collections.*;
import org.apache.commons.collections.list.*;

import flexjson.*;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.*;

@Entity
@Table(name = "profile")
public class Profile implements Serializable, FieldHandled
{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private User user;
    private List<File> files;
    private long confirmationNumber;
    private boolean active;
    private boolean iphoneUser;
    private String source;
    private String iphoneUDID;
    private String number;
    private Date profileCreation;
    private TwitterUserAuthentication twitterUserAuthentication;
    private LinkedInData linkedInData;
    private EfactorData efactorData;
    private ProfileInfo profileInfo;
    private String profileImageUrl;
    private Integer questionIdBackend;
    private FieldHandler fieldHandler;
    private List<MemberShip> memberships;
    private List<FoundTarget> foundTargets;
    private List<AsyncMessage> asyncMessages;
    private List<Notification> notifications;
    private List<Connection> connections;
    private List<Location> locations;
    private Date lastMessageSent;
    private Date lastOnline;
    private long lastNotified;
    
	public Profile() {
        super();
        this.files = (List<File>)LazyList.decorate((List)new ArrayList(), FactoryUtils.instantiateFactory((Class)File.class));
        this.active = false;
        this.iphoneUser = false;
        this.memberships = new ArrayList<MemberShip>();
        this.foundTargets = new ArrayList<FoundTarget>();
        this.asyncMessages = new ArrayList<AsyncMessage>();
        this.notifications = new ArrayList<Notification>();
        this.connections = new ArrayList<Connection>();
        this.locations = new ArrayList<Location>();
        final Calendar calendar = Calendar.getInstance();
        this.profileCreation = calendar.getTime();
        this.source = "system";
    }
    
    public Profile(final int id) {
        super();
        this.files = (List<File>)LazyList.decorate((List)new ArrayList(), FactoryUtils.instantiateFactory((Class)File.class));
        this.active = false;
        this.iphoneUser = false;
        this.memberships = new ArrayList<MemberShip>();
        this.foundTargets = new ArrayList<FoundTarget>();
        this.asyncMessages = new ArrayList<AsyncMessage>();
        this.notifications = new ArrayList<Notification>();
        this.connections = new ArrayList<Connection>();
        this.locations = new ArrayList<Location>();
        this.setId(id);
    }
    
    @ManyToMany(mappedBy = "profiles", fetch = FetchType.LAZY, targetEntity = Location.class, cascade = { CascadeType.ALL })
    public List<Location> getLocations() {
        return this.locations;
    }
    
    public void setLocations(final List<Location> locations) {
        this.locations = locations;
    }
    
    public long getLastTimeNotified() {
        return this.lastNotified;
    }
    
    public void setLastTimeNotified(final long newTime) {
        this.lastNotified = newTime;
    }
    
    @JSON(include = false)
    @OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    public List<MemberShip> getMemberships() {
        return this.memberships;
    }
    
    public void setMemberships(final List<MemberShip> memberships) {
        this.memberships = memberships;
    }
    
    @ManyToMany(mappedBy = "foundProfiles",  fetch = FetchType.LAZY, targetEntity = FoundTarget.class, cascade = { CascadeType.ALL })
    public List<FoundTarget> getFoundTargets() {
        return this.foundTargets;
    }
    
    public void setFoundTargets(final List<FoundTarget> foundTargets) {
        this.foundTargets = foundTargets;
    }
    
    @JSON(include = false)
    public boolean getIphoneUser() {
        return this.iphoneUser;
    }
    
    public void setIphoneUser(final boolean iphoneUser) {
        this.iphoneUser = iphoneUser;
    }
    
    public static long getSerialVersionUID() {
        return 1L;
    }
    
    @JSON(include = false)
    public String getIphoneUDID() {
        return this.iphoneUDID;
    }
    
    public void setIphoneUDID(final String iphoneUDID) {
        this.iphoneUDID = iphoneUDID;
    }
    
    public Profile(final User user) {
        super();
        this.files = (List<File>)LazyList.decorate((List)new ArrayList(), FactoryUtils.instantiateFactory((Class)File.class));
        this.active = false;
        this.iphoneUser = false;
        this.memberships = new ArrayList<MemberShip>();
        this.foundTargets = new ArrayList<FoundTarget>();
        this.asyncMessages = new ArrayList<AsyncMessage>();
        this.notifications = new ArrayList<Notification>();
        this.connections = new ArrayList<Connection>();
        this.locations = new ArrayList<Location>();
        this.user = user;
    }
    
    public Profile(final User user, final List<File> files) {
        super();
        this.files = (List<File>)LazyList.decorate((List)new ArrayList(), FactoryUtils.instantiateFactory((Class)File.class));
        this.active = false;
        this.iphoneUser = false;
        this.memberships = new ArrayList<MemberShip>();
        this.foundTargets = new ArrayList<FoundTarget>();
        this.asyncMessages = new ArrayList<AsyncMessage>();
        this.notifications = new ArrayList<Notification>();
        this.connections = new ArrayList<Connection>();
        this.locations = new ArrayList<Location>();
        this.user = user;
        this.files = files;
    }
    
    @JSON(include = true)
    @Id
    @GeneratedValue
    @Column(name = "PROFILE_ID")
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public Date getLastOnline() {
        return this.lastOnline;
    }
    
    public void setLastOnline(final Date lastOnline) {
        this.lastOnline = lastOnline;
    }
    
    @OneToOne(fetch = FetchType.LAZY, optional = true, mappedBy = "profile", cascade = { CascadeType.ALL })
    @LazyToOne(LazyToOneOption.NO_PROXY)
    public User getUser() {
        if (this.fieldHandler != null) {
            this.user = (User)this.fieldHandler.readObject((Object)this, "user", (Object)this.user);
        }
        return this.user;
    }
    
    public void setUser(final User user) {
        if (this.fieldHandler != null) {
            this.fieldHandler.writeObject((Object)this, "user", (Object)this.user, (Object)user);
        }
        this.user = user;
    }
    
    @JSON(include = false)
    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name = "PROFILE_ID")
    public List<File> getFiles() {
        return this.files;
    }
    
    public void setFiles(final List<File> files) {
        this.files = files;
    }
    
    @JSON(include = false)
    @OneToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL }, mappedBy = "profile")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    public ProfileInfo getProfileInfo() {
        if (this.fieldHandler != null) {
            this.profileInfo = (ProfileInfo)this.fieldHandler.readObject((Object)this, "profileInfo", (Object)this.profileInfo);
        }
        return this.profileInfo;
    }
    
    public void setProfileInfo(final ProfileInfo profileInfo) {
        if (this.fieldHandler != null) {
            this.fieldHandler.writeObject((Object)this, "profileInfo", (Object)this.profileInfo, (Object)profileInfo);
        }
        this.profileInfo = profileInfo;
    }
    
    @JSON(include = false)
    public long getConfirmationNumber() {
        return this.confirmationNumber;
    }
    
    public void setConfirmationNumber(final long confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }
    
    @JSON(include = false)
    public boolean isActive() {
        return this.active;
    }
    
    public void setActive(final boolean active) {
        this.active = active;
    }
    
    @JSON(include = false)
    public Date getProfileCreation() {
        return this.profileCreation;
    }
    
    public void setProfileCreation(final Date profileCreation) {
        this.profileCreation = profileCreation;
    }
    
    @JSON(include = false)
    public String getSource() {
        return this.source;
    }
    
    public void setSource(final String source) {
        this.source = source;
    }
    
    @OneToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL }, mappedBy = "profile")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    public LinkedInData getLinkedInData() {
        if (this.fieldHandler != null) {
            this.linkedInData = (LinkedInData)this.fieldHandler.readObject((Object)this, "linkedInData", (Object)this.linkedInData);
        }
        return this.linkedInData;
    }
    
    public void setLinkedInData(final LinkedInData linkedInData) {
        if (this.fieldHandler != null) {
            this.fieldHandler.writeObject((Object)this, "linkedInData", (Object)this.linkedInData, (Object)linkedInData);
        }
        this.linkedInData = linkedInData;
    }
    
    @JSON(include = false)
    @OneToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL }, mappedBy = "profile")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    public TwitterUserAuthentication getTwitterUserAuthentication() {
        if (this.fieldHandler != null) {
            this.twitterUserAuthentication = (TwitterUserAuthentication)this.fieldHandler.readObject((Object)this, "twitterUserAuthentication", (Object)this.twitterUserAuthentication);
        }
        return this.twitterUserAuthentication;
    }
    
    public void setTwitterUserAuthentication(final TwitterUserAuthentication twitterUserAuthentication) {
        if (this.fieldHandler != null) {
            this.fieldHandler.writeObject((Object)this, "twitterUserAuthentication", (Object)this.twitterUserAuthentication, (Object)twitterUserAuthentication);
        }
        this.twitterUserAuthentication = twitterUserAuthentication;
    }
    
    @Transient
    public String getProfileImageUrl() {
        return this.profileImageUrl;
    }
    
    public void setProfileImageUrl(final String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    
    @JSON(include = false)
    @Transient
    public Integer getQuestionIdBackend() {
        return this.questionIdBackend;
    }
    
    public void setQuestionIdBackend(final Integer questionIdBackend) {
        this.questionIdBackend = questionIdBackend;
    }
    
    @JSON(include = false)
    @Transient
    public FieldHandler getFieldHandler() {
        return this.fieldHandler;
    }
    
    public void setFieldHandler(final FieldHandler handler) {
        this.fieldHandler = handler;
    }
    
    public String getNumber() {
        return this.number;
    }
    
    public void setNumber(final String number) {
        this.number = number;
    }
    
    @OneToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL }, mappedBy = "profile")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    public EfactorData getEfactorData() {
        if (this.fieldHandler != null) {
            this.efactorData = (EfactorData)this.fieldHandler.readObject((Object)this, "efactorData", (Object)this.efactorData);
        }
        return this.efactorData;
    }
    
    public void setEfactorData(final EfactorData efactorData) {
        if (this.fieldHandler != null) {
            this.fieldHandler.writeObject((Object)this, "efactorData", (Object)this.efactorData, (Object)efactorData);
        }
        this.efactorData = efactorData;
    }
    
    @Transient
    public List<FoundTarget> getFilteredTargets() {
        final Iterator<FoundTarget> it = this.foundTargets.iterator();
        while (it.hasNext()) {
            final FoundTarget foundTarget = it.next();
            if (foundTarget.getOwnerId() != null) {
                it.remove();
            }
        }
        return this.foundTargets;
    }
    
    @Transient
    public int getOwnerFoundTarget() {
        for (FoundTarget foundTarget : this.getFoundTargets()) {
            if (foundTarget.getOwnerId() != null && foundTarget.getOwnerId() == (int)this.getId()) {
                return foundTarget.getId();
            }
        }
        return 0;
    }
    
    @Transient
    public MemberShip getMembershipType(final String membershipType) {
        for (final MemberShip theMembership : this.getMemberships()) {
            if (theMembership.getMembershipType().equals(membershipType)) {
                return theMembership;
            }
        }
        return null;
    }
    
    @JSON(include = false)
    @OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    public List<AsyncMessage> getAsyncMessages() {
        return this.asyncMessages;
    }
    
    public void setAsyncMessages(final List<AsyncMessage> asyncMessages) {
        this.asyncMessages = asyncMessages;
    }
    
    @JSON(include = false)
    @OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    public List<Notification> getNotifications() {
        return this.notifications;
    }
    
    public void setNotifications(final List<Notification> notifications) {
        this.notifications = notifications;
    }
    
    @JSON(include = false)
    @OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    public List<Connection> getConnections() {
        return this.connections;
    }
    
    public void setConnections(final List<Connection> connections) {
        this.connections = connections;
    }
    
    public boolean equals(final Object obj) {
        final Profile theProfile = (Profile)obj;
        boolean isTrue = false;
        if (this.getId() == (int)theProfile.getId()) {
            isTrue = true;
        }
        return isTrue;
    }
    
    public int hashCode() {
        return 934566540;
    }
    
    public Date getLastMessageSent() {
        return this.lastMessageSent;
    }
    
    public void setLastMessageSent(final Date lastMessageSent) {
        this.lastMessageSent = lastMessageSent;
    }  

}