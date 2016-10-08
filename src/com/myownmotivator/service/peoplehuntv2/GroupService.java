package com.myownmotivator.service.peoplehuntv2;

import java.util.List;

import com.peoplehuntv2.model.Group;

public interface GroupService {

	public Group getGroupByFacebookId(String facebookId);
	
	public List<Group> getGroupsByType(String groupType);
	
	public List<Group> getAllGroups();
	
	public void addNewGroup(Group aGroup);
	
	public Group getGroupById(Integer id);
	
}
