package com.myownmotivator.service.profile;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.Profile;

@Repository(value="UserService")
@Transactional
public class UserDaoImpl implements UserDao {

	private static final String getByUserNameAndPasswordQuery = "select u from User as u where u.userName=:username and "
			+ "u.password=:password";

	private static final String getUserByProfileId = "select u from User as u where u.profile.id =:id";

	private static final String getUserByCountryState = "select u from User as u where u.country like :country and u.state like :state";

	private static final String getByUserNameQuery = "from User as u where u.userName=:username and u.profile.source !=:source";

	private static final String getUserProfile = "select p from Profile as p where p.id=:id";

	private static final String getDefaultImage = "select f from File as f where f.filename =:name";

	private static final String getAllUsers = "from User";
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public User getByUserName(String username) {

		return (User) sessionFactory.getCurrentSession().createQuery(getByUserNameQuery).setString("username", username)
		.setString("source", "facebook").uniqueResult();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public User getByFacebookUserName(String username) {

		List<User> listUsers = sessionFactory.getCurrentSession().createQuery("select u from User as u where u.userName=:username and u.profile.source =:source").setString("username", username)
		.setString("source", "facebook").list();		 
		if (listUsers.size() == 1) {

			return listUsers.get(0);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public User getByUserNameAndPassword(String username, String password) {

		Query query = sessionFactory.getCurrentSession().createQuery(getByUserNameAndPasswordQuery);
		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put("username", username);		
		parameters.put("password", password);
		List<User> listUsers = query.setProperties(parameters).list();
		if (listUsers.size() == 1) {

			return listUsers.get(0);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public Profile getUserProfile(Integer id) {
	
		return (Profile) sessionFactory.getCurrentSession().load(Profile.class, id);
	}

	public void delete(int userId) {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<User> getAll() {

		List<User> users = sessionFactory.getCurrentSession().createQuery(getAllUsers).list();

		return users;
	}

	@Transactional(readOnly=true)
	public User saveUserFlow (User user) {
		
		user.getProfile().setActive(true);
		return saveUser(user);		
	}
	
	
	@Transactional(readOnly=false)
	public User saveUser(User user) {
	
		//user.getBirthDate().setDate(user.getBirthDate().getFormattedDate());
		Integer userId = (Integer) sessionFactory.getCurrentSession().save(user);
		
		return (User) sessionFactory.getCurrentSession().get(User.class, userId);
	}
	
	@Transactional(readOnly=false)
	public User updateUser(User user) {
		
		return (User) sessionFactory.getCurrentSession().merge(user);				
	}

	

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public File getDefaultImage(String id) {

		List<File> listFile = sessionFactory.getCurrentSession().createQuery(getDefaultImage).setString("name", id).list();		
		if (listFile.size() == 1) {

			return listFile.get(0);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public User getUserByProfilerId(Integer profileId) {

		List<User> listUser = sessionFactory.getCurrentSession().createQuery(getUserByProfileId).setInteger("id", profileId).list();		
		if (listUser.size() == 1) {

			return listUser.get(0);
		}

		return null;
	}
	
	
	@Transactional(readOnly=true)
	public User retrieveUserByEmail(String email) {
		
		return (User)sessionFactory.getCurrentSession().createQuery("From User u where u.email =:email").setString("email", email).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<User> getUsersByCountryState(String country, String state) {

		country = StringUtils.isEmpty(country) ? "%" : country + "%";
		state = StringUtils.isEmpty(state) ? "%" : state + "%";
		
		Query query = sessionFactory.getCurrentSession().createQuery(getUserByCountryState);
		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put("country", country);
		parameters.put("state", state);		

		return query.setProperties(parameters).list();
	}


	@Override
	public User retrieveUserByTwitterHandle(String handle) {
		// TODO Auto-generated method stub
		return  (User)sessionFactory.getCurrentSession()
		.createQuery("From User u where u.profile.twitterUserAuthentication.username =:handle").setString("handle", handle).uniqueResult();
	}	
	
}
