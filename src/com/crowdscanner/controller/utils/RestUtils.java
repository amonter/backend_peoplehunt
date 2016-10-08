package com.crowdscanner.controller.utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.springframework.stereotype.Service;

import com.crowdscanner.controller.MatchAnswer;
import com.crowdscanner.model.ProfileBuffer;
import com.googlecode.batchfb.FacebookBatcher;
import com.googlecode.batchfb.Later;
import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.questions.QuestionDao;

@Service("restUtil")
public class RestUtils {
	
	final static Logger logger = Logger.getLogger(RestUtils.class);	
	
	@Resource
	private QuestionDao questionService;	
	
	
	public static String searchAvatarURL(String avatar) {
		
		return "";
	}
	public static List<HashMap<String, Object>> getAvatarsInfo() {
		
		List<HashMap<String, Object>> personArrays = new ArrayList<HashMap<String,Object>>();
		
		//Merchant
		HashMap<String, Object> theMerchantHash = new HashMap<String, Object>();
		theMerchantHash.put("nerd_type", "Merchant");
		theMerchantHash.put("nert_key", "merchant");
		theMerchantHash.put("description", "Merchants are smart and savvy, especially with people, having a remarkable ability to understand and respond to their needs, hence their magnificent wealth. They are flexible, good at negotiating, yet indulgent, feasting on the finest life has to offer. They are eager to find a bargain, and wont miss an opportunity.");
		theMerchantHash.put("url", "http://s3.amazonaws.com/crowdscanner_images/merchant.png");
		
		List<HashMap<String, String>> arrayClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> cluesHash = new HashMap<String, String>();
		cluesHash.put("question", "In a very competitive environment, how do you react?");
		cluesHash.put("clue", "Merchants tend to be driven");
		HashMap<String, String> cluesHash2 = new HashMap<String, String>();
		cluesHash2.put("question", "If a fire were to break out, right here, what would you do?");
		cluesHash2.put("clue", "Merchants tend to be relaxed and adaptive in a crisis");			
		HashMap<String, String> cluesHash3 = new HashMap<String, String>();
		cluesHash3.put("question", "What would you miss most if you were left alone for a year in the woods?");
		cluesHash3.put("clue", "Merchants tend to love to be around people");
		//add has cues
		arrayClues.add(cluesHash);
		arrayClues.add(cluesHash2);
		arrayClues.add(cluesHash3);
		//add array clues into meta hash
		theMerchantHash.put("clues", arrayClues);
		
		//WIzard
		HashMap<String, Object> theWizardHash = new HashMap<String, Object>();
		theWizardHash.put("nerd_type", "Wizard");
		theWizardHash.put("nert_key", "wizard");
		theWizardHash.put("description", "Wizards can be logical and are known for possessing much greater physical and mental power than ordinary humans. Abstract and theoretical, they are naturally inventive, often on a quest to find the solution to a lofty problem. They are adept at spotting patterns.");
		theWizardHash.put("url", "http://s3.amazonaws.com/crowdscanner_images/wizard.png");
		
		List<HashMap<String, String>> wizardClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> wizardHash = new HashMap<String, String>();
		wizardHash.put("question", "Do you like libraries?");
		wizardHash.put("clue", "Wizards tend to be on a quest for knowledge");
		HashMap<String, String> wizardHash2 = new HashMap<String, String>();
		wizardHash2.put("question", "If something breaks, do you like to try to fix it yourself?");
		wizardHash2.put("clue", "Wizards tend to be efficient problem solvers");			
		HashMap<String, String> wizardHash3 = new HashMap<String, String>();
		wizardHash3.put("question", "How do you work best? Alone or in a team?");
		wizardHash3.put("clue", "Wizards tend to like their own company");
		HashMap<String, String> wizardHash4 = new HashMap<String, String>();
		wizardHash4.put("question", "What future discovery do you anticipate the most?");
		wizardHash4.put("clue", "Wizards tend to be on a quest for knowledge");
		HashMap<String, String> wizardHash5 = new HashMap<String, String>();
		wizardHash5.put("question", "What talent would you like to develop?");
		wizardHash5.put("clue", "Wizards tend to be on a quest for knowledge");
		//add has cues
		wizardClues.add(wizardHash);
		wizardClues.add(wizardHash2);
		wizardClues.add(wizardHash3);
		wizardClues.add(wizardHash4);
		wizardClues.add(wizardHash5);
		//add array clues into meta hash
		theWizardHash.put("clues", wizardClues);
		
		//Black Knight
		HashMap<String, Object> blackNightHashMeta = new HashMap<String, Object>();
		blackNightHashMeta.put("nerd_type", "Black Knight");
		blackNightHashMeta.put("nert_key", "black_knight");
		blackNightHashMeta.put("description", "Black Knights are trustworthy, reliable individuals. They enjoy when everything is done according to plan, and are fair and kind in their approach to others. They are cool under pressure. When leading a team, they are reliable, diligent, and industrious, so their sword is just for show.");
		blackNightHashMeta.put("url", "http://s3.amazonaws.com/crowdscanner_images/blackKnight.png");
		
		List<HashMap<String, String>> blackNightHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> blackNightHash = new HashMap<String, String>();
		blackNightHash.put("question", "Are you ever late for important meetings?");
		blackNightHash.put("clue", "Black Knights tend to be trustworthy");
		HashMap<String, String> blackNightHash2 = new HashMap<String, String>();
		blackNightHash2.put("question", "If you won the lottery, would you spend most of it on yourself?");
		blackNightHash2.put("clue", "Black Knights tend to be generous and caring towards others");			
		HashMap<String, String> blackNightHash3 = new HashMap<String, String>();
		blackNightHash3.put("question", "Do you prefer to work for yourself, or for someone else?");
		blackNightHash3.put("clue", "Black Knights tend to be realistic, and like security");
		//add has cues
		blackNightHashClues.add(blackNightHash);
		blackNightHashClues.add(blackNightHash2);
		blackNightHashClues.add(blackNightHash3);
		//add array clues into meta hash
		blackNightHashMeta.put("clues", blackNightHashClues);
		
		//OutLaw
		HashMap<String, Object> outlawHashMeta = new HashMap<String, Object>();
		outlawHashMeta.put("nerd_type", "Outlaw");
		outlawHashMeta.put("nert_key", "outlaw");
		outlawHashMeta.put("description", "Outlaws follow their gut and strive for the highest ideals in life. They want to make the world a better place, and have a huge appreciation and curiosity for the world around them. They are decisive, inventive with clothing and will most likely be passionately pursuing some worthy cause.");
		outlawHashMeta.put("url", "http://s3.amazonaws.com/crowdscanner_images/outlaw.png");
		
		List<HashMap<String, String>> outlawHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> outlawHash = new HashMap<String, String>();
		outlawHash.put("question", "Where would you like to travel to right now?");
		outlawHash.put("clue", "Outlaws tend to have travelled to unusual places");
		HashMap<String, String> outlawHash2 = new HashMap<String, String>();
		outlawHash2.put("question", "When you wake up, and think about work, how do you feel?");
		outlawHash2.put("clue", "Outlaws tend to be passionate about their career");			
		HashMap<String, String> outlawHash3 = new HashMap<String, String>();
		outlawHash3.put("question", "If you could be president of your country, would you be?");
		outlawHash3.put("clue", "Outlaws tend to like to lead");
		HashMap<String, String> outlawHash4 = new HashMap<String, String>();
		outlawHash4.put("question", "What do you like to do with a free hour?");
		outlawHash4.put("clue", "Outlaws tend to hate wasting time");
		//add has cues
		outlawHashClues.add(outlawHash);
		outlawHashClues.add(outlawHash2);
		outlawHashClues.add(outlawHash3);
		outlawHashClues.add(outlawHash4);
		//add array clues into meta hash
		outlawHashMeta.put("clues", outlawHashClues);
		
		
		//Joker
		HashMap<String, Object> jokerHashMeta = new HashMap<String, Object>();
		jokerHashMeta.put("nerd_type", "Joker");
		jokerHashMeta.put("nert_key", "joker");
		jokerHashMeta.put("description", "Jokers are observant, delighted by experiences and live in the here and now. Their spontaneity can get them into trouble, but they wont miss an opportunity. Either wildly rich, or desperately poor, jokers are relentless in their desire to express their inner dreams. Spending time with them will always be entertaining.");
		jokerHashMeta.put("url", "http://s3.amazonaws.com/crowdscanner_images/joker.png");

		
		List<HashMap<String, String>> jokerHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> jokerHash = new HashMap<String, String>();
		jokerHash.put("question", "Do you think art or science is more important to mankind?");
		jokerHash.put("clue", "Jokers tend to love anything to do with creation or crafting");
		HashMap<String, String> jokerHash2 = new HashMap<String, String>();
		jokerHash2.put("question", "Whats the first thing you notice about someone new?");
		jokerHash2.put("clue", "Jokers tend to be observant");			
		HashMap<String, String> jokerHash3 = new HashMap<String, String>();
		jokerHash3.put("question", "Have you ever run out of gas?");
		jokerHash3.put("clue", "Jokers tend to be scatterbrained");
		HashMap<String, String> jokerHash4 = new HashMap<String, String>();
		jokerHash4.put("question", "What do you consider a waste in our society?");
		jokerHash4.put("clue", "Jokers tend to be observant");
		HashMap<String, String> jokerHash5 = new HashMap<String, String>();
		jokerHash5.put("question", "What are you most proud of creating in your life?");
		jokerHash5.put("clue", "Jokers tend to love anything to do with creation or crafting");
		//add has cues
		jokerHashClues.add(jokerHash);
		jokerHashClues.add(jokerHash2);
		jokerHashClues.add(jokerHash3);
		jokerHashClues.add(jokerHash4);
		jokerHashClues.add(jokerHash5);
		//add array clues into meta hash
		jokerHashMeta.put("clues", jokerHashClues);
		
		personArrays.add(theMerchantHash);
		personArrays.add(theWizardHash);
		personArrays.add(blackNightHashMeta);
		personArrays.add(outlawHashMeta);
		personArrays.add(jokerHashMeta);
		
		return personArrays;
}
	

	
	
