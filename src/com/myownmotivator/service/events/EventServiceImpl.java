package com.myownmotivator.service.events;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.myownmotivator.model.events.Event;
import com.myownmotivator.model.profile.Profile;

@Service("eventService")
public class EventServiceImpl implements EventService, InitializingBean {

	@Override
	public void deleteEvent(String googleId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Event findEventDatabase(Integer eventId, boolean partialRes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event findEventDatabaseByGoogleId(String eventId, boolean partialRes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event getEventById(Integer eventId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertEventGadget(String eid, String shortMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Event> listEventsByProfile(long profileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Profile> listProfileByEvent(long eventId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> retrieveDatabaseEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> retrieveDatabasePastEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event retrieveEvent(Integer id, String startDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Profile> retrieveEventProfiles(String eventId, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> retrieveEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> retrieveProfilesUpcomingEvents(Boolean partialList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> retrieveUpcomingEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void synchronizeTheEvents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEventHtml(Integer eventId, String html) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEventPrices(List<Event> listEvents) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	
}
