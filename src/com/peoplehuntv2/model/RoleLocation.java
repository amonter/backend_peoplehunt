package com.peoplehuntv2.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "role_location")
public class RoleLocation implements Serializable{
	
	private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer role;
    private Integer locationId;
    
    @Id
    @GeneratedValue
    @Column(name = "ROLELOCATION_ID")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
}