	public HashMap<String, Object> processAnswerStat(Integer questionPhoneId, String stringAnswer) throws UnsupportedEncodingException {
		logger.info("Answer Label " + stringAnswer);
		stringAnswer = URLDecoder.decode(stringAnswer, "UTF-8");
		List<Question> questions = questionService.findQuestionWithPhoneId(questionPhoneId);		
		Question questionTemplate = new Question();
		Map<String, Answer> answersMap = new HashMap<String, Answer>();	
		stringAnswer = stringAnswer.trim().replaceAll("\\s", "");
		stringAnswer = stringAnswer.replaceAll("\\\\+", "/");
		stringAnswer = stringAnswer.replaceAll("\\\\+", "/");
		
		if (!questions.isEmpty()) {			
			
			Question tempQuestion = questions.get(0);						
			questionTemplate.setQuestionDescription(tempQuestion.getQuestionDescription());			
			for (Question question: questions) {				
						
				// process the answers overall aggregated
				if (!question.getParent()) {
					List<Answer> answers = question.getAnswers();			
					for (Answer answer : answers) {
						
						String theKey = answer.getTextualAnswer().trim().replaceAll("\\s", "");
						theKey = theKey.replaceAll("\\\\+", "/");
						if (answersMap.containsKey(theKey) && answer.getAnswerNumber() == 1) {
							
							Answer theAnswerObject = (Answer)answersMap.get(theKey);
							int accumulated = theAnswerObject.getAnswerNumber() + 1;
							theAnswerObject.setAnswerNumber(accumulated);
							answersMap.put(theKey, theAnswerObject);				
						}
						else if (theKey.equals(stringAnswer) && answer.getAnswerNumber() == 1) {
							
							Answer insertAnswer = new Answer();
							insertAnswer.setTextualAnswer(answer.getTextualAnswer());
							insertAnswer.setAnswerNumber(answer.getAnswerNumber());
							answersMap.put(theKey, insertAnswer);
						}
					}
				}
			}							
		}
		
		int totalAnsweredQuestions = questions.size() - 1;
		float timesAnswered = 0;
		if(answersMap.containsKey(stringAnswer)) {
			timesAnswered = answersMap.get(stringAnswer).getAnswerNumber() *100;
		}			
		float res = (totalAnsweredQuestions != 0  ? timesAnswered / totalAnsweredQuestions : 0);
		//float res = timesAnswered / totalAnsweredQuestions;
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("percentage", res);
		return results;
	}
	
	
	public static Question createNewQuestion(Profile theProfile, Question question) {
		
		Question newQuestion = new Question();
		copy(newQuestion,question);
		newQuestion.setParent(false);
		newQuestion.addProfile(theProfile);		
		return newQuestion;
	}
	 
	
	public static void copy(Question target, Question source) {
		target.setQuestionDescription(source.getQuestionDescription());
		target.setQuestionBundle(source.getQuestionBundle());
		target.setQuestionPhoneId(source.getQuestionPhoneId());
		target.setQuestionType(source.getQuestionType());
	}
	
	
	
	
	
