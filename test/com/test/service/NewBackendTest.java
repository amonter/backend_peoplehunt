package com.test.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.crowdscanner.controller.utils.RestUtils;
import com.crowdscanner.model.HunterProfile;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.profile.ProfileInfo;
import com.myownmotivator.service.FileDao;
import com.myownmotivator.service.peoplehuntv2.FeelerService;
import com.myownmotivator.service.peoplehuntv2.GroupService;
import com.myownmotivator.service.peoplehuntv2.InboxService;
import com.myownmotivator.service.peoplehuntv2.LocationService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.peoplehuntv2.model.AsyncMessage;
import com.peoplehuntv2.model.Feeler;
import com.peoplehuntv2.model.FoundTarget;
import com.peoplehuntv2.model.Group;
import com.peoplehuntv2.model.Location;
import com.peoplehuntv2.model.MemberShip;
import com.peoplehuntv2.model.Notification;
import com.peoplehuntv2.model.Status;

import flexjson.JSONSerializer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "ApplicationContextTest.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class NewBackendTest {

	
	@Autowired
	private ProfileService profileService;
	
	@Resource
	private FileDao fileService;
	
	@Autowired
	private UserDao userService;	
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private FeelerService feelerService;	
	
	@Autowired
	private InboxService inboxService;
	
	@Autowired
	private LocationService locationService;
	
	private SessionFactory sessionFactory = null;
	
	
	//@Test
	public void giveOtheraccess() {
			
		User user = userService.getByUserName("Adrian_Avendano_568544861@meetforeal.com");
		List<Group> theGroups = user.getProfile().getMembershipType("core").getGroups();		
		
		//retrieve all their core/access memberships of the other user
		User otherUser = userService.getByUserName("Elena_Fox_Utapic_836570097@meetforeal.com");
		Profile otherProfile = otherUser.getProfile();
		List<MemberShip> otherAllMemberships = otherProfile.getMemberships();
		List<Group> allOtherGroups = new ArrayList<Group>();
		for (MemberShip membership : otherAllMemberships) {
			allOtherGroups.addAll(membership.getGroups());			
		}		
		
		//Check what groups we do not share in common
		List<Group> groupsWithNoAccess = new ArrayList<Group>();
		for (Group group : theGroups) {
			if (!allOtherGroups.contains(group)){
				groupsWithNoAccess.add(group);
			}
		}
		
		//add the memberships now	
		MemberShip otherAccessMem = null;
		if (otherProfile.getMembershipType("access") != null){//check if access membership
			otherAccessMem = otherProfile.getMembershipType("access");
			
		} else {
			System.out.println("no membership");
			otherAccessMem = new MemberShip();
			otherAccessMem.setMembershipType("access");
			otherAccessMem.setProfile(otherProfile);
			
		}
		
		List<MemberShip> memberShips = new ArrayList<MemberShip>();	
		memberShips.add(otherProfile.getMembershipType("core"));
		for (Group theGroup : groupsWithNoAccess) {//add groups to memberships			
			theGroup.getMemberships().add(otherAccessMem);	
			otherAccessMem.getGroups().add(theGroup);		
			memberShips.add(otherAccessMem);					
		}	
		
		
		otherProfile.setMemberships(memberShips);
			
	}
	
	
	//@Test
	
	//@Test
	public void addHasBeenViewed() {
		
		/*List<AsyncMessage> restMessages = inboxService.messagesSentToMe("Bob_Porter_100001103545208@meetforeal.com", 21);
		for (AsyncMessage asyncMessage : restMessages) {
			System.out.println(asyncMessage.getContent());
		}*/
		
		User theUser = userService.getByUserName("Mary_Smith_100005034638463@meetforeal.com");
		Profile theProfile = theUser.getProfile();
		List<FoundTarget> targets = theProfile.getFoundTargets();					
		int ownerFoundId = theProfile.getOwnerFoundTarget();
		
		List<Integer> foundProfileIds = new ArrayList<Integer>();		
		List<Map<String, Object>> listConnections = new ArrayList<Map<String, Object>>();
		for (FoundTarget foundTarget : targets) {			
			if (foundTarget.getId().intValue() == ownerFoundId){
				for (Profile aProfile : foundTarget.getFoundProfiles()){
					if (aProfile.getId().intValue() != theProfile.getId().intValue()){
						foundProfileIds.add(aProfile.getId());
						Map<String, Object> connections = new HashMap<String, Object>();
						connections.put("name", aProfile.getUser().getLastName());
						connections.put("username", aProfile.getUser().getUserName());
						connections.put("imageurl", RestUtils.extractURLProfile(aProfile, "http://images.crowdscanner.com/anon_nosmile.png"));
						listConnections.add(connections);
					}
				}		
				break;
			}			
		}
		
		Map<Integer, Boolean> theInbox = inboxService.retrieveNewInbox(foundProfileIds, theUser.getUserName());
		System.err.println(theInbox);
		
	}	
	
	//@Test
	public void fixAsync() throws UnsupportedEncodingException {
		
		List<Notification> notifications = profileService.retrieveProfileNotifications(10);	
		for (Notification notification : notifications) {
			System.out.println(notification.getName()+" "+notification.getLastMessage());
		}		
	}
	
	
	//@Test
	
	
	@Test
	public void deleteStatuses() throws UnsupportedEncodingException {		
			
		Profile theProfile = profileService.getUserProfile(1404);
		List<FoundTarget> foundTargets = theProfile.getFoundTargets();
		System.out.println(foundTargets.size());
		FoundTarget targetToDelete = null;
		Status statusToDelete = null;
		for (FoundTarget foundTarget : foundTargets) {
			Status theStatus = foundTarget.getStatusType("providing");
			if (theStatus != null){
				statusToDelete = theStatus;
				List<Feeler> feelers =  theStatus.getFeelers();
				for (Feeler feeler : feelers) {
					//if (feeler.getId().intValue() == 3){
						targetToDelete = foundTarget;
					//}
				}			
			}
		}
		
		/*
		statusToDelete.setFoundTarget(null);
		targetToDelete.getStatuses().remove(statusToDelete);
		targetToDelete.setFoundProfiles(null);
		
		System.out.println(foundTargets.size());
		profileService.updateProfile(theProfile);		
		*/
		
		theProfile.getFoundTargets().remove(targetToDelete);
		profileService.updateProfile(theProfile);	
		feelerService.deleteFoundTarget(targetToDelete);

		/*
		aFoundTarget.getFoundProfiles().add(theProfile);
		
		Status theStatus = new Status();
		theStatus.setFoundStatus("looking");
		theStatus.setFoundTarget(aFoundTarget);
		aFoundTarget.getStatuses().add(theStatus);
		
		Feeler theFeeler = feelerService.getFeelerById(4);		
		theStatus.getFeelers().add(theFeeler);		
		
		theProfile.getFoundTargets().add(aFoundTarget);	*/
		
	}
	
	
	//@Test
	public void retrieveFeelers() throws UnsupportedEncodingException { 
		
		int myProfileId = 245;
		Profile theProfile = profileService.getUserProfile(myProfileId);		
		Set<Integer> providingIds = new HashSet<Integer>(); 
		Set<Integer> lookingFords = new HashSet<Integer>(); 
		//get what the user learns and follows
		for (FoundTarget foundTarget : theProfile.getFilteredTargets()) {				
			if (foundTarget.getStatusType("providing") != null){					
				List<Feeler> theFeelers = foundTarget.getStatusType("providing").getFeelers();
				for (Feeler feeler : theFeelers) {
					providingIds.add(feeler.getId());
				}
			}				
			if (foundTarget.getStatusType("looking") != null){
				List<Feeler> theFeelers = foundTarget.getStatusType("looking").getFeelers();
				for (Feeler feeler : theFeelers) {
					lookingFords.add(feeler.getId());
				}
			}				
		}		
		//get all the feelers			
		List<Feeler> backDataFeelers = feelerService.getAllFeelers();		
		
		List<Feeler> feelerSort = new ArrayList<Feeler>(backDataFeelers);//sort the feelers by data
		Collections.sort(feelerSort, new Comparator<Feeler>() {
			public int compare(Feeler feeler, Feeler feeler2) {
				return feeler2.getDateCreated().compareTo(feeler.getDateCreated());
			}
		});
		//add to map for json structure
		List<Map<String,Object>> feelerList = new ArrayList<Map<String,Object>>();		
		for (Feeler feeler : feelerSort) {		
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put("id", feeler.getId());
			properties.put("URLEncodedAnswer", feeler.getURLEncodedDescription());		
			List<Status> theStatus = feeler.getStatuses();
			List<Integer> providers = new ArrayList<Integer>();
			Map<String, Object> followerMap = new HashMap<String, Object>();
			List<String> lookersData = new ArrayList<String>();
			
			for (Status status : theStatus) {
				if (status.getFoundStatus().equals("providing")){//all the providers
					providers.add(status.getFoundTarget().getFoundProfiles().get(0).getId());
				}
				//gather data for the followers								
				if (status.getFoundStatus().equals("looking")){
					List<Profile> allProfiles = status.getFoundTarget().getFoundProfiles();
					for (Profile profile : allProfiles) {
						lookersData.add(RestUtils.extractURLProfile(profile, "http://images.crowdscanner.com/anon_nosmile.png"));													
					}			
				}					
			}	
			
			followerMap.put("learning", lookersData.size());
			if (lookersData.size() > 2) lookersData = lookersData.subList(0, 3);
			properties.put("follower_urls", lookersData);
			properties.put("providers", providers);
			feelerList.add(properties);
		}
		
		Map<String, Object> feelerData = new HashMap<String, Object>();
		feelerData.put("feelers", feelerList);
		feelerData.put("providing", providingIds);
		feelerData.put("looking", lookingFords);			
		
		
		
		String theResult = new JSONSerializer().exclude("*.class").serialize(feelerData);	
		System.out.println(theResult);
		System.out.println(providingIds +" "+ lookingFords);		
		
	}
	
	
	//@Test
	public void addFoundTargetFeeler() throws UnsupportedEncodingException { 
		
		User user = saveTheUser("jo12@languaghunt123.com", "espana19", "jo_dib@hotmail.com", "52264512323123133r4", "34534r41231231231wq22132w", true, "Joe Dib" ,"http://s3.amazonaws.com/languagehunt/jo.png");
		//(String username, String password, String email, String uid, String token, Boolean saveBatch, String name, 
		//String imageUrl)
		Profile theProfile = user.getProfile();
		
		String bio = "";
		if (!StringUtils.isBlank(bio)) {
			ProfileInfo theProfileInfo = new ProfileInfo();
			if (theProfile.getProfileInfo() != null) theProfileInfo = theProfile.getProfileInfo();				
			theProfileInfo.setProfile(theProfile);
			theProfileInfo.setSelfPerception(bio);
			theProfile.setProfileInfo(theProfileInfo);
		}				
	
		profileService.updateProfile(theProfile);	
		addInterests(theProfile.getId());
		addPaymentTypes(theProfile.getId());
		addRatings(theProfile.getId());	
		addLocation(theProfile.getId());		
		addLangFeeler(theProfile.getId());	
		
		System.out.println(theProfile.getId());
	}


	//@Test
	public void addLangFeeler(int profileId) {
		
		Profile theProfile = profileService.getUserProfile(profileId);
		FoundTarget aFoundTarget = new FoundTarget();	
		aFoundTarget.getFoundProfiles().add(theProfile);
		
		Status theStatus = new Status();
		theStatus.setFoundStatus("providing");
		theStatus.setFoundTarget(aFoundTarget);
		aFoundTarget.getStatuses().add(theStatus);
		
		//add the language Feeler
		Feeler theFeeler = feelerService.getFeelerById(3);		
		theStatus.getFeelers().add(theFeeler);		
		
		theProfile.getFoundTargets().add(aFoundTarget);
	}
	
	//@Test
	public void addInterests(int profileId) {
		
		//int profileId = 1150;
		MongoDatabase db = getMongoDB();				
		MongoCollection<Document> col = db.getCollection("interests");			
		Document theDoc = Document.parse("{\"profile_id\":"+profileId+",\"interests\":{\"likes\":{\"data\":[{\"name\":\"traveling\",\"id\":\"63723457213\"},{\"name\":\"swimming\",\"id\":\"20232131899223\"}, {\"name\":\"writing/reading\",\"id\":\"831223452923\"}, {\"name\":\"museums\",\"id\":\"239121313311234\"}, {\"name\":\"cinema\",\"id\":\"6121312457213\"}]}}}");
		col.insertOne(theDoc);	
	}
	
	
	//@Test
	public void addRatings(int profileId) {	
		
		MongoDatabase db = getMongoDB();				
		MongoCollection<Document> col = db.getCollection("ratings");
		Document theDoc = Document.parse("{\"profile_id\":"+profileId+", \"rating_score\":4}");
		col.insertOne(theDoc);				
	}
	
	
	//@Test
	public void addPaymentTypes(int profileId) {
		
		//int profileId = 1211;
		MongoDatabase db = getMongoDB();				
		MongoCollection<Document> col = db.getCollection("payment_types");
		Document theDoc = Document.parse("{\"profile_id\":"+profileId+", \"payment_type\":1, \"amount\":7, \"description\":\"coffee plus fee\"}");
		col.insertOne(theDoc);	
		
	}	
	
	
	//@Test
	public void addEmailMapping() {			
			//int profileId = 1211;
			MongoDatabase db = getMongoDB();				
			MongoCollection<Document> col = db.getCollection("email_pairs");
			Document theDoc = Document.parse("{\"profile_id\":1130, \"welcome\":false, \"email\":\"adrian.mont@gmail.com\",\"anon_email\":\"adrian-83872yej92745ggwy3ywh11@reply.languagehunt.com\"}");
			col.insertOne(theDoc);				
	}
	
	
	//@Test
	public void addLocation (int profileId) {
		//1128
		Profile profile = profileService.getUserProfile(profileId);
		Location theLocation = locationService.getLocationById(6);
		List<Location> setLocations = new ArrayList<Location>();
		theLocation.getProfiles().add(profile);
        setLocations.add(theLocation);
        profile.setLocations(setLocations);			
		
	}
	
	//@Test
	public void addNewLocation () { 
		
		Location theLocation = new Location();
		theLocation.setCreationDate(new Date());
		theLocation.setLocation("Cupertino, CA");
		theLocation.setLatitude("37.77493");
		theLocation.setLongitude("-122.419416");		
		locationService.addNewLocation(theLocation);
		
	}
	
	private MongoDatabase getMongoDB() {
		MongoClient mongoClient = new MongoClient( "50.19.45.37" , 27017 );
		MongoDatabase db = mongoClient.getDatabase("peoplehunt");
		return db;
	}
	
	
	//@Test
	public void notContacted() throws UnsupportedEncodingException { 
		
		List<Map<String, Object>> allresults = profileService.doMatchingProcess();
		for (Map<String, Object> theMap : allresults) {
			System.out.println("\n\n"+theMap.get("name")+" : "+theMap.get("email"));
			List<HunterProfile> hunters = (List<HunterProfile>) theMap.get("no_interact");
			for (HunterProfile hunterProfile : hunters) {
				System.out.println("  "+hunterProfile.getName()+" "+hunterProfile.getMatchItem().trim());
			}			
		}
		
		/*
		Map<Integer, HunterProfile> liveMatchedProfiles = new HashMap<Integer, HunterProfile>();
		Profile profile = profileService.getUserProfile(10);
		List<AsyncMessage> myMessages = profile.getAsyncMessages();
		HunterProfile hunterProfile = new HunterProfile();
		hunterProfile.setProfileId(profile.getId());
		extractUserInfo(profile, hunterProfile);
		
		
		List<Profile> profiles = profileService.getAllProfiles();
		System.out.println(profiles.size());
		
		Multimap<Integer, HunterProfile> looking = ArrayListMultimap.create();
		Multimap<Integer, HunterProfile> providing = ArrayListMultimap.create();
		
		//building datasets
		List<HunterProfile> hunterProfiles = new ArrayList<HunterProfile>();
		for(Profile aProfile : profiles){			
			HunterProfile theHunterProfile = new HunterProfile();
			theHunterProfile.setProfileId(aProfile.getId());
			extractUserInfo(aProfile, theHunterProfile);
			hunterProfiles.add(theHunterProfile);			
		
			for (int feeler : theHunterProfile.getProvidingForMap().keySet()) {
				providing.put(feeler, theHunterProfile);
			}		
		
			for (int feeler2 :  theHunterProfile.getLookingForMap().keySet()) {
				looking.put(feeler2, theHunterProfile);
			}								
		}			
		
		
		
		for (HunterProfile hunterProfileRes : hunterProfiles) {
			System.out.println(hunterProfileRes.getProfileId());
			
		}
		/*
		//get the current profile
		
		String matchPart = null;
		//looking for list			
		for(int answer : hunterProfile.getProvidingForMap().keySet()){ 
			if( looking.containsKey(answer) ){
				for(HunterProfile hunterProfile2 : looking.get(answer)){
					//this is where the previously match has been recorded and so on								
					if( hunterProfile.getProfileId().intValue() != hunterProfile2.getProfileId().intValue()){
						if( (matchPart = hunterProfile2.getLookingForMap().get(answer)) != null ){
							if(liveMatchedProfiles.containsKey(hunterProfile2.getProfileId())){
								liveMatchedProfiles.get(hunterProfile2.getProfileId()).addCommon(matchPart);										
							}
							else{
								hunterProfile2.setMatchItem("wants: ");
								//if (reverse) hunterProfile2.setMatchItem("knows: ");// is the inverse so YOU Know
								hunterProfile2.addCommon(matchPart);
								liveMatchedProfiles.put(hunterProfile2.getProfileId(), hunterProfile2);	
							}								
						}
					}
				}					
			}					
		}
		
		for(int answer : hunterProfile.getLookingForMap().keySet()){
			if( providing.containsKey(answer) ){
				for(HunterProfile hunterProfile2 : providing.get(answer)){
					//this is where the previously match has been recorded and so on
					if( hunterProfile.getProfileId().intValue() != hunterProfile2.getProfileId().intValue() ){
						if( (matchPart = hunterProfile2.getProvidingForMap().get(answer)) != null ){
							if(liveMatchedProfiles.containsKey(hunterProfile2.getProfileId())){												
								liveMatchedProfiles.get(hunterProfile2.getProfileId()).addCommon(matchPart);				

							} else{
								hunterProfile2.setMatchItem("knows: ");
								//if (reverse) hunterProfile2.setMatchItem("wants: ");// is the inverse so YOU Know										
								hunterProfile2.addCommon(matchPart);
								liveMatchedProfiles.put(hunterProfile2.getProfileId(), hunterProfile2);	
							}																													
						}
					}
				}
			}						
		}							
		
		
		for (HunterProfile hunterProfileRes : liveMatchedProfiles.values()) {
			System.out.println(hunterProfileRes.getMatchItem());
			
		}	*/
		
		
	}

	
	
	

	
	//@Test
	public void feelerData() throws UnsupportedEncodingException { 
			
		Gson gson = new Gson();	
		String data = "{\"senderid\":249,\"profileid\":452, \"shares\":true, \"matchtext\":\"Just matching data\",\"help\":{\"140\":\"live and work from anywhere\",\"242\":\"make online advertising work\"}}";
		java.lang.reflect.Type collectionType = new TypeToken<Map<String, Object>>(){}.getType();		
		Map<String, Object> theDataObj = gson.fromJson(data, collectionType);
		System.out.println(theDataObj);
		
		
		
		//profileService.addAllProfileNotifications(theData);			
	}
	
	
	//@Test
	public void retrieveShuffleFeelers() throws UnsupportedEncodingException {
		
		List<Feeler> feelers = feelerService.getAllFeelers();
		Collections.shuffle(feelers);
		feelers = feelers.subList(0, 4);		
		for (Feeler feeler : feelers) {
			System.out.println(feeler.getDescription());
		}
	} 
	
	
	//@Test
	public void retrieveFoundProfiles() throws UnsupportedEncodingException { 
		
		//Mary_Smith_100005034638463@meetforeal.com
		//User theuser = userService.getByUserName("Joshua_Brustein_1022996907@meetforeal.com");
		//Profile theProfile = theuser.getProfile();
		List<Profile> theProfiles =  profileService.getAllProfiles();
		
	
		for (Profile theProfile : theProfiles) {		
		List<FoundTarget> targets = theProfile.getFoundTargets();		
		//System.out.println(theProfile.getUser().getLastName());
		int count  = 0;
		for (FoundTarget foundTarget : targets) {
			//System.out.println("found id "+foundTarget.getId()+" "+foundTarget.getOwnerId());
			//for (Profile aProfile : foundTarget.getFoundProfiles()) {						
				//System.out.println(" found "+aProfile.getId());
			//}
			
			for (Status status : foundTarget.getStatuses()) {
				//System.out.println(" status "+status.getFoundStatus());
				if (status.getFoundStatus().equals("looking")){
					count++;
					//for (Feeler theFeeler : status.getFeelers()) {
						//System.out.println("       "+theFeeler.getDescription());
					//}
				}
			}
		}
		
		System.out.println(count);
		
		
		}
		
		/*
		User theuser = userService.getByUserName("Adrian_Ryan_100004969956911@meetforeal.com");	
		Profile theProfile = theuser.getProfile();
		List<FoundTarget> targets = theProfile.getFoundTargets();					
		int ownerFoundId = theProfile.getOwnerFoundTarget();
		
		List<String> foundUsernames = new ArrayList<String>();		
		List<Map<String, Object>> listConnections = new ArrayList<Map<String, Object>>();
		for (FoundTarget foundTarget : targets) {			
			if (foundTarget.getId().intValue() == ownerFoundId){
				for (Profile aProfile : foundTarget.getFoundProfiles()){
					if (aProfile.getId().intValue() != theProfile.getId().intValue()){
						foundUsernames.add(aProfile.getUser().getUserName());
						Map<String, Object> connections = new HashMap<String, Object>();
						connections.put("name", aProfile.getUser().getLastName());
						connections.put("username", aProfile.getUser().getUserName());
						connections.put("imageurl", RestUtils.extractURLProfile(aProfile, "http://images.crowdscanner.com/anon_nosmile.png"));
						listConnections.add(connections);
					}
				}		
				break;
			}			
		}
		
		
		for (Map<String, Object> map : listConnections) {
			System.out.println(map);
		}*/
		
		
	}
	
	
	//@Test
	public void addFoundProfiles() throws UnsupportedEncodingException { 
		
		Profile theProfile = profileService.getUserProfile(249);
		Profile profileFound = profileService.getUserProfile(10);
		
		List<FoundTarget> targets = theProfile.getFoundTargets();		
		int ownerFoundId = theProfile.getOwnerFoundTarget();
		
		for (FoundTarget foundTarget : targets) {			
			if (foundTarget.getId().intValue() == ownerFoundId){
				foundTarget.getFoundProfiles().add(profileFound);			
				break;
			}			
		}
		
		if (ownerFoundId == 0) {
			List<FoundTarget> foundTargets = new ArrayList<FoundTarget>();			
			FoundTarget aFoundTarget = new FoundTarget();	
			aFoundTarget.setOwnerId(theProfile.getId());
			aFoundTarget.getFoundProfiles().add(theProfile);
			foundTargets.addAll(theProfile.getFoundTargets());
			foundTargets.add(aFoundTarget);
			theProfile.setFoundTargets(foundTargets);	
		}		
	}
	
	//@Test
	public void retrieveMyGroups() {
		
		Profile theProfile = profileService.getUserProfile(1675);	
		List<Group> coreGroups = theProfile.getMembershipType("core").getGroups();
		List<Group> accessGroups = theProfile.getMembershipType("access").getGroups();
		
		//filter the open groups
		List<Group> theGroups = groupService.getGroupsByType("open");
		for (Iterator<Group> it = theGroups.iterator(); it.hasNext();) {
			Group group = it.next();
			if (coreGroups.contains(group)){
				it.remove();
			}
		}
		
		Map<String, Object> theObject = new HashMap<String, Object>();
		theObject.put("member", coreGroups);
		theObject.put("access", accessGroups);
		theObject.put("open", theGroups);	
		
		String theResult = new JSONSerializer().exclude("*.class").serialize(theObject);		
		System.out.println(theResult);		
			
	}
	
	
	//@Test	
	public void allProfiles () {
		
		List<Group> allGroups = groupService.getAllGroups();
		//get all the profiles for those groups
		Set<Integer> allProfiles = new HashSet<Integer>();
		for (Group group : allGroups) {
			List<MemberShip> theMembership = group.getMemberships();
			for (MemberShip memberShip : theMembership) {
				allProfiles.add(memberShip.getProfile().getId());
			}			
		}						
		
		String theResult = new JSONSerializer().exclude("*.class").serialize(allProfiles);	
		System.out.println(theResult);		
	}
	
		
	//@Test
	public void addNewGroup() {
			
		Group newGroup = new Group();
		newGroup.setDescription("Health and Life Advice");	
		newGroup.setType("open");
		Calendar theCal = Calendar.getInstance();
		newGroup.setDate(theCal.getTime());
		groupService.addNewGroup(newGroup);
			
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
			theUser.getProfile().setIphoneUDID( token.length()>40?token:null );
			File file = new File();
			file.setFileName("profilePhoto");
			file.setFileName("facebookProfilePhoto_url");
			file.setFileContent(imageUrl.getBytes());
			theUser.setRole("guide");
			theUser.getProfile().getFiles().add(file);							
			user = userService.saveUser(theUser);				
		}		
		
		
		return user;
	}
	
	
	
}
