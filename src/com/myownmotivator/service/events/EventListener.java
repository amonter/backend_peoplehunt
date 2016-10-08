package com.myownmotivator.service.events;

import javax.persistence.PostPersist;

import com.myownmotivator.model.events.Event;

/*
 * Methods of this class will be executed automatically before/after respective 
 * database events.
 */
public class EventListener {
	@PostPersist
	public void postPersist(Event event) {
        
    }
}
