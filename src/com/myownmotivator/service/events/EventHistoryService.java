package com.myownmotivator.service.events;

import java.util.List;

import com.myownmotivator.model.events.EventHistory;

public interface EventHistoryService {
	
	public void createEventHistory(EventHistory history) throws Exception;
	public List<EventHistory> getHistory(int profileId) throws Exception;
	public void deleteEventHistory(int profileId);
	
}
