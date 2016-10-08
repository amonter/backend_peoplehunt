package com.crowdscanner.grouping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.crowdscanner.controller.MongoDbInit;
import com.crowdscanner.controller.avatars.CharacterFactory;
import com.crowdscanner.controller.avatars.CharacterMaker;
import com.crowdscanner.controller.utils.RestUtils;
import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.myownmotivator.model.User;
import com.myownmotivator.model.gaming.HuntRating;
import com.myownmotivator.model.gaming.PeopleHuntId;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.profile.ProfileInfo;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.FileDao;
import com.myownmotivator.service.crowdmodule.QuestionBudleDao;
import com.myownmotivator.service.gaming.PeopleHuntService;
import com.myownmotivator.service.peoplehuntv2.GroupService;
import com.myownmotivator.service.peoplehuntv2.LocationService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;
import com.peoplehuntv2.model.Location;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Controller
public class GamingMatchController {

	
	final static Logger logger = Logger.getLogger(GamingMatchController.class);	
	
	@Resource
	private QuestionDao questionService;
	
	@Autowired
	private QuestionBudleDao questionBudleDao;
	
	@Autowired
	private UserDao userService;			
	
	@Resource
	private FileDao fileService;
	
	@Autowired
	private ProfileService profileService;	
	
	@Autowired
	private PeopleHuntService peopleHuntService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
    private LocationService locationService;
	
