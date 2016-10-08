package com.myownmotivator.service.peoplehuntv2;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peoplehuntv2.model.Group;

@Service("groupService")
@Transactional
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override	
	@Transactional(readOnly=true)
	public Group getGroupByFacebookId(String facebookId) {
		
		return  (Group) sessionFactory.getCurrentSession().createQuery("from Group as gr where gr.facebookId =:facebookId")
				.setString("facebookId", facebookId)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Group> getGroupsByType(String groupType) {
	
		return sessionFactory.getCurrentSession().createQuery("from Group as gr where gr.type =:groupType")
				.setString("groupType", groupType).list();
	}

	@Override
	@Transactional(readOnly=false)
	public void addNewGroup(Group aGroup) {
		sessionFactory.getCurrentSession().merge(aGroup);				
	}

	@Override
	@Transactional(readOnly=true)
	public Group getGroupById(Integer id) {
		
		return (Group) sessionFactory.getCurrentSession().get(Group.class,id);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Group> getAllGroups() {
		
		return sessionFactory.getCurrentSession().createQuery("from Group").list();
	}

}
