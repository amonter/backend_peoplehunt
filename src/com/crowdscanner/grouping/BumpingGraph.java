package com.crowdscanner.grouping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.crowdscanner.model.ProfileBuffer;
import com.myownmotivator.model.gaming.InteractionSession;
import com.myownmotivator.service.crowdmodule.QuestionBudleDao;
import com.myownmotivator.service.gaming.PeopleHuntService;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;

@Controller
public class BumpingGraph {
	
	final static Logger logger = Logger.getLogger(BumpingGraph.class);
	
	@Autowired
	private QuestionDao questionService;
	
	@Autowired
	private UserDao userService;	
	
	@Autowired
	private QuestionBudleDao questionBudleDao;
	
	@Autowired
	private PeopleHuntService peopleHuntService;
	
	
	
	
	private List<ProfileBuffer> processStoredQueue(List<ProfileBuffer> buffersPairs, InteractionSession interactionSession) {
		
		Map<Integer,ProfileBuffer> tempBuff = new HashMap<Integer, ProfileBuffer>();
		if (interactionSession.getStatus() != null) { 
			List<String> profileHistory = new ArrayList<String>();
			Collections.addAll(profileHistory, interactionSession.getStatus().split(",")); 
			
			for (ProfileBuffer storeBuffer : buffersPairs) {
				List<Integer> guessIds = new ArrayList<Integer>(storeBuffer.getGuessIds().values());			
				tempBuff.put(guessIds.get(0), storeBuffer);
			}
			
			for (String storedId : profileHistory) {		
				Integer theId = Integer.valueOf(storedId);
				if (tempBuff.containsKey(theId)) {					
					tempBuff.remove(theId);
				} 
			}			
			
			//add the new guesses			
			if (tempBuff.size() > 0){
				Set<Integer> guesses = tempBuff.keySet();			
				interactionSession.setStatus(interactionSession.getStatus()+","+StringUtils.join(guesses.toArray(),","));
			}		
			
			buffersPairs = new ArrayList<ProfileBuffer>(tempBuff.values());
			
		} else if (buffersPairs.size() > 0){
			
			for (ProfileBuffer storeBuffer : buffersPairs) {
				List<Integer> guessIds = new ArrayList<Integer>(storeBuffer.getGuessIds().values());			
				tempBuff.put(guessIds.get(0), storeBuffer);
			}
			
			if (tempBuff.size() > 0){
				Set<Integer> guesses = tempBuff.keySet();			
				interactionSession.setStatus(StringUtils.join(guesses.toArray(),","));				
			}	
		}
		
		
		return buffersPairs;
	}


	private InteractionSession setUpInteraction(List<ProfileBuffer> buffersPairs, Integer bundleId) {
		
		InteractionSession interactionSession = new InteractionSession();	
		interactionSession.setBundleId(bundleId);		
		interactionSession.setType("graph");
		List<Integer> queuedProfiles = new ArrayList<Integer>();
		for(ProfileBuffer profileBuffer: buffersPairs) {
			queuedProfiles.addAll(profileBuffer.getGuessIds().values());
		}
		
		if (queuedProfiles.size() > 0) {
			interactionSession.setStatus(StringUtils.join(queuedProfiles.toArray(),","));
		}
		return interactionSession;
	}
	
	
	private List<ProfileBuffer> deletePair(List<ProfileBuffer> buffersPairs) {
		
		List<ProfileBuffer> passedPairs = new ArrayList<ProfileBuffer>();
		for (ProfileBuffer buffer: buffersPairs) {				
			Calendar timeOne = Calendar.getInstance();			
			for (HashMap<String, String> bumpedProfile : buffer.getBumpedProfiles()) {
				Calendar timeTwo = Calendar.getInstance();				
				timeOne.setTime(buffer.getGuessedTimes().get(bumpedProfile.get("userName")));
				timeTwo.setTimeInMillis(Long.valueOf(bumpedProfile.get("guessedTime")));						
				
				if (timeOne.after(timeTwo)){
					ProfileBuffer profileBuffer = new ProfileBuffer();
					profileBuffer.setUserName(buffer.getUserName());
					profileBuffer.setName(buffer.getName());
					profileBuffer.setUrlImage(buffer.getUrlImage());
					profileBuffer.setProfileId(buffer.getProfileId());
					profileBuffer.getBumpedProfiles().add(bumpedProfile);
					Integer IdGuess = buffer.getGuessIds().get(bumpedProfile.get("userName"));
					profileBuffer.getGuessIds().put(bumpedProfile.get("userName"), IdGuess);
					profileBuffer.getGuessedTimes().put(bumpedProfile.get("userName"), timeOne.getTime());
					passedPairs.add(profileBuffer);
				}																
			}							
		}		
		
		return passedPairs;
	}


