package com.myownmotivator.model.profile;

import java.util.ArrayList;
import java.util.List;

public class MetaResObject {

	private List<ActiveUserNode> activenodes = new ArrayList<ActiveUserNode>();
	
	private List<ILiked> likes = new ArrayList<ILiked>();

	public List<ActiveUserNode> getActivenodes() {
		return activenodes;
	}

	public void setActivenodes(List<ActiveUserNode> activenodes) {
		this.activenodes = activenodes;
	}

	public List<ILiked> getLikes() {
		return likes;
	}

	public void setLikes(List<ILiked> likes) {
		this.likes = likes;
	}	
}
