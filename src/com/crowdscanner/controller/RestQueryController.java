package com.crowdscanner.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crowdscanner.controller.utils.RestUtils;
import com.crowdscanner.model.Match;
import com.crowdscanner.model.MatchQuestions;
import com.crowdscanner.model.ProfileBuffer;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;

import flexjson.JSONSerializer;

@Controller
public class RestQueryController {
	
	
	final static Logger logger = Logger.getLogger(RestRequestControllerIphone.class);	
	
	@Resource
	private QuestionDao questionService;	
	
	@Resource
	private UserDao userService;
	
	@Resource
	private RestUtils restUtil;

	
	@RequestMapping(value="/findbyquestionpid/{questionPhoneId}", method=RequestMethod.GET)
	public void retrieveUserByQuestionPhId(@PathVariable(value="questionPhoneId") Integer questionPhoneId, HttpServletResponse response) {
		
		
		try {
			
			List<Question> theQuestions = questionService.findQuestionWithPhoneId(questionPhoneId);			
			List<Map<String,Object>> objects = new ArrayList<Map<String,Object>>();
			for (Question question : theQuestions) {				
				if (!question.getParent()){
					
					Map<String, Object> theMap = new HashMap<String, Object>();
					Profile theProfile = question.getProfiles().get(0);
					theMap.put("email", theProfile.getUser().getUserName());
					theMap.put("name", theProfile.getUser().getLastName());
					theMap.put("question", question.getQuestionDescription());
					theMap.put("answer", question.getSelectedAnswer());
					theMap.put("image_url", RestUtils.extractImageUrl(question, "http://images.crowdscanner.com/anon_nosmile.png"));					
					
					objects.add(theMap);
				}
			}
					
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(objects);			
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
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		  
	}
	
	
	
	@RequestMapping(value="/bubblebyuser/{questionBundleId}", method=RequestMethod.GET)
	public void retrieveUserBubbleGraphByUser(@PathVariable(value="questionBundleId") Integer questionBundleId, HttpServletResponse response) {
		
		try {
			
			QuestionBundle theBundle = questionService.retrieveQuestionBundle(questionBundleId);
			List<Question> theQuestions   = theBundle.getQuestions();
			Map <Integer, ProfileBuffer> mapProfile = new HashMap<Integer, ProfileBuffer>();
			for (Question question : theQuestions) {
				if (!question.getParent()) {					
					restUtil.setProfileBuffer(mapProfile, question);
				}				
			}	
			
			for (ProfileBuffer bufferUp: mapProfile.values()){		
				
				for (ProfileBuffer bufferDown: mapProfile.values()){ 				
					if (!bufferUp.getProfileId().equals(bufferDown.getProfileId())){	
						
						for (Question questionUp: bufferUp.getQuestions()){						
							for (Question questionDown: bufferDown.getQuestions()){							
								if (questionDown.getQuestionPhoneId().equals(questionUp.getQuestionPhoneId())) {
									
									Map<Integer, MatchQuestions> questionMap = bufferUp.getMatchedQuestions();
									if (questionDown.getSelectedAnswer().equals(questionUp.getSelectedAnswer())){							
										
										if (questionMap.containsKey(questionUp.getQuestionPhoneId())){										
											MatchQuestions match = questionMap.get(questionUp.getQuestionPhoneId());
											match.setNumberMatch(match.getNumberMatch() + 1);
											bufferUp.getMatchedQuestions().put(questionUp.getQuestionPhoneId(), match);								
											
										} else {											
											MatchQuestions match = new MatchQuestions();
											match.setNumberMatch(1);
											match.setQuestionPhoneId(questionUp.getQuestionPhoneId());
											match.setQuestionDescription(questionUp.getQuestionDescription());			
											match.setSelectedAnswer(questionUp.getSelectedAnswer());
											bufferUp.getMatchedQuestions().put(questionUp.getQuestionPhoneId(), match);	
										}
									} else if (!questionMap.containsKey(questionUp.getQuestionPhoneId())) {
										MatchQuestions match = new MatchQuestions();
										match.setNumberMatch(0);
										match.setQuestionPhoneId(questionUp.getQuestionPhoneId());
										match.setQuestionDescription(questionUp.getQuestionDescription());	
										match.setSelectedAnswer(questionUp.getSelectedAnswer());
										bufferUp.getMatchedQuestions().put(questionUp.getQuestionPhoneId(), match);	
									}									
								}
							}
						}
					}					
				}			
			}			
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(mapProfile.values());			
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
				logger.info("retrieveUserBubbleGraphByUser User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/bubblebyanswers/{questionBundleId}", method=RequestMethod.GET)
	public void retrieveBubbleAnswers(@PathVariable(value="questionBundleId") Integer questionBundleId, HttpServletResponse response) {
		
		
		try {
				
			QuestionBundle theBundle = questionService.retrieveQuestionBundle(questionBundleId);
			List<Question> theQuestions = theBundle.getQuestions();
			List<Question> firstQuestions = new ArrayList<Question>();
			Integer questionPhoneId = theQuestions.get(0).getQuestionPhoneId();
			for (Question question : theQuestions) {
				if (questionPhoneId.equals(question.getQuestionPhoneId())) {
					firstQuestions.add(question);
				}
			}
			
			Map <Integer, ProfileBuffer> mapProfile = new HashMap<Integer, ProfileBuffer>();
			List<MatchAnswer> allAnswers = new ArrayList<MatchAnswer>();
			processBubbleMatch(theQuestions, mapProfile, allAnswers);				
			
			Map<String, Object> resMap = new HashMap<String,Object>();
			Map<String, String> questions = new HashMap<String, String>();
			resMap.put("totalusers", mapProfile.size());		

			/*
			Question firstQuestion = theQuestions.get(0);
			for(Iterator<MatchAnswer> it = allAnswers.iterator(); it.hasNext();){
				MatchAnswer theAnswer = it.next();
					if (!theAnswer.getQuestionPhoneId().equals(firstQuestion.getQuestionPhoneId())) {
						it.remove();
				}				
			}
			*/
			for (Question aQuestion : theQuestions) {
				if (aQuestion.getParent()) {
					questions.put(String.valueOf(aQuestion.getQuestionPhoneId()), aQuestion.getQuestionDescription());
				}
			}
			
			resMap.put("questions", questions);
			resMap.put("allanswers", allAnswers);
			String theResult = new JSONSerializer().exclude("*.class").serialize(resMap);			
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
					logger.info("retrieveBubbleAnswers User Ex", e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}			
	}

	
	
	@RequestMapping(value="/bubblebyanswersbyquestion/{questionPhoneId}", method=RequestMethod.GET)
	public void retrieveBubbleAnswersByQuestion(@PathVariable(value="questionPhoneId") Integer questionPhoneId, HttpServletResponse response) {
		
		
		try {
				
			List<Question> theQuestions = questionService.findQuestionWithPhoneId(questionPhoneId);			
			Map <Integer, ProfileBuffer> mapProfile = new HashMap<Integer, ProfileBuffer>();
			List<MatchAnswer> allAnswers = new ArrayList<MatchAnswer>();
			processBubbleMatch(theQuestions, mapProfile, allAnswers);				
			
			Map<String, Object> resMap = new HashMap<String,Object>();
			resMap.put("totalusers", mapProfile.size());
			resMap.put("allanswers", allAnswers);
			
			Map<String, String> questions = new LinkedHashMap<String, String>();
			List<Question> parentQuestions = questionService.retrieveParentQuestions(theQuestions.get(0).getQuestionBundle().getId());
			Collections.sort(parentQuestions, new Comparator<Question>() {
				public int compare(Question question, Question question2) {
					return question2.getQuestionPhoneId().compareTo(question.getQuestionPhoneId());
				}
			});	
			
			for (Question aQuestion : parentQuestions) {				
					questions.put(String.valueOf(aQuestion.getQuestionPhoneId()), aQuestion.getQuestionDescription());				
			}
			
			resMap.put("questions", questions);			
			String theResult = new JSONSerializer().exclude("*.class").serialize(resMap);			
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
					logger.info("retrieveBubbleAnswers User Ex", e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}			
	}
	
	
	
	@RequestMapping(value="/bubblebyanswersdev/{questionBundleId}", method=RequestMethod.GET)
	public void retrieveBubbleAnswersDEV(@PathVariable(value="questionBundleId") Integer questionBundleId, HttpServletResponse response) {
		
		
		try {
				
			QuestionBundle theBundle = questionService.retrieveQuestionBundle(questionBundleId);
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

			/*
			Question firstQuestion = theQuestions.get(0);
			for(Iterator<MatchAnswer> it = allAnswers.iterator(); it.hasNext();){
				MatchAnswer theAnswer = it.next();
					if (!theAnswer.getQuestionPhoneId().equals(firstQuestion.getQuestionPhoneId())) {
						it.remove();
				}				
			}
			*/
			for (Question aQuestion : theQuestions) {
				if (aQuestion.getParent()) {
					questions.put(String.valueOf(aQuestion.getQuestionPhoneId()), aQuestion.getQuestionDescription());
				}
			}
			
			resMap.put("questions", questions);
			resMap.put("allanswers", allAnswers);
			String theResult = new JSONSerializer().exclude("*.class").serialize(resMap);			
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
					logger.info("retrieveBubbleAnswers User Ex", e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}			
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
						theMatch.setUsername(profileBuffer.getTwitterHandle());
						matchAnswer.getMatchProfiles().add(theMatch);
					}
				}						
			}					
		}
	}
}


	
