/*
 * Intended to be used as an obfuscation, for the handling of Bundles and scheduling.
 * 
 * Notes: 1) using a List is adequate for small values of users in the bundle, will decrease performance exponentially for large values (though DB/Hibernate interaction will suffer first)
 */

package com.crowdscanner.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import twitter4j.internal.logging.Logger;

import com.crowdscanner.notifier.Notifier;
import com.myownmotivator.model.gaming.HuntRating;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.crowdmodule.QuestionBudleDao;
import com.myownmotivator.service.profile.ProfileService;


@Service
@Transactional
public class Bundle {
	
	@Resource
	private QuestionBudleDao questionBudleDao;

	private QuestionBundle bundle;	
	
	@Autowired
	private Notifier notifier;
	
	@Autowired
	private ProfileService profileService;
	
	private static Logger logger = Logger.getLogger(Bundle.class);
	
	private long coolOffPeriod = 0;	//timeout in between notifications
	private int threshold = 0;		//the minimum value of users in the bundle who are ready to be notified(OptedIn && CooledOff)
	private int totalInside = 0;
	private int optedIn = 0;
	
	private List<Profile> profiles;	//profiles for the people in the bundle	

	
	public void initializaParams(int bundleID){
		
		int totalInsideBudle = 0;
		long currentTime = Calendar.getInstance().getTimeInMillis();
		coolOffPeriod = bundle.getCoolOffPeriod();		
		profiles = questionBudleDao.getBundleProfiles(bundleID);	
		List<Profile> bundleProfiles = new ArrayList<Profile>();
		
		for(Iterator<Profile> it = profiles.iterator();it.hasNext();){		
			Profile myProfile = it.next();
			//List<HuntRating> myHuntRatings = myProfile.getHuntRatings();
			List<HuntRating> myHuntRatings = null;
			for (HuntRating storedHuntRating : myHuntRatings) {
				if (storedHuntRating.getTypeRating().equals("hunt-back") && storedHuntRating.getBundleId().intValue() == bundleID &&
						storedHuntRating.getOpting()){					
					
					//Calculating the cooloff periods
					long lastTimeNotifedNumber = myProfile.getLastTimeNotified();				
					if (lastTimeNotifedNumber == 0 || ((lastTimeNotifedNumber + coolOffPeriod) < currentTime)){					
						bundleProfiles.add(myProfile);
						totalInsideBudle++;
					}					
				} 
			}					
		}	
				
		
		bundle = questionBudleDao.findById(bundleID);		
		profiles = bundleProfiles;
		totalInside = totalInsideBudle;	
		threshold = bundle.getThreshold();		
		
		logger.info("totalinside "+totalInside +" threshold "+threshold+" bundle "+bundle.getId());	
		System.out.println("totalinside "+totalInside +" threshold "+threshold+" bundle "+bundle.getId()); 
		//find the number of people who have opted in and compare it to the threshold
		if(totalInside>0 && totalInside >=threshold){	//just a quick logic hack - we won't need to do anymore if there's not enough users to begin with.
			//doLogic();
		}
		
		doLogic();
	}
	
	
	//private methods
	private void doLogic(){
		int size = profiles.size();		
		Profile person;		
		
		logger.info("////////////////////////////\nstart size: " + size);
		System.out.println("////////////////////////////\nstart size: " + size+" bundle "+bundle.getId());
		
		//String regexUDID = ".*";
		//String regexUDID = "^((<|\\s)|[a-f0-9]{8}){8}>";
		String regexUDID = "[a-f0-9]";
		String regexNumber = "^\\+[0-9]{7,12}$";
		String theUDID = null, theNumber = null;
		
		for(int i=0; i<size; ++i){	
			person = profiles.get(i);
			theUDID = person.getIphoneUDID();
			theNumber = person.getNumber();
			
			if( !(theUDID!=null || theNumber!=null) ){	//if neither of them returns true, purge them
				profiles.remove(i);
				--size;
				--i; //otherwise we're: 1) going to get nullPointerException and 2)Miss people, since the next person is now at the same index
				continue;//continue does NOT mean the UPDATE is NOT invoked upon iteration
			}
			else{
				//now we need to check that the data we have is not garbage, roll out the regex
				if( theUDID!=null ? theUDID.length()>40 : false ){ //check UDID, the cheap and dirty hack
					++optedIn;
					//System.out.println(theUDID);
				}
				else if( theNumber!=null ? Pattern.matches(regexNumber, theNumber) : false ){	//check number
					++optedIn;
					//System.out.println(theNumber);
				}
				else{
					profiles.remove(i);
					--size;
					--i; //otherwise we're: 1) going to get nullPointerException and 2)Miss people, since the next person is now at the same index
					continue;	//continue does NOT mean the UPDATE is NOT invoked upon iteration
				}
				
			}
						
		}
		
		logger.info("end size: " + profiles.size());
		System.out.println("end size: " + profiles.size());
		//now check do we have enough to issue notifications
		if(optedIn>0 && optedIn>=threshold){
			//sendNotifications();
		}
		
		sendNotifications();
	}
	
	@Transactional
	private void sendNotifications(){
		//Notifier notifier = new Notifier();
		List<String> texts= new ArrayList<String>();	//temp
		
		/*
		 * mechanism for creating personalised notifications here?    
		 * */
		
		notifier.issueNotifications(profiles, texts, bundle.getId());	//it's okay to pass an empty object, as Adrians current setup sends default generic texts from within the Notifier anyway.
		
		//record the update time in the users profiles - we want to do this afterwards, in order to allow maximum time before the first user gets a notification again, since we process notifications sequentially
		long currentTime = Calendar.getInstance().getTimeInMillis();
		for(Profile person : profiles){
			//person.setLastTimeNotified(currentTime);
			//profileService.saveProfile(person);
		}
		//questionBudleDao.updateProfilesBatch(profiles);
	}	
	
	public QuestionBundle getBundle() {
		return bundle;
	}
	
	
	public void setBundle(QuestionBundle bundle) {
		this.bundle = bundle;
	}
	
}
