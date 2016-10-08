package com.crowdscanner.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crowdscanner.controller.utils.RestUtils;
import com.crowdscanner.model.ProfileBuffer;
import com.crowdscanner.model.SpiderWebMatch;
import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.FileDao;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Controller
public class RestGraphController {

	final static Logger logger = Logger.getLogger(RestRequestControllerIphone.class);
	
	@Resource
	private QuestionDao questionService;
	
	@Autowired
	private UserDao userService;	
	
	@Resource
	private FileDao fileService;
	
	
	@RequestMapping(value="/spidergraph/{username}/{bundleId}", method=RequestMethod.GET)
	public void processSpiderGraph(@PathVariable(value="username") String username,
			@PathVariable(value="bundleId") Integer bundleId, HttpServletResponse response) { 	
		
		try {			
			
			List<Question> userQuestions = questionService.retrieveUserBundle(username, bundleId);		
			HashMap<Integer, ProfileBuffer> profileBuffer = new HashMap<Integer, ProfileBuffer>(); 
			HashMap<Integer, SpiderWebMatch> matches = new HashMap<Integer, SpiderWebMatch>();
			for (Question question : userQuestions) {
			
				Integer questionId = question.getQuestionPhoneId();			
				String theAnswer = question.getSelectedAnswer();				
				List<Question> theQuestions = questionService.findQuestionWithPhoneId(questionId);
				
				for (Question question2 : theQuestions) {			
					
					Profile theProfile = question2.getProfiles().get(0);
					Integer profileId = addProfileBuffer(profileBuffer, question2, theProfile);
					
					ProfileBuffer buffer = profileBuffer.get(profileId);
					String profileName =  buffer.getName();
					String userName =  buffer.getUserName();
					String theFile = buffer.getUrlImage();			
					
					if (!question2.getParent()) {
						for (Answer answer: question2.getAnswers()) {						
							if (theAnswer.equals(answer.getTextualAnswer()) && answer.getAnswerNumber() == 1) {		
								
								addSpiderMatch(matches, question2, profileId, profileName, userName, theFile, answer);
							}	
						}						
					}
				}					
			}
			
			List<SpiderWebMatch> spiderNodes = new ArrayList<SpiderWebMatch>(matches.values());
			swapList(username, spiderNodes);
					
			String theResult = new JSONSerializer().exclude("*.class").serialize(spiderNodes);			
			response.setHeader("Content-Encoding", "gzip");
			OutputStream out = response.getOutputStream();
			GZIPOutputStream theGzip = new GZIPOutputStream(out);
			theGzip.write(theResult.getBytes());			
		
			theGzip.flush();
			theGzip.close();
			out.close();			
		
		} catch (Exception e) {
			
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("processSpiderGraph User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}				
	}


	

	
	@RequestMapping(value="/matchgraphcards/", method=RequestMethod.POST)
	public void processSpiderGraphFull(@RequestParam(value="username") String username, @RequestParam(value="name", required= true ) String name,  @RequestParam(value="uid", required= true ) String uid, 
			@RequestParam(value="password", required= true ) String password, @RequestParam(value="jsondata", required= true ) String jsondata,  
			@RequestParam(value="savebatch", required= true ) Boolean saveBatch, HttpServletResponse response) { 	
		
		try {			
			
			logger.info("Received Json "+jsondata);
			HashMap<String, Object> theMapResult =	new JSONDeserializer<HashMap<String, Object>>().deserialize(URLDecoder.decode(jsondata, "UTF-8"));			
			Integer bundleId = (Integer) theMapResult.get("bundleRemoteId");
			
			List<HashMap<String, Object>> questionsMap = (List<HashMap<String, Object>>) theMapResult.get("questions");		
			List<Question> questionsToSave = new ArrayList<Question>();			
			HashMap<Integer, ProfileBuffer> profileBuffer = new HashMap<Integer, ProfileBuffer>(); 
			HashMap<Integer, SpiderWebMatch> matches = new HashMap<Integer, SpiderWebMatch>();
			boolean sendMailNotFinal = false;		
			User user = null;
			com.myownmotivator.model.profile.File savedFile = fileService.retrieveFileByName("profilePhoto_"+uid);		
			if (StringUtils.contains(username, "facebook_" )) {		
				user = userService.getByFacebookUserName(username);
				if (user == null) {
					//name like accessToken for now
					//String acessToken = StringUtils.remove(username, "facebook_");
					User facebookuser = RestUtils.createFacebookUser(name, username);	
					user = userService.saveUser(facebookuser);
					sendMailNotFinal = true;
				}
			} else { 				
				
				user = userService.getByUserName(username);			
				if (user != null) {				
						if (savedFile != null) {
							savedFile.setFileName("profilePhoto");
							user.getProfile().getFiles().add(savedFile);	
						}
						user = userService.updateUser(user);							
										
				} else {					
						
					User theUser = RestUtils.setUser(name, username, null, password);
					if (savedFile != null) {
						savedFile.setFileName("profilePhoto");
						theUser.getProfile().getFiles().add(savedFile);	
					}
					user = userService.saveUser(theUser);	
					sendMailNotFinal = true;
				}		
			}			     
			
			
			for (HashMap<String, Object> question : questionsMap) {
			
				Integer questionId = (Integer) question.get("questionPhoneId");
				HashMap<String, Object> answerData = (HashMap<String, Object>) question.get("answer_data");					
				String theAnswer = (String)answerData.get("answerDescription");					
				List<Question> theQuestions = questionService.findQuestionWithPhoneId(questionId);
				questionsToSave.add(processQuestion(bundleId, theAnswer, theQuestions, user));
				for (Question question2 : theQuestions) {			
					
					Profile theProfile = question2.getProfiles().get(0);
					Integer profileId = addProfileBuffer(profileBuffer,question2, theProfile);
					
					ProfileBuffer buffer = profileBuffer.get(profileId);
					String profileName =  buffer.getName();
					String userName =  buffer.getUserName();
					String theFile = buffer.getUrlImage();			
					
					if (!question2.getParent()) {
						for (Answer answer: question2.getAnswers()) {								
							if (theAnswer.equals(answer.getTextualAnswer()) && answer.getAnswerNumber() == 1) {		
								
								addSpiderMatch(matches, question2, profileId, profileName, userName, theFile, answer);
							}	else if (!matches.containsKey(profileId)){
								
								SpiderWebMatch theMatch = new SpiderWebMatch();									
								theMatch.setUrl(theFile);
								theMatch.setName(profileName);						  
								theMatch.setEmail(userName);
								matches.put(profileId, theMatch);							
							}
							
							if (answer.getAnswerNumber() == 1) {
								if (matches.containsKey(profileId)) {
									SpiderWebMatch theMatch = matches.get(profileId);								
									Map<Integer, String> selectedAnswers = new HashMap<Integer, String>();
									selectedAnswers.put(question2.getQuestionPhoneId(), answer.getURLEncodedAnswer());
									theMatch.getSelectedAnswers().add(selectedAnswers);							
									matches.put(profileId, theMatch);
								}
							}
						}						
					}
				}					
			}
			
			if (saveBatch) {		
				questionService.saveBatchQuestions(questionsToSave);
			}	
			
			List<SpiderWebMatch> spiderNodes = new ArrayList<SpiderWebMatch>(matches.values());
			swapList(username, spiderNodes);
					
			String theResult = new JSONSerializer().exclude("*.class").serialize(spiderNodes);			
			response.setHeader("Content-Encoding", "gzip");
			OutputStream out = response.getOutputStream();
			GZIPOutputStream theGzip = new GZIPOutputStream(out);
			theGzip.write(theResult.getBytes());			
		
			theGzip.flush();
			theGzip.close();
			out.close();			
		
		} catch (Exception e) {
			
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("processSpiderGraph User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}				
	}



	private Integer addProfileBuffer(	HashMap<Integer, ProfileBuffer> profileBuffer, Question question2, Profile theProfile) {
		
		Integer profileId = theProfile.getId();			
		if (!profileBuffer.containsKey(profileId)){	
			
			ProfileBuffer theBuffer = new ProfileBuffer();
			theBuffer.setName(theProfile.getUser().getLastName());
			theBuffer.setUserName(theProfile.getUser().getUserName());
			theBuffer.setUrlImage(RestUtils.extractImageUrl(question2, "http://images.crowdscanner.com/anon_nosmile.png"));
			profileBuffer.put(profileId, theBuffer);
		}
		return profileId;
	}
	
	
	
	private void addSpiderMatch(HashMap<Integer, SpiderWebMatch> matches, Question question2, Integer profileId, String profileName,
			String userName, String theFile, Answer answer) {

		if (matches.containsKey(profileId)) {
				SpiderWebMatch theMatch = matches.get(profileId);									
				theMatch.setName(profileName);
				Map<Integer, String> answerMap = new HashMap<Integer, String>();
				answerMap.put(question2.getQuestionPhoneId(), answer.getURLEncodedAnswer());
				theMatch.getMatchingAnswers().add(answerMap);							
				matches.put(profileId, theMatch);
			
			} else {
				SpiderWebMatch theMatch = new SpiderWebMatch();									
				theMatch.setUrl(theFile);
				theMatch.setName(profileName);
				Map<Integer, String> answerMap = new HashMap<Integer, String>();
				answerMap.put(question2.getQuestionPhoneId(), answer.getURLEncodedAnswer());
				theMatch.getMatchingAnswers().add(answerMap);
				theMatch.setEmail(userName);
				matches.put(profileId, theMatch);							
		}
	}	
	
	
	
	private Question processQuestion(Integer bundleId, String theAnswer, List<Question> theQuestions, User savedUser) {
		
		QuestionBundle theBundle = questionService.retrieveQuestionBundle(bundleId);		
		return RestUtils.setTheQuestion(theAnswer, theQuestions, savedUser, theBundle);
	}

	private void swapList(String username, List<SpiderWebMatch> spiderNodes) {
		
		int seachIndex = 0;
		for (SpiderWebMatch spiderWebMatch : spiderNodes) {
			if (spiderWebMatch.getEmail().equals(username)) {
				seachIndex = spiderNodes.indexOf(spiderWebMatch);
			}
		}	
		
		Collections.swap(spiderNodes, 0, seachIndex);
	}	
}
