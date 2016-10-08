package com.crowdscanner.controller.peoplehunt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crowdscanner.controller.PollinatingController;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.myownmotivator.service.peoplehuntv2.FeelerService;
import com.myownmotivator.service.peoplehuntv2.GroupService;
import com.myownmotivator.service.peoplehuntv2.LocationService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.peoplehuntv2.model.Feeler;
import com.peoplehuntv2.model.Location;

import flexjson.JSONSerializer;


@Controller
//TODO redeploy
public class LocationController {
	
	@Autowired
    private ProfileService profileService;
	@Autowired
    private FeelerService feelerService;
	@Autowired
    private LocationService locationService;
	@Autowired
    private UserDao userService;	
	@Autowired
    private GroupService groupService;
    static final Logger logger;
	
	
    @RequestMapping(value = { "/gethelp2" }, method = { RequestMethod.GET })
    public void getHelp(HttpServletResponse response) {
        try {
            
        	final List<Feeler> backDataFeelers = (List<Feeler>)feelerService.getRankedProvidingFor();
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
    
    
    
    @RequestMapping(value = { "/getoffers" }, method = { RequestMethod.GET })
    public void getOffers(HttpServletResponse response) {
        try {
            
        	MongoClient mongoClient = new MongoClient( "54.227.110.249" , 27017 );
			DB db = mongoClient.getDB( "peoplehunt" );	
			DBCollection col = db.getCollection("offers");									
			DBCursor cursor = col.find();
			List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
			try {
			   while(cursor.hasNext()) {
				   DBObject theObj = cursor.next();
				   String description = (String) theObj.get("description");
				   double theId = (Double) theObj.get("id");		
				   Map<String, Object> aMap = new HashMap<String, Object>();
				   aMap.put("id", (int)theId);
				   aMap.put("description", description);
				   mapList.add(aMap);
			       
			   }
			} finally {
			   cursor.close();
			}		
			
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize(mapList);
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
    
    
    
    @RequestMapping(value = { "/loclatlong" }, method = { RequestMethod.POST })
    @SuppressWarnings("unchecked")
    public void getLatLongLocations(HttpServletRequest request, HttpServletResponse response) {    	
    	       
         try {
        	 logger.info("coming here 1");
	    	 final StringBuffer processedContent = new StringBuffer();
	         processHttpBody(request, processedContent);
	         logger.info("coming here 2");
	         final Gson gson = new Gson();
	         final Map<String, Object> map = new HashMap<String, Object>();  
			
			final Map<String, Object> locationData = gson.fromJson(URLDecoder.decode(processedContent.toString(), "UTF-8"), map.getClass());
			//iterate thought json to get lat long list
			 logger.info("coming here 3 "+ locationData);
	        List<Map<String, Object>> listLatLongs = (List<Map<String, Object>>) locationData.get("locations");
	        logger.info("coming here 4");
	        List<Location> allLocations = new ArrayList<Location>();
			for (Map<String, Object> mapLocation : listLatLongs) {
				//retrieve locations for the first 
				double lat = Double.valueOf((String)mapLocation.get("latitude"));
				double lgt = Double.valueOf((String)mapLocation.get("longitude"));
				logger.info("coming here 5");
				//retrieve from back end location and determine the distance location and if radius is good add to list
				List<Location> radiusLocations = locationService.getLocationsByLatLong(lat, lgt, 20000);
				allLocations.addAll(radiusLocations);         	
			}         
	         
	         //add the hackerspace list to the mix
			String jsonRes = gson.toJson(allLocations);
			response.setHeader("Content-Encoding", "gzip");
            final OutputStream out = (OutputStream)response.getOutputStream();
            final GZIPOutputStream theGzip = new GZIPOutputStream(out);
            theGzip.write(jsonRes.getBytes());
            theGzip.flush();
            theGzip.close();
            out.close();		
			
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			  LocationController.logger.info((Object)"getLatLongLocations User Ex", (Throwable)e);
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
    
    static {
        logger = Logger.getLogger(LocationController.class);
    }
}
