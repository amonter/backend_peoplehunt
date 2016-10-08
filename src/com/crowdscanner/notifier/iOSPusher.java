/**
 * This is a static class for sending Push notification to iOS devices
 */

package com.crowdscanner.notifier;

import java.util.List;
import java.util.Vector;

import javapns.Push;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;

public class iOSPusher {
	
	public static void send(String appleDeviceID, String Text){
		try{
			//List<PushedNotification> result = Push.alert(Text, "/usr/local/tomcat/peoplepush2.p12", "espana19", true, appleDeviceID);
			List<PushedNotification> result = Push.combined(Text, 0, "default", "peoplehuntv2prodreal.p12", "espana19", true, appleDeviceID);
			//List<PushedNotification> result = Push.alert(Text, "peoplepush2.p12", "espana19", true, appleDeviceID);
			
			for (PushedNotification pushedNotification : result) {
				
				if (!pushedNotification.isSuccessful()) {
					Exception theProblem = pushedNotification.getException();
					System.out.println("error ios "+theProblem.getMessage());

				
					ResponsePacket theErrorResponse = pushedNotification.getResponse();
					if (theErrorResponse != null) {
						System.out.println(" data packet "+theErrorResponse.getMessage());
					}
					
                 } else {
                	 	
                	 	System.out.println("Success send to"+appleDeviceID+" "+Text);
                 }
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void sendMultiple(String [] appleDeviceIDs, String Text){
		try{
			//List<PushedNotification> result = Push.alert(Text, "/usr/local/tomcat/peoplehuntv2prodreal.p12", "espana19", true, appleDeviceIDs);
			//List<PushedNotification> result = Push.combined(Text, 0, "default", "peoplehuntv2prodreal.p12", "espana19", true, appleDeviceIDs);
			List<PayloadPerDevice> pairs = new Vector<PayloadPerDevice>();		
			
			for(int i=0; i<appleDeviceIDs.length; i++){								
				//String theMessage =  String.format("%1$s connection is available now: open the app to get connected" , messageToSend);
				String deviceId = appleDeviceIDs[i];
				pairs.add(new PayloadPerDevice(PushNotificationPayload.combined(Text, 0, "default"), new BasicDevice(deviceId)));
			}
			
			//Push.payloads("peoplepush2.p12", "espana19", true, pairs);
			List<PushedNotification> result = Push.payloads("/var/lib/tomcat7/peoplehuntv2_pushaws.p12", "espana19", true, pairs);	
			
			for (PushedNotification pushedNotification : result) {
				
				if (!pushedNotification.isSuccessful()) {
					Exception theProblem = pushedNotification.getException();
					System.out.println("error ios "+theProblem.getMessage());
				
					ResponsePacket theErrorResponse = pushedNotification.getResponse();
					if (theErrorResponse != null) {
						System.out.println(" data packet "+theErrorResponse.getMessage());
					}
					
                 } else {                	 	
                	 	System.out.println("Successfully sent the notifications.");
                 }
			}
			//List<PushedNotification> result = Push.alert(Text, "peoplepush2.p12", "espana19", true, appleDeviceIDs);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
