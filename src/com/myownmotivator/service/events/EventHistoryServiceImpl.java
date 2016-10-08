package com.myownmotivator.service.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.myownmotivator.model.events.Event;
import com.myownmotivator.model.events.EventHistory;

@Repository
@Transactional
public class EventHistoryServiceImpl implements EventHistoryService {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Insert or update a video record
	 */
	@Transactional
	public void createEventHistory(EventHistory history) throws Exception {
		sessionFactory.getCurrentSession().saveOrUpdate(history);
	}

	@Transactional(readOnly=true)
	public List<EventHistory> getHistory(int profileId) throws Exception {
		Query query = sessionFactory.getCurrentSession().createQuery(
			"from EventHistory WHERE profileId.id=:id ORDER BY creationDate DESC");
		query.setParameter("id",profileId);
		List<EventHistory> eventHistoryList = query.list();
		
		return eventHistoryList;
	}
	
	@Transactional(readOnly=false)
	public void deleteEventHistory(int profileId) {
		
		StatelessSession session =  sessionFactory.openStatelessSession();
		Transaction tx = session.beginTransaction();
		ScrollableResults events = session.createQuery("from EventHistory").scroll(
				ScrollMode.FORWARD_ONLY);		
		
		List<EventHistory> list = generateEventHistoryList(events);
		
		for (EventHistory eventHistory : list) {			
			
			if (eventHistory.getProfileId().getId() == profileId) {
			
				session.delete(eventHistory);			
			}
		}		
		
		tx.commit();
		session.close();	
		
	}
	
	private List<EventHistory> generateEventHistoryList(ScrollableResults events) {
		
		List<EventHistory> eventList = new ArrayList<EventHistory>();		
		while (events.next()) {
			EventHistory eventHistory = (EventHistory) events.get(0);		
			eventList.add(eventHistory);
		}

		return eventList;
	}
}
