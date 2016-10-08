package com.test.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.batchfb.FacebookBatcher;
import com.googlecode.batchfb.Later;
import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.ActiveUser;
import com.myownmotivator.model.profile.ActiveUserNode;
import com.myownmotivator.model.profile.Friend;
import com.myownmotivator.model.profile.ILiked;
import com.myownmotivator.model.profile.MetaResObject;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.profile.ProfileInfo;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.ActiveUserDao;

import flexjson.JSONSerializer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"ApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class FacebookTest {

	
	@Autowired
	private UserDao userService;
	
	@Autowired
	private ActiveUserDao activeUserService;
	
	@Autowired
	private ProfileService profileService;
	
	
	@Test
	public void addProfileInfo () {
		
		String bio = " love meeting new people, travelling, learning languages and drinking chamomile tea.";
		User user = userService.getByUserName("Mary_Smith_100005034638463@meetforeal.com");
		Profile theProfile = user.getProfile();
		
		if (!StringUtils.isBlank(bio)){
			ProfileInfo theProfileInfo = new ProfileInfo();
			theProfileInfo.setProfile(theProfile);
			theProfileInfo.setSelfPerception(bio);
			theProfile.setProfileInfo(theProfileInfo);
		}
		
	
		profileService.updateProfile(theProfile);	
		
	}
	
	
	
	//@Test
	public void testFirstFace () {
	
		
		try {
		//137027032995358|af9110bb1dce4dc02ec3bbc6-750301118|z4JwnmpjDQbD5tIRJqgdTZ5rX8I. (Ellen)
			// 137027032995358|7c50fca033586bd1c58d5dc7-568544861|zZ5sll2nC7Mkg03kcDxNiV80FiI.
			// (ME)
		//137027032995358|3bb0d1a1bc40be8603e2e245-100001393081082|0Zo_A8qhq7YSL0gT9WcUWB5OM5A. Shane
		//x137027032995358|1f7853f87687011ef627d11f-100001443482333|CugDy13ZANbMvoKyYV-dW7WK-3o. Dilan Disrespect
		FacebookBatcher batcher = new FacebookBatcher("137027032995358|7c50fca033586bd1c58d5dc7-568544861|zZ5sll2nC7Mkg03kcDxNiV80FiI.");
		
		Later<JsonNode> justMe = batcher.graph("me");
		Later<JsonNode> friends = batcher.graph("me/friends");
		Later<JsonNode> theFeed = batcher.graph("me/feed");
		Later<JsonNode> myLikes = batcher.graph("me/likes");
		
		
		
		JsonNode meNode = justMe.get();
		Later<ArrayNode> thePic = batcher.query("SELECT pic_square FROM user WHERE uid =="+meNode.get("id").getValueAsText());
		JsonNode picNode = thePic.get().iterator().next();	
		
		System.out.println(picNode.get("pic_square"));
		
		JsonNode aNode = theFeed.get().iterator().next();	
		for (Iterator<JsonNode> iterator = aNode.getElements(); iterator.hasNext();) {
			
			JsonNode node = iterator.next();			
			if (node.get("message") != null) {
				
				System.out.println(node.get("message").getTextValue());
				
			}
			
		
		}
		
		Later<JsonNode> otherfriends = batcher.graph("680395069/friends");		
		User activeUser = userService.getByUserName("aloha876");		
		
		
		System.out.println(meNode.get("name").getValueAsText());
		System.out.println(meNode.get("id").getValueAsText());
		//System.out.println(meNode.get("gender").getValueAsText());
		
		ActiveUser theActiveUser = new ActiveUser();
		theActiveUser.setName(meNode.get("name").getValueAsText());
		//theActiveUser.setGender(meNode.get("gender").getValueAsText());
		theActiveUser.setUid(Long.valueOf(meNode.get("id").getValueAsText()));
		theActiveUser.setUrl("http://profile.ak.fbcdn.net/profile-ak-snc1/v229/1556/99/q750301118_3937.jpg");
		//theActiveUser.setName("Shane McGinty");
		//theActiveUser.setGender("male");
		//theActiveUser.setUid(680395069L);
		List<Friend> myFriends = new ArrayList<Friend>();
		
		JsonNode theNode = friends.get().iterator().next();
		int count = 0;
		for (Iterator<JsonNode> iterator = theNode.getElements(); iterator.hasNext();) {
			
			JsonNode fooNode = iterator.next();
			Friend aFriend = new Friend();
			aFriend.setUid(Long.valueOf(fooNode.get("id").getValueAsText()));
			aFriend.setName(fooNode.get("name").getTextValue());
			System.out.println(aFriend.getName()+" "+aFriend.getUid());
			myFriends.add(aFriend);
			
			count++;
		}
		
		if (myLikes.get()!=null) {
			List<ILiked> myLikesArray = new ArrayList<ILiked>();	
			JsonNode likeNode = myLikes.get().iterator().next();			
			for (Iterator<JsonNode> iterator = likeNode.getElements(); iterator.hasNext();) {
				
				JsonNode yNode = iterator.next();
				ILiked iLiked = new ILiked();
				iLiked.setUid(Long.valueOf(yNode.get("id").getValueAsText()));
				iLiked.setCategory((yNode.get("category").getTextValue()));
				iLiked.setName((yNode.get("name").getTextValue()));
				myLikesArray.add(iLiked);				
			}
			
			theActiveUser.setLikes(myLikesArray);
		}
		
		
		theActiveUser.getFriends().addAll(myFriends);		
		theActiveUser.setProfilePhoto(loadProfileImage(theActiveUser.getUrl()));				
		//theActiveUser.setModerator(activeUser.getProfile().getModerator());
		
		//activeUserService.saveActiveUser(theActiveUser);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	
	//@Test
	public void testMyAlg() {
		
		List<ActiveUser> activeUsers  = activeUserService.retrieveAllActiveUsers();		
		HashMap<Long, ILiked>allLikes = new HashMap<Long, ILiked>();
		
		for (ActiveUser activeUser : activeUsers) {
			
			
			for (ILiked theLike : activeUser.getLikes()) {
				
				if (allLikes.containsKey(theLike.getUid())){
					
					ILiked aLike = allLikes.get(theLike.getUid());
					aLike.setCount(aLike.getCount() + 1);
					allLikes.put(aLike.getUid(), aLike);
					
				} else {
					
					allLikes.put(theLike.getUid(), theLike);
				}
			}				
		}
		
		int count = 0;
		System.out.println(allLikes.size());
		for(Map.Entry<Long, ILiked> entries: allLikes.entrySet()) {
			
			if (entries.getValue().getCount() >0) {
			System.out.println(entries.getValue().getName()+" "+entries.getValue().getCount());
			System.out.println(count++);
			}
		}
		
	}
	
	
	//@Test
	public void testAlgorith() {
		
		MetaResObject theRes = new MetaResObject();
		List<ActiveUserNode> activeUsersNodes = new ArrayList<ActiveUserNode>();		
		List<ActiveUser> activeUsers  = activeUserService.retrieveAllActiveUsers();
		
		calculateConnections(activeUsersNodes, activeUsers);			
		
		Collections.sort(activeUsersNodes, new Comparator<ActiveUserNode>() {

			public int compare(ActiveUserNode active, ActiveUserNode active2) {

				return active.getNumberConnections().compareTo(active2.getNumberConnections());
			}
		});
		
		
		HashMap<Long, ILiked>allLikes = new HashMap<Long, ILiked>();
		for (ActiveUser activeUser : activeUsers) {				
			
			for (ILiked theLike : activeUser.getLikes()) {
				
				if (allLikes.containsKey(theLike.getUid())){
					
					ILiked aLike = allLikes.get(theLike.getUid());
					aLike.setCount(aLike.getCount() + 1);
					allLikes.put(aLike.getUid(), aLike);
					
				} else {
					
					allLikes.put(theLike.getUid(), theLike);
				}
			}				
		}
		
		List<ILiked> theLikesList = new ArrayList<ILiked>(allLikes.values());
		
		Collections.sort(theLikesList, new Comparator<ILiked>() {
			public int compare(ILiked like, ILiked like2) {

				return like.getCount().compareTo(like2.getCount());
			}
		});
		
		
		theRes.setLikes(theLikesList.subList((theLikesList.size() - 10) ,theLikesList.size()));
		theRes.setActivenodes(activeUsersNodes);
		
		String theResult = new JSONSerializer().exclude("*.class").include("activenodes").include("likes").serialize(theRes);	
		System.out.println(theResult);
	}
	
	
	
	
	
	
	//@Test
	public void testProfileURL () {
		
		
		try {
			
			
			//http://profile.ak.fbcdn.net/profile-ak-ash1/v222/693/40/q568544861_2696.jpg
			ActiveUser theActive = activeUserService.retrieveUserbyUID(568544861l);
			
			theActive.setProfilePhoto(loadProfileImage("http://profile.ak.fbcdn.net/profile-ak-ash1/v222/693/40/q568544861_2696.jpg"));					
			activeUserService.saveActiveUser(theActive);
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
private void calculateConnections(List<ActiveUserNode> activeUsersNodes, List<ActiveUser> activeUsers) {
		
		for (ActiveUser activeUser : activeUsers) {
		
			ActiveUserNode theActiveNode = new ActiveUserNode();
			theActiveNode.setName(activeUser.getName());	
			theActiveNode.setUid(activeUser.getUid());				
			theActiveNode.setUrl(String.format("http://87.232.63.199:8080/myownmotivator/crowd/retrieveprofileimage/%1$d.jpg", new Object[]{activeUser.getUid()}));
			theActiveNode.setStatus(activeUser.getStatus());				
			
			List<ActiveUser> elementToDelete = new ArrayList<ActiveUser>();
			elementToDelete.add(activeUser);
			List<ActiveUser> activeExceptCurrentList = ListUtils.subtract(activeUsers, elementToDelete);
			
			for (ActiveUser otherActiveUser : activeExceptCurrentList) {
				
				Long otherUid = otherActiveUser.getUid();
				for(Friend friend : activeUser.getFriends()) {
					
					Long friendUid = friend.getUid();
					if (otherUid.equals(friendUid)) {
						
						theActiveNode.getConnections().add(friendUid);
					}
				}				
			}	
			
			activeUsersNodes.add(theActiveNode);				
		}
	}
	
	
	private byte[] loadProfileImage(String url) throws IOException {
		
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		InputStream is = null;
		byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
		
		try {
			
		 URL u = new URL(url);	
		  is = u.openStream ();
	
		  int n;

		  while ( (n = is.read(byteChunk)) > 0 ) {
		    bais.write(byteChunk, 0, n);
		  }
		}
		catch (IOException e) {
		  System.err.printf ("Failed while reading bytes from %s",  e.getMessage());
		  e.printStackTrace ();
		  // Perform any other exception handling that's appropriate.
		}
		finally {
		  if (is != null) { is.close(); }
		}

		return byteChunk;
	}	
	
	
}