	public static Question setTheQuestion(String theAnswer, List<Question> theQuestions, User savedUser,
			QuestionBundle theBundle) {
		
		Question questionToSave = theQuestions.get(0);
		Question newQuestion = new Question();
		newQuestion.setQuestionDescription(questionToSave.getQuestionDescription());
		newQuestion.setQuestionPhoneId(questionToSave.getQuestionPhoneId());
		newQuestion.setCreationDate(new Date(System.currentTimeMillis()));
		newQuestion.setQuestionBundle(theBundle);
		newQuestion.getProfiles().add(savedUser.getProfile());
		newQuestion.setQuestionType("faces");	
		for (Answer answer : questionToSave.getAnswers()) {
			Answer twoAnswer = new Answer();
			twoAnswer.setTextualAnswer(answer.getTextualAnswer());
			twoAnswer.setAnswerNumber(0);		
			if (answer.getTextualAnswer().equals(theAnswer)) {
				twoAnswer.setAnswerNumber(1);		
			}					
			newQuestion.getAnswers().add(twoAnswer);
		}
		return newQuestion;
	}
	
	
	
	public void addProfileAnswersBubble(List<Question> theQuestions, Map<Integer, ProfileBuffer> mapProfile, List<MatchAnswer> allAnswers) throws UnsupportedEncodingException {
		
			for (Question question : theQuestions) {
				if (question.getParent()) {				
					for (Answer anAnswer: question.getAnswers()) {
						MatchAnswer matchAnswer = new MatchAnswer();
						matchAnswer.setQuestionPhoneId(question.getQuestionPhoneId());
						matchAnswer.setQuestionDescription(question.getQuestionDescription());
						matchAnswer.setTextualAnswer(anAnswer.getTextualAnswer());
						matchAnswer.setEncodedTextualAnswer(URLEncoder.encode(anAnswer.getTextualAnswer(), "UTF-8"));
						allAnswers.add(matchAnswer);
					}
				}				
			}	
				
			for (Question question : theQuestions) {
				if (!question.getParent()) {					
					setProfileBuffer(mapProfile, question);
				}				
			}
	
}	
	
