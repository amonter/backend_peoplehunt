package com.test.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.crowdmodule.QuestionBudleDao;
import com.myownmotivator.service.gaming.PeopleHuntService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "ApplicationContextTest.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class DeleteStuff {

	@Autowired
	private QuestionBudleDao questionBudleDao;
	@Autowired
	private QuestionDao questionService;
	@Autowired
	private UserDao userService;
	@Autowired
	private ProfileService profileService;
	@Autowired
	private PeopleHuntService peopleHuntService;

	//@Test
	public void testDeleteBundles () {
		
		QuestionBundle questionBundle = questionBudleDao.findById(209);		
		Integer profileId = 1557;
		List<Question> questions = Arrays.asList(new Question[questionBundle.getQuestions().size()]);

		Collections.copy(questions, questionBundle.getQuestions());
		// System.out.println(questions.size());

		// It's deleting the user in the view with jquery but the question has
		// not been deleted in the database
		// Create a copy to prevent concurrent modification exception

		// It's deleting the user in the view with jquery but the question has
		// not been deleted in the database
		// System.out.println(questions.size());
		for (Question question : questions) {
			Profile foundProfile = null;
			for (Profile profile : question.getProfiles()) {
				// System.out.println(profile.getId()+" "+profileId);
				if (profile.getId().equals(profileId)) {
					foundProfile = profile;
				}
			}
			if (foundProfile != null) {
				System.out.println("removequestions");
				questionBundle.removeQuestion(question);
				//foundProfile.removeQuestion(question);
				questionService.deleteQuestion(question);
			}
		}

		Profile theProfile = profileService.getUserProfile(profileId);
		//deleteGuessHunts(theProfile);

	}

	
	@Test
	public void testDeleteProfile () {			
		
			Profile theProfile = profileService.getUserProfile(1404);		
			System.out.println(theProfile);
			if (theProfile.getProfileInfo() != null) {
				//profileService.deleteProfileInfo(theProfile.getProfileInfo());
			}

			//profileService.deleteProfile(theProfile);
	}
	
	
	//@Test
	public void testCheckAddedBundles () {
		
		Profile theProfile = profileService.getUserProfile(1560);
		
		
	}

	//@Test
	public void testDeletePeopleHuntObj() {
		User aUser = userService
				.getByUserName("Shauna_Gillan_36916377@meetforeal.com");
		Profile theProfile = aUser.getProfile();

	}



	//@Test
	public void testDeleteAllQuestions() {

		QuestionBundle questionBundle = questionBudleDao.findById(590);
		List<Question> questions = Arrays.asList(new Question[questionBundle
				.getQuestions().size()]);
		Collections.copy(questions, questionBundle.getQuestions());

		// It's deleting the user in the view with jquery but the question has
		// not been deleted in the database
		for (Question question : questions) {
			if (question.getProfiles().size() > 0) {
				Profile foundProfile = question.getProfiles().get(0);
				if (!question.getParent()) {
					if (foundProfile != null) {
						questionBundle.removeQuestion(question);
						//foundProfile.removeQuestion(question);
						questionService.deleteQuestion(question);

					}
				}
			}
		}
	}	
}