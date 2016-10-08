package com.myownmotivator.service.events;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.myownmotivator.model.events.EventSuggestion;


@Service("eventSuggestionService")
public class EventSuggestionServiceImpl implements EventSuggestionService {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Transactional(readOnly=false)
	public void deleteEventSuggestion(EventSuggestion eventSuggestion) {
		
		sessionFactory.getCurrentSession().delete(eventSuggestion);	
	}

	@Transactional(readOnly=false)
	public EventSuggestion saveEventSuggestion(EventSuggestion eventSuggestion) {		
		
		return (EventSuggestion)sessionFactory.getCurrentSession().merge(eventSuggestion);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<EventSuggestion> retrieveEventSuggestions() {
		
		return sessionFactory.getCurrentSession().createQuery("from EventSuggestion").list();
	}

	@Transactional(readOnly=false)
	public EventSuggestion setEventSuggestionVote(int eventSugId) {
		
		EventSuggestion eventSuggestion  = (EventSuggestion) sessionFactory.getCurrentSession().get(EventSuggestion.class, eventSugId);
		
		eventSuggestion.setVotes(eventSuggestion.getVotes() + 1);
		sessionFactory.getCurrentSession().merge(eventSuggestion);
			
		return eventSuggestion;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	public List<EventSuggestion> retrieveFrontendSuggestions() {
		
		return sessionFactory.getCurrentSession().createQuery("from EventSuggestion eg where eg.confirm = true").list();
	}

	@Override
	public EventSuggestion retrieveEventSuggestion(Integer suggestionId) {
		
		return (EventSuggestion) sessionFactory.getCurrentSession().get(EventSuggestion.class, suggestionId);
	}	
}
