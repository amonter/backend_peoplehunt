package com.myownmotivator.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myownmotivator.model.profile.Profile;

@Entity
@Table(name = "plan")
public class Plan {

	private Integer id;
	private String type;
	private Date startDate;
	private Date endDate;
	private int noOfUsers;
	private int totalAmount;
	private int numberBadges;
	private Profile profile;	
	private Set<Transaction> transactions = new HashSet<Transaction>();
	
	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getNumberBadges() {
		return numberBadges;
	}

	public void setNumberBadges(int numberBadges) {
		this.numberBadges = numberBadges;
	}
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
	}

	@OneToMany(cascade = CascadeType.ALL,mappedBy="plan")
	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	@OneToOne
	@JoinColumn(name = "profile_id")
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNoOfUsers() {
		return noOfUsers;
	}

	public void setNoOfUsers(int noOfUsers) {
		this.noOfUsers = noOfUsers;
	}
	
}
