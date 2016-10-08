	package com.myownmotivator.service.profile;

import java.util.List;

import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.Profile;

public interface UserDao {

	public User getByUserNameAndPassword(String username, String password);
	
	public User getByUserName(String username);
	
	public User getByFacebookUserName(String username);
	
	public User getUserByProfilerId(Integer profileId);
	
	public Profile getUserProfile(Integer id);
	
	public User saveUser(User user);
	
	public User saveUserFlow (User user) ;
	
	public User updateUser(User user);	
	
	public File getDefaultImage(String id);
	
	public void delete(int userId);

	public List<User> getAll();
	
	public List<User> getUsersByCountryState(String country, String state);
	
	public User retrieveUserByEmail(String email);
	
	public User retrieveUserByTwitterHandle(String handle);	

}
