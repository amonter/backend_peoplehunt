package com.myownmotivator.service.peoplehuntv2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.hibernate.*;
import org.hibernate.Transaction;

import com.myownmotivator.model.profile.*;
import com.myownmotivator.model.profile.File;

import java.io.*;

import com.myownmotivator.model.*;

import java.util.*;

import com.peoplehuntv2.model.*;
import com.crowdscanner.controller.utils.*;

@Service("inboxService")
@Transactional
public class InboxServiceImpl implements InboxService
{
	
	@Autowired
    private SessionFactory sessionFactory;
    
    @Transactional(readOnly = true)
    public Map<Integer, Boolean> retrieveNewInbox(final List<Integer> profileIds, final String recipientUsername) {
        final Session session = (Session)this.sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();
        final Map<Integer, Boolean> newInbox = new HashMap<Integer, Boolean>();
        for (final Integer profileId : profileIds) {
            final ScrollableResults messageScroll = session.createQuery("from AsyncMessage as asy where asy.sender =:username and asy.profile.id =:profileId order by asy.dateSent desc").setInteger("profileId", (int)profileId).setString("username", recipientUsername).scroll(ScrollMode.FORWARD_ONLY);
            Date lastView = null;
            newInbox.put(profileId, false);
            while (messageScroll.next()) {
                final AsyncMessage asyncMessage = (AsyncMessage)messageScroll.get(0);
                lastView = asyncMessage.getLastView();
                if (lastView == null) {
                    newInbox.put(profileId, true);
                    break;
                }
            }
        }
        tx.commit();
        session.close();
        return newInbox;
    }
    
    @Transactional(readOnly = false)
    public Map<Integer, String> allMessagesIHaveSent(final Integer profileId) {
    	System.out.println("profileid "+profileId);
        final Session session = (Session)this.sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();
        System.out.println("profileid start ");
        final Profile theProfile = (Profile)session.load((Class)Profile.class, (Serializable)profileId);
        final List<AsyncMessage> restMessages = theProfile.getAsyncMessages();
        Collections.sort(restMessages, new Comparator<AsyncMessage>() {
            public int compare(final AsyncMessage async, final AsyncMessage async2) {
                return async2.getDateSent().compareTo(async.getDateSent());
            }
        });
        final Map<String, String> latestMessages = new HashMap<String, String>();
        for (final AsyncMessage asyncMessage : restMessages) {
            if (!latestMessages.containsKey(asyncMessage.getSender())) {
                latestMessages.put(asyncMessage.getSender(), asyncMessage.getContent());
            }
        }
        final Map<Integer, String> formattedLatestMessages = new HashMap<Integer, String>();
        for (final Map.Entry<String, String> entry : latestMessages.entrySet()) {
            final String theUsername = entry.getKey();
            final User theUser = (User)session.createQuery("from User as u where u.userName=:username and u.profile.source !=:source").setString("username", theUsername).setString("source", "facebook").uniqueResult();
            formattedLatestMessages.put(theUser.getProfile().getId(), entry.getValue());
        }
        tx.commit();
        session.close();
        return formattedLatestMessages;
    }
    
    @Transactional(readOnly = false)
    public List<AsyncMessage> messagesSentToMe(final String recipient) {
        List<AsyncMessage> asyncMessages = new ArrayList<AsyncMessage>();
        Session session = (Session)this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        ScrollableResults messageScroll = session.createQuery("from AsyncMessage as asy where asy.sender =:recipient order by asy.dateSent desc").setString("recipient", recipient).scroll(ScrollMode.FORWARD_ONLY);
        while (messageScroll.next()) {
            AsyncMessage asyncMessage = (AsyncMessage)messageScroll.get(0);
            Calendar theCal = Calendar.getInstance();
            asyncMessage.setLastView(theCal.getTime());
            asyncMessage.setReference("other");
            Profile aProfile = asyncMessage.getProfile();
            User aUser = aProfile.getUser();//hack for lazy exception            
            String imageUrl = RestUtils.extractURLProfile(aProfile, "http://images.crowdscanner.com/anon_nosmile.png");
            aProfile.setUser(aUser);
            asyncMessage.setProfile(aProfile);
            asyncMessage.setProfileImageUrl(imageUrl);
            asyncMessages.add(asyncMessage);
        }
        tx.commit();
        session.close();
        return asyncMessages;
    }
    
    @Transactional(readOnly = false)
    public List<AsyncMessage> messagesOtherSent(final String recipient, final Integer sender) {
        final List<AsyncMessage> asyncMessages = new ArrayList<AsyncMessage>();
        final Session session = (Session)this.sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();
        final ScrollableResults messageScroll = session.createQuery("from AsyncMessage as asy where asy.sender =:recipient and asy.profile.id =:calledId order by asy.dateSent desc").setString("recipient", recipient).setInteger("calledId", (int)sender).scroll(ScrollMode.FORWARD_ONLY);
        while (messageScroll.next()) {
            final AsyncMessage asyncMessage = (AsyncMessage)messageScroll.get(0);
            final Calendar theCal = Calendar.getInstance();
            asyncMessage.setLastView(theCal.getTime());
            asyncMessage.setReference("other");
            asyncMessages.add(asyncMessage);
        }
        tx.commit();
        session.close();
        return asyncMessages;
    }
    
    
    @Transactional(readOnly = false)
    public List<AsyncMessage> retrieveMyRelatedActivity(final Integer profileId) {
        final Session session = (Session)this.sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();
        final Profile theProfile = (Profile)session.load((Class)Profile.class, (Serializable)profileId);
        final List<Notification> allNotifications = theProfile.getNotifications();
        final List<AsyncMessage> otherMessages = new ArrayList<AsyncMessage>();
        for (final Notification theNotification : allNotifications) {
            final Profile otherProfile = (Profile)session.load((Class)Profile.class, (Serializable)theNotification.getSenderId());
            final ScrollableResults messageScroll = session.createQuery("from AsyncMessage as asy where asy.sender !=:recipient and asy.profile.id =:calledId order by asy.dateSent desc").setString("recipient", theProfile.getUser().getUserName()).setInteger("calledId", (int)otherProfile.getId()).scroll(ScrollMode.FORWARD_ONLY);
            if (messageScroll.next()) {
                final AsyncMessage asyncMessage = (AsyncMessage)messageScroll.get(0);
                asyncMessage.setSenderImageUrl(RestUtils.extractURLProfile(otherProfile, "http://images.crowdscanner.com/anon_nosmile.png"));
                asyncMessage.setProfileName(asyncMessage.getProfile().getUser().getLastName());
                asyncMessage.setProfileImageUrl(RestUtils.extractURLProfile(asyncMessage.getProfile(), "http://images.crowdscanner.com/anon_nosmile.png"));
                otherMessages.add(asyncMessage);
            }
        }
        tx.commit();
        session.close();
        return otherMessages;
    }
    
    public List<AsyncMessage> retrieveAllMessages() {
        return (List<AsyncMessage>)this.sessionFactory.getCurrentSession().createQuery("from AsyncMessage").list();
    }
}