
package com.test.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.crowdmodule.QuestionBudleDao;
import com.myownmotivator.service.crowdmodule.VisualDao;
import com.myownmotivator.service.peoplehuntv2.FeelerService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;
import com.peoplehuntv2.model.Feeler;
import com.peoplehuntv2.model.Notification;

import flexjson.JSONSerializer;


@RunWith(SpringJUnit4ClassRunner.class)
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations={"ApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class QuestionBundleTest {

	@Autowired
	private QuestionBudleDao questionBudleDao;
	
	
	@Autowired
	private QuestionDao questionService;
	
	
	@Autowired
	private VisualDao visualDao;
	
	
	@Autowired
	private UserDao userService;	
	
	
	@Autowired
	private FeelerService feelerService;
	
	@Autowired
	private ProfileService profileService;
	
	
	//@Test
	public void testRetrieveSortedAnswers() throws InterruptedException {		
		
		List<QuestionBundle> bundleArray = questionService.retrieveAllBundlesHunt();			
		String theResult = new JSONSerializer().exclude("*.class").exclude("parentQuestions").serialize(bundleArray);
		System.out.println(theResult);
	}
	
	//@Test
	public void retrieveSortedAnswers() {
			
		List<Question> parentQuestions = questionService.retrieveParentQuestions(201);
		
		for (Question parentQuestion : parentQuestions) {				
			//if (parentQuestion.getQuestionType().equals("lookingfor")){
				for (Answer theAnswer : parentQuestion.getAnswers()) {
					if (theAnswer.getDateCreated() == null){
						System.out.println("updated date");
						Calendar theCal = Calendar.getInstance();
						theAnswer.setDateCreated(theCal.getTime());
					}
				}
			//}
		}			
	}
	
	
	@Test
	public void retrieveFeelers() {
			
		List<Notification> notifications = profileService.retrieveProfileNotifications(10);	
		for (Notification notification : notifications) {
			notification.setId(notification.getSenderId());
			if (notification.isCallBack()){//can share means
				notification.setMatchItem("knows: "+notification.getMatchItem());
			} else {
				notification.setMatchItem("wants: "+notification.getMatchItem());
			}
		}
		//sort the notifications
		System.out.println(notifications.size());
		Collections.sort(notifications, new Comparator<Notification>() {
			public int compare(Notification notify, Notification notify2) {
				return notify2.getNotificationTime().compareTo(notify.getNotificationTime());
			}
		});			
		
	
		String theResult = new JSONSerializer().exclude("*.class").serialize(notifications);
		System.out.println(theResult);
		
	}	
}