	public static String extractImageUrl(Question question2, String theFile) {
		
		if (question2.getProfiles().get(0).getFiles().size() > 0) {
			
			Map<String, File> mapFiles = new HashMap<String, File>();
			for (File aFile : question2.getProfiles().get(0).getFiles()) {							
				mapFiles.put(aFile.getFileName(), aFile);
			}
			
			theFile = extractFileUrl(theFile, mapFiles);					
		}
		return theFile;
}
	
	
public void setProfileBuffer(Map<Integer, ProfileBuffer> mapProfile,	Question question) {
		
		System.out.println(question.getId());
		Profile theProfile = question.getProfiles().get(0);				
		if (!mapProfile.containsKey(theProfile.getId())){
			
			User theUser = theProfile.getUser();
			ProfileBuffer buffer = new ProfileBuffer();
			buffer.setName(theUser.getLastName());
			buffer.setUserName(theUser.getEmail());
			if (theProfile.getTwitterUserAuthentication() != null) {
				buffer.setTwitterHandle(theProfile.getTwitterUserAuthentication().getUsername());
			}
			buffer.setProfileId(theProfile.getId());
			String theUrl = RestUtils.extractURLProfile(theProfile, "http://images.crowdscanner.com/anon_nosmile.png");
			buffer.setUrlImage(theUrl);
			mapProfile.put(theProfile.getId(), buffer);
		}
		
		ProfileBuffer theBuffer = mapProfile.get(theProfile.getId());
		Question theQuestion = new Question();
		theQuestion.setQuestionPhoneId(question.getQuestionPhoneId());				
		theQuestion.setSelectedAnswer(question.getSelectedAnswer());
		theQuestion.setQuestionDescription(question.getQuestionDescription());
		theBuffer.getQuestions().add(theQuestion);		
		mapProfile.put(theProfile.getId(), theBuffer);
	}

