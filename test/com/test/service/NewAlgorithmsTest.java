package com.test.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.crowdscanner.controller.MatchAnswer;
import com.crowdscanner.controller.utils.RestUtils;
import com.crowdscanner.model.Match;
import com.crowdscanner.model.ProfileBuffer;
import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.crowdmodule.QuestionBudleDao;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;

import flexjson.JSONSerializer;

@RunWith(SpringJUnit4ClassRunner.class)
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations={"ApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class NewAlgorithmsTest {

	
	@Autowired
	private QuestionDao questionService;
	
	
	@Autowired
	private UserDao userService;
	
	@Resource
	private RestUtils restUtil;
	
	@Autowired
	private QuestionBudleDao questionBudleDao;
	
	//@Test
	public void testNewPerma () {
		
		String visual = null;
		QuestionBundle questionBundle = questionBudleDao.findByPermalink("iphone-development-galway");
		if (questionBundle.getVisual().getName().equals("Bubbles")) {
				
			QuestionBundle theBundle = questionService.retrieveQuestionBundle(questionBundle.getId());
			List<Question> theQuestions = theBundle.getQuestions();
			Map <Integer, ProfileBuffer> mapProfile = new HashMap<Integer, ProfileBuffer>();
			List<MatchAnswer> allAnswers = new ArrayList<MatchAnswer>();
			try {
				restUtil.addProfileAnswersBubble(theQuestions, mapProfile, allAnswers);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			Set<String> theURLS = new  HashSet<String>();
			
			for (MatchAnswer matchAnswer : allAnswers){					
				for (ProfileBuffer profileBuffer : mapProfile.values()){
					
					for (Question theQuestion : profileBuffer.getQuestions()){
						if (theQuestion.getSelectedAnswer().equals(matchAnswer.getTextualAnswer())){							
							theURLS.add(profileBuffer.getUrlImage());														
						}
					}						
				}					
			}			
			String preloadImages = String.format("/* @pjs preload=\"%1$s\"; */", new Object[]{StringUtils.join(theURLS, ",")});
			System.out.println(preloadImages);
			
			visual = "visual/bubbles";	
				
					
		} else if (questionBundle.getVisual().getName().equals("mindfield")){			
			visual = "visual/mindfield";
			
		} else {
			visual = "visual/by_user";
		}		
		
	}
	
	
	
	
	
	
	 public static String forJSON(String aText){
		    final StringBuilder result = new StringBuilder();
		    StringCharacterIterator iterator = new StringCharacterIterator(aText);
		    char character = iterator.current();
		    while (character != StringCharacterIterator.DONE){
		      if( character == '\"' ){
		        result.append("\\\"");
		      }
		      else if(character == '\\'){
		        result.append("\\\\");
		      }		     
		      else if(character == '\b'){
		        result.append("\\b");
		      }
		      else if(character == '\f'){
		        result.append("\\f");
		      }
		      else if(character == '\n'){
		        result.append("\\n");
		      }
		      else if(character == '\r'){
		        result.append("\\r");
		      }
		      else if(character == '\t'){
		        result.append("\\t");
		      }
		      else {
		        //the char is not a special one
		        //add it to the result as is
		        result.append(character);
		      }
		      character = iterator.next();
		    }
		    return result.toString();    
		  }
	
	
	
	//@Test
	public void testQuestionMatch () throws UnsupportedEncodingException { 
		
		
		String stringAnswer = new String("It%27s a %25^%26*%28 questinon%2F|%2Fto thin");
		stringAnswer = URLDecoder.decode(stringAnswer, "UTF-8");
		List<Question> questions = questionService.findQuestionWithPhoneId(99009882);		
		Question questionTemplate = new Question();
		Map<String, Answer> answersMap = new HashMap<String, Answer>();	
		stringAnswer = stringAnswer.trim().replaceAll("\\s", "");
		//stringAnswer = stringAnswer.replaceAll("\\\\+", "\\\\+");
		//System.out.println("the answer "+stringAnswer);
		
		if (!questions.isEmpty()) {			
			
			Question tempQuestion = questions.get(0);						
			questionTemplate.setQuestionDescription(tempQuestion.getQuestionDescription());			
			for (Question question: questions) {				
						
				// process the answers overall aggregated
				if (!question.getParent()) {
					List<Answer> answers = question.getAnswers();			
					for (Answer answer : answers) {
						
						String theKey = answer.getTextualAnswer().trim().replaceAll("\\s", "");
						//theKey = theKey.replaceAll("\\\\+", "/");
						//System.out.println(theKey +" and "+ stringAnswer);
						
						if (StringUtils.equals(stringAnswer, theKey)) {
							
							System.out.println("it is equal");						
						}
						
						if (answersMap.containsKey(theKey) && answer.getAnswerNumber() == 1) {
							
							Answer theAnswerObject = (Answer)answersMap.get(theKey);
							int accumulated = theAnswerObject.getAnswerNumber() + 1;
							theAnswerObject.setAnswerNumber(accumulated);
							answersMap.put(theKey, theAnswerObject);				
						}
						else if (theKey.equals(stringAnswer) && answer.getAnswerNumber() == 1) {
							
							System.out.println("insert key "+theKey);
							Answer insertAnswer = new Answer();
							insertAnswer.setTextualAnswer(answer.getTextualAnswer());
							insertAnswer.setAnswerNumber(answer.getAnswerNumber());
							answersMap.put(theKey, insertAnswer);
						}
					}
				}
			}							
		}
		
		
		
		
		int totalAnsweredQuestions = questions.size() - 1;
		float timesAnswered = 0;
		if(answersMap.containsKey(stringAnswer)) {
			timesAnswered = answersMap.get(stringAnswer).getAnswerNumber() *100;
		}			
		float res = timesAnswered / totalAnsweredQuestions;
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("percentage", res);
		
		String theResult = new JSONSerializer().serialize(results);		
		System.out.println(theResult);		
		
		
	}
	
	
	
	
	//@Test
	public void testBubbleAnswer () {
		
		QuestionBundle theBundle = questionService.retrieveQuestionBundle(12);
		List<Question> theQuestions = theBundle.getQuestions();
		Map <Integer, ProfileBuffer> mapProfile = new HashMap<Integer, ProfileBuffer>();
		List<MatchAnswer> allAnswers = new ArrayList<MatchAnswer>();
		for (Question question : theQuestions) {
			if (question.getParent()) {				
				for (Answer anAnswer: question.getAnswers()) {
					MatchAnswer matchAnswer = new MatchAnswer();
					matchAnswer.setQuestionPhoneId(question.getQuestionPhoneId());
					matchAnswer.setTextualAnswer(anAnswer.getTextualAnswer());
					allAnswers.add(matchAnswer);
				}
			}				
		}	
			
		for (Question question : theQuestions) {
			if (!question.getParent()) {					
				Profile theProfile = question.getProfiles().get(0);				
				if (!mapProfile.containsKey(theProfile.getId())){
					
					User theUser = theProfile.getUser();
					ProfileBuffer buffer = new ProfileBuffer();
					buffer.setName(theUser.getLastName());
					buffer.setUserName(theUser.getEmail());
					buffer.setProfileId(theProfile.getId());
					String theUrl = RestUtils.extractURLProfile(theProfile, "http://images.crowdscanner.com/anon_nosmile.png");
					buffer.setUrlImage(theUrl);
					mapProfile.put(theProfile.getId(), buffer);
				}
				
				ProfileBuffer theBuffer = mapProfile.get(theProfile.getId());
				Question theQuestion = new Question();
				theQuestion.setQuestionPhoneId(question.getQuestionPhoneId());				
				theQuestion.setSelectedAnswer(question.getSelectedAnswer());
				theQuestion.setQuestionDescription(question.getQuestionDescription());
				theBuffer.getQuestions().add(theQuestion);		
				mapProfile.put(theProfile.getId(), theBuffer);
			}				
		}	
		
		
		for (MatchAnswer matchAnswer : allAnswers){					
			for (ProfileBuffer profileBuffer : mapProfile.values()){
				
				for (Question theQuestion : profileBuffer.getQuestions()){
					if (theQuestion.getSelectedAnswer().equals(matchAnswer.getTextualAnswer())){
						
						Match theMatch =new Match();
						theMatch.setUrl(profileBuffer.getUrlImage());								
						theMatch.setName(profileBuffer.getName());
						theMatch.setProfileId(profileBuffer.getProfileId());
						theMatch.setEmail(profileBuffer.getUserName());
						matchAnswer.getMatchProfiles().add(theMatch);
					}
				}						
			}					
		}
		
		
		Map<String, Object> resMap = new HashMap<String,Object>();
		Map<String, String> questions = new HashMap<String, String>();
		resMap.put("totalusers", mapProfile.size());		

		Question firstQuestion = theQuestions.get(0);
		for(Iterator<MatchAnswer> it = allAnswers.iterator(); it.hasNext();){
			MatchAnswer theAnswer = it.next();
				if (!theAnswer.getQuestionPhoneId().equals(firstQuestion.getQuestionPhoneId())) {
					it.remove();
			}				
		}
		
		for (Question aQuestion : theQuestions) {
			if (aQuestion.getParent()) {
				questions.put(String.valueOf(aQuestion.getQuestionPhoneId()), aQuestion.getQuestionDescription());
			}
		}
		
		resMap.put("questions", questions);
		resMap.put("allanswers", allAnswers);
		String theResult = new JSONSerializer().exclude("*.class").serialize(resMap);
		System.out.println(theResult);
				
	}
	
	
	//@Test
	public void testBubbleUser() throws UnsupportedEncodingException { 
		
		QuestionBundle theBundle = questionService.retrieveQuestionBundle(14);
		List<Question> theQuestions = theBundle.getQuestions();
		Collections.sort(theQuestions, new Comparator<Question>() {
			public int compare(Question question, Question question2) {
				return question2.getQuestionPhoneId().compareTo(question.getQuestionPhoneId());
			}
		});		
		
		List<Question> firstQuestions = new ArrayList<Question>();
		Integer questionPhoneId = theQuestions.get(0).getQuestionPhoneId();
		for (Question question : theQuestions) {
			if (questionPhoneId.equals(question.getQuestionPhoneId())) {
				firstQuestions.add(question);
			}
		}
		
		Map <Integer, ProfileBuffer> mapProfile = new HashMap<Integer, ProfileBuffer>();
		List<MatchAnswer> allAnswers = new ArrayList<MatchAnswer>();
		processBubbleMatch(firstQuestions, mapProfile, allAnswers);				
		
		Map<String, Object> resMap = new HashMap<String,Object>();
		Map<String, String> questions = new LinkedHashMap<String, String>();
		resMap.put("totalusers", mapProfile.size());		

	
		for (Question aQuestion : theQuestions) {
			if (aQuestion.getParent()) {
				questions.put(String.valueOf(aQuestion.getQuestionPhoneId()), aQuestion.getQuestionDescription());
			}
		}
		
		resMap.put("questions", questions);
		resMap.put("allanswers", allAnswers);
		String theResult = new JSONSerializer().exclude("*.class").serialize(resMap);				
		
		System.out.println(theResult);
		
	}	
	
	
	
	private void processBubbleMatch(List<Question> theQuestions,  Map<Integer, ProfileBuffer> mapProfile, List<MatchAnswer> allAnswers)
				throws UnsupportedEncodingException {

		restUtil.addProfileAnswersBubble(theQuestions, mapProfile, allAnswers);		
		
		for (MatchAnswer matchAnswer : allAnswers){					
			for (ProfileBuffer profileBuffer : mapProfile.values()){
				
				for (Question theQuestion : profileBuffer.getQuestions()){
					if (theQuestion.getSelectedAnswer().equals(matchAnswer.getTextualAnswer())){
						
						Match theMatch =new Match();
						theMatch.setUrl(profileBuffer.getUrlImage());								
						theMatch.setName(profileBuffer.getName());
						theMatch.setProfileId(profileBuffer.getProfileId());
						theMatch.setEmail(profileBuffer.getUserName());
						matchAnswer.getMatchProfiles().add(theMatch);
					}
				}						
			}					
		}
}
	
	//@Test
	public void testDeleteQuestion() { 
		
		questionService.deleteQuestion(2097);
		questionService.deleteQuestion(2098);
		questionService.deleteQuestion(2099);
		questionService.deleteQuestion(2100);
		questionService.deleteQuestion(2101);
		questionService.deleteQuestion(2102);
		questionService.deleteQuestion(2103);
		questionService.deleteQuestion(2104);
		questionService.deleteQuestion(2105);
		questionService.deleteQuestion(2106);
		
	} 
	
	//@Test
	public void testSHAI() {		
	
	    try {
	    	
	    		String theInput ="c5d02900c2ed43e0015d5e6792e2071a7b20afd5.mysecret";
	    	 	byte[] input = theInput.getBytes();
	    	    System.out.println("input     : " + new String(input));    
	    	    MessageDigest hash = MessageDigest.getInstance("SHA1");
	    	    hash.update(input);
	    	    byte[] output = hash.digest();
	    	    
	    	    System.out.println("   "+ bytesToHex(output)); 
	    	    System.out.println("   "+ StringUtils.lowerCase(bytesToHex(output)));    	 
	    	    
			}  catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	
	
	public static String bytesToHex(byte[] b) {
	      char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
	                         '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	      StringBuffer buf = new StringBuffer();
	      for (int j=0; j<b.length; j++) {
	         buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
	         buf.append(hexDigit[b[j] & 0x0f]);
	      }
	      return buf.toString();
	   }
	
	//@Test
	public void testPreloadIMages () {
		
		QuestionBundle theBundle = questionService.retrieveQuestionBundle(23);
		List<Question> theQuestions = theBundle.getQuestions();
		Map <Integer, ProfileBuffer> mapProfile = new HashMap<Integer, ProfileBuffer>();
		List<MatchAnswer> allAnswers = new ArrayList<MatchAnswer>();
		try {
			restUtil.addProfileAnswersBubble(theQuestions, mapProfile, allAnswers);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		List<String> theURLS = new  ArrayList<String>();
		
		for (MatchAnswer matchAnswer : allAnswers){					
			for (ProfileBuffer profileBuffer : mapProfile.values()){
				
				for (Question theQuestion : profileBuffer.getQuestions()){
					if (theQuestion.getSelectedAnswer().equals(matchAnswer.getTextualAnswer())){							
						theURLS.add(profileBuffer.getUrlImage());														
					}
				}						
			}					
		}			
		String preloadImages = String.format("/* @pjs preload=\"%1$s\"; */", new Object[]{StringUtils.join(theURLS, ",")});
		System.out.println(preloadImages);
		
		
	}
	
	
}
