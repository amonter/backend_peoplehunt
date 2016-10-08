package com.myownmotivator.model.crowdmodule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.myownmotivator.utils.PermalinkUtil;

@Entity
@Table(name = "visual")
public class Visual {

	@Id
	@GeneratedValue
	@Column(name = "VISUAL_ID")
	private Integer visualId;

	private String name;

	private String description;

	private String image;

	private Integer cost;

	private String permaLink;

	public Integer getVisualId() {
		return visualId;
	}

	public void setVisualId(Integer visualId) {
		this.visualId = visualId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}
	
	public String getPermaLink() {
		if (permaLink == null) {
			permaLink = PermalinkUtil.prepareForPermalink(name); 
		}
		return permaLink;
	}

	public void setPermaLink(String permaLink) {
		this.permaLink = permaLink;
	}
}
