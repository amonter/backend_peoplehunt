package com.crowdscanner.controller.peoplehunt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crowdscanner.controller.MongoDbInit;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.service.peoplehuntv2.FeelerService;
import com.myownmotivator.service.peoplehuntv2.GroupService;
import com.myownmotivator.service.peoplehuntv2.InboxService;
import com.myownmotivator.service.peoplehuntv2.LocationService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.peoplehuntv2.model.Connection;
import com.peoplehuntv2.model.Feeler;
import com.peoplehuntv2.model.FoundTarget;
import com.peoplehuntv2.model.Location;
import com.peoplehuntv2.model.Status;

import flexjson.JSONSerializer;

@Controller
//Changes language
//TODO redeploy
public class SwipeController
{
	@Autowired
    private ProfileService profileService;
	@Autowired
    private FeelerService feelerService;
	@Autowired
    private LocationService locationService;
	@Autowired
    private UserDao userService;
	@Autowired
    private InboxService inboxService;
	@Autowired
    private GroupService groupService;
	@Autowired
	private MongoDbInit mongoService;
	
    static final Logger logger;
    
    
    
    @RequestMapping(value = { "/addmissinglang/{profileid}" }, method = { RequestMethod.POST })
    public void addMissingLanguage(@PathVariable("profileid") final Integer profileId, final HttpServletRequest request, final HttpServletResponse response) {
        try {
            final StringBuffer processedContent = new StringBuffer();
            this.processHttpBody(request, processedContent);
            final String theContent = URLDecoder.decode(processedContent.toString(), "UTF-8");
            MongoDatabase mongoDB = mongoService.mongoDB;
            MongoCollection<Document> col = mongoDB.getCollection("missing_lang");
    		Document theDoc = Document.parse(theContent);
    		col.insertOne(theDoc);	
            
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                SwipeController.logger.info((Object)"retrieveMatchConnections User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    
    
    
    
    @RequestMapping(value = { "/changeconnectionstatus/{profileid}" }, method = { RequestMethod.POST })
    public void changeConnectionStatus(@PathVariable("profileid") final Integer profileId, final HttpServletRequest request, final HttpServletResponse response) {
        try {
            final Type collectionType = new TypeToken<Collection<Map<String, Object>>>() {}.getType();
            final Gson gson = new Gson();
            final StringBuffer processedContent = new StringBuffer();
            this.processHttpBody(request, processedContent);
            final String theContent = URLDecoder.decode(processedContent.toString(), "UTF-8");
            final Collection<Map<String, Object>> theData = (Collection<Map<String, Object>>)gson.fromJson(theContent, collectionType);
            Profile aProfile = profileService.getUserProfile(profileId);
            double connectionId = 0;
            for (Map<String, Object> map : theData) {
				connectionId = (Double) map.get("id");
			}
            List<Connection> allConnections = new ArrayList<Connection>();
            for (Connection theConnection : aProfile.getConnections()) {
            	if (theConnection.getWhoConnected().intValue() == (int)connectionId){
            		theConnection.setStatus(1);
            	}
            	allConnections.add(theConnection);
			}
            aProfile.setConnections(allConnections);
            profileService.updateProfile(aProfile);
            
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                SwipeController.logger.info((Object)"retrieveMatchConnections User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    
    
    @RequestMapping(value = { "/postconnections/{profileid}" }, method = { RequestMethod.POST })
    public void postNewConnections(@PathVariable("profileid") final Integer profileId, final HttpServletRequest request, final HttpServletResponse response) {
        try {
            final Type collectionType = new TypeToken<Collection<Map<String, Object>>>() {}.getType();
            final Gson gson = new Gson();
            final StringBuffer processedContent = new StringBuffer();
            this.processHttpBody(request, processedContent);
            final String theContent = URLDecoder.decode(processedContent.toString(), "UTF-8");
            final Collection<Map<String, Object>> theData = (Collection<Map<String, Object>>)gson.fromJson(theContent, collectionType);
            profileService.addAllConnections(theData, profileId);
        }
        catch (Exception e) {
            try {
                response.sendError(500);
                SwipeController.logger.info((Object)"retrieveMatchConnections User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    @RequestMapping(value = { "/getlocations" }, method = { RequestMethod.GET })
    public void getLocations(final HttpServletResponse response) {
        try {
            final List<Location> allLocations = (List<Location>) locationService.getAllLocations();
            final Map<Integer, String> dataLocation = new HashMap<Integer, String>();
            for (final Location location : allLocations) {
                dataLocation.put(location.getId(), location.getLocation());
            }
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)dataLocation);
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
                SwipeController.logger.info((Object)"retrieveGroupProfilaData User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    
    @RequestMapping(value = { "/getlocationscoord" }, method = { RequestMethod.GET })
    public void getLocationsCoord(final HttpServletResponse response) {
        try {
            final List<Location> allLocations = (List<Location>) locationService.getAllLocations();
            List<Map<String, Object>> locationArray = new ArrayList<Map<String, Object>>();
            for (final Location location : allLocations) {
            	Map<String, Object> dataLocation = new HashMap<String, Object>();
            	dataLocation.put("id", location.getId());
            	dataLocation.put("description", location.getLocation());
            	dataLocation.put("latitude", location.getLatitude());
            	dataLocation.put("longitude", location.getLongitude());
            	locationArray.add(dataLocation);
            }
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize(locationArray);
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
                SwipeController.logger.info((Object)"getLocationsCoord User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    
    @RequestMapping(value = { "/setlocations/{profileid}" }, method = { RequestMethod.GET })
    public void setLocations(@PathVariable("profileid") final Integer profileId, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
    
    	final Profile theProfile = profileService.getUserProfile(profileId);
    	locationService.deleteLocationLink(theProfile);
		 	
    	logger.info("setLocation 3");
    }
    
    
    //TODO redeploy
    @RequestMapping(value = { "/addallfeelers/{profileid}" }, method = { RequestMethod.POST })
    public void addAllFeelers(@PathVariable("profileid") final Integer profileId, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        
    	final Profile theProfile = profileService.getUserProfile(profileId);
        final Gson gson = new Gson();
        final Map<String, Object> map = new HashMap<String, Object>();
        try {
        	
            final StringBuffer processedContent = new StringBuffer();
            processHttpBody(request, processedContent);
            final Map<String, Object> feelerData = gson.fromJson(URLDecoder.decode(processedContent.toString(), "UTF-8"), map.getClass());
            final Map<String, String> interestedIn = (Map<String, String>) feelerData.get("interested");
            final Map<String, String> helpWith = (Map<String, String>) feelerData.get("help");
            final Map<String, Object> proficiency = (Map<String, Object>) feelerData.get("proficiency");
            final List<Map<String, Object>> locations = (List<Map<String, Object>>) feelerData.get("locations");
            final List<FoundTarget> interestedFeelers = addFeelerDataMap(theProfile, interestedIn, null,"looking");
            final List<FoundTarget> helpWithFeelers = addFeelerDataMap(theProfile, helpWith, proficiency, "providing");
            interestedFeelers.addAll(helpWithFeelers);
            //clear locations if it contains some
            //locationService.deleteLocationLink(theProfile);    		
            
            final List<Location> setLocations = new ArrayList<Location>();
            for (Map<String, Object> locationMap : locations) {
                double intKey = (Double) locationMap.get("id");
                Location theLocation = locationService.getLocationById((int)intKey);               
                if (theLocation == null) {
                    theLocation = new Location();
                    theLocation.setCreationDate(new Date());
                    theLocation.setLocation((String)locationMap.get("location"));
                    theLocation.setRanking(1);
                    theLocation.setLatitude("37.77493");
                    theLocation.setLongitude("-122.419416");
                   
                    theLocation = locationService.addNewLocation(theLocation);
                    theLocation.getProfiles().add(theProfile);
                    setLocations.add(theLocation);
                    /*new Thread(new Runnable() {
                        public void run() {
                            try {
                                final Map<String, Object> theObject = new HashMap<String, Object>();
                                theObject.put("key", "NHpudxhV9HV6zakj7-gH0A");
                                theObject.put("template_name", "peoplehunt-new-location");
                                theObject.put("template_content", new ArrayList());
                                final Map<String, Object> messageContent = new HashMap<String, Object>();
                                final List<Map<String, String>> to = new ArrayList<Map<String, String>>();
                                final Map<String, String> toMap = new HashMap<String, String>();
                                toMap.put("email", "ellen@peoplehunt.me");
                                toMap.put("name", "Ellen");
                                to.add(toMap);
                                messageContent.put("to", to);
                                final Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Reply-To", "ellen@peoplehunt.me");
                                messageContent.put("headers", headers);
                                messageContent.put("important", false);
                                messageContent.put("merge", true);
                                messageContent.put("global_merge_vars", new ArrayList());
                                final List<Map<String, Object>> merge_varsArray = new ArrayList<Map<String, Object>>();
                                final Map<String, Object> merge_vars = new HashMap<String, Object>();
                                merge_vars.put("rcpt", "ellen@peoplehunt.me");
                                final List<Map<String, String>> arrayVars = new ArrayList<Map<String, String>>();
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
                    }).start();*/
                    
                }
                else {
                    List<Location> existingLocations = (List<Location>)theProfile.getLocations();
                    boolean hasLocation = false;
                    for (Location profileLocation : existingLocations) {
                        if (profileLocation.getId() == (int)intKey) {
                            hasLocation = true;
                            break;
                        }
                    }
                    if (!hasLocation) {
                        theLocation.getProfiles().add(theProfile);
                        setLocations.add(theLocation);
                    }
                	//theLocation.getProfiles().add(theProfile);
                	//setLocations.add(theLocation);
                }
            }
            
            theProfile.setLocations(setLocations);
            if (!theProfile.getFoundTargets().isEmpty()) {
                interestedFeelers.addAll(theProfile.getFoundTargets());
            }
            theProfile.setFoundTargets(interestedFeelers);
            profileService.updateProfile(theProfile);
            response.setStatus(200);
        }
        catch (JsonSyntaxException e) {
            response.sendError(500);
            SwipeController.logger.info("addAllFeelers User Ex", (Throwable)e);
            e.printStackTrace();
        }
        catch (Exception e2) {
            response.sendError(500);
            SwipeController.logger.info("addAllFeelers User Ex", (Throwable)e2);
            e2.printStackTrace();
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
    
    private List<FoundTarget> addFeelerDataMap(final Profile theProfile, final Map<String, String> dataMap, final Map<String, Object> proficiencyMap,  
    		final String feelerMode) {
    	
    	List<FoundTarget> foundTargets = theProfile.getFilteredTargets();
    	 for (FoundTarget theFoundTarget : foundTargets) {
         	logger.info(theFoundTarget.getId() +" size "+theFoundTarget.getStatuses());
         	for (Status status : theFoundTarget.getStatuses()) {
         		System.out.println(theFoundTarget.getId() +" status "+status.getFoundStatus());
			}
    	 }
    	
    	List<FoundTarget> oldFoundTargets = new ArrayList<FoundTarget>(foundTargets);
    	
    	Collections.copy(oldFoundTargets, foundTargets);
    	feelerService.deleteFoundTargetsBatch(theProfile, feelerMode);   	
    	
    	List<FoundTarget> targetsToFound = new ArrayList<FoundTarget>();    	
        for (final String theKey : dataMap.keySet()) {
            final Integer intKey = Integer.valueOf(theKey);
            final FoundTarget aFoundTarget = new FoundTarget();
            aFoundTarget.getFoundProfiles().add(theProfile);
            final Status theStatus = new Status();
            theStatus.setFoundStatus(feelerMode);
            theStatus.setFoundTarget(aFoundTarget);
            if (proficiencyMap != null){            	
            	if (proficiencyMap.containsKey(theKey)) {
            		double proficiency = (Double) proficiencyMap.get(theKey);
            		theStatus.setProficiency((int)proficiency);
            	}
            }
            aFoundTarget.getStatuses().add(theStatus);
            boolean feelerExists = false;
            Feeler theFeeler = this.feelerService.getFeelerById(intKey);
            if (theFeeler == null) {
                theFeeler = new Feeler();
                theFeeler.setDescription((String)dataMap.get(theKey));
                
            } else {            	  
            	
	            for (FoundTarget theFoundTarget : oldFoundTargets) {
	            	logger.info(theFoundTarget.getId() +" comi1 "+oldFoundTargets.size()+" size "+theFoundTarget.getStatuses());
	            	for (Status status : theFoundTarget.getStatuses()) {
	            		System.out.println(theFoundTarget.getId() +" status "+status.getFoundStatus());
					}
	            	
	            	logger.info("status type  "+theFoundTarget.getStatusType(feelerMode)+" "+theFoundTarget);
	            	 if (theFoundTarget.getStatusType(feelerMode) != null) {
	            		 logger.info("not null status type "+oldFoundTargets.size());
	                     final List<Feeler> theFeelers = (List<Feeler>)theFoundTarget.getStatusType(feelerMode).getFeelers();
	                     for (final Feeler feeler : theFeelers) {
	                    	 if (feeler.getId().intValue() == intKey){
	                    		 logger.info("Feeler id "+feeler.getId());
	                    		 feelerExists = true;
	                    		 if (feelerMode.equals("looking")) theFeeler.setLookingRank(theFeeler.getLookingRank() + 1);
	                    		 if (feelerMode.equals("providing")) theFeeler.setProvidingRank(theFeeler.getProvidingRank() + 1);
	                    		 logger.info("update feeler mode "+ feelerMode);
	                    		 feelerService.addNewFeeler(theFeeler);  
	                    		 break;
	                    	 }
	                     }
	                 }                
				}
            }
            
            if (!feelerExists){
            	//if (feelerMode.equals("looking")) theFeeler.setLookingRank(theFeeler.getLookingRank() + 1);
            	//if (feelerMode.equals("providing")) theFeeler.setProvidingRank(theFeeler.getProvidingRank() + 1);
            	theFeeler = feelerService.addNewFeeler(theFeeler);  
            }
            
                   
            final Calendar theCal = Calendar.getInstance();
            theFeeler.setDateCreated(theCal.getTime());
            theStatus.getFeelers().add(theFeeler);            
            
            targetsToFound.add(aFoundTarget);
            
        }
        return targetsToFound;
    }
    
    
    
    @RequestMapping(value = { "/gethelp" }, method = { RequestMethod.GET })
    public void getHelp(HttpServletResponse response) {
        try {
            
            final List<Feeler> backDataFeelers = getSortedProviding();
            List<Map<String, Object>> interestedList = new ArrayList<Map<String, Object>>();            
            for (final Feeler feeler : backDataFeelers) {
            	Map<String, Object> compressData = new LinkedHashMap<String, Object>();
            	compressData.put("id", feeler.getId());
            	compressData.put("description", feeler.getDescription());
            	compressData.put("interested", feeler.getLookingRank());
            	compressData.put("help", feeler.getProvidingRank());
            	interestedList.add(compressData);
            }
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize(interestedList);
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
                SwipeController.logger.info((Object)"retrieveGroupProfilaData User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    
    @RequestMapping(value = { "/getinterested" }, method = { RequestMethod.GET })
    public void getInterested(HttpServletResponse response) {
        try {
            
        	List<Feeler> lookingFeelers = (List<Feeler>)feelerService.getRankedLookingFor();		 
    		//List<Feeler> providingFeelers = (List<Feeler>) feelerService.getRankedProvidingFor();
    		//String mode = "providing";		
    		//providingFeelers = filterFeelers(providingFeelers, mode);				       
            
    		List<Map<String, Object>> interestedList = new ArrayList<Map<String,Object>>();
            for (final Feeler feeler : lookingFeelers) {
            	if (feeler.getId() == 9){//Mandarin 
            		Map<String, Object> compressData = new LinkedHashMap<String, Object>();
	            	compressData.put("id", feeler.getId());
	            	compressData.put("description", feeler.getDescription());
	            	interestedList.add(compressData);
            	}
            	
            	if (feeler.getId() == 2){//English 
            		Map<String, Object> compressData = new LinkedHashMap<String, Object>();
	            	compressData.put("id", feeler.getId());
	            	compressData.put("description", feeler.getDescription());
	            	interestedList.add(compressData);
            	}
            	
            	if (feeler.getId() == 3){//French 
            		Map<String, Object> compressData = new LinkedHashMap<String, Object>();
	            	compressData.put("id", feeler.getId());
	            	compressData.put("description", feeler.getDescription());
	            	interestedList.add(compressData);
            	}
            	
             	/*boolean hasProviders = false;
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
             	}*/
             }
            
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize(interestedList);
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
                SwipeController.logger.info((Object)"retrieveGroupProfilaData User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
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
    
    
    @RequestMapping(value = { "/getlookingfeelers" }, method = { RequestMethod.GET })
    public void getFeelersLooking(@RequestParam("profileid") final Integer profileId, final HttpServletResponse response) {
        try {
            
            final List<Feeler> backDataFeelers = sortLookingData();
            
            final Map<Integer, String> compressData = new LinkedHashMap<Integer, String>();
            for (final Feeler feeler : backDataFeelers) {
                compressData.put(feeler.getId(), feeler.getDescription());
            }
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize(compressData);
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
                SwipeController.logger.info((Object)"retrieveGroupProfilaData User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

	private List<Feeler> sortLookingData() {
		
		final List<Feeler> backDataFeelers = (List<Feeler>)feelerService.getRankedLookingFor();
		/*final List<Feeler> dateSortedFeelers = new ArrayList<Feeler>(backDataFeelers);
		Collections.sort(dateSortedFeelers, new Comparator<Feeler>() {
		    public int compare(final Feeler feeler, final Feeler feeler2) {
		        return feeler2.getDateCreated().compareTo(feeler.getDateCreated());
		    }
		});
		
		final List<Feeler> topLatestFeelers = dateSortedFeelers.subList(0, 2);
		Iterator<Feeler> it = backDataFeelers.iterator();
		while (it.hasNext()) {
		    final Feeler feeler2 = it.next();
		    if (topLatestFeelers.contains(feeler2)) {
		        it.remove();
		    }
		}
		final Iterator<Feeler> it2 = backDataFeelers.iterator();
		while (it2.hasNext()) {
		    final Feeler feeler2 = it2.next();
		    backDataFeelers.add(0, feeler2);
		}*/
		return backDataFeelers;
	}
    
    
    
    @RequestMapping(value = { "/retrieveallprofileids" }, method = { RequestMethod.GET })
    public void retrieveAllProfiles(final HttpServletResponse response) {
        try {
            final List<Profile> allProfiles = (List<Profile>)this.profileService.getAllProfiles();
            final List<Integer> profileIds = new ArrayList<Integer>();
            for (final Profile theProfile : allProfiles) {
                profileIds.add(theProfile.getId());
            }
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)profileIds);
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
                SwipeController.logger.info((Object)"processQuestionAlgorithm User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    @RequestMapping(value = { "/getprovidingfeelers" }, method = { RequestMethod.GET })
    public void getFeelersProviding(@RequestParam("profileid") final Integer profileId, final HttpServletResponse response) {
        try {
            
            final List<Feeler> backDataFeelers = getSortedProviding();
            final Map<Integer, String> compressData = new LinkedHashMap<Integer, String>();
            for (final Feeler feeler : backDataFeelers) {
                compressData.put(feeler.getId(), feeler.getDescription());
            }
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)compressData);
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
                SwipeController.logger.info((Object)"retrieveGroupProfilaData User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

	private List<Feeler> getSortedProviding() {
		final List<Feeler> backDataFeelers = (List<Feeler>)feelerService.getRankedProvidingFor();
		/*final List<Feeler> dateSortedFeelers = new ArrayList<Feeler>(backDataFeelers);
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
		final Iterator<Feeler> i$3 = backDataFeelers.iterator();
		while (i$3.hasNext()) {
		    final Feeler feeler2 = i$3.next();
		    backDataFeelers.add(0, feeler2);
		}*/
		return backDataFeelers;
	}
    
    static {
        logger = Logger.getLogger(SwipeController.class);
    }
}