 package com.crowdscanner.controller;

 import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.zip.GZIPOutputStream;

import javapns.Push;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;

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

import com.crowdscanner.controller.utils.RestUtils;
import com.crowdscanner.model.HunterProfile;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.service.peoplehuntv2.FeelerService;
import com.myownmotivator.service.peoplehuntv2.GroupService;
import com.myownmotivator.service.peoplehuntv2.InboxService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.peoplehuntv2.model.AsyncMessage;
import com.peoplehuntv2.model.Connection;
import com.peoplehuntv2.model.Feeler;
import com.peoplehuntv2.model.FoundTarget;
import com.peoplehuntv2.model.Group;
import com.peoplehuntv2.model.Notification;
import com.peoplehuntv2.model.Status;

import flexjson.JSONSerializer;

@Controller
//TODO redeploy
public class PollinatingController {
	
	final static Logger logger = Logger.getLogger(PollinatingController.class);	

	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private UserDao userService;
	
	@Autowired
	private InboxService inboxService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private FeelerService feelerService;
	
	@Autowired
	private MongoDbInit mongoService;
	
	
	@RequestMapping(value = { "/postnotification" }, method = { RequestMethod.POST })
    public void postNewNotification(@RequestParam("jsondata") final String jsonData, final HttpServletResponse response) {
        try {
            final Type collectionType = new TypeToken<Collection<Map<String, Object>>>() {}.getType();
            final Gson gson = new Gson();
            final Collection<Map<String, Object>> theData = (Collection<Map<String, Object>>)gson.fromJson(jsonData, collectionType);
            this.profileService.addAllProfileNotifications((Collection)theData);
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                PollinatingController.logger.info((Object)"retrieveMatchConnections User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    @RequestMapping(value = { "/retrievenotifications" }, method = { RequestMethod.GET })
    public void retrieveMyNotifications(@RequestParam("profileid") final Integer profileId, final HttpServletResponse response) {
        try {
            PollinatingController.logger.info((Object)("PROF " + (int)profileId));
            if (profileId > 0) {
                final List<Notification> notifications = (List<Notification>)this.profileService.retrieveProfileNotifications(profileId);
                for (final Notification notification : notifications) {
                    notification.setId(notification.getSenderId());
                    if (notification.isCallBack()) {
                        notification.setMatchItem("knows: " + notification.getMatchItem());
                    }
                    else {
                        notification.setMatchItem("wants: " + notification.getMatchItem());
                    }
                }
                Collections.sort(notifications, new Comparator<Notification>() {
                    public int compare(final Notification notify, final Notification notify2) {
                        return notify2.getNotificationTime().compareTo(notify.getNotificationTime());
                    }
                });
                final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)notifications);
                response.setHeader("Content-Encoding", "gzip");
                final OutputStream out = (OutputStream)response.getOutputStream();
                final GZIPOutputStream theGzip = new GZIPOutputStream(out);
                theGzip.write(theResult.getBytes());
                theGzip.flush();
                theGzip.close();
                out.close();
            }
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                PollinatingController.logger.info((Object)"retrieveMyNotifications User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    @RequestMapping(value = { "/getrandomfeelers" }, method = { RequestMethod.GET })
    public void getRondomFeelers(final HttpServletResponse response) {
        try {
            List<Feeler> feelers = (List<Feeler>)this.feelerService.getAllFeelers();
            Collections.shuffle(feelers);
            feelers = feelers.subList(0, 4);
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)feelers);
            response.setHeader("Content-Encoding", "gzip");
            final OutputStream out = (OutputStream)response.getOutputStream();
            final GZIPOutputStream theGzip = new GZIPOutputStream(out);
            theGzip.write(theResult.getBytes());
            theGzip.flush();
            theGzip.close();
            out.close();
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                PollinatingController.logger.info((Object)"retrieveMatchConnections User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    @RequestMapping(value = { "/matchconnections" }, method = { RequestMethod.GET })
    public void retrieveMatchConnections(@RequestParam("username") final String username, final HttpServletResponse response) {
        try {
            final User theUser = this.userService.getByUserName(username);
            final Profile theProfile = theUser.getProfile();
            final List<FoundTarget> targets = (List<FoundTarget>)theProfile.getFoundTargets();
            final int ownerFoundId = theProfile.getOwnerFoundTarget();
            final List<Integer> foundProfileIds = new ArrayList<Integer>();
            final List<Map<String, Object>> listConnections = new ArrayList<Map<String, Object>>();
            for (final FoundTarget foundTarget : targets) {
                if (foundTarget.getId() == ownerFoundId) {
                    for (final Profile aProfile : foundTarget.getFoundProfiles()) {
                        if (aProfile.getId() != (int)theProfile.getId()) {
                            foundProfileIds.add(aProfile.getId());
                            final Map<String, Object> connections = new HashMap<String, Object>();
                            connections.put("name", aProfile.getUser().getLastName());
                            connections.put("profileId", aProfile.getId());
                            connections.put("username", aProfile.getUser().getUserName());
                            connections.put("imageurl", RestUtils.extractURLProfile(aProfile, "http://images.crowdscanner.com/anon_nosmile.png"));
                            listConnections.add(connections);
                        }
                    }
                    break;
                }
            }
            final Map<Integer, Boolean> theInbox = (Map<Integer, Boolean>)this.inboxService.retrieveNewInbox((List)foundProfileIds, theUser.getUserName());
            for (final Map<String, Object> connection : listConnections) {
                final Boolean hasNew = theInbox.get(connection.get("profileId"));
                connection.put("hasnew", hasNew);
            }
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)listConnections);
            response.setHeader("Content-Encoding", "gzip");
            final OutputStream out = (OutputStream)response.getOutputStream();
            final GZIPOutputStream theGzip = new GZIPOutputStream(out);
            theGzip.write(theResult.getBytes());
            theGzip.flush();
            theGzip.close();
            out.close();
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                PollinatingController.logger.info((Object)"retrieveMatchConnections User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    @RequestMapping(value = { "/postpushnotification" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public void postPushNotification(HttpServletResponse response) {
        try {
        	logger.info("coming post push");
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
        			List<PushedNotification> result = Push.payloads("/var/lib/tomcat7/peoplehunt_pushprodv2.p12", "espana19", true, pairs);	
        			
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
        			logger.info("postPushNotification inner User Ex"+ e.getMessage());
        			//e.printStackTrace();
        		}               
            }
        }
        catch (Exception e) {           
        	logger.info("postPushNotification User Ex"+ e.getMessage());          
        }
    }
    
    @RequestMapping(value = { "/postnewmessage2/{profileid}/{recipient}" }, method = { RequestMethod.POST })
    public void postNewMessage2(@PathVariable("profileid") final Integer profileId, @PathVariable("recipient") final Integer recipient, final HttpServletRequest request, final HttpServletResponse response) {
        try {
            final StringBuffer jb = new StringBuffer();
            this.processHttpBody(request, jb);
            PollinatingController.logger.info((Object)("Content " + URLDecoder.decode(jb.toString(), "UTF-8") + "  profile id " + profileId));
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                PollinatingController.logger.info((Object)"postNewMessage User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    private void processHttpBody(final HttpServletRequest request, final StringBuffer jb) {
        String line = null;
        try {
            final BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        }
        catch (Exception e) {}
    }
    
    @RequestMapping(value = { "/sendapns" }, method = { RequestMethod.POST })
    public void sendMultiple(@RequestParam("udid") final String udid, @RequestParam("text") final String text, final HttpServletRequest request, final HttpServletResponse response) {
        try {
            PollinatingController.logger.info((Object)"sendMultiple here");
            final String[] appleDeviceIDs = { udid };
            final List<PayloadPerDevice> pairs = new Vector<PayloadPerDevice>();
            for (int i = 0; i < appleDeviceIDs.length; ++i) {
                final String deviceId = appleDeviceIDs[i];
                pairs.add(new PayloadPerDevice(PushNotificationPayload.combined(text, 0, "default"), new BasicDevice(deviceId)));
            }
            final List<PushedNotification> result = (List<PushedNotification>)Push.payloads((Object)"/usr/local/tomcat/peoplehuntv2prodreal.p12", "espana19", true, (Object)pairs);
            System.out.println("sendMultiple 2");
            for (final PushedNotification pushedNotification : result) {
                if (!pushedNotification.isSuccessful()) {
                    final Exception theProblem = pushedNotification.getException();
                    System.out.println("error ios " + theProblem.getMessage());
                    final ResponsePacket theErrorResponse = pushedNotification.getResponse();
                    if (theErrorResponse != null) {
                        System.out.println(" data packet " + theErrorResponse.getMessage());
                    }
                }
                else {
                    System.out.println("Successfully sent the notifications.");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            PollinatingController.logger.info((Object)"sendMultiple User Ex", (Throwable)e);
        }
    }
    
    @RequestMapping(value = { "/postnewmessage/{profileid}/{recipient}" }, method = { RequestMethod.POST })
    public void postNewMessage(@PathVariable("profileid") final Integer profileId, @PathVariable("recipient") final Integer recipient, final HttpServletRequest request, final HttpServletResponse response) {
        try {
            if (profileId > 0) {
                PollinatingController.logger.info((Object)"coming here");
                final Profile theProfile = this.profileService.getUserProfile(profileId);
                final Profile recipientProfile = this.profileService.getUserProfile(recipient);
                final Calendar lastMessageSent = Calendar.getInstance();
                if (recipientProfile.getLastMessageSent() != null) {
                    lastMessageSent.setTime(recipientProfile.getLastMessageSent());
                }
                final List<AsyncMessage> messages = new ArrayList<AsyncMessage>();
                final AsyncMessage message = new AsyncMessage();
                final StringBuffer processedContent = new StringBuffer();
                this.processHttpBody(request, processedContent);
                PollinatingController.logger.info((Object)"coming here 2");
                final String theContent = URLDecoder.decode(processedContent.toString(), "UTF-8");
                message.setContent(theContent);
                message.setSender(recipientProfile.getUser().getUserName());
                final Calendar timeCurrentMessage = Calendar.getInstance();
                message.setDateSent(timeCurrentMessage.getTime());
                message.setProfile(theProfile);
                messages.add(message);
                theProfile.setAsyncMessages((List) messages);
                final List<Connection> otherConnections = (List<Connection>)recipientProfile.getConnections();
                boolean containsConnection = false;
                for (final Connection connection : otherConnections) {
                    if (connection.getWhoConnected() == (int)theProfile.getId()) {
                        connection.setWhoConnected(theProfile.getId());
                        final Calendar theCal = Calendar.getInstance();
                        connection.setStatus(1);
                        connection.setInteractionTime(theCal.getTime());
                        containsConnection = true;
                    }
                }
                if (!containsConnection) {
                    final Connection connection2 = new Connection();
                    connection2.setWhoConnected(theProfile.getId());
                    connection2.setStatus(1);
                    final Calendar theCal2 = Calendar.getInstance();
                    connection2.setInteractionTime(theCal2.getTime());
                    connection2.setProfile(recipientProfile);
                    otherConnections.add(connection2);
                }
                recipientProfile.setConnections((List)otherConnections);
                this.profileService.updateProfile(recipientProfile);
                this.profileService.updateProfile(theProfile);
                final String udid = recipientProfile.getIphoneUDID();
                if (udid != null) {
                    final String udidEnd = udid.replaceAll("(<|>)", "").replace(" ", "");
                    PollinatingController.logger.info((Object)"coming here sendinfg");
                    String theMessage = String.format("%1$s just sent you a message", theProfile.getUser().getLastName());                   
                    final String[] uids = { udidEnd };
                    try{
                    	List<PayloadPerDevice> pairs = new Vector<PayloadPerDevice>();	            			
            			for(int i=0; i<uids.length; i++){								
            				//String theMessage =  String.format("%1$s connection is available now: open the app to get connected" , messageToSend);
            				String deviceId = uids[i];
            				pairs.add(new PayloadPerDevice(PushNotificationPayload.combined(theMessage, 0, "default"), new BasicDevice(deviceId)));
            			}
            			
            			List<PushedNotification> result = Push.payloads("/var/lib/tomcat7/languagehunt_prod.p12", "espana19", true, pairs);            			
            			/*for (PushedNotification pushedNotification : result) {            				
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
            			}*/
            			
            		}catch(Exception e){
            			logger.info("postPushNotification inner User Ex"+ e.getMessage());
            			//e.printStackTrace();
            		}      
                   
                }
                final long diff = timeCurrentMessage.getTimeInMillis() - lastMessageSent.getTimeInMillis();
                final long dminutes = diff / 60000L;
                PollinatingController.logger.info((Object)("minute difference 2" + dminutes));
                //if (dminutes == 0L || dminutes > 59L) {
                if (true){
                    recipientProfile.setLastMessageSent(Calendar.getInstance().getTime());
                    this.profileService.updateProfile(recipientProfile);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                final Map<String, Object> theObject = new HashMap<String, Object>();
                                theObject.put("key", "NHpudxhV9HV6zakj7-gH0A");
                                String theTemplate = "new-message-learner";
                                logger.info("Role "+ recipientProfile.getUser().getRole()+" Id "+theProfile.getId());
                                if (recipientProfile.getUser().getRole().equals("guide")) theTemplate = "new-message-languagehunt";
                                //new-message-learner
                                theObject.put("template_name", theTemplate);
                                theObject.put("template_content", new ArrayList());
                                final Map<String, Object> messageContent = new HashMap<String, Object>();
                                final List<Map<String, String>> to = new ArrayList<Map<String, String>>();
                                final Map<String, String> toMap = new HashMap<String, String>();
                                toMap.put("email", recipientProfile.getUser().getEmail());
                                toMap.put("name", recipientProfile.getUser().getLastName());
                                to.add(toMap);
                                messageContent.put("to", to);
                                final Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Reply-To", "g@languagehunt.com");
                                messageContent.put("headers", headers);
                                messageContent.put("important", false);
                                messageContent.put("merge", true);
                                messageContent.put("global_merge_vars", new ArrayList());
                                final List<Map<String, Object>> merge_varsArray = new ArrayList<Map<String, Object>>();
                                final Map<String, Object> merge_vars = new HashMap<String, Object>();
                                merge_vars.put("rcpt", recipientProfile.getUser().getEmail());
                                final List<Map<String, String>> arrayVars = new ArrayList<Map<String, String>>();
                                final Map<String, String> vars1 = new HashMap<String, String>();
                                vars1.put("name", "message");
                                vars1.put("content", theContent);
                                final Map<String, String> vars2 = new HashMap<String, String>();
                                vars2.put("name", "fullsendername");
                                vars2.put("content", theProfile.getUser().getLastName());
                                final Map<String, String> vars3 = new HashMap<String, String>();
                                vars3.put("name", "firstname");
                                vars3.put("content", recipientProfile.getUser().getLastName());
                                
                                final Map<String, String> vars4 = new HashMap<String, String>();
                                vars4.put("name", "chatlink");
                                vars4.put("content", " http://languagehunt.com/postmsg?sender="+recipient+"&recipient="+profileId);
                                
                                arrayVars.add(vars1);
                                arrayVars.add(vars2);
                                arrayVars.add(vars3);
                                arrayVars.add(vars4);
                                merge_vars.put("vars", arrayVars);
                                merge_varsArray.add(merge_vars);
                                messageContent.put("merge_vars", merge_varsArray);
                                theObject.put("message", messageContent);
                                theObject.put("async", false);
                                final Gson gson = new Gson();
                                final String json = gson.toJson((Object)theObject);
                                System.out.println(json);
                                final URL address = new URL("https://mandrillapp.com/api/1.0/messages/send-template.json");
                                final URLConnection conn = address.openConnection();
                                conn.setDoOutput(true);
                                conn.setRequestProperty("Content-Type", "application/json");
                                conn.setRequestProperty("Accept", "application/json");
                                final OutputStream writer = conn.getOutputStream();
                                writer.write(json.getBytes());
                                writer.flush();
                                final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String line;
                                while ((line = rd.readLine()) != null) {
                                    System.out.println(line);
                                }
                                rd.close();
                            }
                            catch (Exception e) {}
                        }
                    }).start();
                }
            }
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                PollinatingController.logger.info((Object)"postNewMessage User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    @RequestMapping(value = { "/sentmessages" }, method = { RequestMethod.GET })
    public void retrieveSentMessages(@RequestParam(value = "profileid", required = true) final Integer profileid, final HttpServletResponse response) {
    	
    	 try {
            
    		 Profile theProfile = this.profileService.getUserProfile(profileid);
    		 List<Map<String, Object>> msgList = new ArrayList<Map<String, Object>>();
    		 List<AsyncMessage> restMessages = (List<AsyncMessage>) inboxService.messagesSentToMe(theProfile.getUser().getUserName());
             
    		 for (AsyncMessage theMessage : theProfile.getAsyncMessages()) {
    			 boolean addedAlready = false;
    			 for (Map<String, Object> map : msgList) {
					if (map.get("username").equals(theMessage.getSender())){
						addedAlready = true;
					}
    			 }    			 
    			  			
    			 if (!addedAlready) {
    				 User theUser = userService.getByUserName(theMessage.getSender());    				 
    				 createMessageMap(msgList, theMessage, theUser);
    			 }    			
    		 }
    		 
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
             //add latest message sent to me and actual date   		 
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
             
             final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)msgList);
             response.setHeader("Content-Encoding", "gzip");
             final OutputStream out = (OutputStream)response.getOutputStream();
             final GZIPOutputStream theGzip = new GZIPOutputStream(out);
             theGzip.write(theResult.getBytes());
             theGzip.flush();
             theGzip.close();
             out.close();
         }
         catch (Exception e) {
             try {
                 response.sendError(500);
                 PollinatingController.logger.info((Object)"retrieveGroupProfilaData User Ex", (Throwable)e);
             }
             catch (IOException e1) {
                 e1.printStackTrace();
             }
         }    	
    }

	private void createMessageMap(List<Map<String, Object>> msgList,
			AsyncMessage theMessage, User theUser) {
		Map<String, Object> theMsgObj = new HashMap<String, Object>();   
		 
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
    
    
    @RequestMapping(value = { "/getopengroups" }, method = { RequestMethod.GET })
    public void retrieveGroupProfilaData(final HttpServletResponse response) {
        try {
            final List<Group> theGroups = (List<Group>)this.groupService.getGroupsByType("open");
            final Map<String, Object> theObject = new HashMap<String, Object>();
            theObject.put("open", theGroups);
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)theObject);
            response.setHeader("Content-Encoding", "gzip");
            final OutputStream out = (OutputStream)response.getOutputStream();
            final GZIPOutputStream theGzip = new GZIPOutputStream(out);
            theGzip.write(theResult.getBytes());
            theGzip.flush();
            theGzip.close();
            out.close();
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                PollinatingController.logger.info((Object)"retrieveGroupProfilaData User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    
    
    
    @RequestMapping(value = { "/retrieveinbox" }, method = { RequestMethod.GET })
    public void retrieveInbox(@RequestParam(value = "profileid", required = true) final Integer profileid, @RequestParam(value = "otherprofileid", required = true) final Integer otherProfileid, final HttpServletResponse response) {
        try {
            Profile theProfile = null;
            Profile otherProfile = null;
            theProfile = this.profileService.getUserProfile(profileid);
            otherProfile = this.profileService.getUserProfile(otherProfileid);
            final List<AsyncMessage> allMessages = (List<AsyncMessage>)theProfile.getAsyncMessages();
            for (final AsyncMessage asyncMessage : allMessages) {
                if (asyncMessage.getSender().equals(otherProfile.getUser().getUserName())) {
                    final Calendar theCal = Calendar.getInstance();
                    asyncMessage.setLastView(theCal.getTime());
                }
            }
            
            theProfile.setAsyncMessages((List)allMessages);
            this.profileService.updateProfile(theProfile);
            final HunterProfile myHunterProfile = new HunterProfile();
            myHunterProfile.setUsername(theProfile.getUser().getUserName());
            this.extractUserInfo(theProfile, myHunterProfile);
            
            final HunterProfile otherHunterProfile = new HunterProfile();
            otherHunterProfile.setUsername(otherProfile.getUser().getUserName());
            this.extractUserInfo(otherProfile, otherHunterProfile);  
          
            
            final Map<String, Object> matchData = new HashMap<String, Object>();
            matchData.put("otherurl", RestUtils.extractURLProfile(otherProfile, "http://images.crowdscanner.com/anon_nosmile.png"));
            matchData.put("otherusername", otherProfile.getUser().getUserName());
            matchData.put("name", otherProfile.getUser().getLastName());
            matchData.put("id", otherProfile.getId());
            matchData.put("email", otherProfile.getUser().getEmail());
            
            if (otherProfile.getProfileInfo() != null) {
                String bio = otherProfile.getProfileInfo().getSelfPerception();
                if (StringUtils.isBlank(bio)) {
                    bio = null;
                }
                matchData.put("bio", bio);
            }
            
            //matchData.put("help", otherHunterProfile.getHelp());
            //matchData.put("interested", otherHunterProfile.getInterested());
            //get mongo ratings and payment               
            mongoService.getMongoRatings(otherProfile.getId(), otherHunterProfile);
            //get mongo payment_type
            mongoService.getMongoPaymentType(otherProfile.getId(), otherHunterProfile);    
            //get Interests
            mongoService.getMongoInterests(otherProfile.getId(), otherHunterProfile);
            
            matchData.put("match_criteria", otherHunterProfile.getMatch_criteria());
            matchData.put("locations", otherProfile.getLocations());
            matchData.put("ratings", otherHunterProfile.getRatings());
            matchData.put("paymentType", otherHunterProfile.getPaymentType());
            matchData.put("personalInterests", otherHunterProfile.getPersonalInterests());
          
            final List<AsyncMessage> userInbox = new ArrayList<AsyncMessage>();
            for (final AsyncMessage asyncMessage2 : theProfile.getAsyncMessages()) {
                if (asyncMessage2.getSender().endsWith(otherProfile.getUser().getUserName())) {
                    asyncMessage2.setReference("mine");
                    asyncMessage2.setSenderId(profileid);
                    asyncMessage2.setSenderImageUrl(RestUtils.extractURLProfile(theProfile, ""));
                    userInbox.add(asyncMessage2);
                }
            }
            
            final List<AsyncMessage> restMessages = (List<AsyncMessage>)inboxService.messagesOtherSent(theProfile.getUser().getUserName(), otherProfile.getId());
            for (AsyncMessage asyncMessage : restMessages) {
				asyncMessage.setSenderId(otherProfileid);
				asyncMessage.setSenderImageUrl(RestUtils.extractURLProfile(otherProfile, ""));
			}
            
            userInbox.addAll(restMessages);    
            
            Collections.sort(userInbox, new Comparator<AsyncMessage>() {
                public int compare(final AsyncMessage async, final AsyncMessage async2) {
                    return async2.getDateSent().compareTo(async.getDateSent());
                }
            });
            
            
            matchData.put("inbox", userInbox);
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)matchData);
            response.setHeader("Content-Encoding", "gzip");
            final OutputStream out = (OutputStream)response.getOutputStream();
            final GZIPOutputStream theGzip = new GZIPOutputStream(out);
            theGzip.write(theResult.getBytes());
            theGzip.flush();
            theGzip.close();
            out.close();
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                PollinatingController.logger.info((Object)"retrieveInbox User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    //TODO redeploy
    @RequestMapping(value = { "/retrievehunterprofile/" }, method = { RequestMethod.GET })
    public void retrieveProfileByHuntid(@RequestParam("profileid") final Integer profileId, final HttpServletResponse response) {
        try {
            if (profileId != null && profileId > 0) {
            	
                final Profile theProfile = this.profileService.getUserProfile(profileId);
                logger.info("extract"+ theProfile);
                final HunterProfile hunterProfile = new HunterProfile();
                hunterProfile.setUsername(theProfile.getUser().getUserName());
                logger.info("username");
                this.extractUserInfo(theProfile, hunterProfile);
                logger.info("extractUserInfo");
                hunterProfile.setLocations(theProfile.getLocations());
                final List<Map<String, Object>> mapConnections = new ArrayList<Map<String, Object>>();
                for (final Connection aConnection : theProfile.getConnections()) {
                	if (aConnection.getStatus() == 1){
                		final Map<String, Object> mapConnect = new HashMap<String, Object>();
                		mapConnect.put("id", aConnection.getWhoConnected());
                		mapConnect.put("status", aConnection.getStatus());
                		mapConnect.put("matchText", "");
                		mapConnect.put("shares", 0);
                		mapConnect.put("interactionTime", aConnection.getInteractionTime());
                		mapConnections.add(mapConnect);
                	}
                }
                
                hunterProfile.setConnections(mapConnections);
                if (theProfile.getProfileInfo() != null){
                	if (theProfile.getProfileInfo().getSelfPerception() != null) {
                		if (theProfile.getProfileInfo().getSelfPerception().equals("(null)")){
                			hunterProfile.setBio("");
                		} else {
                			hunterProfile.setBio(theProfile.getProfileInfo().getSelfPerception());
                		}
                	}
                }
                
                
                logger.info("prof_service "+profileService+" inbox "+inboxService);
                
                final Map<Integer, String> restMessages = inboxService.allMessagesIHaveSent(theProfile.getId());
                hunterProfile.setLastMessages(restMessages);
                String udid = theProfile.getIphoneUDID();
                if (udid != null) {
                    udid = udid.replaceAll("(<|>)", "").replace(" ", "");
                }
                hunterProfile.setUdid(udid);
                hunterProfile.setName(theProfile.getUser().getLastName());
                hunterProfile.setProfileId(theProfile.getId());
                if(theProfile.getProfileInfo() != null) hunterProfile.setAvailableDay(theProfile.getProfileInfo().getAvailableDay());
                if(theProfile.getProfileInfo() != null) hunterProfile.setAvailableTime(theProfile.getProfileInfo().getAvailableTime());
                hunterProfile.setImageURL(RestUtils.extractURLProfile(theProfile, "http://images.crowdscanner.com/anon_nosmile.png"));
                
                //do mongo interests               
                mongoService.getMongoInterests(profileId, hunterProfile);
                //get mongo ratings and payment               
                mongoService.getMongoRatings(profileId, hunterProfile);
                //get mongo payment_type
                mongoService.getMongoPaymentType(profileId, hunterProfile);
                
                final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).exclude(new String[] { "matchItem" }).serialize((Object)hunterProfile);
                response.setHeader("Content-Encoding", "gzip");
                final OutputStream out = (OutputStream)response.getOutputStream();
                final GZIPOutputStream theGzip = new GZIPOutputStream(out);
                final byte[] gzipBuff = EncodingUtils.getBytes(theResult, "UTF-8");
                theGzip.write(gzipBuff);
                theGzip.flush();
                theGzip.close();
                out.close();
            }
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                PollinatingController.logger.info((Object)"retrievehunterprofile User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

	
    
    @RequestMapping(value = { "/retrievelikefeelers" }, method = { RequestMethod.GET })
    public void retrieveLikeFeelers(@RequestParam("likefeeler") final String likeFeeler, final HttpServletResponse response) {
        try {
            final List<Feeler> allFeelers = (List<Feeler>)this.feelerService.getFeelersLike(likeFeeler);
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).exclude(new String[] { "statuses" }).exclude(new String[] { "dateCreated" }).serialize((Object)allFeelers);
            response.setHeader("Content-Encoding", "gzip");
            final OutputStream out = (OutputStream)response.getOutputStream();
            final GZIPOutputStream theGzip = new GZIPOutputStream(out);
            final byte[] gzipBuff = EncodingUtils.getBytes(theResult, "UTF-8");
            theGzip.write(gzipBuff);
            theGzip.flush();
            theGzip.close();
            out.close();
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                PollinatingController.logger.info((Object)"retrievehunterprofile User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    private void extractUserInfo(final Profile theProfile, final HunterProfile hunterProfile) {
        for (final FoundTarget foundTarget : theProfile.getFilteredTargets()) {
            if (foundTarget.getStatusType("providing") != null) {
            	Status status = foundTarget.getStatusType("providing");
                final List<Feeler> theFeelers = (List<Feeler>)status.getFeelers();
                for (final Feeler feeler : theFeelers) {
                    hunterProfile.getHelp().put(feeler.getId(), feeler.getDescription());
                    if (status.getProficiency() != null) hunterProfile.getProficiency().put(feeler.getId(), status.getProficiency());
                }
            }
            if (foundTarget.getStatusType("looking") != null) {
                final List<Feeler> theFeelers = (List<Feeler>)foundTarget.getStatusType("looking").getFeelers();
                for (final Feeler feeler : theFeelers) {
                    hunterProfile.getInterested().put(feeler.getId(), feeler.getDescription());
                }
            }
        }
    }
}
