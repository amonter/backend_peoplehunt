package com.crowdscanner.controller.peoplehunt;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.crowdscanner.grouping.UserBundleOperationsThread;
import com.myownmotivator.model.User;
import com.myownmotivator.model.gaming.HuntRating;
import com.myownmotivator.model.gaming.PeopleHuntId;
import com.myownmotivator.model.profile.EfactorData;
import com.myownmotivator.model.profile.LinkedInData;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.profile.TwitterUserAuthentication;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.crowdmodule.QuestionBudleDao;
import com.myownmotivator.service.gaming.PeopleHuntService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Controller
public class PeopleHuntBundleController {
	
	
	final static Logger logger = Logger.getLogger(PeopleHuntBundleController.class);	
	
	@Resource
	private QuestionDao questionService;	
	
	@Autowired
	PeopleHuntService peopleHuntService;	
	
	@Autowired
	private UserDao userService;		
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private QuestionBudleDao questionBudleDao;
	
	
	@RequestMapping(value="/retrievehuntbatchquestions/", method=RequestMethod.GET)
	public void  processQuestionMatch(HttpServletResponse response) { 	
		
		
		try {
			
			List<QuestionBundle> bundleArray = questionService.retrieveAllBundlesHunt();					
			String theResult = new JSONSerializer().exclude("*.class").exclude("parentQuestions").serialize(bundleArray);				
			
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
		
	
	@RequestMapping(value="/addpushtoken/", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void addPushToken(@RequestParam(value="username") String theUsername, @RequestParam(value="token") String token, 
			HttpSession session, HttpServletResponse response) {
		
		Profile theProfile = userService.getByUserName(theUsername).getProfile();
		theProfile.setIphoneUDID(token);		
		profileService.saveProfile(theProfile);
	
	}
	
	
	@RequestMapping(value="/updateuseremail/", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void updateSelectedTags(@RequestParam(value="username") String username, @RequestParam(value="email") String theEmail,
			HttpServletResponse response) { 
		
		User theUser = userService.getByUserName(username);
		theUser.setEmail(theEmail);
		userService.updateUser(theUser);
		
	}
	
	
	
	
	@RequestMapping(value="/addtwitterhandle/", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void addtwitterhandle(@RequestParam(value="username") String username, @RequestParam(value="handle") String twitterHandle,
			HttpServletResponse response) { 
		
		User theUser = userService.getByUserName(username);
		Profile myProfile = theUser.getProfile();		
		if (myProfile.getTwitterUserAuthentication() == null){
			
			TwitterUserAuthentication tAuth = new TwitterUserAuthentication();
			if (!StringUtils.contains(twitterHandle, "@")){
				twitterHandle = String.format("@%1$s ", twitterHandle);					
			}
			logger.info("added "+twitterHandle);
			tAuth.setUsername(twitterHandle);
			tAuth.setProfile(myProfile);
			myProfile.setTwitterUserAuthentication(tAuth);	
			profileService.updateProfile(myProfile);
		}		
	}	
	
	
	@RequestMapping(value="/addlinkedindata/", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void addlinkedindata(@RequestParam(value="username") String username, @RequestParam(value="name") String name,
			 @RequestParam(value="url") String url, HttpServletResponse response) { 
		
		User theUser = userService.getByUserName(username);
		Profile myProfile = theUser.getProfile();
		if (myProfile.getLinkedInData() == null){
			LinkedInData linkedInData = new LinkedInData();
			linkedInData.setName(name);
			linkedInData.setProfile(myProfile);
			linkedInData.setUrl(url);
			myProfile.setLinkedInData(linkedInData);
			profileService.updateProfile(myProfile);			
		}		
	}	
	

	@RequestMapping(value="/addefactordata/", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void addEfactorData(@RequestParam(value="username") String username, @RequestParam(value="jsondata") String data,
			 HttpServletResponse response) { 		
		
		try {
			
			HashMap<String, Object> theMapResult = new JSONDeserializer<HashMap<String, Object>>().deserialize(URLDecoder.decode(data, "UTF-8"));
			User theUser = userService.getByUserName(username);
			Profile myProfile = theUser.getProfile();		
			if (myProfile.getEfactorData() == null){			
				EfactorData efactorData = new EfactorData();
				efactorData.setEfactorId(Integer.valueOf((String)theMapResult.get("id")));
				efactorData.setEmail((String) theMapResult.get("email"));
				efactorData.setEfactorUrl((String) theMapResult.get("profile_url"));
				efactorData.setProfile(myProfile);	
				myProfile.setEfactorData(efactorData);
				profileService.updateProfile(myProfile);	
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}	
	
	
	//Currently used	
	@RequestMapping(value="/retrievequestionsbybundle", method=RequestMethod.GET)
	public void  retrieveLeaderBoardData(@RequestParam(value="username") String username, 
			@RequestParam(value="bundleid") Integer bundleId, HttpServletResponse response) { 	
		
		
		try {
			
			QuestionBundle theBundle = questionService.retrieveQuestionBundle(bundleId);
			Map<String, Object> mapJson = new HashMap<String, Object>();
			mapJson.put("bundle", theBundle);
			User theUser = userService.getByUserName(username);
			//List<Question> theQuestions = theUser.getProfile().getBundleQuestions(bundleId);
			List<Question> theQuestions =null;
			List<Integer> answersId = new ArrayList<Integer>();	
			for (Question theQuestion : theQuestions) {					
				for (Answer theAnswer : theQuestion.getAnswers()) {									
					answersId.add(theAnswer.getParentAnswerId());					
				}				
			}
			
			//get questiontype looking for			
			for (Question aQuestion : theBundle.getParentQuestions()) {				
			
				if(aQuestion.getQuestionType().equals("lookingfor")){					
					Map<Integer, Integer> mapRes = questionBudleDao.retrieveRepeatedProvidingFor(bundleId, aQuestion.getAnswers());
					mapJson.put("parent_answers", mapRes);
				}
			}
		
			
			//PeopleHuntId thePeopleHuntId = theUser.getProfile().getPeopleHuntIdByBundle(bundleId);
			PeopleHuntId thePeopleHuntId = null;
			//if HuntId is null add it
			if (thePeopleHuntId == null){				
				//Do HuntId check per bundle
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
				theProfile.setId(theUser.getProfile().getId());
				PeopleHuntId peopleHuntId = new PeopleHuntId();
				peopleHuntId.setBundleId(bundleId);
				peopleHuntId.setHuntId(huntId);
				peopleHuntId.setProfile(theUser.getProfile());
				peopleHuntId.setPersona("no_nerd");				
				//theProfile.getMyHuntId().add(peopleHuntId);			
				peopleHuntService.addPeopleHuntId(peopleHuntId)	;	
				thePeopleHuntId = peopleHuntId;
				
			} else if (thePeopleHuntId.getPairedHuntIds() != null) {
				mapJson.put("provide_res", thePeopleHuntId.getPairedHuntIds());
				
			}
			
			mapJson.put("user_res", answersId);
			mapJson.put("huntid", thePeopleHuntId.getHuntId());
			String theResult = new JSONSerializer().exclude("*.class").serialize(mapJson);			
			
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
	
}
