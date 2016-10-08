package com.crowdscanner.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.crowdscanner.controller.jsonobject.CountryJson;
import com.crowdscanner.controller.jsonobject.GeoLocationJson;
import com.crowdscanner.controller.jsonobject.PlaceMarkJson;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.service.questions.QuestionDao;

import flexjson.JSONDeserializer;

@Component
@Transactional
public class RestControllerUtil {

@Resource
private QuestionDao questionService;	

final static Logger logger = Logger.getLogger(RestControllerUtil.class);



public  void processPushNotification(final Question theSavedQuestion, String username, final String path) {	
	
	
	final Question theQuestion = questionService.findQuestionParentByPhoneId(theSavedQuestion.getQuestionPhoneId());
	final String phoneUIToken = theQuestion.getProfiles().get(0).getIphoneUDID();
	
	
	new Thread(new Runnable(){
		@Override
		public void run() {
	
			if (!theSavedQuestion.getParent()) {				
			
				if (!StringUtils.isEmpty(phoneUIToken)) {
					
					try {
						
						/*
						theQuestion.setHasAnswers(true);
						questionService.saveQuestion(theQuestion);					
					    PayLoad payLoad = new Payload();
					    String abbreviated = StringUtils.abbreviate(theQuestion.getQuestionDescription(), 35);
					    String theQuestionText = "The question '"+abbreviated+"' has been answered";
					    payLoad.addAlert(theQuestionText);
					    payLoad.addBadge(1);
					    payLoad.addSound("default");
					    payLoad.addCustomDictionary("questionPhoneId", theQuestion.getQuestionPhoneId());
			
					    PushNotificationManager pushManager = PushNotificationManager.getInstance();
					    String theDevice = "device_"+theQuestion.getId();					  
					    pushManager.addDevice(theDevice, phoneUIToken);					    												
			
					    // Connect to APNs
					    logger.info("push notification cert path:" + path);					
					    
					    String pushURL =  propertiesBean.getProperties().getProperty("pushURL");
					    String password =  propertiesBean.getProperties().getProperty("password");
					    pushManager.initializeConnection(pushURL, 2195, 
					    		path, password, 
					    SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);							   
					    Device client = pushManager.getDevice(theDevice);			
					    
					    System.out.println(client.getToken() +" "+client.getId());
			
					    // Send Push					   
					    PushNotificationManager.getInstance().sendNotification(client, payLoad);
					    PushNotificationManager.getInstance().removeDevice(theDevice);
					    */
					 }
					 catch (Exception e) {
						 e.printStackTrace();
					 }
				}	
			}
		}
	}).start();	
}


	
public  void processQuestionLocation(final Question theSavedQuestion) {
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				
				String theLatitude = theSavedQuestion.getLatitude();
				String theLongitude =  theSavedQuestion.getLongitude();
				if (!theLatitude.equals("0") || !theLatitude.equals("0"))  {
						
						StringBuffer buffer = new StringBuffer();
						
						try {
		
							String urlString = String.format("http://maps.google.com/maps/geo?q=%1$s,%2$s&output=json&sensor=true_or_false&key=ABQIAAAAqOLU1ZmjoAJhpAUMXWu3fBTTib4Hydy0wq4MxfvJ2obqaysWGBQVQ9uzd6jz0_TA1bCFVmA_ZRr_Ng",
									new Object[]{theLatitude,theLongitude});
							URL url = new URL(urlString);
							URLConnection conn = url.openConnection();
							
							conn.setDoOutput(true);
							OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());		
							wr.flush();
		
							// Get the response
							BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							String line;
							while ((line = rd.readLine()) != null) {
		
								buffer.append(line);
								buffer.append("\n");
							}
							wr.close();
							rd.close();					
							
							GeoLocationJson locationJson = new JSONDeserializer<GeoLocationJson>().use(null, GeoLocationJson.class)
							.deserialize(buffer.toString()); 
							String country = null;
							String state = null;				
							
							if (locationJson.getStatus().getCode() == 200) {
								
								if (locationJson.getPlacemark().size()>0) {
									
									PlaceMarkJson thePLacemark = locationJson.getPlacemark().get(0);
									CountryJson theCountry = thePLacemark.getAddressDetails().getCountry();
									country = theCountry.getCountryName();
									if (theCountry.getAdministrativeArea() == null) {								
										state = theCountry.getSubAdministrativeArea().getSubAdministrativeAreaName();
									}
									else {								
										state = theCountry.getAdministrativeArea().getAdministrativeAreaName();
									}
								}								
							}
							else {
								
								theSavedQuestion.setHelpWith("");
								theSavedQuestion.setHuntingFor("the world");
							}
							
							theSavedQuestion.setHelpWith(country);
							theSavedQuestion.setHuntingFor(state);
							questionService.saveQuestion(theSavedQuestion);
						
		
						} catch (Exception e) {
		
							e.printStackTrace();
						}				
				}
				else {
					
					theSavedQuestion.setHelpWith("");
					theSavedQuestion.setHuntingFor("the world");
					questionService.saveQuestion(theSavedQuestion);
					
				}
			}	
		
		}).start();
	}
	

