/*
 * This class obfuscates the details of the notifications that are dependent on what device the end-user uses, ie. iOS push notifications vs. SMS
 * This will allow you to issue notification without having to worry about what device the user operates.
 * Just create an instance of this class(empty constructor) and call method passing in the parameters that suit you,
 * i.e. individual 'people' objects or a list of them to iterate over.
 *  
 * */

package com.crowdscanner.notifier;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.service.questions.QuestionDao;

@Component
public class Notifier {
	
	final static Logger logger = Logger.getLogger(Notifier.class);
	
	//variables
	private SmsSender smsSender;
	
	@Autowired
	private QuestionDao questionService;
	
	//constructor
	public Notifier(){
		smsSender = new SmsSender();
	}
	
	//public methods
	public void issueNotifications(List<Profile> peopleList, List<String> text, Integer bundleId){	//handle Lists of people
	
		//String genericText = "Hey Rising Affluent attendee! Thanks for playing PeopleHunt! Link up your social accounts now to see your matches http://bit.ly/wbB8pk";
		List<String> allIphoneUDID = new ArrayList<String>();		
		
		for(Profile people : peopleList){				
			//see if it's iPhone/iPod			
			String topMsg = "";
			//List<Question> questions = questionService.retrieveUserBundle(people.getUser().getUserName(), bundleId);
			String lookingfor = "";
			/*
			for (Question question : questions) {
				if (question.getQuestionType().equals("lookingfor")){
					lookingfor = question.getSelectedAnswer();
				}
			}*/
			if(people.getIphoneUser() && people.getIphoneUDID()!=null){
				//do the iPhone magic
				String theUDID = people.getIphoneUDID().replaceAll("(<|>)", "").replace(" ", "");					
				allIphoneUDID.add(theUDID);				
				
				//topMsg =  String.format("A target who matches you on %1$s is lurking. Come play again and catch them!" , lookingfor);
				topMsg = "Like live music, inspirational talks, and meeting smart people? Come play PeopleHunt at Lucid NYC on the 5th Sept. 50% discount code PPLHUNT http://lucidnyc.eventbrite.com/";
				System.out.println(theUDID+" "+topMsg);
				
				iOSPusher.send(theUDID, topMsg);
				
			}
			else if(people.getNumber() != null){ //alternative is to use SMS...for now		
				topMsg =  String.format("Don't forget to add your email, linkedIn and twitter to get details of who you connected with at the NYC Lean Startup Meetup http://bit.ly/wbB8pk" , lookingfor);
				logger.info("number "+people.getNumber());
				System.out.println("number "+people.getNumber() +" "+topMsg);
				
				smsSender.send(people.getNumber(), topMsg);	
			}
			else{
				// the user didn't allow for push/sms notifications
			}		
		}
		
		if (allIphoneUDID.size() > 0){
			String genericText2 = "Hey Rising Affluent attendee! Thanks for playing PeopleHunt! Link up your social accounts now to see your matches";
			String[] arrayNumbers = allIphoneUDID.toArray(new String[allIphoneUDID.size()]);
			for (int i = 0; i < arrayNumbers.length; i++) {
				logger.info("TOKEN "+arrayNumbers[i]);
			}
			
			//iOSPusher.sendMultiple(arrayNumbers, genericText2);
		}
		
		
	}
	
	public void issueNotifications(Profile profile, String text){			//take input as a list of 'Person's, depending on which device they have call according notification methods
		//see if it's iPhone/iPod
		System.out.println(profile.getIphoneUDID() +" "+profile.getNumber());
		if(profile.getIphoneUser() && profile.getIphoneUDID()!=null){
			//do the iPhone magic
			String theUDID = profile.getIphoneUDID().replaceAll("(<|>)", "");
			System.out.println(theUDID);
			//iOSPusher.send(theUDID.replace(" ", ""), text);
		}
		else if(profile.getNumber() != null){ //alternative is to use SMS...for now
			System.out.println(profile.getNumber());
			//smsSender.send(profile.getNumber(), text);	
		}
		else{
			// the user didn't allow for push/sms notifications
		}
	}
	
	//private methods

}