package com.test.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.TwilioRestResponse;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.AvailablePhoneNumber;
import com.twilio.sdk.resource.list.AvailablePhoneNumberList;



public class twilioMessageTest {
	//variables	
	private static String accSID = "AC942c230380184d03b9a7c21ac4edb701";
	private static String authToken = "3d8e058a2eb34c98c071b45bf0b3f01c";
	private static String From = "+16464033045";
	private TwilioRestClient client;
	private Account account;	
	
	//methods
	//@Test
	public void testSendMessageDetailed(){
		
		//String To = "+447900316333";	//Jasons number
		String To = "3472653718";
		String Text = "Testing testSendMessageDetailed()!";
		
		client = new TwilioRestClient(accSID, authToken, null);	//default value from Adrians account
		account = client.getAccount();
		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("To", To);
		params.put("From", From);
		params.put("Body", Text);
		
		try{
			TwilioRestResponse response = client.request("/"+client.DEFAULT_VERSION+"/Accounts/"+client.getAccountSid()+"/SMS/Messages/", "POST", params);
			if(response.isError()){
				System.out.println("Error sending sms:\n " + response.getResponseText());
			}
			else{
				System.out.println("sent sms! ;D");
			}
		}catch(TwilioRestException e){
			e.printStackTrace();
		}
	}//end
	
	//@Test
	public void testSendMessage(){
		
			//String To = "+447900316333";	//Jasons (UK) number
			String To = "3472653718";
			String Text = "Testing testSendMessage()!";
			
			client = new TwilioRestClient(accSID, authToken);	//default value from Adrians account
			account = client.getAccount();
			SmsFactory smsFactory = account.getSmsFactory();	//gets a message factory associated with the account
			
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("To", To);
			parameters.put("From", From);		//self-explanatory
			parameters.put("Body", Text);
			
			//try to send the sms
			try{
				com.twilio.sdk.resource.instance.Sms sms = smsFactory.create(parameters);	//this sends a POST request
				System.out.println("Sent the sms: " + sms.getSid());
			}catch(TwilioRestException e){
				e.printStackTrace();
			}
	}//end
	
	
	//@Test
	public void testWhatGetAvailableNumbersDoes(){	//some code that gets the list of available numbers - whatever that means
		
		String returnedNumber = null;
				
		client = new TwilioRestClient(accSID, authToken, null);	//default value from Adrians account
		account = client.getAccount();
		
		AvailablePhoneNumberList numbers = account.getAvailablePhoneNumbers();
		Iterator<AvailablePhoneNumber> our = numbers.iterator();
		AvailablePhoneNumber num = null;
		if(our.hasNext()){
			num = our.next();
			returnedNumber = num.getPhoneNumber();
		}
		else{
			System.out.println("Error: there's no 'next' iterator...");
		}
		
		//see is the number we own the same as the number we return from the API call
		if(From.equals(returnedNumber)){
			System.out.println("The getAvailablePhoneNumbers() method return the Twilio numbers that you own.");
		}
		else{
			System.out.println("The getAvailablePhoneNumbers() method returns random(?) numbers that are available(to buy?).");
			System.out.println("Our Twilio number is: " + From);
			System.out.println("The number returned is: "+ returnedNumber);
		}
	}//end
	
	
	
	@Test
	public void testCheckAllBundle(){
		
		
		
		
	}

}
