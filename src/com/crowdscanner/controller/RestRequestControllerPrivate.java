package com.crowdscanner.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.googlecode.batchfb.FacebookBatcher;
import com.googlecode.batchfb.Later;
import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.ActiveUser;
import com.myownmotivator.model.profile.ActiveUserNode;
import com.myownmotivator.model.profile.Friend;
import com.myownmotivator.model.profile.ILiked;
import com.myownmotivator.model.profile.MetaResObject;
import com.myownmotivator.model.profile.Moderator;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.ActiveUserDao;
import com.myownmotivator.service.questions.QuestionDao;

import flexjson.JSONSerializer;

@Controller
public class RestRequestControllerPrivate {

	final static Logger logger = Logger.getLogger(RestRequestControllerPrivate.class);
	
	@Resource
	private QuestionDao questionService;
	
	@Autowired
	private UserDao userService;	
	
	@Resource
	private RestControllerUtil restControllerUtil;
	
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ActiveUserDao activeUserService;
	
	
	
	
	@RequestMapping(value="/retrieveilikegraph/{uid}", method=RequestMethod.GET)
	public void retrieveIlikeGraph(@PathVariable("uid") Long theUid, HttpServletResponse response){			
		
		try {
			
			List<ActiveUser> activeUsers = activeUserService.retrieveIlikeGraph(theUid);		
			List<ActiveUserNode> activeUsersNodes = new ArrayList<ActiveUserNode>();		
			calculateConnections(activeUsersNodes, activeUsers);					
			
			String theResult = new JSONSerializer().exclude("*.class").include("connections").serialize("activenodes", activeUsersNodes);		
			
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
	
	
	
	
	@RequestMapping(value="/retrievefacebooknodescall*", method=RequestMethod.GET)
	public void retrieveFacebookNodesCall(@RequestParam("callback") String callback, HttpServletResponse response) {
		
		try {		
			
			MetaResObject theRes = new MetaResObject();
			processTheNodes(theRes);
			
			String theResult = new JSONSerializer().exclude("*.class").include("activenodes").include("likes").serialize(theRes);
			theResult = String.format("%1$s(%2$s)", new Object[]{callback, theResult});
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
					logger.info("retrievefacebooknodescall User Ex", e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}			
	}	
	
	
	@RequestMapping(value="/retrievefacebooknodes", method=RequestMethod.GET)
	public void retrieveFacebookNodes(HttpServletResponse response) {
		
		try {		
		
			MetaResObject theRes = new MetaResObject();
			processTheNodes(theRes);
			
			String theResult = new JSONSerializer().exclude("*.class").include("activenodes").include("likes").serialize(theRes);
			
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
					logger.info("retrieveprivatequestions User Ex", e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}		
	}


	private void processTheNodes(MetaResObject theRes) {
		
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
	}
	
	

	private void calculateConnections(List<ActiveUserNode> activeUsersNodes, List<ActiveUser> activeUsers) {
		
		for (ActiveUser activeUser : activeUsers) {
		
			ActiveUserNode theActiveNode = new ActiveUserNode();
			theActiveNode.setName(activeUser.getName());	
			theActiveNode.setUid(activeUser.getUid());				
			theActiveNode.setUrl(String.format("http://www.crowdscanner.com/picture_library/graphface/%1$d.jpg", new Object[]{activeUser.getUid()}));
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
	
	
	
	@RequestMapping(value="/createmoderator", method=RequestMethod.POST)
	public void createModerator(@RequestParam(value="username", required= true ) String username,
			 HttpServletRequest request, @RequestParam(value="name", required= true ) String name,	
			 @RequestParam(value="device", required= true ) String device, @RequestParam(value="email", required= true ) String email, @RequestParam(value="password", required= true ) String password, 
			 HttpServletResponse response) {				
		
		try {			
				
				User user = userService.getByUserName(username);
				
				if (user != null) {
				
					response.getOutputStream().println("error," + messageSource.getMessage("message.iphone.invalidUsername", new Object[] {username}, Locale.ENGLISH));
					
				}	else {		
			
					user = new User();		
					user.setUserName(username);
					user.setPassword(password);
					user.setLastName(name);
					user.setEmail(email);
						
					user.setState("moderator_city");
					user.setCountry("moderator_country");		
					user.getProfile().setSource("moderator");
				
					user.getProfile().setIphoneUser(true);				
					//user.setBirthDate(new CustomDateFormat(Calendar.getInstance()));		
					
					Moderator moderator = new Moderator();
					moderator.setDeviceId("");
					moderator.setProfile(user.getProfile());
					
					//user.getProfile().setModerator(moderator);
					
					//File theFile = new File();
					//theFile.setFileName("facebookProfilePhoto_url");				
					//theFile.setFileContent(pic.getBytes());
					//user.getProfile().getFiles().add(theFile);					
					
					userService.saveUser(user);
					
					response.getOutputStream().println("saved_ok"+"");
					response.setStatus(HttpServletResponse.SC_OK);					
			}						
				
		} catch (Exception ex) { 
			
			try {
				//response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getOutputStream().println("error," +"An authentication error happened. Please try to create a user normally." );
				response.setStatus(HttpServletResponse.SC_OK);	
				logger.info("createModerator User Ex", ex);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}	
	
	
	@RequestMapping(value="/linkfacebookuser", method=RequestMethod.POST)
	public void linkFacebookUser(@RequestParam(value="acesstoken") String acesstoken,
			 @RequestParam(value="picture_url") String pictureUrl,
			 HttpServletRequest request, @RequestParam(value="moderatorusername") String moderatorusername,	
			 HttpServletResponse response) {				
		
		try {			
				
			FacebookBatcher batcher = new FacebookBatcher(acesstoken);
			
			Later<JsonNode> justMe = batcher.graph("me");
			Later<JsonNode> friends = batcher.graph("me/friends");
			Later<JsonNode> theFeed = batcher.graph("me/feed");
			Later<JsonNode> myLikes = batcher.graph("me/likes");
			
			User activeUser = userService.getByUserName(moderatorusername);			
			JsonNode meNode = justMe.get();			
			Later<ArrayNode> thePic = batcher.query("SELECT pic_square FROM user WHERE uid ==" +meNode.get("id").getValueAsText());
			JsonNode picNode = thePic.get().iterator().next();	
			String thePict = picNode.get("pic_square").getTextValue();
			 	
			ActiveUser theActiveUser = new ActiveUser();
			theActiveUser.setName(meNode.get("name").getValueAsText());
			if (meNode.get("gender")!= null) {
				
				theActiveUser.setGender(meNode.get("gender").getValueAsText());
			}
		
			theActiveUser.setUid(Long.valueOf(meNode.get("id").getValueAsText()));
			theActiveUser.setUrl(pictureUrl);
			
			
			JsonNode fooNode = theFeed.get().iterator().next();	
			for (Iterator<JsonNode> iterator = fooNode.getElements(); iterator.hasNext();) {
				
				JsonNode node = iterator.next();			
				if (node.get("message") != null) {
					
					theActiveUser.setStatus(node.get("message").getTextValue());
					break;
				}			
			}
			
			
			List<Friend> myFriends = new ArrayList<Friend>();			
			JsonNode theNode = friends.get().iterator().next();			
			for (Iterator<JsonNode> iterator = theNode.getElements(); iterator.hasNext();) {
				
				JsonNode aNode = iterator.next();
				Friend aFriend = new Friend();
				aFriend.setUid(Long.valueOf(aNode.get("id").getValueAsText()));
				aFriend.setName(aNode.get("name").getTextValue());
				myFriends.add(aFriend);				
			}
			
			if (myLikes.get()!=null) {
				List<ILiked> myLikesArray = new ArrayList<ILiked>();	
				JsonNode likeNode = myLikes.get().iterator().next();			
				for (Iterator<JsonNode> iterator = likeNode.getElements(); iterator.hasNext();) {
					
					JsonNode aNode = iterator.next();
					ILiked iLiked = new ILiked();
					iLiked.setUid(Long.valueOf(aNode.get("id").getValueAsText()));
					iLiked.setCategory((aNode.get("category").getTextValue()));
					iLiked.setCount(0);
					iLiked.setName((aNode.get("name").getTextValue()));
					myLikesArray.add(iLiked);				
				}
				
				theActiveUser.setLikes(myLikesArray);
			}
			
			
			theActiveUser.getFriends().addAll(myFriends);				
			
			theActiveUser.setProfilePhoto(loadProfileImage(thePict));			
			//theActiveUser.setModerator(activeUser.getProfile().getModerator());		
			
			activeUserService.saveActiveUser(theActiveUser);
				
			response.getOutputStream().println("saved_ok"+"");
			response.setStatus(HttpServletResponse.SC_OK);					
								
				
		} catch (Exception ex) { 
			
			try {
				//response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getOutputStream().println("error," +"An authentication error happened. Please try to create a user normally." );
				response.setStatus(HttpServletResponse.SC_OK);	
				logger.info("linkFacebookUser User Ex", ex);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}		
	
	
	
	private byte[] loadProfileImage(String url) throws IOException {
		
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		URL u = new URL(url);
		InputStream is = null;
		byte[] byteChunk = new byte[u.openConnection().getContentLength()]; // Or whatever size you want to read in at a time.
		
		try {
			
			
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