	private InteractionSession processInteractionSession(	List<ProfileBuffer> buffersPairs, InteractionSession interactionSession,
			Integer bundleId) {
		
		if (interactionSession == null) {
			interactionSession = new InteractionSession();	
			interactionSession.setBundleId(bundleId);
			interactionSession.setStatus("updated");
			List<Integer> queuedProfiles = new ArrayList<Integer>();
			for(ProfileBuffer profileBuffer: buffersPairs) {
				queuedProfiles.add(profileBuffer.getProfileId());
			}
			
			if (queuedProfiles.size() > 0) {
				interactionSession.setStatus(StringUtils.join(queuedProfiles.toArray(),","));
			}
			
		} else {
			
			if (interactionSession.getStatus() != null) {
				List<String> profileHistory = new ArrayList<String>();
				Collections.addAll(profileHistory, interactionSession.getStatus().split(",")); 
				for (Iterator<ProfileBuffer> iterator = buffersPairs.iterator(); iterator.hasNext();) {		
					ProfileBuffer buffer = iterator.next();
					for (String storedId : profileHistory) {
						Integer id = Integer.valueOf(storedId);
						if (id.equals(buffer.getProfileId())) {
							iterator.remove();
						}
					}					
				}
			}
		}
		return interactionSession;
	}
	
	
	
	
}

//Old method before porting to multiple events
/*
 
 //Integer bundle_id = Integer.parseInt(propertiesBean.getProperties().getProperty("bundle_id"));
			List<Profile> theProfiles = questionBudleDao.getBundleProfiles(bundleid);
			List<ProfileBuffer> buffers = new ArrayList<ProfileBuffer>();
			
			for (Profile profile : theProfiles) {
				profile.setProfileImageUrl(RestUtils.extractURLProfile(profile,  "http://images.crowdscanner.com/anon_nosmile.png"));
				ProfileBuffer profileBuffer = new ProfileBuffer();
				if (profile.getScore() != null) {
					Score aScore  = profile.getScore();
					profileBuffer.setUserName(profile.getUser().getUserName());
					profileBuffer.setName(profile.getUser().getLastName());
					profileBuffer.setUrlImage(profile.getProfileImageUrl());
					List<HashMap<String, Object>> listMapResult =	new JSONDeserializer<List<HashMap<String, Object>>>()
					.deserialize(URLDecoder.decode(aScore.getBumpedProfiles(), "UTF-8"));
					
					//Array for bumped users
					List<HashMap<String, String>> bumpedUserList = new ArrayList<HashMap<String,String>>();
					for (HashMap<String, Object> hashMap : listMapResult) {
						HashMap<String, String> bumpedUserHash = new HashMap<String, String>();						
						User theUser = userService.getByUserName((String) hashMap.get("username"));
						if (theUser != null) {
							boolean userStored = false;
							for (HashMap<String, String> storedBumpedUsers : bumpedUserList) {
								if (storedBumpedUsers.containsValue(theUser.getUserName())) {
									userStored = true;
								}								
							}
							if (!userStored) {
								bumpedUserHash.put("username", theUser.getUserName());
								bumpedUserHash.put("name", theUser.getLastName());
								bumpedUserHash.put("image_url", RestUtils.extractURLProfile(theUser.getProfile(),  "http://images.crowdscanner.com/anon_nosmile.png"));
								bumpedUserList.add(bumpedUserHash);
							}
						}
					}
					
					profileBuffer.setBumpedProfiles(bumpedUserList);
					buffers.add(profileBuffer);
					
				}
			}
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(buffers);
 
 * 
 */



