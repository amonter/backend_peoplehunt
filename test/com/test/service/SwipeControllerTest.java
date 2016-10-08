package com.test.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.crowdscanner.controller.MongoDbInit;
import com.crowdscanner.controller.utils.RestUtils;
import com.google.gson.Gson;
import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.service.peoplehuntv2.FeelerService;
import com.myownmotivator.service.peoplehuntv2.InboxService;
import com.myownmotivator.service.peoplehuntv2.LocationService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.peoplehuntv2.model.AsyncMessage;
import com.peoplehuntv2.model.Connection;
import com.peoplehuntv2.model.Feeler;
import com.peoplehuntv2.model.FoundTarget;
import com.peoplehuntv2.model.Location;
import com.peoplehuntv2.model.Status;

import flexjson.JSONSerializer;


@RunWith(SpringJUnit4ClassRunner.class)
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations={"ApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class SwipeControllerTest {
	
	@Autowired
	private FeelerService feelerService;
	
	@Autowired
    private LocationService locationService;	
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private MongoDbInit mongoService;
	
	@Autowired
	private UserDao userService;
	
	@Autowired
	private InboxService inboxService;
	
	
	
	@Test
	public void testLocationMatch () {
		
		String placemark = "London, United Kingdom";
		String[] placeMarkArray = placemark.split(",");
		
		List<Location> allLocations = (List<Location>) locationService.getAllLocations();
		Map<String, Object> dataLocation = new HashMap<String, Object>();
        for (final Location location : allLocations) {
        	//System.out.println(location.getLocation()+" "+ placemark);
        	if (StringUtils.contains(location.getLocation(), placeMarkArray[0])) {
            	dataLocation.put("id", location.getId());
            	dataLocation.put("location", location.getLocation());         
        	}            	
        }		
		
        if (dataLocation.isEmpty()){
        	dataLocation.put("id", -1);
        	dataLocation.put("location", placemark);          	
        }  
		
        System.out.println(dataLocation);
        
	} 
	
	
	//@Test
	public void feelerAction () { 
		
		Feeler theFeeler = feelerService.getFeelerById(9);
		
		List<Status> allStatus = theFeeler.getStatuses();
		for (Status status : allStatus) {
			if (status.getFoundStatus().equals("providing")){
				List<Profile> allProfiles = status.getFoundTarget().getFoundProfiles();
				for (Profile profile : allProfiles) {
					System.out.println(profile.getId());
				}
			}
		}						
	}
	
	
	//@Test
	public void mongoTest () {
		
		 Profile theProfile = this.profileService.getUserProfile(17);
		 List<Map<String, Object>> msgList = new ArrayList<Map<String, Object>>();
		 for (AsyncMessage theMessage : theProfile.getAsyncMessages()) {
			 
			 boolean addedAlready = checkExisting(msgList, theMessage);  			  			
			 if (!addedAlready) {
				 User theUser = userService.getByUserName(theMessage.getSender());
				 createMessageMap(msgList, theMessage, theUser);
			 }			
		 }
		 
		 List<AsyncMessage> restMessages = (List<AsyncMessage>) inboxService.messagesSentToMe(theProfile.getUser().getUserName());
		 Map<String, Map<String, Object>> messageList = new HashMap<String, Map<String, Object>>();
		 for (AsyncMessage asyncMessage : restMessages) {
			 if (!messageList.containsKey(asyncMessage.getProfile().getUser().getUserName())) {
				 Map<String, Object> theMessage = new HashMap<String, Object>();				
				 theMessage.put("content", asyncMessage.getContent());
				 theMessage.put("date_sent", asyncMessage.getDateSent());
				 messageList.put(asyncMessage.getProfile().getUser().getUserName(), theMessage);
			 }
		 }		 
		 
         for (AsyncMessage asyncMessage : restMessages) {
        	
        	 User otherUser = asyncMessage.getProfile().getUser();        	
        	 boolean addedAlready = false;
        	 for (Map<String, Object> map : msgList) {     			
     			if (map.get("username").equals(otherUser.getUserName())){
     				addedAlready = true;     				
     			}
     		 }        	 
        	 //boolean addedAlready = checkExisting(msgList, asyncMessage);        	
        	 if (!addedAlready) {        		
        		 createMessageMap(msgList, asyncMessage, otherUser);
        	 }
         }
         
         for (Map<String, Object> listMessage : msgList) {       
        	 Map<String, Object> mapData = messageList.get(listMessage.get("username"));   
        	 if (mapData != null) {
        		 if (mapData.containsKey("content")) listMessage.put("message", mapData.get("content"));
        		 if (mapData.containsKey("date_sent")) listMessage.put("date_sent", mapData.get("date_sent"));
        	 }
         }
		
         
         Collections.sort(msgList, new Comparator<Map<String, Object>>() {
  			public int compare(Map<String, Object> one, Map<String, Object> two) {		
  				Date dateOne = (Date) one.get("date_sent");
  				Date dateTwo = (Date) two.get("date_sent");
  				return dateOne.compareTo(dateTwo);
  			}
          });
 		 
         Collections.reverse(msgList);
         
        
          
         System.out.println(msgList);
         
         
	}


	private boolean checkExisting(List<Map<String, Object>> msgList,
			AsyncMessage theMessage) {
		boolean addedAlready = false;
		for (Map<String, Object> map : msgList) {			
			if (map.get("username").equals(theMessage.getSender())){
				addedAlready = true;
			}
		 }
		return addedAlready;
	}
	
	
	private void createMessageMap(List<Map<String, Object>> msgList,
			AsyncMessage theMessage, User theUser) {
		Map<String, Object> theMsgObj = new HashMap<String, Object>();   
		 System.out.println(theMessage.getDateSent()+"  "+theUser.getUserName());
		String imageUrl = theMessage.getProfileImageUrl();
		if (imageUrl == null) imageUrl = RestUtils.extractURLProfile(theUser.getProfile(), "http://images.crowdscanner.com/anon_nosmile.png");
		
		theMsgObj.put("username", theUser.getUserName());
		theMsgObj.put("profile_id", theUser.getProfile().getId());
		theMsgObj.put("image_url", imageUrl);
		theMsgObj.put("name", theUser.getLastName());
		theMsgObj.put("message", theMessage.getContent());
		theMsgObj.put("date_sent", theMessage.getDateSent());
		msgList.add(theMsgObj);
	}
	
	//@Test
	public void changeConnectioins() {
			
		 Profile aProfile = profileService.getUserProfile(21);
         int connectionId = 710;       
         List<Connection> allConnections = new ArrayList<Connection>();
         for (Connection theConnection : aProfile.getConnections()) {
         	if (theConnection.getWhoConnected().intValue() == connectionId){
         		theConnection.setStatus(1);
         	}
         	allConnections.add(theConnection);
         }
         
         aProfile.setConnections(allConnections);
         profileService.updateProfile(aProfile);
			
	}
	
	
	
	//@Test
	public void anotherTest() {
		 final List<Feeler> backDataFeelers = (List<Feeler>)feelerService.getRankedProvidingFor();
         final List<Feeler> dateSortedFeelers = new ArrayList<Feeler>(backDataFeelers);
         Collections.sort(dateSortedFeelers, new Comparator<Feeler>() {
             public int compare(final Feeler feeler, final Feeler feeler2) {
                 return feeler2.getDateCreated().compareTo(feeler.getDateCreated());
             }
         });
         final List<Feeler> topLatestFeelers = dateSortedFeelers.subList(0, 2);
         final Iterator<Feeler> it = backDataFeelers.iterator();
         while (it.hasNext()) {
             final Feeler feeler2 = it.next();
             if (topLatestFeelers.contains(feeler2)) {
                 it.remove();
             }
         }
         final Iterator<Feeler> i$3 = topLatestFeelers.iterator();
         while (i$3.hasNext()) {
             final Feeler feeler2 = i$3.next();
             backDataFeelers.add(0, feeler2);
         }
         final Map<Integer, String> compressData = new LinkedHashMap<Integer, String>();
         for (final Feeler feeler : backDataFeelers) {
             compressData.put(feeler.getId(), feeler.getDescription());
         }
         final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize(compressData);
         System.out.println(theResult);
		
	}
	
	//@Test
	public void repeatStatus() {
		
		final Profile theProfile = profileService.getUserProfile(1111);
		int targetId = 15316;
		//locationService.deleteLocationLink(theProfile);
		
		deleteFountTargets(theProfile);
		
	}

	
	//@Test
	public void testNewLocation() {
		
		String updateUrl = "http://ph001-1259637119.us-east-1.elb.amazonaws.com/rest/loclatlong";
		Map<String, Object> resLocs = null;
		Gson gson = new Gson();
		try{
			
			
			Location theLoc = new Location();
			theLoc.setLatitude("-34.603723");
			theLoc.setLongitude("-58.381593");
			List<Location> theLocs = new ArrayList<Location>();
			theLocs.add(theLoc);
			Map<String, Object> mapLoc = new HashMap<String, Object>();
			mapLoc.put("locations", theLocs);
						
			URL address = new URL(updateUrl);
			URLConnection myURLConnection = address.openConnection();			
			//InputStream incoming = new GZIPInputStream( address.openStream() );
			myURLConnection.setDoOutput(true);
			myURLConnection.setRequestProperty("Content-Type", "application/json");
			myURLConnection.setRequestProperty("Accept", "application/json");
			System.out.println(gson.toJson(mapLoc));
			OutputStream writer = myURLConnection.getOutputStream();													
			writer.write(gson.toJson(mapLoc).getBytes());
			writer.flush();
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
			
			System.out.println(gson.toJson(mapLoc));
			String line;
			 while ((line = rd.readLine()) != null) {
				 System.out.println(line);
			 }	
			 rd.close();
			
			
			/*OutputStreamWriter writer = new OutputStreamWriter(myURLConnection.getOutputStream());		
			String json = gson.toJson(mapLoc);
			System.out.println(" hello "+json);
			writer.write(URLEncoder.encode(json, "UTF-8"));
			writer.flush();
			
			BufferedReader decoder = new BufferedReader( new InputStreamReader(incoming, "UTF-8"));			
			Type typ = new TypeToken<Collection<Map<String, Object>>>(){}.getType();
			resLocs = gson.fromJson(decoder, typ);
			System.out.println("Hello"+resLocs);*/
			
		} catch (Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	//@Test
	public void testAddNewFeeler () {
		
		Feeler theFeeler = new Feeler();
		theFeeler.setDescription("Arabic");	
		theFeeler.setDateCreated(new Date());
		
		feelerService.addNewFeeler(theFeeler); 		
	}

	
	private void deleteFountTargets(Profile theProfile) {
		feelerService.deleteFoundTargetsBatch(theProfile, "looking");
		
	}
	

	private void deleteFountTargets(Profile theProfile, int targetId) {
		
		final List<FoundTarget> foundTargets = theProfile.getFilteredTargets();
        FoundTarget targetToDelete = null;
        String feelerMode = "looking";
        
        /*for (FoundTarget foundTarget : foundTargets) {
        	//System.out.println("de");
        	List<Status> theStatus = foundTarget.getStatuses();
        	for (Status status : theStatus) {
        		System.out.println(status.getId()+" "+status.getFoundStatus()+" "+status.getFeelers().get(0).getId());
			}
        	
        	targetToDelete = foundTarget;
        	
        	/*if(foundTarget.getId().intValue() == targetId){	
        		targetToDelete = foundTarget;
				System.out.println("delete "+targetId);
				
			}*/
        	//theProfile.getFoundTargets().remove(targetToDelete);
        	//feelerService.deleteFoundTarget(targetToDelete);
        //}
        
        for (FoundTarget foundTarget : foundTargets) {
        	List<Status> theStatus = foundTarget.getStatuses();
        	for (Status status : theStatus) {
				if (status.getFoundStatus().equals(feelerMode)){
					targetToDelete = foundTarget;
					feelerService.deleteFoundTarget(targetToDelete);
					break;
				}
			}       	
        }
        
        
        theProfile.getFoundTargets().clear();
        for (Location theLoc : theProfile.getLocations()) {
        	System.out.println(theLoc.getLocation());
		}
        
       // theProfile.getLocations().clear();
        //profileService.updateProfile(theProfile);
       
	}
	
	
	//@Test
	public void getLocations() {
		List<Location> locations = locationService.getAllLocations();
		
	
		Collections.sort(locations, new Comparator<Location>() {
			public int compare(Location one, Location two) {
				Integer intTwo = new Integer(two.getProfiles().size());
				Integer intOne = new Integer(one.getProfiles().size());
				
				return intTwo.compareTo(intOne);
			}
		});
		
		for (Location location : locations) {
			System.out.println(location.getProfiles().size() +" "+location.getLocation());
		}
		
	}
	
	//@Test
	public void testAddFeeler() {
		
		
		Profile theProfile = profileService.getUserProfile(1115);
    	List<FoundTarget> targetsToFound = new ArrayList<FoundTarget>();    	
            final FoundTarget aFoundTarget = new FoundTarget();
            aFoundTarget.getFoundProfiles().add(theProfile);
            final Status theStatus = new Status();
            theStatus.setFoundStatus("providing");
            theStatus.setFoundTarget(aFoundTarget);
           
            aFoundTarget.getStatuses().add(theStatus);
            
            Feeler theFeeler = this.feelerService.getFeelerById(9);                      
                   
            final Calendar theCal = Calendar.getInstance();
            theFeeler.setDateCreated(theCal.getTime());
            theStatus.getFeelers().add(theFeeler);            
            
            targetsToFound.add(aFoundTarget);            
            targetsToFound.addAll(theProfile.getFoundTargets());
            
            theProfile.setFoundTargets(targetsToFound);
            profileService.updateProfile(theProfile);       
		
	}
	
	
	//@Test
	public void test() {
			
		List<Feeler> theFeelers = feelerService.getAllFeelers();
		List<Feeler> feelers = new ArrayList<Feeler>();
		for (Feeler feeler : theFeelers) {
			int interested = 0;
			int help = 0;
			List<Integer> proficiency = new ArrayList<Integer>();
			for (final Status status : feeler.getStatuses()) {
                if (status.getFoundStatus().equals("providing")) {
                    //System.out.println(status.getFoundTarget().getFoundProfiles().get(0).getId());
                    interested++;
                    proficiency.add(status.getProficiency());
                }
                if (status.getFoundStatus().equals("looking")) {
                    //System.out.println(status.getFoundTarget().getFoundProfiles().get(0).getId());
                    help++;
                }
            }
			
			//System.out.println(feeler.getId()+" "+feeler.getDescription()+" "+interested+" "+help);
			feeler.getProficiencies().addAll(proficiency);
			feeler.setProvidingRank(help);
			feeler.setLookingRank(interested);
			feelers.add(feeler);			
		}
		
		Collections.sort(feelers, new Comparator<Feeler>() {
			public int compare(Feeler one, Feeler two) {						
				return two.getProvidingRank().compareTo(one.getProvidingRank());
			}
		});
		
		System.out.println("Interested table");
		for (Feeler feeler : feelers) {
			System.out.println(feeler.getId()+" "+feeler.getDescription()+" "+feeler.getProvidingRank()+" "+StringUtils.join(feeler.getProficiencies().toArray(new Integer[0])));
		}			
	}
	
	//@Test
	public void editFeelers () {
		String feelerMode = "looking";
		Feeler theFeeler = this.feelerService.getFeelerById(3);
        if (theFeeler == null) {
            theFeeler = new Feeler();
            
            
        } else {                  
            
                 if (feelerMode.equals("looking")) theFeeler.setLookingRank(theFeeler.getLookingRank() + 1);
          		 if (feelerMode.equals("providing")) theFeeler.setProvidingRank(theFeeler.getProvidingRank() + 1);
                 feelerService.addNewFeeler(theFeeler);        		
             
        }
		
		
	}
	
	//@Test
	public void singleTest() {		
		
		//feelerService.cleanFoundTarget();	
		
		int[] ids = {17};	
		
		Set<Integer> idSets = new HashSet<Integer>();
		for (int theId : ids) {
			idSets.add(new Integer(theId));
		}
		
		Set<Integer> profileIds = new HashSet<Integer>();
		for (int theId : idSets) {		
		
			Feeler theFeeler = feelerService.getFeelerById(theId);			
			
			for (Status aStatus : theFeeler.getStatuses()) {
	        	//System.out.println(aStatus.getFoundStatus());
	        	FoundTarget foundTarget =  aStatus.getFoundTarget();	
	        	//System.out.println(foundTarget.getId()+" "+foundTarget.getFoundProfiles().get(0).getId());
	        	profileIds.add(foundTarget.getFoundProfiles().get(0).getId());
	        	feelerService.deleteFoundTarget(foundTarget);    	
			}    
	        
	        //feelerService.deleteFeeler(theFeeler);        
		}
		System.out.println("count "+profileIds.size());
		System.out.println(profileIds);
		
	}

}
