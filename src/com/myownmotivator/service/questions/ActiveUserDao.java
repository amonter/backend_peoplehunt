package com.myownmotivator.service.questions;

import java.util.List;

import com.myownmotivator.model.profile.ActiveUser;

public interface ActiveUserDao {

	public ActiveUser saveActiveUser(ActiveUser theActiveUser);
	
	public List<ActiveUser> retrieveAllActiveUsers();
	
	public ActiveUser retrieveUserbyUID(Long uid);
	
	public List<ActiveUser> retrieveIlikeGraph(Long uid);
	
}
