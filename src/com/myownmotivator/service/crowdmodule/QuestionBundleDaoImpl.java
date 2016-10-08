package com.myownmotivator.service.crowdmodule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.myownmotivator.model.gaming.PeopleHuntId;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.QuestionBundle;

@Repository
@Transactional(readOnly=true)
public class QuestionBundleDaoImpl implements QuestionBudleDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<QuestionBundle> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from QuestionBundle").list();
	}
	
	@SuppressWarnings("unchecked")	
	@Transactional(readOnly = true)
	public Map<Integer, Integer> retrieveRepeatedProvidingFor(Integer bundleId, List<Answer> listAnswers) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Map<Integer, Integer> answerResults = new HashMap<Integer, Integer>();
		for (Answer answer: listAnswers){
			
			String resAnswerId = "%"+String.valueOf(answer.getId())+"%";			
			List<PeopleHuntId> peopleHuntList = session.createQuery("from PeopleHuntId as ph where ph.bundleId =:bundleId and ph.pairedHuntIds like :theNumber")
			.setInteger("bundleId", bundleId)
			.setString("theNumber", resAnswerId)
			.list();
		
			answerResults.put(answer.getId(), peopleHuntList.size());			
		}
		
		tx.commit();
		session.close();		
		
		return answerResults;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<QuestionBundle> findBundleByUser(String username) {
		return sessionFactory.getCurrentSession().createQuery("from QuestionBundle qBun where  qBun.id in  " +
				"(select qu.questionBundle.id from Question as qu join qu.profiles as prof where prof.user.userName =:username" +
				" and qu.parent = false)").setParameter("username", username).list();	
	}

	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Profile> getBundleProfiles(Integer bundleId) {
		
		List<Profile> profiles = sessionFactory.getCurrentSession()
		.createQuery("select distinct prof from Question as q1 join q1.profiles as prof where q1.parent =FALSE and q1.questionBundle.id =:id")
		.setInteger("id", bundleId)
		.setMaxResults(-1)
		.list();
				
		return profiles;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Long countBundleProfiles(Integer bundleId) {
		
		Long theCount = (Long) sessionFactory.getCurrentSession()
		.createQuery("select count(distinct prof) from Question as q1 join q1.profiles as prof where q1.parent =FALSE and q1.questionBundle.id =:id")
		.setInteger("id", bundleId)		
		.uniqueResult();
				
		return theCount;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Profile> getBundleProfilesBySize(Integer bundleId, Integer maxSize) {
		
		List<Profile> profiles = sessionFactory.getCurrentSession()
		.createQuery("select distinct prof from Question as q1 join q1.profiles as prof where q1.parent =FALSE and q1.questionBundle.id =:id order by prof.score.score desc")
		.setInteger("id", bundleId)
		.setMaxResults(maxSize)
		.list();
				
		return profiles;
	}
	
	

	public QuestionBundle findById(Integer id) {
		return (QuestionBundle) sessionFactory.getCurrentSession().get(QuestionBundle.class, id);
	}

	@Override
	@Transactional(readOnly=false)
	public void update(QuestionBundle questionBundle) {
		sessionFactory.getCurrentSession().merge(questionBundle);
	}

	@Override
	@Transactional(readOnly=false)
	public void create(QuestionBundle questionBundle) {
		sessionFactory.getCurrentSession().save(questionBundle);
	}
	
	@Transactional(readOnly=true)
	public QuestionBundle findByPermalink(String eventPermaLink) {
		return (QuestionBundle) sessionFactory.getCurrentSession()
					.createQuery("from QuestionBundle qb where qb.permaLink = :eventPermaLink")
					.setString("eventPermaLink",eventPermaLink)					
					.uniqueResult();	
	}
	
	@Transactional(readOnly=false)
	public void updateProfilesBatch(List<Profile> profiles){
		
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		for(Profile person : profiles){
			session.merge(person);
		}
		
		tx.commit();
		session.close();
	}

}
