package com.test.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.myownmotivator.model.events.Event;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.service.FileDao;
import com.myownmotivator.service.events.EventHistoryService;
import com.myownmotivator.service.events.EventService;
import com.myownmotivator.service.profile.ProfileService;

@RunWith(SpringJUnit4ClassRunner.class)
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations={"ApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class EventServiceTest {

	@Autowired
	private EventService eventService;	
	
	@Autowired
	private EventHistoryService eventHistoryService;
	
	@Autowired
	private FileDao fileService;
	
	
	@Autowired
	private ProfileService profileService;
	
	@Test
	public void testEventSync (){
		
		final int targetId = 17;
		final int receiverId5 = 563;
		final String content = "―ηθ  ――― as η―――";
		
		try{//store messages sent
			new Thread(new Runnable() {								
				@Override
				public void run() {
					try{
						
						String data = URLEncoder.encode("profileid", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(targetId), "UTF-8");
				        data += "&" + URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8");
				        data += "&" + URLEncoder.encode("recipient", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(receiverId5), "UTF-8");
						
						
				        //System.out.println(data);
				        
						URL address = new URL("http://dev.crowdscanner.com/rest/postnewmessage2/"+targetId+"/"+receiverId5);							 						 
						URLConnection conn = address.openConnection();	
						conn.setDoOutput(true);
						conn.setRequestProperty("Accept-Charset", "UTF-8;q=0.7,*;q=0.7");				
						conn.setRequestProperty("Accept", "text/plain,text/html,application/xhtml+xml,application/xml;q=0.9,q=0.8");
						OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());							
						//writer.write(String.format("profileid=%1$d&content=%2$s&recipient=%3$d", targetId, content, receiverId5));
						writer.write( URLEncoder.encode(data, "UTF-8") );
						writer.flush();

						BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));							
				
						String line;
						while ((line = rd.readLine()) != null) {
							System.out.println(line);
						}	
						rd.close();
						}catch(Exception e){
							//logger.info(e.getMessage());  
						}	
					}
				}).start();
			}catch(Exception e){
				//logger.info(e.getMessage());  
			}		 
		
	}
	
	//@Test
	public void updateEventPrices () {
		
		Event event = new Event();
		event.setGoogleId("http://www.google.com/calendar/feeds/r975a1bppid43vhdi5gemn75dg@group.calendar.google.com/public/full/cbbcaebpc6urvtam1secvnnkmg");	
		event.setPrice(59.34);
		
		Event event2 = new Event();
		event2.setGoogleId("http://www.google.com/calendar/feeds/r975a1bppid43vhdi5gemn75dg@group.calendar.google.com/public/full/irep5p7jicajbp0enf03qce9s8");	
		event2.setPrice(59.5);
		
		List<Event> listEvents = new ArrayList<Event>();
		listEvents.add(event);
		listEvents.add(event2);
		
		//eventService.updateEventPrices(listEvents);
		
		
	}
	
	//@Test
	public void testGadgetInsertion(){
		
		//eventService.insertEventGadget("http://www.google.com/calendar/feeds/adrian.mont@gmail.com/public/full/84q77ocvg777umm4e6bpf9hg78", "This is going to be another event");
		
	}
	
	
	//@Test
	public void testDeleteEvent(){
		
		//eventService.deleteEvent("http://www.google.com/calendar/feeds/r975a1bppid43vhdi5gemn75dg@group.calendar.google.com/public/full/joddi2ikcc45lrf281gaqrf71c");
		
	}
	
	
	
	public void testRetrieveEventProfiles(){
		
		Event event = eventService.findEventDatabase(2, true);
		for (Profile  profile : event.getParticipants()) {
			
			//System.out.println(event.getParticipants().size() +" "+profile.getUser().getUserName());
		}	
	}
	
	//@Test
	public void testDeleteEventHistory() { 
		
		
		eventHistoryService.deleteEventHistory(71);
		
	}
	
	
	//@Test
	public void testRetrieveProfiles() {
		
		List<Event> events = eventService.retrieveProfilesUpcomingEvents(true);
		for (Event event : events) {
			
			System.out.println(event.getName());
			for(Profile profile : event.getParticipants()) {
				
				//System.out.println(event.getParticipants().size() +" "+profile.getUser().getUserName());
			}
		}
	}
	
	//@Test
	public void testRetrievePastEvents() { 
		
		List<Event> events = eventService.retrieveDatabasePastEvents();
		for (Event event : events) {
			
			//System.out.println(event.getName());		
		}
		
	}
	
	//@Test
	public void testRetrieveTheEvent() {  
		
		List<Event> events = eventService.retrieveEvents();
		for (Event event : events) {
			
			System.out.println(event.getName());		
		}
		
	}
	
}
