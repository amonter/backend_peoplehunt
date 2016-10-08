package com.myownmotivator.model.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.service.events.EventListener;

@Entity
@Table(name = "event")
//This is the callback class for CUSTOMER entity.
@EntityListeners({EventListener.class})
public class Event implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;	

	private String description = ""; 

	private String reference;
	
	private String googleId;
	
	private String author;
	
	private String name;
	
	private Date startDate;
	
	private Date endDate;
	
	private Double price;
	
	private Integer bookingFee;
	
	private String location;
	
	private String content;
	
	private String html;

	private Integer totalCount = 0;	
	
	private Collection<Profile> participants = new ArrayList<Profile>();

	private Set<EventHistory> eventHistory;
	
	private Double mapLatitude;
	
	private Double mapLongitude;

	public Double getMapLatitude() {
		return mapLatitude;
	}

	public void setMapLatitude(Double mapLatitude) {
		this.mapLatitude = mapLatitude;
	}

	public Double getMapLongitude() {
		return mapLongitude;
	}

	public void setMapLongitude(Double mapLongitude) {
		this.mapLongitude = mapLongitude;
	}

	public String getContent() {
		return content;
	}

	@Transient
	public Collection<Profile> getParticipants() {
		return participants;
	}

	public void setParticipants(Collection<Profile> participants) {
		this.participants = participants;
	}

	public void setContent(String content) {
		this.content = content;
	}	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Integer getBookingFee() {
		return bookingFee;
	}

	public void setBookingFee(Integer bookingFee) {
		this.bookingFee = bookingFee;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	
	
	public String getGoogleId() {
		return googleId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	
	@Id
	@GeneratedValue
	@Column(name = "EVENT_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}	

	@Transient
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
	
	@Override
	public boolean equals(Object eventObject) {
		
		boolean equals = false;
		Event event = (Event)eventObject;
		if (event.getGoogleId().equals(getGoogleId())) {			
			return equals = true;
		}
		
		return equals;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 1;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "EVENT_ID") 
	public Set<EventHistory> getEventHistory() {
		return eventHistory;
	}

	public void setEventHistory(Set<EventHistory> eventHistory) {
		this.eventHistory = eventHistory;
	}	
}