	public static String extractFileUrl(String theFile, Map<String, File> mapFiles) {
		
		if (mapFiles.containsKey("profilePhoto")){							
			File storedFile= mapFiles.get("profilePhoto");
			theFile = "http://dev.crowdscanner.com/picture_library/profile/" +storedFile.getId()+".jpg";
			
		} else if (mapFiles.containsKey("facebookProfilePhoto_url") || mapFiles.containsKey("twitterProfilePhoto_url")) {							
			if (mapFiles.containsKey("facebookProfilePhoto_url")) {
				
				File storedFile= mapFiles.get("facebookProfilePhoto_url");
				theFile = new String(storedFile.getFileContent());		
			} else {
				
				File storedFile= mapFiles.get("twitterProfilePhoto_url");
				theFile = new String(storedFile.getFileContent());						
			}				
		}
		return theFile;
	}
	
	
	public static String extractURLProfile (Profile profile, String theFile) {		
		
		if (profile.getFiles().size() > 0) {
			
			Map<String, File> mapFiles = new HashMap<String, File>();
			for (File aFile : profile.getFiles()) {							
				mapFiles.put(aFile.getFileName(), aFile);
			}
			
			theFile = extractFileUrl(theFile, mapFiles);					
		}
		return theFile;
		
	}	
	
	
	public static User setUser(String name, String email, String username, String password) throws UnsupportedEncodingException {

		User user = new User();			
		user.setUserName(username);
		user.setPassword(URLDecoder.decode(password, "UTF-8"));
		user.setLastName(name);
		user.setEmail(email);
		
		String location = "to_be_set";		
		user.setState(location);
		user.setCountry(location);		
		//user.getProfile().setPresentationTitle("crowd_faces");

		user.getProfile().setIphoneUser(true);				
		//user.setBirthDate(new CustomDateFormat(Calendar.getInstance()));	
		user.getProfile().setSource("not_linked");
		return user;
	}
	
	
	public String processURLPath (String theUrl) {
		
		String aURL = null;
		
		  try {
			
			URL theURL = new URL(theUrl);
			aURL = "http://" + theURL.getHost();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return aURL;		
	}
	
	
	public static HashMap<String, String> characterAlgBeta(List<Question> theQuestions) {
		
		//for loop
		int finalRange = 0;
		for (Question question : theQuestions) {
			//get Answer stringAnswer = stringAnswer.trim().replaceAll("\\s", "");
			String theAnswer = question.getSelectedAnswer().trim().replaceAll("\\s", "");
			//calculate the Number Agree/Disagree
			int answerDegree = 0;
			if (theAnswer.equalsIgnoreCase("a.stronglyagree")) {
				answerDegree = 5;
			} else if (theAnswer.equalsIgnoreCase("b.agree")) {
				answerDegree = 4;
			} else if (theAnswer.equalsIgnoreCase("c.neitheragreenordisagree")){
				answerDegree = 3;				
			} else if (theAnswer.equalsIgnoreCase("d.disagree")) {
				answerDegree = 2;				
			} else if (theAnswer.equalsIgnoreCase("e.stronglydisagree")){
				answerDegree = 1;				
			} 
					
			finalRange += answerDegree;			
		}
			
		
		//Find the character range 		
		HashMap<String, String> resHash = new HashMap<String, String>();		
		if (finalRange <= 11) {
			resHash.put("nerd_image", "http://images.crowdscanner.com/nerd.png");
			resHash.put("nerd_type", "Cyber Geek");
			resHash.put("nerd_key", "cyber_geek");
		} else if (finalRange >=12 && finalRange <= 16) {
			resHash.put("nerd_image", "http://images.crowdscanner.com/scientist.png");
			resHash.put("nerd_type", "Mad Hacker Scientist");
			resHash.put("nerd_key", "nerdy_hackerscientist");
		} else if (finalRange >=17 && finalRange <= 18){
			resHash.put("nerd_image", "http://images.crowdscanner.com/politician.png");
			resHash.put("nerd_type", "Political Poet");
			resHash.put("nerd_key", "nerdy_politicalpoet");				
		} else if (finalRange >= 19 && finalRange <= 21) {
			resHash.put("nerd_image", "http://images.crowdscanner.com/social.png");
			resHash.put("nerd_type", "Social Anarchist");
			resHash.put("nerd_key", "nerdy_socialanarchist");				
		} else if (finalRange >= 22 && finalRange <= 25){
			resHash.put("nerd_image", "http://images.crowdscanner.com/artist.png");
			resHash.put("nerd_type", "Artistic Genius");
			resHash.put("nerd_key", "nerdy_artisticgenius");
		} 				
		
		return resHash;
	}
	
	
	public static HashMap<String, String> characterAlg(List<Question> theQuestions) {				
		
		//Find the character range 		
		HashMap<String, HashMap<String, String>> resHash = new HashMap<String, HashMap<String, String>>();	
		HashMap<String, String> mechantHash = new HashMap<String, String>();
		mechantHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/merchant.png");
		mechantHash.put("nerd_type", "Merchant");
		mechantHash.put("nerd_key", "merchant");
		HashMap<String, String> blackNightHash = new HashMap<String, String>();
		blackNightHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/blackKnight.png");
		blackNightHash.put("nerd_type", "Black Knight");
		blackNightHash.put("nerd_key", "black_knight");
		HashMap<String, String> jokerHash = new HashMap<String, String>();
		jokerHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/joker.png");
		jokerHash.put("nerd_type", "Joker");
		jokerHash.put("nerd_key", "joker");		
		HashMap<String, String> wizardHash = new HashMap<String, String>();
		wizardHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/wizard.png");
		wizardHash.put("nerd_type", "Wizard");
		wizardHash.put("nerd_key", "wizard");			
		HashMap<String, String> outlawHash = new HashMap<String, String>();
		outlawHash.put("nerd_image", "http://s3.amazonaws.com/crowdscanner_images/outlaw.png");
		outlawHash.put("nerd_type", "Outlaw");
		outlawHash.put("nerd_key", "outlaw");
						
		resHash.put("merchant", mechantHash);
		resHash.put("black_knight", blackNightHash);
		resHash.put("joker", jokerHash);
		resHash.put("wizard", wizardHash);
		resHash.put("outlaw", outlawHash);
		
		Map<String,Integer> theCharactersScore = new HashMap<String,Integer>();
		theCharactersScore.put("black_knight", 0);
		theCharactersScore.put("joker", 0);
		theCharactersScore.put("merchant", 0);
		theCharactersScore.put("outlaw", 0);
		theCharactersScore.put("wizard", 0);
		
		for (Question question : theQuestions) {		
			switch (question.getQuestionPhoneId()) {
				case 27807://openness			
					getOpenness(theCharactersScore, question); 			
					break;
				case 8943874://agreeableness			
					getAgreeableness(theCharactersScore, question); 			
					break;
				case 5396313:// concienciosness			
					getConcienciosness(theCharactersScore, question); 			
					break;
				case 6903753:// stability			
					getStability(theCharactersScore, question); 			
					break;
				case 4620022: //extroversion			
					getExtroversion(theCharactersScore, question);			
					break;			
			}		
		}	
			
		
		int repeat = checkForRepeat(theCharactersScore);		
		if (repeat > 1) {			
			//System.out.println("repeat "+repeat);
			outer:
			for (Question question : theQuestions) {		
				switch (question.getQuestionPhoneId()) {
					case 27807://openness			
						getOpenness(theCharactersScore, question); 
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 8943874://agreeableness			
						getAgreeableness(theCharactersScore, question); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 5396313:// concienciosness			
						getConcienciosness(theCharactersScore, question); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 6903753:// stability			
						getStability(theCharactersScore, question); 			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}
					case 4620022: //extroversion			
						getExtroversion(theCharactersScore, question);			
						if (checkForRepeat(theCharactersScore) > 1) {
							break;
						} else {
							break outer;
						}			
				}		
			}
		}
		
		HashMap<String, String>characterResult = null;
		List<Integer> allValues = sortCharacterCollec(theCharactersScore);
		Integer characterValue = allValues.get(0);
		for (Map.Entry<String, Integer> entries : theCharactersScore.entrySet()) {
			if (entries.getValue().equals(characterValue)) {
				characterResult = resHash.get(entries.getKey());
			}			
		}			
		
		
		return characterResult;
	}
	
	
	private static int checkForRepeat(Map<String, Integer> theCharacters) {
		
		List<Integer> allValues = sortCharacterCollec(theCharacters);		
		Integer maxValue = allValues.get(0);
		int repeat = 0;
		for (Integer integer : allValues) {
			if(maxValue.equals(integer)){
				repeat++;
			}
		}
		return repeat;
	}
	
	
	private static List<Integer> sortCharacterCollec(Map<String, Integer> theCharacters) {
		List<Integer> allValues = new ArrayList<Integer>(theCharacters.values());
		Collections.sort(allValues, new Comparator<Integer>() {
			public int compare(Integer one, Integer two) {				
				return two.compareTo(one);
			}
		});
		return allValues;
	}
	
