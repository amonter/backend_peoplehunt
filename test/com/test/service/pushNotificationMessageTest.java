package com.test.service;

import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;

import org.junit.Test;

public class pushNotificationMessageTest {

	@Test
	public void testPusher(){
		
		try {
			List<PushedNotification> notifications =  Push.alert("Hello a new part of your brain is active 345", "languagehunt_prod.p12", "espana19", true, "c4c4def77966ca8e5012f157a1893e6420c22f37ae09f0d0d26c11ecf5e61636");
			 for (PushedNotification notification : notifications) {
                 if (notification.isSuccessful()) {
                         /* Apple accepted the notification and should deliver it */  
                         System.out.println("Push notification sent successfully to: " +
                                                         notification.getDevice().getToken());
                         /* Still need to query the Feedback Service regularly */  
                 } else {
                         String invalidToken = notification.getDevice().getToken();
                         /* Add code here to remove invalidToken from your database */  

                         /* Find out more about what the problem was */  
                         Exception theProblem = notification.getException();
                         theProblem.printStackTrace();

                         /* If the problem was an error-response packet returned by Apple, get it */  
                         ResponsePacket theErrorResponse = notification.getResponse();
                         if (theErrorResponse != null) {
                                 System.out.println(theErrorResponse.getMessage());
                         }
                 }
         }
			
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeystoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	
}
