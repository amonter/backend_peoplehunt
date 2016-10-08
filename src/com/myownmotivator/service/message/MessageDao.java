package com.myownmotivator.service.message;

import java.util.List;

import com.myownmotivator.model.message.Message;

public interface MessageDao {

	public void sendMessage(Message message);
	
	public List<Message> retrieveMessage(int profileId);
	
	public void setReadMessage(Integer messagePK);
	
	public void deleteMessage (Integer messagePK);
	
	public long retrieveUnreadMessages(int profileId) ;
	
}
