package com.crowdscanner.controller.peoplehunt;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.EncodingUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import com.crowdscanner.controller.utils.RestUtils;
import com.crowdscanner.grouping.UserBundleOperationsThread;
import com.crowdscanner.model.HunterProfile;
import com.myownmotivator.model.User;
import com.myownmotivator.model.gaming.Connection;
import com.myownmotivator.model.gaming.HuntRating;
import com.myownmotivator.model.gaming.PeopleHuntId;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.crowdmodule.QuestionBudleDao;
import com.myownmotivator.service.gaming.PeopleHuntService;
import com.myownmotivator.service.peoplehuntv2.GroupService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;
import com.peoplehuntv2.model.Feeler;
import com.peoplehuntv2.model.FoundTarget;
import com.peoplehuntv2.model.Group;
import com.peoplehuntv2.model.MemberShip;


import flexjson.JSONSerializer;

@Controller
public class QueueDataService {

	final static Logger logger = Logger.getLogger(QueueDataService.class);	
	
	@Autowired
	private QuestionBudleDao questionBudleDao;
	
	@Autowired
	private PeopleHuntService peopleHuntService;
	
	@Resource
	private QuestionDao questionService;	
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private GroupService groupService;
	
	//Twitter updates
	public void matchMade(String tweet){
		try{
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.updateStatus(tweet);
			
		}catch(Exception e){
			logger.info(e.getLocalizedMessage());
		}
	}
	
	
	@RequestMapping(value="/updateselectedtags/", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void updateSelectedTags(@RequestParam(value="selectedtags") String selectedTagList, 
			@RequestParam(value="myhuntid") Integer myHuntId, @RequestParam(value="bundleId") Integer bundleId,
			HttpServletResponse response) { 
		
		logger.info("selectedTags "+selectedTagList +" huntid "+myHuntId);
		PeopleHuntId myHunt = peopleHuntService.findUserWithHuntId(myHuntId, bundleId);
		String selectedPairs = myHunt.getPairedHuntIds();
		logger.info("selectedTags "+selectedTagList +" huntid "+myHuntId+" stored "+selectedPairs);
		PeopleHuntId newPeopleHunt = new PeopleHuntId();
		newPeopleHunt.setId(myHunt.getId());
		newPeopleHunt.setBundleId(myHunt.getBundleId());
		newPeopleHunt.setPersona(myHunt.getPersona());
		newPeopleHunt.setHuntId(myHunt.getHuntId());
		newPeopleHunt.setProfile(myHunt.getProfile());
		newPeopleHunt.setPairedHuntIds(selectedTagList);
		peopleHuntService.addPeopleHuntId(newPeopleHunt);			
		
		if (!StringUtils.isBlank(selectedPairs)){
			new UserBundleOperationsThread(bundleId, myHunt.getHuntId(), 2).start();
			
		} else {
			new UserBundleOperationsThread(bundleId, myHunt.getHuntId(), 1).start();
		}		
	}	
	
	
	@RequestMapping(value="/matchmade/", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void matchMade(@RequestParam(value="bundleid") Integer bundleId, 
			@RequestParam(value="profileidone") Integer profileone, @RequestParam(value="profileidtwo") Integer profileidtwo,
			@RequestParam(value="common") String common, HttpServletResponse response) { 
		

		List<Connection> connections = peopleHuntService.checkIfConnectionExists(bundleId, profileone, profileidtwo);
		if (connections.isEmpty()) {		
			Connection connection = new Connection();
			connection.setBundleId(bundleId);
			connection.setConnectionOne(profileone);
			connection.setConnectionTwo(profileidtwo);	
			connection.setTopic(common);
			Calendar theDate = Calendar.getInstance();
			connection.setConnectionMade(theDate.getTime());
			logger.info("Match made "+profileidtwo+" "+profileone+" "+bundleId);
			
			peopleHuntService.addConnection(connection);
		
		}
	}
	
	
	@RequestMapping(value="/matchnotmade/", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void matchMade(@RequestParam(value="bundleid") Integer bundleId, 
			@RequestParam(value="huntid") Integer huntid, HttpServletRequest request) { 
	
		PeopleHuntId huntId = peopleHuntService.findUserWithHuntId(huntid, bundleId);
		HuntRating huntRating = new HuntRating();
		huntRating.setBundleId(bundleId);
		Calendar theCalendar = Calendar.getInstance();
		huntRating.setDateCalled(theCalendar.getTime());
		huntRating.setRating("not_match");
		huntRating.setTypeRating("notmatch");
		huntRating.setProfile(huntId.getProfile());
		
		peopleHuntService.mergeHuntRating(huntRating);
	
	}
	
	@RequestMapping(value="/retrievecurrentplayers/{bundleId}", method=RequestMethod.GET)
	public void retrieveCurrentPlayers(@PathVariable("bundleId") Integer bundleId, HttpServletResponse response) { 
		
		try {			
			
			Long theCount = questionBudleDao.countBundleProfiles(bundleId);
					
			String theResult = "{\"players\":"+theCount+"}";		
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
	
	
	
	@RequestMapping(value="/retrieveactivebundles/", method=RequestMethod.GET)
	public void  processRetrieveAllActiveBundles(HttpServletResponse response) { 
		
	try {			
		
			
			List<Integer> allBundles = new ArrayList<Integer>();
			allBundles.add(3033);//our global bundle
					
			String theResult = new JSONSerializer().exclude("*.class").serialize(allBundles);			
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
	
	
	
	@RequestMapping(value="/profilelastonline/{profileid}", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void updateLastOnline(@PathVariable("profileid") Integer profileId, HttpServletResponse response) {	
			
		Profile theProfile = profileService.getUserProfile(profileId);			
		theProfile.setLastOnline(Calendar.getInstance().getTime());
		profileService.updateProfile(theProfile);			
	}	
	
	
	@RequestMapping(value="/retrieveprofilelistbybundle/{bundleId}", method=RequestMethod.GET)
	public void  processHuntIdByProfile(@PathVariable("bundleId") Integer bundleId, HttpServletResponse response) { 
		
		try {
			
			List<Profile> allProfiles = profileService.getAllProfiles();
			//get all the profiles for those groups
			List<Integer> profileIds = new ArrayList<Integer>();
			for (Profile theProfile : allProfiles) {		
				System.out.println(theProfile.getId());
				profileIds.add(theProfile.getId());				
			}						
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(profileIds);			
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
	
}
