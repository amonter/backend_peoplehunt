package com.myownmotivator.service.events;

import java.util.List;

import com.myownmotivator.model.events.EventSuggestion;

public interface EventSuggestionService {

	public EventSuggestion saveEventSuggestion(EventSuggestion eventSuggestion);
	
	public EventSuggestion retrieveEventSuggestion(Integer suggestionId);
	
	public void deleteEventSuggestion(EventSuggestion eventSuggestion);
	
	public List<EventSuggestion> retrieveEventSuggestions();
	
	public List<EventSuggestion> retrieveFrontendSuggestions();
	
	public EventSuggestion setEventSuggestionVote(int eventSugId);
	
}