	private static void getExtroversion(Map<String, Integer> theCharacters, Question question) {
		
		String theAnswer5 = question.getSelectedAnswer().trim().replaceAll("\\s", "");				
		if (theAnswer5.equalsIgnoreCase("a.stronglyagree")) {
			theCharacters.put("joker", theCharacters.get("joker") + 1);
			theCharacters.put("merchant", theCharacters.get("merchant") + 1);
			theCharacters.put("outlaw", theCharacters.get("outlaw") + 1);
			//joker +=1;
			//merchant += 1;
		} else if (theAnswer5.equalsIgnoreCase("b.agree")) {
			theCharacters.put("outlaw", theCharacters.get("outlaw") + 1);
			theCharacters.put("black_knight", theCharacters.get("black_knight") + 1);
			//outlaw += 1;
			//black_knight += 1;
		} else if (theAnswer5.equalsIgnoreCase("c.neitheragreenordisagree")){
			theCharacters.put("wizard", theCharacters.get("wizard") + 1);
			//wizard +=1;				
		} else if (theAnswer5.equalsIgnoreCase("d.disagree")) {
			theCharacters.put("wizard", theCharacters.get("wizard") + 1);
			//wizard +=1;				
		} else if (theAnswer5.equalsIgnoreCase("e.stronglydisagree")){
			theCharacters.put("wizard", theCharacters.get("wizard") + 1);
			//wizard +=1;				
		}
	}

	
	
	
	
