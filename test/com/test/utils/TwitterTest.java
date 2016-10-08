package com.test.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crowdscanner.controller.MongoDbInit;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.myownmotivator.model.questions.Question;

import flexjson.JSONSerializer;


public class TwitterTest  {

	
	@Autowired
	private MongoDbInit mongoService;
	
	public DB mongoDatabase = null;
	
	@Test
	public void testMongo() throws UnsupportedEncodingException {	
		
		
	            //MongoDatabase mongoDB = mongoService.mongoDB;
				MongoClient mongoClient = new MongoClient( "50.19.45.37" , 27017 );
				MongoDatabase mongoDB = mongoClient.getDatabase("peoplehunt");
	            MongoCollection<Document> col = mongoDB.getCollection("missing_lang");
	            System.out.println(col);
	}
	
	
	
	//@Test
	public void testTheUsers() throws UnsupportedEncodingException {		
	
	
			try{
				
				Map<String, Object> theObject = new HashMap<String, Object>();
				theObject.put("key", "NHpudxhV9HV6zakj7-gH0A");
				theObject.put("template_name", "peoplehunt-new-message");
				theObject.put("template_content", new ArrayList());			
			
				Map<String, Object> messageContent = new HashMap<String, Object>();
				
				List<Map<String, String>> to = new ArrayList<Map<String,String>>();
				Map<String, String> toMap = new HashMap<String, String>();
				toMap.put("email", "Rebeccaellison1001@gmail.com");
				toMap.put("name", "Elenita");
				to.add(toMap);
				messageContent.put("to", to);
				
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Reply-To", "ellen@peoplehunt.me");
				messageContent.put("headers", headers);
				messageContent.put("important", false);
				messageContent.put("merge", true);
				messageContent.put("global_merge_vars", new ArrayList());
				
				List<Map<String, Object>> merge_varsArray = new ArrayList<Map<String,Object>>();
				Map<String, Object> merge_vars = new HashMap<String, Object>();					
				merge_vars.put("rcpt", "Rebeccaellison1001@gmail.com");
				
				List<Map<String, String>> arrayVars = new ArrayList<Map<String,String>>();
				Map<String, String> vars1 = new HashMap<String, String>();
				vars1.put("name", "message");
				vars1.put("content", "This is code from code");
				
				Map<String, String> vars2 = new HashMap<String, String>();
				vars2.put("name", "fullsendername");
				vars2.put("content", "Coditino II");
				
				Map<String, String> vars3 = new HashMap<String, String>();
				vars3.put("name", "firstname");
				vars3.put("content", "Culpable II");
				arrayVars.add(vars1);
				arrayVars.add(vars2);
				arrayVars.add(vars3);				
				merge_vars.put("vars", arrayVars);
				merge_varsArray.add(merge_vars);
				messageContent.put("merge_vars", merge_varsArray);
				theObject.put("message", messageContent);
				theObject.put("async", false);
				
				Gson gson = new Gson();
				String json =  gson.toJson(theObject);
				System.out.println(json);
				
				URL address = new URL("https://mandrillapp.com/api/1.0/messages/send-template.json");							 						 
				URLConnection conn = address.openConnection();	
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				OutputStream writer = conn.getOutputStream();													
				writer.write(json.getBytes());
				writer.flush();
				
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				String line;
				 while ((line = rd.readLine()) != null) {
					 System.out.println(line);
				  }	
				  rd.close();
			}catch(Exception e){
				//logger.info(e.getMessage());  
			}
		
		
	}
	
	
	
	
	//@Test
	public void testUserDate(){
		
		 new Thread(new Runnable() {
             public void run() {
                 try {
                     final URL address = new URL("http://dev.crowdscanner.com/rest/postpushnotification");
                     final URLConnection conn = address.openConnection();
                     conn.setDoOutput(true);
                     conn.setRequestProperty("Accept-Charset", "UTF-8;q=0.7,*;q=0.7");
                     conn.setRequestProperty("Accept", "text/plain,text/html,application/xhtml+xml,application/xml;q=0.9,q=0.8");
                     final OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                     //writer.write(String.format("udid=%1$s&text=%2$s", udidEnd, String.format("%1$s just sent you a message", theProfile.getUser().getLastName())));
                     writer.flush();
                     final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                     String line;
                     while ((line = rd.readLine()) != null) {
                         System.out.println(line);
                     }
                     rd.close();
                     System.out.println("DONEEEE");
                 }
                 catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         }).start();
	       
	}
	
	//@Test
 	public void testTwItterSearch() throws MalformedURLException{
	
		System.out.println(RandomStringUtils.randomAlphanumeric(30).toLowerCase());
			
	
		
	}
	
	//@Test
	public void testAvatars () {
		
		List<HashMap<String, Object>> personArrays = new ArrayList<HashMap<String,Object>>();
		
		//Merchant
		HashMap<String, Object> theMerchantHash = new HashMap<String, Object>();
		theMerchantHash.put("nerd_type", "Merchant");
		theMerchantHash.put("nert_key", "merchant");
		theMerchantHash.put("url", "http://images.crowdscanner.com/merchant.png");
		
		List<HashMap<String, String>> arrayClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> cluesHash = new HashMap<String, String>();
		cluesHash.put("question", "Does he love strong beers?");
		cluesHash.put("clue", "He is probably an extrovert");
		HashMap<String, String> cluesHash2 = new HashMap<String, String>();
		cluesHash2.put("question", "Does he love mixed beers?");
		cluesHash2.put("clue", "He is probably an introvert");			
		HashMap<String, String> cluesHash3 = new HashMap<String, String>();
		cluesHash3.put("question", "Does he love swimming?");
		cluesHash3.put("clue", "He is probably built");
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
		theWizardHash.put("url", "http://images.crowdscanner.com/wizard.png");
		
		List<HashMap<String, String>> wizardClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> wizardHash = new HashMap<String, String>();
		wizardHash.put("question", "Does he love strong food?");
		wizardHash.put("clue", "He is probably fat");
		HashMap<String, String> wizardHash2 = new HashMap<String, String>();
		wizardHash2.put("question", "Does he love string movies?");
		wizardHash2.put("clue", "He is probably crazy");			
		HashMap<String, String> wizardHash3 = new HashMap<String, String>();
		wizardHash3.put("question", "Does he love jumping?");
		wizardHash3.put("clue", "He is probably jumper");
		//add has cues
		wizardClues.add(wizardHash);
		wizardClues.add(wizardHash2);
		wizardClues.add(wizardHash3);
		//add array clues into meta hash
		theWizardHash.put("clues", wizardClues);
		
		//Black Knight
		HashMap<String, Object> blackNightHashMeta = new HashMap<String, Object>();
		blackNightHashMeta.put("nerd_type", "Black Knight");
		blackNightHashMeta.put("nert_key", "black_knight");
		blackNightHashMeta.put("url", "http://images.crowdscanner.com/blackKnight.png");
		
		List<HashMap<String, String>> blackNightHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> blackNightHash = new HashMap<String, String>();
		blackNightHash.put("question", "Does he love strong Knight food?");
		blackNightHash.put("clue", "He is probably fat");
		HashMap<String, String> blackNightHash2 = new HashMap<String, String>();
		blackNightHash2.put("question", "Does he love string  Knightmovies?");
		blackNightHash2.put("clue", "He is probably Knight crazy");			
		HashMap<String, String> blackNightHash3 = new HashMap<String, String>();
		blackNightHash3.put("question", "Does he love Knight jumping?");
		blackNightHash3.put("clue", "He is probably Knight jumper");
		//add has cues
		blackNightHashClues.add(blackNightHash);
		blackNightHashClues.add(blackNightHash2);
		blackNightHashClues.add(blackNightHash3);
		//add array clues into meta hash
		blackNightHashMeta.put("clues", blackNightHashClues);
		
		//OutLaw
		HashMap<String, Object> outlawHashMeta = new HashMap<String, Object>();
		outlawHashMeta.put("nerd_type", "OutLaw");
		outlawHashMeta.put("nert_key", "outLaw");
		outlawHashMeta.put("url", "http://images.crowdscanner.com/outLaw.png");
		
		List<HashMap<String, String>> outlawHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> outlawHash = new HashMap<String, String>();
		outlawHash.put("question", "Does he love strong outlaw food?");
		outlawHash.put("clue", "He is probably outlaw");
		HashMap<String, String> outlawHash2 = new HashMap<String, String>();
		outlawHash2.put("question", "Does he love string outlaw movies?");
		outlawHash2.put("clue", "He is probably outlaw crazy");			
		HashMap<String, String> outlawHash3 = new HashMap<String, String>();
		outlawHash3.put("question", "Does he love outlaw jumping?");
		outlawHash3.put("clue", "He is probably outlaw jumper");
		//add has cues
		outlawHashClues.add(outlawHash);
		outlawHashClues.add(outlawHash2);
		outlawHashClues.add(outlawHash3);
		//add array clues into meta hash
		outlawHashMeta.put("clues", outlawHashClues);
		
		
		//Joker
		HashMap<String, Object> jokerHashMeta = new HashMap<String, Object>();
		jokerHashMeta.put("nerd_type", "Joker");
		jokerHashMeta.put("nerd_key", "joker");
		jokerHashMeta.put("url", "http://images.crowdscanner.com/joker.png");
		
		List<HashMap<String, String>> jokerHashClues = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> jokerHash = new HashMap<String, String>();
		jokerHash.put("question", "Does he love strong joker food?");
		jokerHash.put("clue", "He is probably joker fat");
		HashMap<String, String> jokerHash2 = new HashMap<String, String>();
		jokerHash2.put("question", "Does he love string joker movies?");
		jokerHash2.put("clue", "He is probably  jokercrazy");			
		HashMap<String, String> jokerHash3 = new HashMap<String, String>();
		jokerHash3.put("question", "Does he love joker jumping?");
		jokerHash3.put("clue", "He is probably  joker jumper");
		//add has cues
		jokerHashClues.add(jokerHash);
		jokerHashClues.add(jokerHash2);
		jokerHashClues.add(jokerHash3);
		//add array clues into meta hash
		jokerHashMeta.put("clues", jokerHashClues);
		
		personArrays.add(theMerchantHash);
		personArrays.add(theWizardHash);
		personArrays.add(blackNightHashMeta);
		personArrays.add(outlawHashMeta);
		personArrays.add(jokerHashMeta);
		
		String theResult = new JSONSerializer().exclude("*.class").serialize(personArrays);
		System.out.println(theResult);
		
		
	}
	
	//@Test
	public void testNewAlg () {
		
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
		
		Map<String,Integer> theCharacters = new HashMap<String,Integer>();
		theCharacters.put("black_knight", 0);
		theCharacters.put("joker", 0);
		theCharacters.put("merchant", 0);
		theCharacters.put("outlaw", 0);
		theCharacters.put("wizard", 0);
		
		List<Question> allquestions = new ArrayList<Question>();
		Question aQuestion = new Question();
		aQuestion.setQuestionDescription("openness");
		aQuestion.setQuestionPhoneId(1224);		
		aQuestion.setSelectedAnswer("b.agree");
		
		Question aQuestion2 = new Question();
		aQuestion2.setQuestionDescription("agreeableness");
		aQuestion2.setQuestionPhoneId(1225);
		aQuestion2.setSelectedAnswer("b.agree");
		
		Question aQuestion3 = new Question();
		aQuestion3.setQuestionDescription("concioncioness");
		aQuestion3.setQuestionPhoneId(1226);
		aQuestion3.setSelectedAnswer("c.neitheragreenordisagree");
		
		Question aQuestion4 = new Question();
		aQuestion4.setQuestionDescription("stability");
		aQuestion4.setQuestionPhoneId(1227);
		aQuestion4.setSelectedAnswer("a.stronglyagree");
		
		Question aQuestion5 = new Question();
		aQuestion5.setQuestionDescription("extraversion");
		aQuestion5.setQuestionPhoneId(1228);	
		aQuestion5.setSelectedAnswer("a.stronglyagree");
		
		allquestions.add(aQuestion);
		allquestions.add(aQuestion2);
		allquestions.add(aQuestion3);
		allquestions.add(aQuestion4);
		allquestions.add(aQuestion5);
		
		for (Question question : allquestions) {		
			switch (question.getQuestionPhoneId()) {
				case 1224://openness			
					getOpenness(theCharacters, question); 			
					break;
				case 1225://agreeableness			
					getAgreeableness(theCharacters, question); 			
					break;
				case 1226:// concienciosness			
					getConcienciosness(theCharacters, question); 			
					break;
				case 1227:// stability			
					getStability(theCharacters, question); 			
					break;
				case 1228: //extroversion			
					getExtroversion(theCharacters, question);			
					break;			
			}		
		}
			
		
		int repeat = checkForRepeat(theCharacters);		
		if (repeat > 1) {			
			//System.out.println("repeat "+repeat);
			outer:
			for (Question question : allquestions) {		
				switch (question.getQuestionPhoneId()) {
					case 1224://openness			
						getOpenness(theCharacters, question); 
						if (checkForRepeat(theCharacters) > 1) {
							break;
						} else {
							break outer;
						}
					case 1225://agreeableness			
						getAgreeableness(theCharacters, question); 			
						if (checkForRepeat(theCharacters) > 1) {
							break;
						} else {
							break outer;
						}
					case 1226:// concienciosness			
						getConcienciosness(theCharacters, question); 			
						if (checkForRepeat(theCharacters) > 1) {
							break;
						} else {
							break outer;
						}
					case 1227:// stability			
						getStability(theCharacters, question); 			
						if (checkForRepeat(theCharacters) > 1) {
							break;
						} else {
							break outer;
						}
					case 1228: //extroversion			
						getExtroversion(theCharacters, question);			
						if (checkForRepeat(theCharacters) > 1) {
							break;
						} else {
							break outer;
						}			
				}		
			}
		}
		
		for (Map.Entry<String, Integer> entries : theCharacters.entrySet()) {
			System.out.println(entries.getKey()+" "+entries.getValue());				
		}	
		
		HashMap<String, String>characterResult = null;
		List<Integer> allValues = sortCharacterCollec(theCharacters);
		Integer characterValue = allValues.get(0);
		for (Map.Entry<String, Integer> entries : theCharacters.entrySet()) {
			if (entries.getValue().equals(characterValue)) {
				System.out.println("result "+entries.getKey());
				characterResult = resHash.get(entries.getKey());
			}			
		}		
		
		System.out.println(characterResult);
			
	}

	private int checkForRepeat(Map<String, Integer> theCharacters) {
		
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

	private List<Integer> sortCharacterCollec(Map<String, Integer> theCharacters) {
		List<Integer> allValues = new ArrayList<Integer>(theCharacters.values());
		Collections.sort(allValues, new Comparator<Integer>() {
			public int compare(Integer one, Integer two) {				
				return two.compareTo(one);
			}
		});
		return allValues;
	}

	
	public int numberOccurences(Integer[] arr) {
		
		//int arr[] = new int[] { 2, 5, 1, 1, 2, 3 }; // 1, 1, 2, 2, 3, 5
		Arrays.sort(arr);
		// System.out.print(Arrays.toString(arr)) ;

		int newArray[] = new int[arr.length];
		int count = 0;

		for (int i = 0; i < arr.length; i++) {
			// if the next element is the equal to the current one
			if (i + 1 < arr.length && arr[i + 1] == arr[i]) {
				// skip all such equal elements
			} else {
				// Otherwise, add it to your new array
				newArray[count++] = arr[i];
			}
			// System.out.print(temp + " ") ; // 1, 2, 3, 5
		}

		for (int i = 0; i < newArray.length; i++) {
			//System.out.print(newArray[i] + " ");
		}
		
		System.out.println("count "+count);
		return count;
	}
	
	
	
	private void getExtroversion(Map<String, Integer> theCharacters, Question question) {
		
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

	private void getStability(Map<String, Integer> theCharacters, Question question) {
		
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

	private void getConcienciosness(Map<String, Integer> theCharacters, Question question) {
		
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

	private void getAgreeableness(Map<String, Integer> theCharacters, Question question) {
		
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

	private void getOpenness(Map<String, Integer> theCharacters, Question question) {
		
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
	
	//@Test
	public void testTwittUpdate(){ 
		
		Gson gson = new Gson();	
		String data = "{\"senderid\":249,\"profileid\":452, \"shares\":true, \"matchtext\":\"Just matching data\"}";
		java.lang.reflect.Type collectionType = new TypeToken<Map<String, Object>>(){}.getType();		
		Map<String, Object> theDataObj = gson.fromJson(data, collectionType);
		final Map<String, String> proficiency = (Map<String, String>) theDataObj.get("help");	
		if (proficiency == null) System.out.println("compelte");
		System.out.println(proficiency);

	}
}
