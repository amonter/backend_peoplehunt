package com.myownmotivator.service.questions;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myownmotivator.model.profile.ActiveUser;

@Service("activeUserService")
@Transactional
public class ActiveUserDaoImpl implements ActiveUserDao {

	@Autowired
	private SessionFactory sessionFactory;	
	
	
	@Transactional(readOnly = false)
	public ActiveUser saveActiveUser(ActiveUser theActiveUser) {
		// TODO Auto-generated method stub
		return (ActiveUser) sessionFactory.getCurrentSession().merge(theActiveUser);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ActiveUser> retrieveAllActiveUsers() {
		// TODO Auto-generated method stub
		return  sessionFactory.getCurrentSession().createQuery("from ActiveUser").list();
	}


	@Override
	public ActiveUser retrieveUserbyUID(Long uid) {
		// TODO Auto-generated method stub
		return  (ActiveUser) sessionFactory.getCurrentSession()
		.createQuery("from ActiveUser ac where ac.uid=:uid").setLong("uid", uid).uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ActiveUser> retrieveIlikeGraph(Long uid) {
		// TODO Auto-generated method stub
		return  sessionFactory.getCurrentSession().createQuery("from ActiveUser ac where :uid in (select li.uid from ac.likes as li)").setLong("uid", uid).list();
	}	

}
