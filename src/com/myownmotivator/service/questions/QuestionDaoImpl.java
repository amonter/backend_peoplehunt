package com.myownmotivator.service.questions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myownmotivator.model.gaming.PeopleHuntId;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;

@Service("questionService")
@Transactional
public class QuestionDaoImpl implements QuestionDao {

	@Autowired
	private SessionFactory sessionFactory;	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteAnswers(Collection<Answer> answers) {

		StatelessSession session = sessionFactory.openStatelessSession();
		Transaction tx = session.beginTransaction();
		for (Answer answer : answers) {

			session.delete(answer);
		}
		tx.commit();
		session.close();
	}	
	

	@Transactional(readOnly = false)
	public void insertAnswers(Collection<Answer> answers) {

		StatelessSession session = sessionFactory.openStatelessSession();
		Transaction tx = session.beginTransaction();
		for (Answer answer : answers) {

			session.insert(answer);
		}
		tx.commit();
		session.close();
	}	
	
	
	@Transactional(readOnly = false)
	public void saveBatchQuestions(List<Question> questions) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for (Question theQuestion : questions) {
			
			session.merge(theQuestion);
		}
		tx.commit();
		session.close();
	}		


	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Long findBinaryAnswers(String tagName, boolean binaryAnswer) {

		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"select count(*) from Answer an where an.binaryAnswer =:binaryAnswer and an.tag.name =:tagName");
		query.setString("tagName", tagName);
		query.setBoolean("binaryAnswer", binaryAnswer);

		return (Long) query.uniqueResult();
	}

	@Transactional(readOnly = false)
	public Question saveQuestion(Question question) {

		return (Question) sessionFactory.getCurrentSession().merge(question);

	}
	

	@Transactional(readOnly = true)
	public Question findById(Integer id) {
		return (Question) sessionFactory.getCurrentSession().get(Question.class,id);
	}


	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Question> retrieveAllQuestions() {

		return (List<Question>) sessionFactory.getCurrentSession()
				.createQuery("from Question qu where qu.parent = TRUE  and qu.questionaireVersion ='crowd' ").list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Question> retrieveAllQuestionsByLoc() {

		return (List<Question>) sessionFactory.getCurrentSession()
				.createQuery("from Question qu  where qu.questionaireVersion ='crowd'").list();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Question> retrieveWeekQuestions(String startDate, String endDate) {			
		
		SimpleDateFormat aDate = new SimpleDateFormat("dd-MM-yyyy");
		Date parsedDateStart = null;
		Date parsedDateEnd = null;
		
		try {
			
			parsedDateStart = aDate.parse(startDate);
			parsedDateEnd = aDate.parse(endDate);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
		List<Question> theQuestion = sessionFactory.getCurrentSession()
		.createQuery("from Question qu where qu.parent = TRUE and qu.creationDate >=:start and qu.creationDate <=:endDate").setDate("start", parsedDateStart).setDate("endDate", parsedDateEnd).list();	
		
		return theQuestion;
	}
	
	

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Question> retrieveQuestionsAsc() {

		List<Question> question = (List<Question>) sessionFactory.getCurrentSession().createQuery("from Question qu  where qu.questionaireVersion ='crowd' order by qu.id asc")
		.list();
		
		return question;		
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Question> findQuestionWithPhoneId(Integer id) {
		
		List<Question> question = (List<Question>) sessionFactory.getCurrentSession().createQuery("from Question qu where qu.questionPhoneId =:questionId")
		.setInteger("questionId", id).list();
		
		return question;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Question> findQuestionWithUsername(String username, Integer questionPhoneId) {
		
		List<Question> usernames = sessionFactory.getCurrentSession().createQuery("select q1 from Question as q1 join q1.profiles as prof where prof.user.userName =:username and q1.questionPhoneId =:questionPhoneId")
		.setString("username", username).setInteger("questionPhoneId", questionPhoneId)
		.list();
				
		return usernames;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Question findBackendQuestionWithUsername(String username, Integer questionId) {
		
		Question question = (Question) sessionFactory.getCurrentSession()
		.createQuery("select q1 from Question as q1 join q1.profiles as prof where prof.user.userName =:username and q1.id =:id")
		.setString("username", username).setInteger("id", questionId)
		.uniqueResult();
				
		return question;
	}

	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Question> findQuestionsByUsername(String username) {
		
		List<Question> questions = sessionFactory.getCurrentSession()
		.createQuery("select q1 from Question as q1 join q1.profiles as prof where prof.user.userName =:username")
		.setString("username", username)
		.list();
				
		return questions;
	}
	
	@Transactional(readOnly = false)
	public void deleteQuestion(Integer questionId) {
		
		Question toDelete = (Question) sessionFactory.getCurrentSession().load(Question.class, questionId);
		sessionFactory.getCurrentSession().delete(toDelete);		
	}	

	
	@Override
	@Transactional(readOnly = false)	
	public void deleteQuestion(Question question) {
		sessionFactory.getCurrentSession().delete(question);
	}


	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Question findQuestionParentByPhoneId(Integer questionPhoneId) {
		
		return (Question) sessionFactory.getCurrentSession()
		.createQuery("from Question as qu where qu.questionPhoneId =:questionPhoneId and qu.parent = TRUE")
		.setInteger("questionPhoneId",questionPhoneId ).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Question findQuestionParentByType(String questionType, Integer bundleId) {
		
		return (Question) sessionFactory.getCurrentSession()
		.createQuery("from Question as qu where qu.questionType =:questionType and qu.parent = TRUE and qu.questionBundle.id =:bundleId")
		.setInteger("bundleId", bundleId)
		.setString("questionType", questionType).uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Question> retrievePagedQuestions(int pageNumber, int pageSize) {
		
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Question qu where qu.parent = true and qu.questionaireVersion ='crowd' order by qu.creationDate DESC");
		query.setFirstResult(pageSize * (pageNumber - 1));
		query.setMaxResults(pageSize);
		
		return query.list();		
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<Integer, Map<String, Object>> retrieveAnswersByParentQuestionId(Integer bundleId) { 		
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		ScrollableResults retrieveAllPeopleHunt = session.createQuery("from PeopleHuntId as ph where ph.bundleId =:bundleId")
		.setInteger("bundleId", bundleId)
		.scroll(ScrollMode.FORWARD_ONLY);
		Map<Integer, Map<String, Object>> answerResults = new HashMap<Integer, Map<String,Object>>();
		while (retrieveAllPeopleHunt.next()) {	
			PeopleHuntId peopleHuntId = (PeopleHuntId) retrieveAllPeopleHunt.get(0);
			if (peopleHuntId.getPairedHuntIds() != null){
				//System.out.println(peopleHuntId.getPairedHuntIds());
				List<String> listTags = java.util.Arrays.asList(peopleHuntId.getPairedHuntIds().split(","));
				
				for (String parentId : listTags) {							
					int parentIdAnswer = Integer.valueOf(parentId);
					if (!answerResults.containsKey(parentIdAnswer)){
						ScrollableResults allAnswers = session.createQuery("from Answer as a where a.id =:parentId")
						.setInteger("parentId", parentIdAnswer)
						.scroll(ScrollMode.FORWARD_ONLY);
						Map<String, Object> theMapAnswer = new HashMap<String, Object>();						
						while (allAnswers.next()){
						
							Answer theAnswer = (Answer) allAnswers.get(0);							
							theMapAnswer.put("answer", theAnswer.getTextualAnswer());	
							theMapAnswer.put("repeat", 1);
							answerResults.put(parentIdAnswer, theMapAnswer);
						}										
						
					} else {
						Map<String, Object> existingMapAnswer = answerResults.get(parentIdAnswer);
						int repeatCount = (Integer) existingMapAnswer.get("repeat") + 1;
						existingMapAnswer.put("repeat", repeatCount);
						answerResults.put(parentIdAnswer, existingMapAnswer);
						
					}
				}
			}
		}		
		
		tx.commit();
		session.close();		
		
		return answerResults;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<Integer, List<Question>> retrieveBatchQuestionsStats(List<Question> theQuestions) { 		
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Map<Integer, List<Question>> mapStats = new HashMap<Integer, List<Question>>();
		ScrollableResults retrievedQuestions = session.createQuery("from Question as qu where qu.parent= FALSE and qu.questionaireVersion ='crowd' ") .scroll(ScrollMode.FORWARD_ONLY);
		
		for (Question pageQuestion : theQuestions) {
			
			int count=0;		
			retrievedQuestions.first();
			while (retrievedQuestions.next()) {				
				
				Question scrollQuestion = (Question) retrievedQuestions.get(0);				
				Integer theKey = scrollQuestion.getQuestionPhoneId();				
				if (theKey.equals(pageQuestion.getQuestionPhoneId())) {
					
					if (mapStats.containsKey(theKey)) {
						
						List<Question> questionArray = mapStats.get(theKey);
						scrollQuestion.setAnswersAggregate(scrollQuestion.getTotalAnswerCount());
						questionArray.add(scrollQuestion);
						mapStats.put(theKey, questionArray);
					}
					else {
						
						List<Question> questionArray = new ArrayList<Question>();
						scrollQuestion.setAnswersAggregate(scrollQuestion.getTotalAnswerCount());
						questionArray.add(scrollQuestion);						
						mapStats.put(theKey, questionArray);
					}
				}
				
				
			   if ( ++count % 10 == 0 ) {
			      //flush a batch of updates and release memory:
			      session.flush();
			      session.clear();
			   }
			}		
		}

		tx.commit();
		session.close();		
		
		return mapStats;
	}


	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Question> findVirtualInfluence(String username) {
		return (List<Question>) sessionFactory.getCurrentSession()
		.createQuery("select qu from Question as qu join qu.profiles as prof where prof.user.userName !=:username and qu.createdBy =:username").setParameter("username", username).list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Question> retrieveUserBundle(String username, Integer bundleId) {
		return (List<Question>) sessionFactory.getCurrentSession().createQuery("select qu from Question as qu join qu.profiles as prof where prof.user.userName =:username and qu.questionBundle.id =:bundleId")
		.setParameter("username", username)
		.setParameter("bundleId", bundleId)
		.list();
	}
	
	
	
	public QuestionBundle retrieveQuestionBundle(Integer bundleId) {
		
		return (QuestionBundle) sessionFactory.getCurrentSession().load(QuestionBundle.class, bundleId);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionBundle> retrieveAllBundles() {
		// TODO Auto-generated method stub
		return  sessionFactory.getCurrentSession().createQuery("From QuestionBundle where inProgress= false and version = 'crowdscanner'").list();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionBundle> retrieveAllBundlesHunt() {
		// TODO Auto-generated method stub
		return  sessionFactory.getCurrentSession().createQuery("From QuestionBundle where inProgress= false and version = 'peoplehunt'").list();
	}
	 

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> retrieveParentQuestions(Integer bundleId) {
		return  sessionFactory.getCurrentSession().createQuery("from Question q where q.parent = true and q.questionBundle.id = :bundleId")
					.setParameter("bundleId",bundleId)
					.list();
		
	}
	
}
