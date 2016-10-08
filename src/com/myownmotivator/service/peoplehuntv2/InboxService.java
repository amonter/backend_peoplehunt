package com.myownmotivator.service.peoplehuntv2;

import java.util.*;

import com.peoplehuntv2.model.*;

public interface InboxService
{
    Map<Integer, Boolean> retrieveNewInbox(List<Integer> p0, String p1);
    
    List<AsyncMessage> messagesSentToMe(String p0);
    
    List<AsyncMessage> retrieveAllMessages();
    
    List<AsyncMessage> retrieveMyRelatedActivity(Integer p0);
    
    Map<Integer, String> allMessagesIHaveSent(Integer p0);
    
    public List<AsyncMessage> messagesOtherSent(final String recipient, final Integer sender);
}