package com.crowdscanner.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
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

import com.crowdscanner.controller.utils.MatchingUtils;
import com.crowdscanner.controller.utils.RestUtils;
import com.crowdscanner.model.Match;
import com.crowdscanner.model.ProfileBuffer;
import com.myownmotivator.model.User;
import com.myownmotivator.model.podcasts.Podcast;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.FileDao;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Controller
public class RestMatchController {

	final static Logger logger = Logger.getLogger(RestMatchController.class);
	
	@Resource
	private QuestionDao questionService;
	
	@Autowired
	private UserDao userService;	
	
	
	
	@Resource
	private FileDao fileService;
	
	@Resource
	private RestUtils restUtil;
	
	
	
	@RequestMapping(value="/retrievebatchquestions/", method=RequestMethod.GET)
	public void  processQuestionMatch(HttpServletResponse response) { 	
		
		
		try {
			
			List<QuestionBundle> bundleArray = questionService.retrieveAllBundles();
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(bundleArray);
			
			response.setContentType("text/plain; charset=UTF-8");
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
				logger.info("retrieveilikegraph User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
}	
	
	
	
	
	@RequestMapping(value="/retrievebundleavatars/{bundleId}", method=RequestMethod.GET)
	public void retrieveQuestionAvatar(@PathVariable(value="bundleId") Integer bundleId, HttpServletResponse response) { 	
		
		try {
				
			QuestionBundle theBundle = questionService.retrieveQuestionBundle(bundleId);
			List<Question> theQuestions = theBundle.getQuestions();
			Map <Integer, String> mapProfile = new HashMap<Integer, String>();
			for (Question question : theQuestions) {
				if (!question.getParent()) {					
					Profile theProfile = question.getProfiles().get(0);				
					if (!mapProfile.containsKey(theProfile.getId())){
						
						String theUrl = RestUtils.extractImageUrl(question, "http://images.crowdscanner.com/anon_nosmile.png");
						mapProfile.put(theProfile.getId(), theUrl);
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
				logger.info("processQuestionAlgorithm User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}
	
	
	@RequestMapping(value="/processminimalquestionsalghtml/{username}/{bundleId}", method=RequestMethod.GET	)
	public void processQuestionMinimalAlgorithmHtml(@PathVariable(value="username") String username,
			@PathVariable(value="bundleId") Integer bundleId, HttpServletResponse response) { 	
		
		try {
			
			HashMap<Integer, Match> matches = new HashMap<Integer, Match>();
			HashMap<String, List<Match>> sortMatches = new HashMap<String, List<Match>>();
			
			minAlgoMethod(username, bundleId, matches);				
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(sortMatches);			
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
				logger.info("processQuestionAlgorithm User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		
	}

	
	

	@RequestMapping(value="/processminimalquestionsalg/{username}/{bundleId}", method=RequestMethod.GET	)
	public void processQuestionMinimalAlgorithm(@PathVariable(value="username") String username,
			@PathVariable(value="bundleId") Integer bundleId, HttpServletResponse response) { 	
		
		try {
			
			HashMap<Integer, Match> matches = new HashMap<Integer, Match>();
			HashMap<Integer, List<Match>> sortMatches = new HashMap<Integer, List<Match>>();
			
			minAlgoMethod(username, bundleId, matches);						
			String theResult = new JSONSerializer().exclude("*.class").serialize(sortMatches);			
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
				logger.info("processQuestionAlgorithm User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		
	}





	private void minAlgoMethod(String username, Integer bundleId, HashMap<Integer, Match> matches) {
		
		List<Question> userQuestions = questionService.retrieveUserBundle(username, bundleId);		
		HashMap<Integer, ProfileBuffer> profileBuffer = new HashMap<Integer, ProfileBuffer>(); 
		for (Question question : userQuestions) {
		
			Integer questionId = question.getQuestionPhoneId();			
			String theAnswer = question.getSelectedAnswer();				
			List<Question> theQuestions = questionService.findQuestionWithPhoneId(questionId);
			logger.info("min Alg :::::::: "+question.getId() +" "+theAnswer);
			//questionsToSave.add(processQuestion(bundleId, theAnswer, theQuestions, user));					
			MatchingUtils.matchTheQuestions(matches, theAnswer, theQuestions, profileBuffer);	
		}
	}

	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/processquestionsalg/", method=RequestMethod.POST)
	public void processQuestionAlgorithm(@RequestParam(value="username", required= true ) String username,
			 @RequestParam(value="email", required= false ) final String email, @RequestParam(value="name", required= true ) String name,  @RequestParam(value="uid", required= true ) String uid, @RequestParam(value="password", required= true ) String password,
			 @RequestParam(value="jsondata", required= true ) String jsondata,  @RequestParam(value="savebatch", required= true ) Boolean saveBatch, @RequestParam(value="twitter_image", required= false ) String twitter_image,  HttpServletResponse response) { 	
		
		
		try {
			
			boolean sendMailNotFinal = false;		
			logger.info("Received Json "+jsondata);
			HashMap<String, Object> theMapResult =	new JSONDeserializer<HashMap<String, Object>>().deserialize(URLDecoder.decode(jsondata, "UTF-8"));			
			
			Integer bundleId = (Integer) theMapResult.get("bundleRemoteId");
			List<HashMap<String, Object>> questionsMap = (List<HashMap<String, Object>>) theMapResult.get("questions");
			
			HashMap<Integer, Match> matches = new HashMap<Integer, Match>();
			List<Question> questionsToSave = new ArrayList<Question>();
			
			User user = null;
			File savedFile = fileService.retrieveFileByName("profilePhoto_"+uid);		
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
				
				user = userService.getByUserName(email);			
				if (user != null) {		
					if (user.getRole().equals("provider")) saveBatch = false;
					if(saveBatch) {
						if (savedFile != null) {
							savedFile.setFileName("profilePhoto");
							user.getProfile().getFiles().add(savedFile);	
						}
						user = userService.updateUser(user);							
					}					
				} else {					
						
					User theUser = RestUtils.setUser(name, email, username, password);
					if (savedFile != null) {
						savedFile.setFileName("profilePhoto");
						theUser.getProfile().getFiles().add(savedFile);	
					}
					if (StringUtils.isNotBlank(twitter_image)){
						twitter_image = StringUtils.replace(twitter_image, "_normal", "_bigger");
						File theFile = new File();
						theFile.setFileName("twitterProfilePhoto_url");
						theFile.setFileContent(twitter_image.getBytes());
						theUser.getProfile().getFiles().add(theFile);						
					}
					user = userService.saveUser(theUser);	
					sendMailNotFinal = true;
				}		
			}
			
			HashMap<Integer, ProfileBuffer> profileBuffer = new HashMap<Integer, ProfileBuffer>(); 
			for (HashMap<String, Object> question : questionsMap) {
				
					Integer questionId = (Integer) question.get("questionPhoneId");
					HashMap<String, Object> answerData = (HashMap<String, Object>) question.get("answer_data");					
					String theAnswer = (String)answerData.get("answerDescription");				
					List<Question> theQuestions = questionService.findQuestionWithPhoneId(questionId);
					questionsToSave.add(processQuestion(bundleId, theAnswer, theQuestions, user));					
					MatchingUtils.matchTheQuestions(matches, theAnswer, theQuestions, profileBuffer);	
			}	
			
			Map<String, File> mapFiles = new HashMap<String, File>();
			for (File aFile : user.getProfile().getFiles()) {							
				mapFiles.put(aFile.getFileName(), aFile);
			}
			
			Match selfMatch = new Match();		
			selfMatch.setName(user.getLastName());			
			String theEmail = user.getEmail();
			if (StringUtils.contains(username, "facebook_" )) theEmail = "no_email";
			selfMatch.setEmail(theEmail);
			selfMatch.setUrl(RestUtils.extractFileUrl("http://images.crowdscanner.com/anon_nosmile.png", mapFiles));
			matches.put(user.getProfile().getId(), selfMatch);
			
			HashMap<Integer, List<Match>> sortMatches = new HashMap<Integer, List<Match>>();		
			
			if (saveBatch) {		
				questionService.saveBatchQuestions(questionsToSave);
			}	
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(sortMatches);		
			logger.info("Algorith RES "+ theResult);
			response.setHeader("Content-Encoding", "gzip");
			OutputStream out = response.getOutputStream();
			GZIPOutputStream theGzip = new GZIPOutputStream(out);                                                                                                             
			theGzip.write(theResult.getBytes());			
			
			theGzip.flush();
			theGzip.close();
			out.close();			
			final boolean sendMail = sendMailNotFinal;
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						if (sendMail) {
							sendEmailConfirm("f95fedc6a0",email);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.info("Send emailconfirmation User Ex", e);
					}					
				}				
			}).start();
			
		} catch (Exception e) {
			
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("processQuestionAlgorithm User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}

	
	
	
	


	private Question processQuestion(Integer bundleId, String theAnswer, List<Question> theQuestions, User savedUser) {
		
		QuestionBundle theBundle = questionService.retrieveQuestionBundle(bundleId);		
		return RestUtils.setTheQuestion(theAnswer, theQuestions, savedUser, theBundle);
	}

	
	
	@RequestMapping(value="/computequestionmatch/", method=RequestMethod.POST)
	public void computeQuestionMatchesPost(@RequestParam("questionPhoneId") Integer questionPhoneId, 
			HttpServletResponse response, @RequestParam("stringanswer") String stringAnswer){  	
		
		try {
			
			HashMap<String, Object> results = restUtil.processAnswerStat(questionPhoneId, stringAnswer);			
			String theResult = new JSONSerializer().serialize(results);		
			
			//response.setHeader("Content-Encoding", "gzip");
			OutputStream out = response.getOutputStream();
			out.write(theResult.getBytes());
			
			//GZIPOutputStream theGzip = new GZIPOutputStream(out);
			//theGzip.write(theResult.getBytes());			
			
			//theGzip.flush();
			//theGzip.close();
			out.close();	
		
		} catch (Exception e) {
			
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("retrieveilikegraph User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}

	
	
	
	@RequestMapping(value="/computequestionmatch/{questionPhoneId}/{stringanswer}", method=RequestMethod.GET)
	public void computeQuestionMatchesGet(@PathVariable("questionPhoneId") Integer questionPhoneId, 
			HttpServletResponse response, @PathVariable("stringanswer") String stringAnswer){  		
		
		computeQuestionMatchesPost(questionPhoneId, response, stringAnswer);
	}	
	
	
	private void sendEmailConfirm(String list, String email) throws MalformedURLException,	IOException {
		
		StringBuffer buffer = new StringBuffer();
		String urlString = String.format("http://us2.api.mailchimp.com/1.2/?output=json&method=listSubscribe&apikey=df3cf34c2f48fca87cd9b905c8948dee-us2&id=%1$s&email_address=%2$s&merge_vars=&replace_interests=false&send_welcome=true",
				new Object[]{list, email});
		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());		
		wr.flush();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {

			buffer.append(line);
			buffer.append("\n");
		}
		wr.close();
		rd.close();
	}
	
}
