package com.test.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.crowdscanner.controller.RestControllerUtil;
import com.myownmotivator.model.User;
import com.myownmotivator.model.crowdmodule.Visual;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.crowdmodule.QuestionBudleDao;
import com.myownmotivator.service.crowdmodule.VisualDao;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;

import flexjson.JSONSerializer;

@RunWith(SpringJUnit4ClassRunner.class)
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations={"ApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class QuestionServiceTest {
	
	
	@Autowired
	private QuestionBudleDao questionBudleDao;
	
	
	@Autowired
	private QuestionDao questionService;
	
	
	@Autowired
	private VisualDao visualDao;
	
	@Autowired
	private UserDao userService;
	
	
	
	@Test
	public void testQuestionBundles() {		
		
		//Bundle id 13 should already exist in the database otherwise comment setId(13) for the method to create a new bundle in the database
		// and then uncommented it to link the question to the Brunch Berlin QuestionBundle and to kamaluser23
		// The user has to exist in order for this to work. So please create as many users as necessary in the PostServicesTest.java and each user can 
		//have one or may questions.
		List<Question> questions = new ArrayList<Question>();
		QuestionBundle bundle2 = new QuestionBundle();
		bundle2.setId(182); 
		bundle2.setDescription("GSAS New Student Orientation Series");
		bundle2.setTopic("GSAS New Student Orientation Series");
		bundle2.setFreeBundle(true);
		bundle2.setPermaLink("#GSAS");
		bundle2.setLocation("Kimmel Center, NYU");
		bundle2.setDate(new Date());
		bundle2.setLatitude("40.730097");
		bundle2.setLongitude("-73.997859");
		//Visual theVisual = new Visual();
		//theVisual.setVisualId(2);
		bundle2.setVisual(null);
		bundle2.setVersion("peoplehunt");
		bundle2.setInProgress(true);
		bundle2.setImageURL("http://s3.amazonaws.com/PeopleHunt/nyugraduate.png");
		User theUser = userService.getByUserName("amonter5");

		
		//////////BUNDLE USER GENERATED ////////////////////////////////////////////////////////////////////////////		
		
		//Validation Question	
		Question aQuestion11 = new Question();
		aQuestion11.setQuestionDescription("3. What are you passionate about?");
		double thedouble11 = Math.random() * 10000000;
		aQuestion11.setQuestionPhoneId(83748596);
		aQuestion11.setParent(true);
		aQuestion11.setQuestionType("talkabout");		
		doAnswersValidation4(aQuestion11);
		
		
		
		//Validation Question
		/*Question aQuestion12 = new Question();
		aQuestion12.setQuestionDescription("Where would you love to travel to?");
		double thedouble12 = Math.random() * 10000000;
		aQuestion12.setQuestionPhoneId(53841133);
		aQuestion12.setParent(true);
		aQuestion12.setQuestionType("travel");		
		doAnswersValidation67(aQuestion12);	*/
		
		
		Question aQuestion13 = new Question();
		aQuestion13.setQuestionDescription("2. Name a clue location within the venue where you can meet your PeopleHunt match");
		double thedouble13 = Math.random() * 10000000;
		aQuestion13.setQuestionPhoneId(65748596);
		aQuestion13.setParent(true);
		aQuestion13.setQuestionType("location");		
		doAnswersValidation70(aQuestion13);
		
		
		//Validation Question
		Question aQuestion10 = new Question();
		aQuestion10.setQuestionDescription("1. What are you looking for so you can be matched with the right people?");
		double thedouble10 = Math.random() * 10000000;
		aQuestion10.setQuestionPhoneId(12874647);
		aQuestion10.setParent(true);
		aQuestion10.setQuestionType("lookingfor");	
		aQuestion10.setHuntingFor("Select what you can offer:");
		aQuestion10.setHelpWith("Select all that apply");	
		doAnswersValidation(aQuestion10);	
		
		/*Question aQuestion14 = new Question();
		aQuestion14.setQuestionDescription("What insect best describes you?");
		double thedouble14 = Math.random() * 10000000;
		aQuestion14.setQuestionPhoneId(48263917);
		aQuestion14.setParent(true);
		aQuestion14.setQuestionType("insect");		
		doAnswersValidation27(aQuestion14);*/
		
		aQuestion10.getProfiles().add(theUser.getProfile());
		aQuestion11.getProfiles().add(theUser.getProfile());
		//aQuestion12.getProfiles().add(theUser.getProfile());
		aQuestion13.getProfiles().add(theUser.getProfile());
		//aQuestion14.getProfiles().add(theUser.getProfile());
	
		aQuestion10.setQuestionBundle(bundle2);
		aQuestion11.setQuestionBundle(bundle2);
		//aQuestion12.setQuestionBundle(bundle2);
		aQuestion13.setQuestionBundle(bundle2);
		//aQuestion14.setQuestionBundle(bundle2);
		
		questions.add(aQuestion10);
		questions.add(aQuestion11);
		//questions.add(aQuestion12);
		questions.add(aQuestion13);
		//questions.add(aQuestion14);
		
		questionService.saveBatchQuestions(questions);				
				
	}	
	
	private void doAnswersValidation70(Question aQuestion13) {
		Answer twoAnswer = new Answer();
		twoAnswer.setTextualAnswer("In front of the window");
		twoAnswer.setAnswerNumber(0);
		Answer three = new Answer();
		three.setTextualAnswer("In front of the podium");
		three.setAnswerNumber(0);
		Answer four = new Answer();
		four.setTextualAnswer("Under the clock");
		four.setAnswerNumber(0);
		Answer five = new Answer();
		five.setTextualAnswer("Under the exit sign at the entrance");
		five.setAnswerNumber(0);
		/*Answer six = new Answer();
		six.setTextualAnswer("By the Soundboard");
		six.setAnswerNumber(0);
		Answer seven = new Answer();
		seven.setTextualAnswer("By the turn tables");
		seven.setAnswerNumber(0);*/
		
		aQuestion13.getAnswers().add(twoAnswer);
		aQuestion13.getAnswers().add(three);
		aQuestion13.getAnswers().add(four);
		aQuestion13.getAnswers().add(five);
		//aQuestion13.getAnswers().add(six);
		//aQuestion13.getAnswers().add(seven);
		//aQuestion13.getAnswers().add(threeAnswer);
		
	}

	private void doAnswersValidation67(Question aQuestion12) {
		
		Answer twoAnswer = new Answer();
		twoAnswer.setTextualAnswer("Mexico City");
		twoAnswer.setAnswerNumber(0);
		Answer three = new Answer();
		three.setTextualAnswer("Dublin");
		three.setAnswerNumber(0);
		Answer four = new Answer();
		four.setTextualAnswer("Bangalore");
		four.setAnswerNumber(0);
		Answer five = new Answer();
		five.setTextualAnswer("Hawaii");
		five.setAnswerNumber(0);
		Answer six = new Answer();
		six.setTextualAnswer("Cape Town");
		six.setAnswerNumber(0);
		Answer seven = new Answer();
		seven.setTextualAnswer("The Antartica");
		seven.setAnswerNumber(0);
		/*Answer eight = new Answer();
		eight.setTextualAnswer("Bali");
		eight.setAnswerNumber(0);
		Answer nine = new Answer();
		nine.setTextualAnswer("Hungary");
		nine.setAnswerNumber(0);
		Answer ten = new Answer();
		ten.setTextualAnswer("Warsaw");
		ten.setAnswerNumber(0);
		Answer eleven = new Answer();
		eleven.setTextualAnswer("The Antartica");
		eleven.setAnswerNumber(0);
		Answer twelve = new Answer();
		twelve.setTextualAnswer("Beirut");
		twelve.setAnswerNumber(0);
		Answer thirteen = new Answer();
		thirteen.setTextualAnswer("Malaga");
		thirteen.setAnswerNumber(0);
		Answer fourteen = new Answer();
		fourteen.setTextualAnswer("Kilkenny");
		fourteen.setAnswerNumber(0);*/
		
		
		
		aQuestion12.getAnswers().add(twoAnswer);
		aQuestion12.getAnswers().add(three);
		aQuestion12.getAnswers().add(four);
		aQuestion12.getAnswers().add(five);
		aQuestion12.getAnswers().add(six);
		aQuestion12.getAnswers().add(seven);
		/*aQuestion12.getAnswers().add(eight);
		aQuestion12.getAnswers().add(nine);
		aQuestion12.getAnswers().add(ten);
		aQuestion12.getAnswers().add(eleven);
		aQuestion12.getAnswers().add(twelve);
		aQuestion12.getAnswers().add(thirteen);
		aQuestion12.getAnswers().add(fourteen);*/
		
	}




	private void doAnswersValidation4(Question aQuestion11) {
		
		Answer twoAnswer = new Answer();
		twoAnswer.setTextualAnswer("All things outdoors");
		twoAnswer.setAnswerNumber(0);
		Answer three = new Answer();
		three.setTextualAnswer("Dance");
		three.setAnswerNumber(0);
		Answer four = new Answer();
		four.setTextualAnswer("Food");
		four.setAnswerNumber(0);
		Answer five = new Answer();
		five.setTextualAnswer("Art");
		five.setAnswerNumber(0);
		Answer six = new Answer();
		six.setTextualAnswer("Sports");
		six.setAnswerNumber(0);
		Answer seven = new Answer();
		seven.setTextualAnswer("Travel");
		seven.setAnswerNumber(0);
		/*Answer eight = new Answer();
		eight.setTextualAnswer("Sports");
		eight.setAnswerNumber(0);
		Answer nine = new Answer();
		nine.setTextualAnswer("Travel");
		nine.setAnswerNumber(0);
		Answer ten = new Answer();
		ten.setTextualAnswer("Animals");
		ten.setAnswerNumber(0);
		Answer eleven = new Answer();
		eleven.setTextualAnswer("Jazz");
		eleven.setAnswerNumber(0);
		Answer twelve = new Answer();
		twelve.setTextualAnswer("Economics");
		twelve.setAnswerNumber(0);
		Answer thirteen = new Answer();
		thirteen.setTextualAnswer("Politics");
		thirteen.setAnswerNumber(0);
		Answer fourteen = new Answer();
		fourteen.setTextualAnswer("Outdoors");
		fourteen.setAnswerNumber(0);
		Answer fifteen = new Answer();
		fifteen.setTextualAnswer("Academics");
		fifteen.setAnswerNumber(0);*/
		
		
		aQuestion11.getAnswers().add(twoAnswer);
		aQuestion11.getAnswers().add(three);
		aQuestion11.getAnswers().add(four);
		aQuestion11.getAnswers().add(five);
		aQuestion11.getAnswers().add(six);
		aQuestion11.getAnswers().add(seven);
		/*aQuestion11.getAnswers().add(eight);
		aQuestion11.getAnswers().add(nine);
		aQuestion11.getAnswers().add(ten);
		aQuestion11.getAnswers().add(eleven);
		aQuestion11.getAnswers().add(twelve);
		aQuestion11.getAnswers().add(thirteen);
		aQuestion11.getAnswers().add(fourteen);
		aQuestion11.getAnswers().add(fifteen);*/
		
	}




	private void doAnswersValidation(Question aQuestion10) {
		Answer two = new Answer();
		two.setTextualAnswer("Someone to explore the city with");
		two.setAnswerNumber(0);
		Answer three = new Answer();
		three.setTextualAnswer("Someone who is not a US citizen");
		three.setAnswerNumber(0);
		Answer four = new Answer();
		four.setTextualAnswer("Someone who is an NYC local");
		four.setAnswerNumber(0);
		Answer five = new Answer();
		five.setTextualAnswer("Someone who is a fellow foodie");
		five.setAnswerNumber(0);
		Answer six = new Answer();
		six.setTextualAnswer("Someone to be an activity partner");
		six.setAnswerNumber(0);
		Answer seven = new Answer();
		seven.setTextualAnswer("Someone who has an interesting story about their summer");
		seven.setAnswerNumber(0);
		/*Answer eight = new Answer();
		eight.setTextualAnswer("Love");
		eight.setAnswerNumber(0);
		Answer nine = new Answer();
		nine.setTextualAnswer("Investors");
		nine.setAnswerNumber(0);
		Answer ten = new Answer();
		ten.setTextualAnswer("Travel Companions");
		ten.setAnswerNumber(0);
		Answer eleven = new Answer();
		eleven.setTextualAnswer("Partners in crime");
		eleven.setAnswerNumber(0);
		Answer twelve = new Answer();
		twelve.setTextualAnswer("Dance Partner");
		twelve.setAnswerNumber(0);
		Answer thirteen = new Answer();
		thirteen.setTextualAnswer("Web Developer");
		thirteen.setAnswerNumber(0);
		Answer fourteen = new Answer();
		fourteen.setTextualAnswer("Fashion Guide");
		fourteen.setAnswerNumber(0);
		Answer fifteen = new Answer();
		fifteen.setTextualAnswer("Life Coach");
		fifteen.setAnswerNumber(0);
		Answer sixteen = new Answer();
		sixteen.setTextualAnswer("Career Counselor");
		sixteen.setAnswerNumber(0);*/
				
		
		aQuestion10.getAnswers().add(two);
		aQuestion10.getAnswers().add(three);
		aQuestion10.getAnswers().add(four);
		aQuestion10.getAnswers().add(five);
		aQuestion10.getAnswers().add(six);
		aQuestion10.getAnswers().add(seven);
		/*aQuestion10.getAnswers().add(eight);
		aQuestion10.getAnswers().add(nine);
		aQuestion10.getAnswers().add(ten);
		aQuestion10.getAnswers().add(eleven);
		aQuestion10.getAnswers().add(twelve);
		aQuestion10.getAnswers().add(thirteen);
		aQuestion10.getAnswers().add(fourteen);
		aQuestion10.getAnswers().add(fifteen);
		aQuestion10.getAnswers().add(sixteen);*/
	}
	
	private void doAnswersValidation27(Question aQuestion14) {
		Answer two = new Answer();
		two.setTextualAnswer("Ant");
		two.setAnswerNumber(0);
		Answer three = new Answer();
		three.setTextualAnswer("Cockroach");
		three.setAnswerNumber(0);
		Answer four = new Answer();
		four.setTextualAnswer("Wasp");
		four.setAnswerNumber(0);
		Answer five = new Answer();
		five.setTextualAnswer("Caterpillar");
		five.setAnswerNumber(0);
		Answer six = new Answer();
		six.setTextualAnswer("Mosquito");
		six.setAnswerNumber(0);
				
		
		aQuestion14.getAnswers().add(two);
		aQuestion14.getAnswers().add(three);
		aQuestion14.getAnswers().add(four);
		aQuestion14.getAnswers().add(five);
		aQuestion14.getAnswers().add(six);
		//aQuestion10.getAnswers().add(seven);
		//aQuestion10.getAnswers().add(eight);
		//aQuestion10.getAnswers().add(nine);
	}




	private void doAnswers9(Question aQuestion9) {
		Answer twoAnswer = new Answer();
		twoAnswer.setTextualAnswer("Answer it");
		twoAnswer.setAnswerNumber(0);
		Answer three = new Answer();
		three.setTextualAnswer("Let it go to voicemail");
		three.setAnswerNumber(0);		
		
		aQuestion9.getAnswers().add(twoAnswer);
		aQuestion9.getAnswers().add(three);
		
	}




	private void doAnswers7(Question question) {
		
		Answer twoAnswer = new Answer();
		twoAnswer.setTextualAnswer("Organized");
		twoAnswer.setAnswerNumber(0);
		Answer three = new Answer();
		three.setTextualAnswer("Adaptable");
		three.setAnswerNumber(0);		
		
		question.getAnswers().add(twoAnswer);
		question.getAnswers().add(three);			
	}
	
	
	
	private void doAnswers6(Question question) {
		
		Answer twoAnswer = new Answer();
		twoAnswer.setTextualAnswer("Give money spontaneously");
		twoAnswer.setAnswerNumber(0);
		Answer three = new Answer();
		three.setTextualAnswer("Help in other ways");
		three.setAnswerNumber(0);		
		
		question.getAnswers().add(twoAnswer);
		question.getAnswers().add(three);			
	}
	
	
	
	private void doAnswers5(Question question) {
		
		Answer twoAnswer = new Answer();
		twoAnswer.setTextualAnswer("Feel uncomfortable and wish I had more information.");
		twoAnswer.setAnswerNumber(0);
		Answer three = new Answer();
		three.setTextualAnswer("Am able to do so with available data.");
		three.setAnswerNumber(0);		
		
		question.getAnswers().add(twoAnswer);
		question.getAnswers().add(three);			
	}
	
	
	private void doAnswers4(Question question) {
		
		Answer twoAnswer = new Answer();
		twoAnswer.setTextualAnswer("Relate it to my own experience.");
		twoAnswer.setAnswerNumber(0);
		Answer three = new Answer();
		three.setTextualAnswer("Assess and analyze the message.");
		three.setAnswerNumber(0);		
		
		question.getAnswers().add(twoAnswer);
		question.getAnswers().add(three);		
		
	}
	
	
	
	private void doAnswers3(Question question) {
		
		Answer twoAnswer = new Answer();
		twoAnswer.setTextualAnswer("See the big picture.");
		twoAnswer.setAnswerNumber(0);
		Answer three = new Answer();
		three.setTextualAnswer("Grasp the specifics of the situation.");
		three.setAnswerNumber(0);		
		
		question.getAnswers().add(twoAnswer);
		question.getAnswers().add(three);		
		
	}
	
	

	private void doAnswers2(Question question) {
		
		Answer twoAnswer = new Answer();
		twoAnswer.setTextualAnswer("Make lists and plans whenever I start something.");
		twoAnswer.setAnswerNumber(0);
		Answer three = new Answer();
		three.setTextualAnswer("Avoid plans and just let things progress.");
		three.setAnswerNumber(0);		
		
		question.getAnswers().add(twoAnswer);
		question.getAnswers().add(three);		
		
	}
	

	private void doAnswers(Question question) {
		
		Answer oneAnswer = new Answer();
		oneAnswer.setTextualAnswer("a. Strongly agree");
		oneAnswer.setAnswerNumber(0);
		Answer twoAnswer = new Answer();
		twoAnswer.setTextualAnswer("b. Agree");
		twoAnswer.setAnswerNumber(0);
		Answer threeAnswer = new Answer();
		threeAnswer.setTextualAnswer("c. Neither agree nor disagree");
		threeAnswer.setAnswerNumber(0);
		Answer fourAnswer = new Answer();
		fourAnswer.setTextualAnswer("d. Disagree");
		fourAnswer.setAnswerNumber(0);
		Answer fiveAnswer = new Answer();
		fiveAnswer.setTextualAnswer("e. Strongly disagree");
		fiveAnswer.setAnswerNumber(0);		
		
		question.getAnswers().add(fiveAnswer);
		question.getAnswers().add(fourAnswer);
		question.getAnswers().add(threeAnswer);
		question.getAnswers().add(twoAnswer);	
		question.getAnswers().add(oneAnswer);
	}
	
	
	
	
	private void doAnswersGender(Question question) {
		
		Answer twoAnswer = new Answer();
		twoAnswer.setTextualAnswer("a. Male");
		twoAnswer.setAnswerNumber(0);
		Answer three = new Answer();
		three.setTextualAnswer("b. Female");
		three.setAnswerNumber(0);		
		
		question.getAnswers().add(twoAnswer);
		question.getAnswers().add(three);			
	}
	
	
	//@Test
	public void testAddVisual() {	
				
		Visual theVisual = new Visual();
		theVisual.setName("mindfield");
		theVisual.setImage("http://images.crowdscanner.com/nerd.png");
		theVisual.setCost(23);
		theVisual.setDescription("MindField Visual");
		visualDao.create(theVisual);
		
	}
	
	//@Test
	public void testUserBundle() {	
		
		List<Question> theQuestions = questionService.findQuestionWithPhoneId(471173);			
		List<Map<String,Object>> objects = new ArrayList<Map<String,Object>>();
		for (Question question : theQuestions) {				
			if (!question.getParent()){
				
				Map<String, Object> theMap = new HashMap<String, Object>();
				Profile theProfile = question.getProfiles().get(0);
				theMap.put("email", theProfile.getUser().getUserName());
				theMap.put("name", theProfile.getUser().getLastName());
				theMap.put("question", question.getQuestionDescription());
				theMap.put("answer", question.getSelectedAnswer());
				
				objects.add(theMap);
			}
		}
				
		
		String theResult = new JSONSerializer().exclude("*.class").serialize(objects);	
		System.out.println(theResult);
}
	
	
	
private String extractFileUrl(String theFile, Map<String, File> mapFiles) {
		
		if (mapFiles.containsKey("profilePhoto")){							
			File storedFile= mapFiles.get("profilePhoto");
			theFile = "http://87.232.63.199:8080/myownmotivator/picture_library/profile/" +storedFile.getId()+".jpg";
			
		} else if (mapFiles.containsKey("facebookProfilePhoto_url") || mapFiles.containsKey("twitterProfilePhoto_url")) {							
			if (mapFiles.containsKey("facebookProfilePhoto_url")) {
				
				File storedFile= mapFiles.get("facebookProfilePhoto_url");
				theFile = new String(storedFile.getFileContent());		
			} else {
				
				File storedFile= mapFiles.get("twitterProfilePhoto_url");
				theFile = new String(storedFile.getFileContent());						
			}				
		}
		return theFile;
	}
	
	
	
	
	//@Test
	public void testQuestionByLocation () {
		
		List<Question> theQuestions = questionService.retrieveAllQuestionsByLoc();			
		List<Question> questionsByDistance = new ArrayList<Question>();
					
		for (Question question : theQuestions) {
			Double latitudeQuestion = Double.valueOf(question.getLatitude());
			Double longitudeQuestion = Double.valueOf(question.getLongitude());
			
			double distance = RestControllerUtil.distance(53.2538, -9.15519, latitudeQuestion, longitudeQuestion);
			question.setDistance(distance);		
			questionsByDistance.add(question);
		}
		
		Collections.sort(questionsByDistance, new Comparator<Question>() {
			public int compare(Question question, Question question2) {
				return question.getDistance().compareTo(question2.getDistance());
			}
		});	
		
		
		Set<Question> questionSet = new HashSet<Question>();		
		for (Question question1 : questionsByDistance) {
			
				if (!questionSet.contains(question1)) {
					questionSet.add(question1);							
				}					
		}			
		
		
		List<Question> finalList = new ArrayList<Question>(questionSet);
		Collections.sort(finalList, new Comparator<Question>() {
			public int compare(Question question, Question question2) {
				return question.getDistance().compareTo(question2.getDistance());
			}
		});
		
		
		int page = 2;
		int initIndex = (page - 1)  * 15;		
		List<Question> paginatedList =  finalList.subList(initIndex, (15 * page));
		for (Question question : paginatedList) {	
		
			System.out.println(question.getQuestionDescription() +" " + question.getDistance());
		}	
		
	}
	
	
	//@Test
	public void testCalculateInfluence() { 
		
		String username = "testingjuly";
		
		User theUser = userService.getByUserName(username);
		
		String gender = theUser.getGender();
		String placeLive = theUser.getCountry();
		String website = theUser.getProfile().getProfileInfo().getWebsite();
		String aboutMe = theUser.getProfile().getProfileInfo().getSelfPerception();	
		
		List <Question> questionPhysical = questionService.findQuestionsByUsername(username);
		int answersPhysical = 0;
		
		for  (Question question : questionPhysical) {
			
			int answers = question.getTotalAnswerCount();								
			answersPhysical = answersPhysical + answers;				
		}
		
		List <Question> questionVirtual = questionService.findVirtualInfluence(username);
		int answersVirtual = 0;
		
		for  (Question question : questionVirtual) {
			
			int answers = question.getTotalAnswerCount();								
			answersVirtual = answersVirtual + answers;				
		}
		
		Map<String, Object> mapOfParameters = new HashMap<String, Object>();
		mapOfParameters.put("gender", gender);
		mapOfParameters.put("placeLive", placeLive);
		mapOfParameters.put("website", website);
		mapOfParameters.put("aboutMe", aboutMe);
		mapOfParameters.put("answersPhysical", answersPhysical);
		mapOfParameters.put("answersVirtual", answersVirtual);
		
		String theResult = new JSONSerializer().serialize(mapOfParameters);		
		
		System.out.println(theResult);
		
	}
	
	//@Test
	public void testLocationAlgorith (){
		
		List<Question> theQuestions = new ArrayList<Question>();
		
		Question theQuestion1 = new Question();
		theQuestion1.setLatitude("53.2538");
		theQuestion1.setLongitude("-9.15519");
		
		Question theQuestion2 = new Question();
		theQuestion2.setLatitude("53.2538");
		theQuestion2.setLongitude("-9.15519");
		Question theQuestion3 = new Question();
		theQuestion3.setLatitude("53.2538");
		theQuestion3.setLongitude("-9.15519");
		Question theQuestion4 = new Question();
		theQuestion4.setLatitude("53.2538");
		theQuestion4.setLongitude("-9.15519");
		Question theQuestion5 = new Question();
		theQuestion5.setLatitude("53.2538");
		theQuestion5.setLongitude("-9.15519");
		Question theQuestion6 = new Question();
		theQuestion6.setLatitude("53.2538");
		theQuestion6.setLongitude("-9.15519");
		Question theQuestion7 = new Question();
		theQuestion7.setLatitude("53.2538");
		theQuestion7.setLongitude("-9.15519");
		Question theQuestion8 = new Question();
		theQuestion8.setLatitude("53.2538");
		theQuestion8.setLongitude("-9.15519");
		Question theQuestion9 = new Question();
		theQuestion9.setLatitude("53.2538");
		theQuestion9.setLongitude("-9.15519");
		
		theQuestions.add(theQuestion1);
		theQuestions.add(theQuestion2);
		theQuestions.add(theQuestion3);
		theQuestions.add(theQuestion4);
		theQuestions.add(theQuestion5);
		theQuestions.add(theQuestion6);
		theQuestions.add(theQuestion7);
		theQuestions.add(theQuestion8);
		theQuestions.add(theQuestion9);
	
		
		
		theQuestions = RestControllerUtil.rearrange(theQuestions);
		for (Question question : theQuestions) {
			//System.out.println(question.getLatitude() +" "+question.getLongitude());
		}		
		
	}
	
	//@Test
	public void testQuestionsLatitudes(){ 
		
		List<Question> theQuestions = questionService.retrieveAllQuestionsByLoc();					
		List<Question> questionsByDistance = new ArrayList<Question>();
					
		for (Question question : theQuestions) {
			Double latitudeQuestion = Double.valueOf(question.getLatitude());
			Double longitudeQuestion = Double.valueOf(question.getLongitude());
			
			double distance = RestControllerUtil.distance(53.2538, -9.15519, latitudeQuestion, longitudeQuestion);
			question.setDistance(distance);
		
			questionsByDistance.add(question);
		}
		
		Collections.sort(questionsByDistance, new Comparator<Question>() {
			public int compare(Question question, Question question2) {

				return question.getDistance().compareTo(question2.getDistance());
			}
		});	
		
		List<Question> questionsSorted = new ArrayList<Question>();
		List<Question> questionsWithinRange = new ArrayList<Question>();
		
		for(Question question : questionsByDistance) {
			
			if(question.getDistance()<=0.05){
				questionsWithinRange.add(question);
			}
		}
		
		questionsSorted.addAll(questionsByDistance);
		if(questionsWithinRange.size()==0){
			
			if(questionsSorted.size()>=30){
				Collections.sort(questionsSorted, new Comparator<Question>() {
					public int compare(Question question, Question question2) {

						return question.getCreationDate().compareTo(question2.getCreationDate());
					}
				});		
				
				questionsSorted = questionsSorted.subList(0, 30); 
			}
			
		} else{
			
			questionsSorted.addAll(questionsWithinRange);
			if (questionsSorted.size() >= 30) {
				questionsSorted = questionsSorted.subList(0, 30);
			}
		}
							
		List<Question> questionsRearranged = new ArrayList<Question>();		
		List<Question> tempArray = new ArrayList<Question>(questionsSorted);
		for (Question question : questionsSorted) {
			double latitudeOuter = Double.valueOf(question.getLatitude());
			double longitudeOuter = Double.valueOf(question.getLongitude());
			
			List<Question> questionsSameRange = new ArrayList<Question>();			
			for (Iterator<Question> it  = tempArray.iterator(); it.hasNext(); ) {
				
				Question theQuestion = it.next();
				double latitudeInner = Double.valueOf(theQuestion.getLatitude());
				double longitudeInner = Double.valueOf(theQuestion.getLongitude());				
				if(latitudeOuter==latitudeInner && longitudeOuter==longitudeInner){			
					questionsSameRange.add(theQuestion);
					it.remove();
				}				
			}		
			
			questionsRearranged.addAll(RestControllerUtil.rearrange(questionsSameRange));							
		}
		
		
		
	
		String theResult = new JSONSerializer().exclude("*.class").include("answers").serialize("questions", questionsRearranged);		
		
		
	}

	
	
	
	
	
	
	
    //@Test
	public void testCreateBundle () {
		
		QuestionBundle bundle2 = new QuestionBundle();

		//bundle2.setId(106); 

		bundle2.setDescription("GSAS New Student Orientation Series");
		bundle2.setTopic("GSAS New Student Orientation Series");
		bundle2.setFreeBundle(true);
		bundle2.setPermaLink("#GSAS");
		bundle2.setLocation("Kimmel Center, NYU");

		bundle2.setDate(new Date());
		bundle2.setLatitude("40.730097");
		bundle2.setLongitude("-73.997859");
		//Visual theVisual = new Visual();
		//theVisual.setVisualId(2);
		bundle2.setVisual(null);
		bundle2.setVersion("peoplehunt");
		bundle2.setInProgress(true);

		bundle2.setImageURL("http://s3.amazonaws.com/PeopleHunt/nyugraduate.png");
		questionBudleDao.create(bundle2);
		
	}
	
	
	//@Test
	public void testDeleteQuestion () {
		
		
		QuestionBundle questionBundle = questionBudleDao.findById(34);
		
		// Create a copy to prevent concurrent modification exception
		List<Question> questions = new ArrayList<Question>();
		Collections.copy(questionBundle.getQuestions(), questions);
		
		//It's deleting the user in the view with jquery but the question has not been deleted in the database		
		for(Question question:  questions) {
			Profile foundProfile = null;
			for(Profile profile:question.getProfiles()) {
				if (profile.getId() == 67) {
					foundProfile = profile;
				}
			}
			if (foundProfile != null) {
			questionBundle.removeQuestion(question);
				questionService.deleteQuestion(question);
				
			}
		}
	
	
		/*
		Question question = questionService.findById(2450);
		
			Iterator<Answer> theAnswers = question.getAnswers().iterator();
			 while (theAnswers.hasNext()) {
				Answer theAnswer = theAnswers.next();
				if (theAnswer.getId().equals(15069)) {
					theAnswers.remove();
				}				
			}
			//question.removeAnswer(answerToDelete);
			questionService.saveQuestion(question);
		
		*/
	}	
	
}
