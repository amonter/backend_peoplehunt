package com.test.service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javapns.Push;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.crowdscanner.logic.Bundle;
import com.crowdscanner.notifier.Notifier;
import com.crowdscanner.notifier.iOSPusher;
import com.google.gson.Gson;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.crowdmodule.QuestionBudleDao;
import com.myownmotivator.service.peoplehuntv2.FeelerService;
import com.myownmotivator.service.peoplehuntv2.InboxService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.questions.QuestionDao;
import com.peoplehuntv2.model.AsyncMessage;
import com.peoplehuntv2.model.Feeler;
import com.peoplehuntv2.model.FoundTarget;
import com.peoplehuntv2.model.Status;

import flexjson.JSONSerializer;

@RunWith(SpringJUnit4ClassRunner.class)
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations = { "ApplicationContextTest.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class NotifierTest {

	@Autowired
	private QuestionBudleDao questionBundle;
	
	
	@Autowired
	private Bundle bundle;

	@Autowired
	private QuestionDao questionService;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private InboxService inboxService;
	
	@Autowired
	private FeelerService feelerService;
	
	
	
	@Test
	public void doTestNow() {
		
		List<Feeler> lookingFeelers = (List<Feeler>)feelerService.getRankedLookingFor();		 
		List<Feeler> providingFeelers = (List<Feeler>) feelerService.getRankedProvidingFor();
		String mode = "providing";		
		providingFeelers = filterFeelers(providingFeelers, mode);				       
        
		List<Map<String, Object>> interestedList = new ArrayList<Map<String,Object>>();
        for (final Feeler feeler : lookingFeelers) {
         	boolean hasProviders = false;
         	for (Feeler providingFeeler : providingFeelers) {         		       		
         		if (providingFeeler.getId() == feeler.getId()){		
         			hasProviders = true;         			
         		}            		
			}
         	
         	if (hasProviders) {    
         		boolean keyExists = false;
         		for (Map<String, Object> map : interestedList) {         			
					if(map.get("id").equals(feeler.getId())){
						keyExists = true;
					}
				}
         		
         		if (!keyExists){
	            	Map<String, Object> compressData = new LinkedHashMap<String, Object>();
	            	compressData.put("id", feeler.getId());
	            	compressData.put("description", feeler.getDescription());
	            	interestedList.add(compressData);
         		}
         	}
         }
         
        final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize(interestedList);
		System.out.println(theResult);
		
		
	}
	private List<Feeler> filterFeelers(List<Feeler> providingFeelers, String mode) {
		List<Feeler> filterFeelers = new ArrayList<Feeler>();
			  for (Feeler feeler : providingFeelers) {
		        	 List<Status> allStatus = feeler.getStatuses();
		        	 for (Status status : allStatus) {
		        		if (status.getFoundStatus().equals(mode)){
		        			filterFeelers.add(feeler);  
		        			System.out.println(mode+" "+feeler.getDescription());
		        		}
		        	 }
			  }
			  return filterFeelers;
	}
	//@Test
	public void testDoTarget() {
		
		String feelerMode = "looking";
		Profile theProfile = profileService.getUserProfile(1107);
		List<FoundTarget> foundTargets = theProfile.getFilteredTargets();
    	List<FoundTarget> oldFoundTargets = new ArrayList<FoundTarget>(foundTargets);
    	
    	Collections.copy(oldFoundTargets, foundTargets);
    	//feelerService.deleteFoundTargetsBatch(theProfile, feelerMode);  
    	for (FoundTarget theFoundTarget : oldFoundTargets) {
    		System.out.println(theFoundTarget.getId() +" comi1 "+oldFoundTargets.size()+" size "+theFoundTarget.getStatuses());
        	for (Status status : theFoundTarget.getStatuses()) {
        		System.out.println(theFoundTarget.getId() +" status "+status.getFoundStatus());
			}
    		
       	 if (theFoundTarget.getStatusType(feelerMode) != null) {
       		   final List<Feeler> theFeelers = (List<Feeler>)theFoundTarget.getStatusType(feelerMode).getFeelers();
                for (final Feeler feeler : theFeelers) {
                	System.out.println("galore");
               	 /*if (feeler.getId().intValue() == intKey){
               		 logger.info("Feeler id "+feeler.getId());
               		 feelerExists = true;
               		 if (feelerMode.equals("looking")) theFeeler.setLookingRank(theFeeler.getLookingRank() + 1);
               		 if (feelerMode.equals("providing")) theFeeler.setProvidingRank(theFeeler.getProvidingRank() + 1);
               		 logger.info("update feeler mode "+ feelerMode);
               		 feelerService.addNewFeeler(theFeeler);  
               		 break;
               	 }*/
                }
            }                
		}
		
	}
	
	
	//@Test
	public void testNotifier() {
		
		 try {
	        	//logger.info("coming post push");
	            //final Profile senderProfile = this.profileService.getUserProfile(senderId);
	            //final Profile callerProfile = this.profileService.getUserProfile(callerId);
	            //PollinatingController.logger.info((Object)("senderid " + senderId + " caller " + callerId));
	            //String udid = senderProfile.getIphoneUDID();
	        	String udid = "eb4d46a770308904e2a7e46f5927d372879f7c85515ade05ac4e443eddae8949";
	            if (udid != null) {
	                udid = udid.replaceAll("(<|>)", "").replace(" ", "");
	                final String[] uids = { udid };
	                //PollinatingController.logger.info((Object)("Sending to  " + senderProfile.getId()));
	                //iOSPusher.sendMultiple(uids, String.format("%1$s is calling you now! Open PeopleHunt to connect.", "tonitos"));
	                try{
	                	List<PayloadPerDevice> pairs = new Vector<PayloadPerDevice>();		
	        			
	        			for(int i=0; i<uids.length; i++){								
	        				//String theMessage =  String.format("%1$s connection is available now: open the app to get connected" , messageToSend);
	        				String deviceId = uids[i];
	        				pairs.add(new PayloadPerDevice(PushNotificationPayload.combined("random taxt", 0, "default"), new BasicDevice(deviceId)));
	        			}
	        			
	        			//Push.payloads("peoplepush2.p12", "espana19", true, pairs);
	        			List<PushedNotification> result = Push.payloads("peoplehuntv2_pushaws.p12", "espana19", true, pairs);	
	        			
	        			for (PushedNotification pushedNotification : result) {
	        				
	        				if (!pushedNotification.isSuccessful()) {
	        					Exception theProblem = pushedNotification.getException();
	        					System.out.println("error ios "+theProblem.getMessage());
	        				
	        					ResponsePacket theErrorResponse = pushedNotification.getResponse();
	        					if (theErrorResponse != null) {
	        						System.out.println(" data packet "+theErrorResponse.getMessage());
	        					}
	        					
	                         } else {                	 	
	                        	 	System.out.println("Successfully sent the notifications.");
	                         }
	        			}
	        			
	        		}catch(Exception e){
	        			//logger.info("postPushNotification inner User Ex"+ e.getMessage());
	        			e.printStackTrace();
	        		}               
	            }
	        }
	        catch (Exception e) {           
	        	//logger.info("postPushNotification User Ex"+ e.getMessage());
	        	e.printStackTrace();
	        }



	}

	
	//@Test
	public void singleTest() {
		
		//<eb4d46a7 70308904 e2a7e46f 5927d372 879f7c85 515ade05 ac4e443e ddae8949>
		//<eb4d46a7 70308904 e2a7e46f 5927d372 879f7c85 515ade05 ac4e443e ddae8949>
		
		//fef1a650 0a5c8a71 70ccb1f3 17c9202b 6f74433c 77d6c65a a4478509 fc4541da
		//fef1a6500a5c8a7170ccb1f317c9202b6f74433c77d6c65aa4478509fc4541da
		List<Profile> allProfiles = profileService.getAllProfiles();
		Set<String> uids = new HashSet<String>();
		for (Profile profile : allProfiles) {			
			String udid = profile.getIphoneUDID();
			if (udid != null){
				udid = udid.replaceAll("(<|>)", "").replace(" ", "");
				uids.add(udid);
				//System.out.println(udid);
			}
		}
		
		
		for (String string : uids) {
			//System.out.println(string);
		}
		
		//uids.add("cf7a2852c5352bae7cf07a85c72b08df925afd1e911b63dd496216caad1eec7c");
		//uids.add("eb4d46a770308904e2a7e46f5927d372879f7c85515ade05ac4e443eddae8949");
		
		String [] arrayUids = {"eb4d46a770308904e2a7e46f5927d372879f7c85515ade05ac4e443eddae8949"};
		//String [] arrayUids = uids.toArray(new String[uids.size()]);
		System.out.println(arrayUids.length);
		                        //fef1a6500a5c8a7170ccb1f317c9202b6f74433c77d6c65aa4478509fc4541da
		//<eb4d46a7 70308904 e2a7e46f 5927d372 879f7c85 515ade05 ac4e443e ddae8949>
		//iOSPusher.send("43feeb4471ca663e3c44c53685de944d77a21bfb275434236f92d489dc9c049b", "Like live music, inspirational talks, and meeting smart people? Come play PeopleHunt at Lucid NYC on the 5th Sept. 50% discount code PPLHUNT http://lucidnyc.eventbrite.com/");
		//SmsSender smsSender = new SmsSender();
		//smsSender.send("+353867826597", "Aloha here from NYC");
		//iOSPusher.sendMultiple(arrayUids, "Big update available right now. Find people based on what they know!");
		
	}
	
	
	//@Test
	public void checkUsersInBundles() {
		
		 final List<AsyncMessage> restMessages = (List<AsyncMessage>)inboxService.messagesOtherSent("Thelma_Monterrubio_568399334@meetforeal.com", 1108);
         for (AsyncMessage asyncMessage : restMessages) {
			System.out.println(asyncMessage.getContent());
		}
		 
		System.out.println("minute difference "+restMessages);
	}
	
	
	
	
	//@Test
	public void finalNotifierTest() {
		

		/*
		List<QuestionBundle> bundles = questionBundle.findAll();
		
		if(bundles.size()>0)
		for(QuestionBundle aBundle : bundles){
			if(!aBundle.getInProgress()){
				try {
					
					bundle.setBundle(aBundle);
					bundle.initializaParams(aBundle.getId());
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
			}
		}	*/
		
		
		QuestionBundle theQuestionBundle = questionBundle.findById(173);
		bundle.setBundle(theQuestionBundle);
		bundle.initializaParams(theQuestionBundle.getId());
		
	}
	
	
	//@Test
	public void retrieveRelatedMessages(){
		
		List<AsyncMessage> msgs = inboxService.retrieveMyRelatedActivity(10);
		//sort the messages
		Collections.sort(msgs, new Comparator<AsyncMessage>() {
			public int compare(AsyncMessage async, AsyncMessage async2) {
				return async2.getDateSent().compareTo(async.getDateSent());
			}
		});		
		for (AsyncMessage asyncMessage : msgs) {
			
			System.out.println(asyncMessage.getProfileName()+" other "+asyncMessage.getSender()+" "+asyncMessage.getDateSent());
			//System.out.println(asyncMessage.getContent());
		}		
	}
	
	
	//@Test
	public void answersData(){
		
		Map<Integer, Map<String, Object>> provideForMap = questionService.retrieveAnswersByParentQuestionId(149);
		
		List<Question> lookingForList = questionService.findQuestionWithPhoneId(91728696);
		Map<Integer, Map<String,Object>> lookingForMap = new HashMap<Integer, Map<String,Object>>();
		for (Question question : lookingForList) {
			if (!question.getParent()) {
				for (Answer theAnswer : question.getAnswers()) {
					if(!lookingForMap.containsKey(theAnswer.getParentAnswerId())){
						Map<String,Object> someAnswers = new HashMap<String,Object>();
						someAnswers.put("answer",theAnswer.getTextualAnswer());
						someAnswers.put("repeat",1);
						lookingForMap.put(theAnswer.getParentAnswerId(), someAnswers);
						
					} else {
						Map<String,Object> retrieveAnswers = lookingForMap.get(theAnswer.getParentAnswerId());
						int theRepeat = (Integer) retrieveAnswers.get("repeat") + 1;
						retrieveAnswers.put("repeat", theRepeat);
						lookingForMap.put(theAnswer.getParentAnswerId(), retrieveAnswers);
					}			
				}
			}
		}
		
		//resultMap.put("looking", lookingForMap.values());		
		//resultMap.put("providing", provideForMap.values());
		
		System.out.println(lookingForMap.values());
		System.out.println(provideForMap.values());
		
		
	}
	
}

