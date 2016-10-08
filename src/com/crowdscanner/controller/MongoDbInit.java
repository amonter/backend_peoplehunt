package com.crowdscanner.controller;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.crowdscanner.model.HunterProfile;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Service("mongoService")
public class MongoDbInit {
	
	public DB mongoDatabase = null;
	public MongoDatabase mongoDB;
	
	public MongoDbInit() {
		// TODO Auto-generated constructor stub
		 try {
 			MongoClient mongoClient = new MongoClient( "50.19.45.37" , 27017 );
 			this.mongoDatabase = mongoClient.getDB( "peoplehunt" );			
 			this.mongoDB = mongoClient.getDatabase("peoplehunt");
 			
 			
 		} catch (Exception e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		} 
	}
	
	
	
	public void getMongoRatings(Integer profileId, HunterProfile hunterProfile) {
		
		MongoCollection<Document> paymentType = mongoDB.getCollection("ratings");
		Document doc = paymentType.find(eq("profile_id", profileId)).first();
		if (doc != null) hunterProfile.getRatings().putAll(doc);
	}
	
	public void getMongoPaymentType(Integer profileId, HunterProfile hunterProfile) {
		
		MongoCollection<Document> paymentType = mongoDB.getCollection("payment_types");
		Document doc = paymentType.find(eq("profile_id", profileId)).first();
		if (doc != null) hunterProfile.getPaymentType().putAll(doc);	
	}
	
	public void getMongoInterests(final Integer profileId, HunterProfile hunterProfile) {
		
		DBCollection col = mongoDatabase.getCollection("interests");	
		
		//logger.info("asking mongo "+id);
		BasicDBObject query = new BasicDBObject("profile_id", profileId);
		DBCursor cursor = col.find(query);
		DBObject dbObj = null;
		try {
		   while(cursor.hasNext()) {
			   dbObj = cursor.next();					   
		   }
		} finally {
		   cursor.close();
		}
		
		//check if interests
		if (dbObj != null && dbObj.get("interests") != null) {				
			BasicDBObject interestsObj = (BasicDBObject) dbObj.get("interests");				
			BasicDBObject skillsObj = (BasicDBObject) interestsObj.get("skills");
			
			if (skillsObj != null) {
				List<Map<String, Object>> theMapList = new ArrayList<Map<String,Object>>();
				BasicDBList valuesObj = (BasicDBList) skillsObj.get("values");
				for (int i = 0; i < valuesObj.size(); i++) {
					BasicDBObject valueObj = (BasicDBObject) valuesObj.get(i);
					BasicDBObject theSkillObj = (BasicDBObject) valueObj.get("skill");
					theMapList.add(theSkillObj);
					if (i == 5) break;
				}			
				hunterProfile.setPersonalInterests(theMapList);					
			}
			
			BasicDBObject likesObj = (BasicDBObject) interestsObj.get("likes");
			if (likesObj != null){
				List<Map<String, Object>> theMapList = new ArrayList<Map<String,Object>>();
				BasicDBList likesList = (BasicDBList) likesObj.get("data");
				for (int i = 0; i < likesList.size(); i++) {
					BasicDBObject valueObj = (BasicDBObject) likesList.get(i);						
					theMapList.add(valueObj.toMap());
					if (i == 5) break;
				}
				hunterProfile.setPersonalInterests(theMapList);	
			}				
		}
		
		/*List<Map<String, Object>> theInterest = new ArrayList<Map<String, Object>>();
		Map<String, Object> singleInterest = new HashMap<String, Object>();
		singleInterest.put("name", "Reading novels");
		theInterest.add(singleInterest);
		hunterProfile.setPersonalInterests(theInterest);*/
		
	}
}