	private static void getStability(Map<String, Integer> theCharacters, Question question) {
		
		String theAnswer4 = question.getSelectedAnswer().trim().replaceAll("\\s", "");				
		if (theAnswer4.equalsIgnoreCase("a.stronglyagree")) {
			theCharacters.put("merchant", theCharacters.get("merchant") + 1);
			theCharacters.put("black_knight", theCharacters.get("black_knight") + 1);
			//merchant += 1;
			//black_knight += 1;
		} else if (theAnswer4.equalsIgnoreCase("b.agree")) {
			theCharacters.put("merchant", theCharacters.get("merchant") + 1);
			theCharacters.put("black_knight", theCharacters.get("black_knight") + 1);
			//merchant += 1;
			//black_knight += 1;
		} else if (theAnswer4.equalsIgnoreCase("c.neitheragreenordisagree")){
			theCharacters.put("outlaw", theCharacters.get("outlaw") + 1);
			theCharacters.put("joker", theCharacters.get("joker") + 1);
			theCharacters.put("wizard", theCharacters.get("wizard") + 1);
			//outlaw += 1;		
			//joker +=1;
			//wizard +=1;
		} else if (theAnswer4.equalsIgnoreCase("d.disagree")) {
			theCharacters.put("outlaw", theCharacters.get("outlaw") + 1);
			theCharacters.put("joker", theCharacters.get("joker") + 1);
			theCharacters.put("wizard", theCharacters.get("wizard") + 1);
			//outlaw += 1;		
			//joker +=1;
			//wizard +=1;				
		} else if (theAnswer4.equalsIgnoreCase("e.stronglydisagree")){
			theCharacters.put("joker", theCharacters.get("joker") + 1);
			//joker +=1;				
		}
	}

	private static void getConcienciosness(Map<String, Integer> theCharacters, Question question) {
		
		String theAnswer3 = question.getSelectedAnswer().trim().replaceAll("\\s", "");				
		if (theAnswer3.equalsIgnoreCase("a.stronglyagree")) {
			theCharacters.put("joker", theCharacters.get("joker") + 1);
			//joker +=1;
		} else if (theAnswer3.equalsIgnoreCase("b.agree")) {
			theCharacters.put("joker", theCharacters.get("joker") + 1);
			//joker +=1;
		} else if (theAnswer3.equalsIgnoreCase("c.neitheragreenordisagree")){
			theCharacters.put("outlaw", theCharacters.get("outlaw") + 1);
			theCharacters.put("joker", theCharacters.get("joker") + 1);
			theCharacters.put("merchant", theCharacters.get("merchant") + 1);
			//outlaw += 1;		
			//joker +=1;
			//merchant += 1;
		} else if (theAnswer3.equalsIgnoreCase("d.disagree")) {
			theCharacters.put("outlaw", theCharacters.get("outlaw") + 1);
			theCharacters.put("wizard", theCharacters.get("wizard") + 1);
			theCharacters.put("merchant", theCharacters.get("merchant") + 1);
			theCharacters.put("black_knight", theCharacters.get("black_knight") + 1);
			//outlaw += 1;		
			//merchant += 1;
			//black_knight += 1;
			//wizard +=1;
		} else if (theAnswer3.equalsIgnoreCase("e.stronglydisagree")){
			theCharacters.put("outlaw", theCharacters.get("outlaw") + 1);
			theCharacters.put("wizard", theCharacters.get("wizard") + 1);
			theCharacters.put("black_knight", theCharacters.get("black_knight") + 1);
			//black_knight += 1;
			//wizard +=1;	
			//outlaw += 1;
		}
	}

	private static void getAgreeableness(Map<String, Integer> theCharacters, Question question) {
		
		String theAnswer2 = question.getSelectedAnswer().trim().replaceAll("\\s", "");				
		if (theAnswer2.equalsIgnoreCase("a.stronglyagree")) {
			theCharacters.put("black_knight", theCharacters.get("black_knight") + 1);
			theCharacters.put("joker", theCharacters.get("joker") + 1);
			//black_knight += 1;
		} else if (theAnswer2.equalsIgnoreCase("b.agree")) {
			theCharacters.put("joker", theCharacters.get("joker") + 1);
			theCharacters.put("wizard", theCharacters.get("wizard") + 1);
			theCharacters.put("merchant", theCharacters.get("merchant") + 1);
			theCharacters.put("black_knight", theCharacters.get("black_knight") + 1);
			//joker +=1;
			//black_knight += 1;
			//wizard +=1;
			//merchant += 1;
		} else if (theAnswer2.equalsIgnoreCase("c.neitheragreenordisagree")){
			theCharacters.put("outlaw", theCharacters.get("outlaw") + 1);
			theCharacters.put("wizard", theCharacters.get("wizard") + 1);
			//outlaw += 1;		
			//wizard +=1;
		} else if (theAnswer2.equalsIgnoreCase("d.disagree")) {
			theCharacters.put("outlaw", theCharacters.get("outlaw") + 1);
			theCharacters.put("wizard", theCharacters.get("wizard") + 1);
			//outlaw += 1;		
			//wizard +=1;				
		} else if (theAnswer2.equalsIgnoreCase("e.stronglydisagree")){
			theCharacters.put("outlaw", theCharacters.get("outlaw") + 1);
			//outlaw += 1;					
		}
	}

