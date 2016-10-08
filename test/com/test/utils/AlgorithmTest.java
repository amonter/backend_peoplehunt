package com.test.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.validator.EmailValidator;
import org.junit.Test;

import com.crowdscanner.controller.avatars.CharacterFactory;
import com.crowdscanner.controller.avatars.CharacterMaker;
import com.crowdscanner.controller.utils.KerseyTypesMatch;
import com.myownmotivator.model.questions.Question;

public class AlgorithmTest {

	
	private static EmailValidator emailValidator = EmailValidator.getInstance();	
	
	//@Test
	public void testEmails() throws UnsupportedEncodingException {
		
		String email = "Culundro _perierere_473514.1319152882@meetfoeal.com";		
		email = URLDecoder.decode(email , "UTF-8");
		System.out.println(email);
		if (!emailValidator.isValid(email)) {
			System.out.println("nine");
		}
		
		String [] theEmail = email.split("@");
		email = theEmail[0].replaceAll("\\W", "_");
		email = String.format("%1$s@meetforeal.com",  email);	
		System.out.println(email);
		
	}
	
	
	
	@Test
	public void testAlgorithm() {  
	
		List<Question> questions = new ArrayList<Question>();
		
		Question aQuestion2 = new Question();	
		aQuestion2.setQuestionPhoneId(36387384);//open
		aQuestion2.setSelectedAnswer("e.Stronglydisagree");
		
		Question aQuestion3 = new Question();	
		aQuestion3.setQuestionPhoneId(36507925);//agree
		aQuestion3.setSelectedAnswer("e.Stronglydisagree");
		
		Question aQuestion4 = new Question();	
		aQuestion4.setQuestionPhoneId(48625091);//concienc
		aQuestion4.setSelectedAnswer("a.Stronglyagree");
		
		Question aQuestion5 = new Question();	
		aQuestion5.setQuestionPhoneId(82058253);//stability
		aQuestion5.setSelectedAnswer("e.Stronglydisagree");
		
		Question aQuestion6 = new Question();	
		aQuestion6.setQuestionPhoneId(12424026);//extra
		aQuestion6.setSelectedAnswer("a.Stronglyagree");
		
		Question aQuestion7 = new Question();	
		aQuestion7.setQuestionPhoneId(64943376);//extra
		aQuestion7.setSelectedAnswer("a.Stronglyagree");
		
		Question aQuestion8 = new Question();	
		aQuestion8.setQuestionPhoneId(67742148);//extra
		aQuestion8.setSelectedAnswer("e.Stronglydisagree");
		
		Question aQuestion9 = new Question();	
		aQuestion9.setQuestionPhoneId(44075500);//extra
		aQuestion9.setSelectedAnswer("a.Stronglyagree");
		
		Question aQuestion10 = new Question();	
		aQuestion10.setQuestionPhoneId(20384231);//extra
		aQuestion10.setSelectedAnswer("b.Female");
		
		
		questions.add(aQuestion10);
		questions.add(aQuestion9);
		questions.add(aQuestion8);
		questions.add(aQuestion7);		
		questions.add(aQuestion6);
		questions.add(aQuestion5);
		questions.add(aQuestion4); 
		questions.add(aQuestion3);
		questions.add(aQuestion2);					
		
		
		CharacterMaker characterMaker = CharacterFactory.retrieveBundleCharacters(111);			
		HashMap<String, String>theRest = characterMaker.characterAlg(questions);			
		System.out.println(theRest);
		
		//System.out.println(KerseyTypesMatch.findCharacterByLetters(theRest.get("code")));
		
	}
}
