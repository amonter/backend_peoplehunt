package com.myownmotivator.service.crowdmodule;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.myownmotivator.model.crowdmodule.Visual;

@Repository
@Transactional(readOnly=true)
public class VisualDaoImpl implements VisualDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional(readOnly=false)
	public Visual create(Visual newVisual) {
		sessionFactory.getCurrentSession().save(newVisual);
		return newVisual;
	}

	@SuppressWarnings("unchecked")
	public List<Visual> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from Visual").list();
	}

	public Visual findByName(String name) {
		return (Visual) sessionFactory.getCurrentSession()
							.createQuery("from Visual v where v.name = :name")
							.setString("name",name)
							.uniqueResult();
	}

	public Visual findById(Integer id) {
		return (Visual) sessionFactory.getCurrentSession()
							.get(Visual.class, id);
	}

	public Visual findByPermalink(String eventPermaLink) {
		return (Visual) sessionFactory.getCurrentSession()
					.createQuery("select qb.visual from QuestionBundle qb where qb.permaLink = :eventPermaLink")
					.setString("eventPermaLink",eventPermaLink)					
					.uniqueResult();
		
		//.setString("visualPermalink",visualPermalink)
	}
	
	
	

}
