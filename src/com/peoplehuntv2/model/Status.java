package com.peoplehuntv2.model;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Table;

@Entity
@Table(name = "status")
public class Status implements Serializable {	

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private FoundTarget foundTarget;		
	
	private String foundStatus;	
	
	private Integer proficiency;	

	private List<Feeler> feelers = new ArrayList<Feeler>();

	@Id
	@GeneratedValue
	@Column(name="STATUS_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FOUND_ID", updatable= false)
	public FoundTarget getFoundTarget() {
		return foundTarget;
	}

	public void setFoundTarget(FoundTarget foundTarget) {
		this.foundTarget = foundTarget;
	}
	
	
	@ManyToMany(cascade = {
			CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST 			
	},  fetch = FetchType.LAZY)
	@JoinTable(name="status_feelers",  
			joinColumns=@JoinColumn(name="STATUS_ID", unique = true, updatable = false, insertable = true),
			inverseJoinColumns=@JoinColumn(name="FEELER_ID", updatable = false, insertable = true))
	public List<Feeler> getFeelers() {
		return feelers;
	}

	public void setFeelers(List<Feeler> feelers) {
		this.feelers = feelers;
	}
	
	public String getFoundStatus() {
		return foundStatus;
	}

	public void setFoundStatus(String foundStatus) {
		this.foundStatus = foundStatus;
	}

	public Integer getProficiency() {
		return proficiency;
	}

	public void setProficiency(Integer proficiency) {
		this.proficiency = proficiency;
	}
}