public  void processTwitteImage(final String username) {
	
	//new Thread(new Runnable(){
		//@Override
		//public void run() {
			
	//Twitter twitter = new Twitter();
	//twitter.getUserDetail(id)

		//}			
	//}).start();
}

private void parseTwitterXML(String twiterXml, Map<String, String> theXMLMap) {
	//get the factory
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	try {

		//Using factory get an instance of document builder
		DocumentBuilder db = dbf.newDocumentBuilder();

		//parse using builder to get DOM representation of the XML file
		Document dom  = db.parse(new InputSource(new StringReader(twiterXml.trim())));
		
		NodeList nodes_i = dom.getDocumentElement().getChildNodes();			
		if(nodes_i != null && nodes_i.getLength() > 0) {
			for(int i = 0 ; i < nodes_i.getLength();i++) {
				
				String tagName = nodes_i.item(i).getNodeName();
				String tagValue = nodes_i.item(i).getTextContent();					
				theXMLMap.put(tagName, tagValue);
			}
		}

	}	catch(ParserConfigurationException pce) {
			pce.printStackTrace();
	}	catch(SAXException se) {
			se.printStackTrace();
	}	catch(IOException ioe) {
			ioe.printStackTrace();
	}
}	

	public static double distance(double lat1, double lon1, double lat2, double lon2) {		
		
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return (dist);
	}
	
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::  This function converts decimal degrees to radians             :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::  This function converts radians to decimal degrees             :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}


	
	public static List<Question> rearrange(List<Question> questionsSameLatLong) {
		
		List<Question> rearrangedQuestions = new ArrayList<Question>();
		
		int counter = 0;
		for (Question question : questionsSameLatLong) {
			if(counter>0 || counter<8){
				double latitude = Double.valueOf(question.getLatitude());
				double longitude = Double.valueOf(question.getLongitude());
				
				switch (counter) {
				case 1:
					double lat1 = ((0.01 * 180)/(6378.1*Math.PI)) + latitude;
					double long1 = longitude;
					question.setLatitude(String.valueOf(lat1));
					question.setLongitude(String.valueOf(long1));
					break;
				case 2:
					double lat2 = latitude;
					double long2 = ((0.01*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat2));
					question.setLongitude(String.valueOf(long2));
					break;
				case 3:
					double lat3 = ((0.008 * 180)/(6378.1*Math.PI)) + latitude;
					double long3 = ((0.008*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat3));
					question.setLongitude(String.valueOf(long3));
					break;
				case 4:
					double lat4 = ((-0.01 * 180)/(6378.1*Math.PI)) + latitude;
					double long4 = longitude;
					question.setLatitude(String.valueOf(lat4));
					question.setLongitude(String.valueOf(long4));
					break;
				case 5:
					double lat5 = latitude;
					double long5 = ((-0.01*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat5));
					question.setLongitude(String.valueOf(long5));
					break;
				case 6:
					double lat6 = ((-0.008 * 180)/(6378.1*Math.PI)) + latitude;
					double long6 = ((-0.008*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat6));
					question.setLongitude(String.valueOf(long6));
					break;
				case 7:
					double lat7 = ((-0.008 * 180)/(6378.1*Math.PI)) + latitude;
					double long7 = ((0.008*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat7));
					question.setLongitude(String.valueOf(long7));
					break;
				case 8:
					double lat8 = ((0.008 * 180)/(6378.1*Math.PI)) + latitude;
					double long8 = ((-0.008*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat8));
					question.setLongitude(String.valueOf(long8));
					break;
					case 9:
					double lat9 = ((0.02 * 180)/(6378.1*Math.PI)) + latitude;
					double long9 = longitude;
					question.setLatitude(String.valueOf(lat9));
					question.setLongitude(String.valueOf(long9));
					break;
				case 10:
					double lat10 = latitude;
					double long10 = ((0.02*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat10));
					question.setLongitude(String.valueOf(long10));
					break;
				case 11:
					double lat11 = ((0.016 * 180)/(6378.1*Math.PI)) + latitude;
					double long11 = ((0.016*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat11));
					question.setLongitude(String.valueOf(long11));
					break;
				case 12:
					double lat12 = ((-0.02 * 180)/(6378.1*Math.PI)) + latitude;
					double long12 = longitude;
					question.setLatitude(String.valueOf(lat12));
					question.setLongitude(String.valueOf(long12));
					break;
				case 13:
					double lat13 = latitude;
					double long13 = ((-0.02*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat13));
					question.setLongitude(String.valueOf(long13));
					break;
				case 14:
					double lat14 = ((-0.016 * 180)/(6378.1*Math.PI)) + latitude;
					double long14 = ((-0.016*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat14));
					question.setLongitude(String.valueOf(long14));
					break;
				case 15:
					double lat15 = ((-0.016 * 180)/(6378.1*Math.PI)) + latitude;
					double long15 = ((0.016*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat15));
					question.setLongitude(String.valueOf(long15));
					break;
				case 16:
					double lat16 = ((0.016 * 180)/(6378.1*Math.PI)) + latitude;
					double long16 = ((-0.016*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat16));
					question.setLongitude(String.valueOf(long16));
					break;
				case 17:
					double lat17 = ((0.03 * 180)/(6378.1*Math.PI)) + latitude;
					double long17 = longitude;
					question.setLatitude(String.valueOf(lat17));
					question.setLongitude(String.valueOf(long17));
					break;
				case 18:
					double lat18 = latitude;
					double long18 = ((0.03*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat18));
					question.setLongitude(String.valueOf(long18));
					break;
				case 19:
					double lat19 = ((0.024 * 180)/(6378.1*Math.PI)) + latitude;
					double long19 = ((0.024*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat19));
					question.setLongitude(String.valueOf(long19));
					break;
				case 20:
					double lat20 = ((-0.03 * 180)/(6378.1*Math.PI)) + latitude;
					double long20 = longitude;
					question.setLatitude(String.valueOf(lat20));
					question.setLongitude(String.valueOf(long20));
					break;
				case 21:
					double lat21 = latitude;
					double long21 = ((-0.03*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat21));
					question.setLongitude(String.valueOf(long21));
					break;
				case 22:
					double lat22 = ((-0.024 * 180)/(6378.1*Math.PI)) + latitude;
					double long22 = ((-0.024*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat22));
					question.setLongitude(String.valueOf(long22));
					break;
				case 23:
					double lat23 = ((-0.024 * 180)/(6378.1*Math.PI)) + latitude;
					double long23 = ((0.024*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat23));
					question.setLongitude(String.valueOf(long23));
					break;
				case 24:
					double lat24 = ((0.024 * 180)/(6378.1*Math.PI)) + latitude;
					double long24 = ((-0.024*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat24));
					question.setLongitude(String.valueOf(long24));
					break;
				case 25:
					double lat25 = ((0.04 * 180)/(6378.1*Math.PI)) + latitude;
					double long25 = longitude;
					question.setLatitude(String.valueOf(lat25));
					question.setLongitude(String.valueOf(long25));
					break;
				case 26:
					double lat26 = latitude;
					double long26 = ((0.04*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat26));
					question.setLongitude(String.valueOf(long26));
					break;
				case 27:
					double lat27 = ((0.032 * 180)/(6378.1*Math.PI)) + latitude;
					double long27 = ((0.032*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat27));
					question.setLongitude(String.valueOf(long27));
					break;
				case 28:
					double lat28 = ((-0.04 * 180)/(6378.1*Math.PI)) + latitude;
					double long28 = longitude;
					question.setLatitude(String.valueOf(lat28));
					question.setLongitude(String.valueOf(long28));
					break;
				case 29:
					double lat29 = latitude;
					double long29 = ((-0.04*180)/(Math.cos(latitude)*(Math.PI)*6378.1)) + longitude;
					question.setLatitude(String.valueOf(lat29));
					question.setLongitude(String.valueOf(long29));
					break;
				}
			}
			
			counter++;
			rearrangedQuestions.add(question);
		}
		return rearrangedQuestions;
	}
	
	
	
 public static String colorArray (int colors) {
		
		String[] colorArray = new String[] {"9000FF","90FF00","FF9000","0074FF","00FF74","FFF200","F200FF","22B573","FF0074","00FFF2"};
		StringBuffer theBuffer = new StringBuffer();
		for (int i = 0; i < colors; i++) {
			
			theBuffer.append(colorArray[i] +"|");
		}	
		
		return StringUtils.chop(theBuffer.toString());
	}
 
 
 public static  Integer[] convertStringArraytoIntArray(String[] sarray) {

		if (sarray != null) {

			Integer intarray[] = new Integer[sarray.length];
			for (int i = 0; i < sarray.length; i++) {

				intarray[i] = Integer.parseInt(sarray[i]);
			}
			return intarray;
		}
		return null;
	}
 
 
 
 public static String setProfileImageURL(Profile theProfile) {
		
		List<File> files = theProfile.getFiles();
		String profileImageUrl  = "http://images.meetforeal.com/images/anon_nosmile.png";
		for (File file : files) {
			
			String fileName = file.getFileName();
			if(fileName.equals("profilePhoto")) {
			
				profileImageUrl = String.format("http://images.meetforeal.com/picture_library/profile/%1$d.jpg", new Object[]{ file.getId() });
			}
			if (fileName.equals("twitterProfilePhoto_url")) {
				
				profileImageUrl = new String(file.getFileContent());
			}					
		}
		return profileImageUrl;
	}		

}
