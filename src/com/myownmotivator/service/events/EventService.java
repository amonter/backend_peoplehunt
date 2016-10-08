package com.myownmotivator.service.events;

import java.util.List;

import com.myownmotivator.model.events.Event;
import com.myownmotivator.model.profile.Profile;

public interface EventService {

	public List<Event> retrieveEvents();
	
	public List<Event> retrieveDatabaseEvents();
	
	public Event retrieveEvent(Integer id, String startDate);
	
	public void updateEventPrices(List<Event> listEvents);	
	
	public Event findEventDatabase(Integer eventId, boolean partialRes);
	
	public Event findEventDatabaseByGoogleId(String eventId, boolean partialRes);
	
	public void insertEventGadget(String eid, String shortMessage);
	
	public List<Event> retrieveProfilesUpcomingEvents(Boolean partialList);
	
	public List<Event> retrieveUpcomingEvents();
	
	public void deleteEvent(String googleId);
	
	public void updateEventHtml(Integer eventId, String html);
	
	public List<Profile> retrieveEventProfiles(String eventId, String type) ;	
	
	public List<Event> retrieveDatabasePastEvents();

	public List<Profile> listProfileByEvent(long eventId);
	
	public List<Event> listEventsByProfile(long profileId);
	
	public Event getEventById(Integer eventId);	
	
	public void synchronizeTheEvents ();
	
}
