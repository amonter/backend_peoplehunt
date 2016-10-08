package com.crowdscanner.controller.languagehunt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crowdscanner.controller.MongoDbInit;
import com.crowdscanner.controller.utils.RestUtils;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.profile.ProfileInfo;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;


@Controller
public class LearnerController {
	
	final static Logger logger = Logger.getLogger(LearnerController.class);    
    
	@Autowired
    private ProfileService profileService;
	
	@Autowired
    private UserDao userService;
	
	@Autowired
	private MongoDbInit mongoService;

    
	
	 @RequestMapping(value = { "/dummyreq" }, method = { RequestMethod.POST })
	 public void dummyRequest (final HttpServletRequest request, final HttpServletResponse response) {
	
		try {
			
			Map<String, Object> theMap = new HashMap<String, Object>();
			theMap.put("profile_id", 21);
			theMap.put("data", "this is just dummy data for sure");
			
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(theMap);				
			
			//response.setHeader("Content-Encoding", "gzip");
			OutputStream out = response.getOutputStream();
			//GZIPOutputStream theGzip = new GZIPOutputStream(out);                                                                                                             
			out.write(theResult.getBytes());			
				
			out.flush();
			out.close();
			out.close();	
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}	 
		 
	 }
	
	
	
	
	 @RequestMapping(value = { "/addnewguide" }, method = { RequestMethod.POST })
	    public void postNewMessage(final HttpServletRequest request, final HttpServletResponse response) {
	        try {
	        	
	        	final StringBuffer processedContent = new StringBuffer();
	        	this.processHttpBody(request, processedContent);	        	
	        	HashMap<String, Object> theMapResult = new JSONDeserializer<HashMap<String, Object>>().deserialize(URLDecoder.decode(processedContent.toString(), "UTF-8"));
				
	        	//save the user
	        	User theUser = RestUtils.setUser((String)theMapResult.get("name"), (String)theMapResult.get("email"), (String)theMapResult.get("username"), "espana19");
				File file = new File();
				file.setFileName("profilePhoto");
				file.setFileName("facebookProfilePhoto_url");
				file.setFileContent(((String)theMapResult.get("name")).getBytes());
				theUser.setRole("guide");
				theUser.getProfile().getFiles().add(file);							
				userService.saveUser(theUser);
	        	//add Interests								
				MongoCollection<Document> col = mongoService.mongoDB.getCollection("interests");
				Map<String, Object> mapInterests = new HashMap<String, Object>();
				mapInterests.put("profile_id", theUser.getProfile().getId());
				//get interests and assign 
				List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
				for (Map.Entry<String, Object> theInterests : ((Map<String, Object>)theMapResult.get("interests")).entrySet()) {
					Map<String, Object> anInterest = new HashMap<String, Object>();
					anInterest.put("name", theInterests.getValue());
					anInterest.put("id", Math.random() * ( 10000000 - 1000000 ));
					dataList.add(anInterest);					
				}
				Map<String, Object> theDataMap = new HashMap<String, Object>();
				theDataMap.put("data", dataList);
				
				Map<String, Object> theLikesMap = new HashMap<String, Object>();
				theLikesMap.put("likes", theDataMap);				
				mapInterests.put("interests", mapInterests);
				
	        	
	        	
	        	
	        		        	
	        } catch (Exception e) {            
	            
	        	logger.info("saveProfileInfo", e);
	        	e.printStackTrace();	            
	        }	
	 }
	
    @RequestMapping(value = { "/saveprofileinfo" }, method = { RequestMethod.POST })
    public void saveProfileInfo(@RequestParam("profileid") final Integer profileId, @RequestParam(value = "bio", required = false) String bio, @RequestParam(value = "interests", required = false) String interests, @RequestParam(value = "currentCity", required = false) String currentCity, 
    		@RequestParam(value = "avail_day", required = false) String day,@RequestParam(value = "avail_time", required = false) String time, final HttpServletResponse response, final HttpServletRequest request) {       
        
        try {                
        
	        Profile theProfile = profileService.getUserProfile(profileId);        
	       
	        ProfileInfo theProfileInfo = theProfile.getProfileInfo();
	        if (theProfileInfo == null) {
	            theProfileInfo = new ProfileInfo();
	        }
	        theProfileInfo.setProfile(theProfile);
	        if (bio.equals("(null)") || StringUtils.isBlank(bio)) {
	            bio = "none";
	        }
	               
	        
	        theProfileInfo.setSelfPerception(bio);
	        theProfileInfo.setAvailableDay(day);
	        theProfileInfo.setAvailableTime(time);
	        
	        theProfile.setProfileInfo(theProfileInfo);
	        profileService.updateProfile(theProfile);
	        //process interests
	        /*String [] allInterests = interests.split(",");
	        if (allInterests.length > 0){
	        	
		        List<Map<String, Object>> interestList = new ArrayList<Map<String, Object>>();
		        for (int i = 0; i < allInterests.length; i++) {
					Map<String, Object> interest = new HashMap<String, Object>();
					interest.put("name", allInterests[i]);
					interest.put("id", (int)(Math.random() * 999999999));
					interestList.add(interest);
				}
		        
		        Map<String, Object> object = new HashMap<String, Object>();
		        object.put("data", interestList);	        
		        Map<String, Object> object2 = new HashMap<String, Object>();
		        object2.put("likes", object);
		        Map<String, Object> object3 = new HashMap<String, Object>();
		        object3.put("interests", object2);
		        object3.put("profile_id", profileId);
		        String jsonResult = gson.toJson(object3);
		        //add to mongoDB
		        MongoDatabase db = mongoService.mongoDB;				
				MongoCollection<Document> col = db.getCollection("interests");			
				Document theDoc = Document.parse(jsonResult);
				col.insertOne(theDoc);		        
		        
		        theProfileInfo.setInterests(interests);		       
		        theProfile.setProfileInfo(theProfileInfo);
		        profileService.updateProfile(theProfile);	        
	        }*/
        
        } catch (Exception e) {            
            
        	logger.info("saveProfileInfo", e);
        	e.printStackTrace();
            
        }
    }    
    
    //addtransaction
    @RequestMapping(value = { "/addtransaction" }, method = { RequestMethod.POST })
    public void addMissingLanguage(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            final StringBuffer processedContent = new StringBuffer();
            processHttpBody(request, processedContent);            
            final String theContent = URLDecoder.decode(processedContent.toString(), "UTF-8");
            logger.info("json "+theContent);
            MongoDatabase mongoDB = mongoService.mongoDB;
            MongoCollection<Document> col = mongoDB.getCollection("transactions");
    		Document theDoc = Document.parse(theContent);
    		col.insertOne(theDoc);	
            
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                logger.info((Object)"retrieveMatchConnections User Ex", (Throwable)e);
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
    
    
    
    
    
}