	private static void getOpenness(Map<String, Integer> theCharacters, Question question) {
		
		String theAnswer = question.getSelectedAnswer().trim().replaceAll("\\s", "");				
		if (theAnswer.equalsIgnoreCase("a.stronglyagree")) {
			theCharacters.put("outlaw", theCharacters.get("outlaw") + 1);
			theCharacters.put("joker", theCharacters.get("joker") + 1);
			theCharacters.put("wizard", theCharacters.get("wizard") + 1);
			//outlaw += 1;
			//joker +=1;
			//wizard +=1;
		} else if (theAnswer.equalsIgnoreCase("b.agree")) {
			theCharacters.put("outlaw", theCharacters.get("outlaw") + 1);
			theCharacters.put("joker", theCharacters.get("joker") + 1);
			theCharacters.put("wizard", theCharacters.get("wizard") + 1);
			theCharacters.put("merchant", theCharacters.get("merchant") + 1);
			theCharacters.put("black_knight", theCharacters.get("black_knight") + 1);
			//outlaw += 1;
			//joker +=1;
			//wizard +=1;
			//merchant += 1;
			//black_knight += 1;
		} else if (theAnswer.equalsIgnoreCase("c.neitheragreenordisagree")){
			theCharacters.put("merchant", theCharacters.get("merchant") + 1);
			theCharacters.put("black_knight", theCharacters.get("black_knight") + 1);
			//black_knight += 1;	
			//merchant += 1;
		} else if (theAnswer.equalsIgnoreCase("d.disagree")) {
			theCharacters.put("black_knight", theCharacters.get("black_knight") + 1);
			//black_knight += 1;				
		} else if (theAnswer.equalsIgnoreCase("e.stronglydisagree")){
			theCharacters.put("black_knight", theCharacters.get("black_knight") + 1);
			//black_knight += 1;				
		}
	}
	
	public static User createFacebookUser(String acessToken, String key) {
		
		User theActiveUser = new User();
		
		try {			
			
			FacebookBatcher batcher = new FacebookBatcher(acessToken);
			
			Later<JsonNode> justMe = batcher.graph("me");		
			JsonNode meNode = justMe.get();			
			Later<ArrayNode> thePic = batcher.query("SELECT pic, pic_square, pic_big FROM user WHERE uid ==" +meNode.get("id").getValueAsText());
			JsonNode picNode = thePic.get().iterator().next();	
			
			String thePictureUrl = "";			
			if (!StringUtils.isBlank( picNode.get("pic_square").getTextValue())) thePictureUrl = picNode.get("pic_square").getTextValue();	
			if (!StringUtils.isBlank( picNode.get("pic").getTextValue())) thePictureUrl =  picNode.get("pic").getTextValue();
			if (!StringUtils.isBlank( picNode.get("pic_big").getTextValue())) thePictureUrl = picNode.get("pic_big").getTextValue();	
			
			//theActiveUser = setUser(meNode.get("name").getValueAsText(), meNode.get("email").getValueAsText(), "no_password");
			theActiveUser.setUserName(key);
			theActiveUser.getProfile().setSource("facebook");	
			//theActiveUser.getProfile().setPresentationTitle("crowd_faces");
			if (meNode.get("gender")!= null) {				
				theActiveUser.setGender(meNode.get("gender").getValueAsText());
			}		
			
			File urlFile = new File();
			urlFile.setFileName("facebookProfilePhoto_url");
			urlFile.setFileContent(new String(thePictureUrl).getBytes());
			urlFile.setProfile(theActiveUser.getProfile());
			theActiveUser.getProfile().getFiles().add(urlFile);			
		
			return theActiveUser;					
				
		} catch (Exception ex) { 
			
			//logger.info("createFacebookUser User Ex", ex);	
		}
		return theActiveUser;
	}	
	
}