	@Autowired
	private MongoDbInit mongoService;
	
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value="/addsimpleprofile/", method=RequestMethod.POST)
	public void addBundleProfile(@RequestParam(value="username", required= true ) String username,
			 @RequestParam(value="email", required= false ) final String email, @RequestParam(value="name", required= true ) String name, @RequestParam(value="password", required= true ) String password,
			 @RequestParam(value="latitude", required= true ) String latitude, @RequestParam(value="longitude", required= true ) String longitude, @RequestParam(value="token", required= false) String token,
			 @RequestParam(value="placemark", required= false) String placemark, @RequestParam(value="myimage", required= true ) String imageUrl,
			 @RequestParam(value="bio", required= false ) String bio,			
			 HttpServletRequest request, HttpServletResponse response) { 	
		
				
		final Gson gson = new Gson();   
		final Map<String, Object> map = new HashMap<String, Object>();		
		Map<String, Object> compoundMap = null;
		
		try {		
			
			Map<String, Object> mapGroups = new HashMap<String, Object>();		
			User user = saveTheUser(username, password, URLDecoder.decode(email, "UTF-8"), null, token, true, name, imageUrl);		
			Profile theProfile = user.getProfile();
			//add mongo interests
			//MongoClient mongoClient = new MongoClient( "54.227.110.249" , 27017 );
			//DB db = mongoClient.getDB( "peoplehunt" );	
			//DBCollection col = db.getCollection("interests");
			
						
			
			final StringBuffer processedContent = new StringBuffer();
			processHttpBody(request, processedContent);
			logger.info("json "+processedContent.toString());
			
			/*if (interests != null) {				
				Map<String, Object> interestData = gson.fromJson(URLDecoder.decode(interests, "UTF-8"), map.getClass());
				compoundMap = new HashMap<String, Object>();
				compoundMap.put("skills", interestData);
			}
			
			if (languages != null) {				
				Map<String, Object> languageData = gson.fromJson(URLDecoder.decode(languages, "UTF-8"), map.getClass());
				if (compoundMap == null) compoundMap = new HashMap<String, Object>();
				compoundMap.put("languages", languageData);
				
			}
			
			logger.info("likes "+likes);
			if (likes != null) {				
				Map<String, Object> likeData = gson.fromJson(URLDecoder.decode(likes, "UTF-8"), map.getClass());
				if (compoundMap == null) compoundMap = new HashMap<String, Object>();
				compoundMap.put("likes", likeData);
				
			}
			
			if (compoundMap != null){
				compoundMap.put("profile_id", theProfile.getId());
				BasicDBObject theObj = new BasicDBObject(compoundMap);
				col.insert(theObj);
			}*/			
			//check location
			List<Location> allLocations = (List<Location>) locationService.getAllLocations();
			Map<String, Object> dataLocation = new HashMap<String, Object>();			
			String [] locationArray = placemark.split(",");
			
			if (locationArray.length > 0){
	            for (final Location location : allLocations) {
	            	
	            	if (StringUtils.contains(location.getLocation(), locationArray[0])) {
		            	dataLocation.put("id", location.getId());
		            	dataLocation.put("location", location.getLocation());         
	            	}            	
	            }					
			}
			
            if (dataLocation.isEmpty()){
            	dataLocation.put("id", -1);
            	dataLocation.put("location", placemark);          	
            }            
			
			theProfile.setSource(latitude+","+longitude);
			theProfile.setLastOnline(Calendar.getInstance().getTime());
			if (!StringUtils.isBlank(bio)) {
				ProfileInfo theProfileInfo = new ProfileInfo();
				if (theProfile.getProfileInfo() != null) theProfileInfo = theProfile.getProfileInfo();				
				theProfileInfo.setProfile(theProfile);
				theProfileInfo.setSelfPerception(bio);
				theProfile.setProfileInfo(theProfileInfo);
			}
			mapGroups.put("profileid", theProfile.getId());
			mapGroups.put("location", dataLocation);
			//the map object		
		
			profileService.updateProfile(theProfile);
			logger.info("to send "+mapGroups);
			String theResult = new JSONSerializer().exclude("*.class").serialize(mapGroups);				
			
			//response.setHeader("Content-Encoding", "gzip");
			OutputStream out = response.getOutputStream();
			//GZIPOutputStream theGzip = new GZIPOutputStream(out);                                                                                                             
			out.write(theResult.getBytes());			
				
			out.flush();
			out.close();
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
	
	
	
	//peoplehuntv2
	@RequestMapping(value="/retrieveuserhuntid", method=RequestMethod.GET)
	public void processQuestionAlgorithmNewCharacters(@RequestParam(value="username", required= true ) String username,
			@RequestParam(value="bundleid", required= true) Integer bundleId, HttpServletResponse response) { 	
		
		try {
		
			
			User theUser = userService.getByUserName(username);
			
			//PeopleHuntId theHuntIdObj = theUser.getProfile().getPeopleHuntIdByBundle(bundleId);
			PeopleHuntId theHuntIdObj =  null;
			Map<String,Object> result = new HashMap<String, Object>();
			if (theHuntIdObj == null){
				result.put("huntid", 0);
			} else {
				result.put("huntid", theHuntIdObj.getHuntId());
			}
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(result);		
			
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
	
	
	//peoplehuntv2
	@RequestMapping(value="/addprovidinganswer/", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void addNewProdivingAttribute(@RequestParam(value="username", required= true ) String username,
			 @RequestParam(value="jsondata", required= true ) String jsondata,  
			 HttpServletResponse response) { 	
		
		
		try {
			
			HashMap<String, Object> theMapResult =	new JSONDeserializer<HashMap<String, Object>>().deserialize(URLDecoder.decode(jsondata, "UTF-8"));			
			Integer bundleId = (Integer) theMapResult.get("bundleRemoteId");
			List<Map<String, Object>> questionsMap = (List<Map<String, Object>>) theMapResult.get("questions");
			
			List<Question> questionsToSave = new ArrayList<Question>();			
			User user = userService.getByUserName(username);		
				
			Profile theProfile = user.getProfile();				
			//List<Question> userHasAnswered = theProfile.getBundleQuestions(bundleId);
			List<Question> userHasAnswered = null;
			logger.info("previous answers "+userHasAnswered.size());
			
			List<Integer> newAnswers = processSaveQuestions(false, bundleId, questionsMap, questionsToSave, theProfile);	
			Map<String, Object> resJson = new HashMap<String, Object>();
			resJson.put("answers", newAnswers);
			
			//PeopleHuntId myHunt =  theProfile.getPeopleHuntIdByBundle(bundleId);
			PeopleHuntId myHunt = null;
			String selectedPairs = myHunt.getPairedHuntIds();
			if (myHunt != null){			
				PeopleHuntId newPeopleHunt = new PeopleHuntId();
				newPeopleHunt.setId(myHunt.getId());
				newPeopleHunt.setBundleId(myHunt.getBundleId());	
				newPeopleHunt.setPersona(myHunt.getPersona());
				newPeopleHunt.setHuntId(myHunt.getHuntId());
				newPeopleHunt.setProfile(myHunt.getProfile());
				newPeopleHunt.setPairedHuntIds(String.valueOf(newAnswers.get(0)));
				String preselectedAnswers = myHunt.getPairedHuntIds();
				if (!StringUtils.isBlank(preselectedAnswers)) {						
					Set<String> storedProvide = new HashSet<String>(Arrays.asList(preselectedAnswers.split(",")));
					for (Integer theAnswer : newAnswers) {
						storedProvide.add(String.valueOf(theAnswer));
					}
					newPeopleHunt.setPairedHuntIds(StringUtils.join(storedProvide, ","));					
				}
				
				peopleHuntService.addPeopleHuntId(newPeopleHunt);	
			}
			
			if (userHasAnswered.size() > 0 || !StringUtils.isBlank(selectedPairs)){
				logger.info("adding bundle 2");
				new UserBundleOperationsThread(bundleId, myHunt.getHuntId(), 2).start();
				
			} else {
				logger.info("adding bundle 1");
				new UserBundleOperationsThread(bundleId, myHunt.getHuntId(), 1).start();
			}		
				
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(resJson);					
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
				logger.info("addNewProdivingAttribute User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}
	
	
	
	
	
	@RequestMapping(value="/retrievehuntprofile", method=RequestMethod.GET)
	public void retrieveProfileHunt(@RequestParam(value="username", required= true) String username,
			 @RequestParam(value="bundleid", required= true) Integer bundleId, HttpServletResponse response) {
		
		try {
			
			List<Question> allQuestions = questionService.retrieveUserBundle(username, bundleId);					
			CharacterMaker characterMaker = CharacterFactory.retrieveBundleCharacters(bundleId);			
			HashMap<String, String> characterRes = characterMaker.characterAlg(allQuestions);			
			
			User theUser = userService.getByUserName(username);
			//PeopleHuntId peopleHuntId = theUser.getProfile().getPeopleHuntIdByBundle(bundleId);
			PeopleHuntId peopleHuntId = null;
			characterRes.put("huntid", String.valueOf(peopleHuntId.getHuntId()));
			String theResult = new JSONSerializer().exclude("*.class").serialize(characterRes);		
			
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
				logger.info("retrieveProfileHunt User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}			
	}	
	
	
	//peoplehuntv2
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/processhuntanswers/", method=RequestMethod.POST)
	public void processQuestionAlgorithm(@RequestParam(value="username", required= true ) String username,
			 @RequestParam(value="email", required= false ) final String email, @RequestParam(value="name", required= true ) String name,  @RequestParam(value="uid", required= true) String uid, @RequestParam(value="password", required= true ) String password,
			 @RequestParam(value="jsondata", required= true ) String jsondata,  @RequestParam(value="savebatch", required= true ) Boolean saveBatch, @RequestParam(value="twitter_image", required= false ) String twitter_image,  
			 @RequestParam(value="latitude", required= true ) String latitude, @RequestParam(value="longitude", required= true ) String longitude, @RequestParam(value="token", required= false) String token,
			 HttpServletResponse response) { 	
		
		
		try {
			
			HashMap<String, Object> theMapResult =	new JSONDeserializer<HashMap<String, Object>>().deserialize(URLDecoder.decode(jsondata, "UTF-8"));			
			
			Integer bundleId = (Integer) theMapResult.get("bundleRemoteId");
			List<Map<String, Object>> questionsMap = (List<Map<String, Object>>) theMapResult.get("questions");
			
			List<Question> questionsToSave = new ArrayList<Question>();			
			User user = saveTheUser(username, password, null, uid, token, saveBatch, name, null);			
			List<Integer> retrieveNewAnswer = processSaveQuestions(saveBatch, bundleId, questionsMap, questionsToSave, user.getProfile());		
			   
			int huntId = (int)(Math.random() * 99999);
			boolean idExists = true;
			while (idExists) {
				PeopleHuntId peopleHuntIdObj = peopleHuntService.findUserWithHuntId(huntId, bundleId);
				if (peopleHuntIdObj == null){
					idExists = false;
				} else {
					huntId = (int)(Math.random() * 99999);					
				}			
			}
			
			Profile theProfile = new Profile();
			theProfile.setId(user.getProfile().getId());
			PeopleHuntId peopleHuntId = new PeopleHuntId();
			peopleHuntId.setBundleId(bundleId);
			peopleHuntId.setHuntId(huntId);
			peopleHuntId.setProfile(user.getProfile());
			peopleHuntId.setPersona("no_nerd");
			//theProfile.getMyHuntId().add(peopleHuntId);			
			peopleHuntService.addPeopleHuntId(peopleHuntId)	;	
			
			//List<HuntRating> myHuntRatings = user.getProfile().getHuntRatings();
			List<HuntRating> myHuntRatings = null;
			HuntRating huntRating = new HuntRating();		

			for (HuntRating storedHuntRating : myHuntRatings) {
				if (storedHuntRating.getTypeRating().equals("hunt-back")){
					huntRating = storedHuntRating;
				}
			}		   		
			
			huntRating.setBundleId(bundleId);	
			huntRating.setOpting(true);
			if (huntRating.getId()== null){			
				huntRating.setTypeRating("hunt-back"); 
				huntRating.setProfile(theProfile);			
			}
			
			peopleHuntService.mergeHuntRating(huntRating);		
			
			new UserBundleOperationsThread(bundleId, peopleHuntId.getHuntId() ,1).start();			
			
			Map<String,Object> result = new HashMap<String, Object>();
			result.put("huntid", String.valueOf(huntId));		
			result.put("answers", retrieveNewAnswer);
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(result);		
			
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

	//peoplehuntv2
	@RequestMapping(value="/updatebundleanswers/", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void updateUserAnswers(@RequestParam(value="username", required= true ) String username,
			 @RequestParam(value="jsondata", required= true ) String jsondata,  
			 HttpServletResponse response) { 	
		
		
		try {
			
			HashMap<String, Object> theMapResult =	new JSONDeserializer<HashMap<String, Object>>().deserialize(URLDecoder.decode(jsondata, "UTF-8"));			
			
			Integer bundleId = (Integer) theMapResult.get("bundleRemoteId");
			List<Map<String, Object>> questionsMap = (List<Map<String, Object>>) theMapResult.get("questions");
			
			List<Question> questionsToSave = new ArrayList<Question>();			
			User user = userService.getByUserName(username);		
				
			Profile theProfile = user.getProfile();
			//List<Question> questions = theProfile.getBundleQuestions(bundleId);
			List<Question> questions = null;
			List<Question> questionsCopy = Arrays.asList(new Question[questions.size()]);
			Collections.copy(questionsCopy, questions);
			for (Question question : questionsCopy) {		
				QuestionBundle theBundle = question.getQuestionBundle();
				theBundle.removeQuestion(question);
				//theProfile.removeQuestion(question);
				questionService.deleteQuestion(question);
				         
			}					
			  
			List<Integer> newAnswers = processSaveQuestions(true, bundleId, questionsMap, questionsToSave, theProfile);	
			Map<String, Object> resJson = new HashMap<String, Object>();
			resJson.put("answers", newAnswers);
			
			//PeopleHuntId theHuntId =  theProfile.getPeopleHuntIdByBundle(bundleId);
			PeopleHuntId theHuntId = null;
			if (theHuntId == null){								
				int huntId = (int)(Math.random() * 99999);
				boolean idExists = true;
				while (idExists) {
					PeopleHuntId peopleHuntIdObj = peopleHuntService.findUserWithHuntId(huntId, bundleId);
					if (peopleHuntIdObj == null){
						idExists = false;
					} else {
						huntId = (int)(Math.random() * 99999);					
					}			
				}
				
				theProfile.setId(user.getProfile().getId());
				PeopleHuntId peopleHuntId = new PeopleHuntId();
				peopleHuntId.setBundleId(bundleId);
				peopleHuntId.setHuntId(huntId);
				peopleHuntId.setProfile(user.getProfile());
				peopleHuntId.setPersona("no_nerd");
				//theProfile.getMyHuntId().add(peopleHuntId);			
				peopleHuntService.addPeopleHuntId(peopleHuntId)	;	
				theHuntId = peopleHuntId;
			}
			
			new UserBundleOperationsThread(bundleId, theHuntId.getHuntId(), 2).start();		
			resJson.put("myhuntid", theHuntId.getHuntId());
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(resJson);		
			
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
				logger.info("updateUserAnswers User Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}	
	
	

	private List<Integer> processSaveQuestions(Boolean saveBatch, Integer bundleId, List<Map<String, Object>> questionsMap,
			List<Question> questionsToSave, Profile theProfile) {
		
		List<Question> parentQuestions = questionService.retrieveParentQuestions(bundleId);
		List<Integer> newAnswerIds = new ArrayList<Integer>();
		for (Question parentQuestion : parentQuestions) {
			
			//add multiple option question to parent
			int presentSize = parentQuestion.getAnswers().size();
			List<String> selectedAnswers = checkForAnswer(questionsMap, parentQuestion);			
			for (String selectedAnswer : selectedAnswers) {			
				boolean addedNewAnswer = true;
				String selAnswer = selectedAnswer.replaceAll("\\s+", "");
				for (Answer anAnswer : parentQuestion.getAnswers()) {
					String backAnswer = anAnswer.getTextualAnswer().replaceAll("\\s+", "");					
					if (backAnswer.equals(selAnswer)){
						addedNewAnswer = false;
					}
				}
				if (addedNewAnswer){
					Answer newAnswer = new Answer();
					newAnswer.setTextualAnswer(selectedAnswer);
					newAnswer.setAnswerNumber(0);
					Calendar theCal = Calendar.getInstance();
					newAnswer.setDateCreated(theCal.getTime());
					parentQuestion.getAnswers().add(newAnswer);
				}		
				
				if (parentQuestion.getAnswers().size() > presentSize){
					parentQuestion = questionService.saveQuestion(parentQuestion);
					if (addedNewAnswer && parentQuestion.getQuestionType().equals("lookingfor")){
						for (Answer theAnswer : parentQuestion.getAnswers()) {
							String backAnswer2 = theAnswer.getTextualAnswer().replaceAll("\\s+", "");							
							if (backAnswer2.equals(selAnswer)){
								newAnswerIds.add(theAnswer.getId());
							}
						}
					}
				}
			}						
		}	
		
		// Add user questions      
		if (saveBatch) {	
			for (Question parentQuestion : parentQuestions) {
			
				Question newQuestion = RestUtils.createNewQuestion(theProfile, parentQuestion);
				List<String> selectedAnswers = checkForAnswer(questionsMap, parentQuestion);		
				for (String multipleAnswer : selectedAnswers) {
					String mulAnswer = multipleAnswer.replaceAll("\\s+", "");
					Answer newAnswer = new Answer();
					newAnswer.setTextualAnswer(multipleAnswer);
					newAnswer.setAnswerNumber(1);	
					for (Answer anAnswer : parentQuestion.getAnswers()) {		
						String textAnswer = anAnswer.getTextualAnswer().replaceAll("\\s+", "");
						if (mulAnswer.equalsIgnoreCase(textAnswer)){
							newAnswer.setParentAnswerId(anAnswer.getId());
						}
					}			
					
					newQuestion.addAnswer(newAnswer);				
				}		
						
				questionsToSave.add(newQuestion);		
			}
			
			questionService.saveBatchQuestions(questionsToSave);
		}
		
		return newAnswerIds;
	}
	
	
	
	private List<String> checkForAnswer( List<Map<String, Object>> questionMap, Question parentQuestion) {
		
		List<String> selectedAnswers = new ArrayList<String>();		
		for (Map<String, Object> question : questionMap) {	
		
			String theQuestionType = (String) question.get("questionType");
			if (parentQuestion.getQuestionType().equals(theQuestionType)){
				List<Map<String, Object>> answerData = (List<Map<String, Object>>) question.get("answer_data");
				for (Map<String, Object> theAnswerData : answerData) {		
					String selectedAnswer = (String) theAnswerData.get("answerDescription");
					if (!StringUtils.isBlank(selectedAnswer)){
						selectedAnswers.add(selectedAnswer);
					}
				}					
			}
		}
		
		return selectedAnswers;
	}
	
	public User saveTheUser (String username, String password, String email, String uid, String token, Boolean saveBatch, String name, 
			String imageUrl) 
		throws UnsupportedEncodingException {
		
		User user = null;				
		user = userService.getByUserName(username);
		File savedFile = null;				
		if (user != null) {					
			if (user.getProfile().getFiles().size() > 0) {
				savedFile = user.getProfile().getFiles().get(0);				
			} else {			
				savedFile = new File();	
			}
									
			savedFile.setFileName("facebookProfilePhoto_url");
			savedFile.setFileContent(imageUrl.getBytes());
			user.getProfile().getFiles().add(savedFile);
			user = userService.updateUser(user);							
								
		} else {					
				
			User theUser = RestUtils.setUser(name, email, username, password);
			//theUser.getProfile().setIphoneUDID(token);
			if (token != null) theUser.getProfile().setIphoneUDID( token.length()>40?token:null );
			File file = new File();
			file.setFileName("profilePhoto");
			file.setFileName("facebookProfilePhoto_url");
			file.setFileContent(imageUrl.getBytes());
			
			theUser.getProfile().getFiles().add(file);	
			theUser.setRole("learner");
			user = userService.saveUser(theUser);	
			
			//add anonymous client
			MongoDatabase db = mongoService.mongoDB;			
			MongoCollection<Document> col = db.getCollection("email_pairs");
			//create map with values
			Map<String, Object> emailValues = new HashMap<String, Object>();
			emailValues.put("profile_id", user.getProfile().getId());
			emailValues.put("email", email);			
			emailValues.put("anon_email",  RandomStringUtils.randomAlphanumeric(28).toLowerCase()+"@reply.languagehunt.com");
			String theResult = new JSONSerializer().exclude("*.class").serialize(emailValues);			
			
			Document theDoc = Document.parse(theResult);
			col.insertOne(theDoc);
			
		}		
		
		
		return user;
	}
	
	
	private void processHttpBody(final HttpServletRequest request, StringBuffer jb) {
	        String line = null;
	        try {
	            final BufferedReader reader = request.getReader();
	            while ((line = reader.readLine()) != null) {
	                jb.append(line);
	            }
	            
	            logger.info("REquest "+jb);
	            String[] theSplit = jb.toString().split("====");
	            jb = new StringBuffer(theSplit[1]);
	            
	        }
	        catch (Exception e) {}
	    }
	
	
	private Question processQuestion(Integer bundleId, String theAnswer, List<Question> theQuestions, User savedUser) {
		
		QuestionBundle theBundle = questionService.retrieveQuestionBundle(bundleId);		
		return RestUtils.setTheQuestion(theAnswer, theQuestions, savedUser, theBundle);
	}
	
}
