package com.myownmotivator.service.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.apache.log4j.*;

import java.io.*;

import org.apache.commons.lang.*;

import com.myownmotivator.model.profile.*;
import com.myownmotivator.model.profile.File;

import java.util.*;

import com.crowdscanner.controller.utils.*;

import org.hibernate.*;

import com.crowdscanner.model.*;
import com.google.common.collect.*;
import com.peoplehuntv2.model.*;
import com.crowdscanner.grouping.*;

@Service("profileService")
@Transactional
public class ProfileDaoImpl implements ProfileService {
	
	@Autowired
    private SessionFactory sessionFactory;
    static final Logger logger;
    private static final String getUserProfile = "select p from Profile as p where p.id=:id";
    private static final String getUserId = "select p.user.id from Profile as p where p.id=:id";
    private static final String getUserFiles = "Select f from File as f where f.profile.id=:userProfile and f.fileName = 'profilePhoto' ";
    private static final String searchUserProfile = "Select p from Profile as p where p.user.gender like :gender and p.user.country like :country and p.user.state like :state and p.user.userName like :criteria";
    private static final String globalUserProfileSearch = "Select p from Profile as p where p.user.country like :criteria or p.user.userName like :criteria";
    private static final String getProfilesByGoals = "from Profile profile where :goals in (select goal.description from Goal as goal where profile.id=goal.profileID)";
    
    @Transactional(readOnly = true)
    public Profile getUserProfile(final Integer id) {
        return (Profile)this.sessionFactory.getCurrentSession().load((Class)Profile.class, (Serializable)id);
    }
    
    @Transactional(readOnly = true)
    public List<Profile> getProfileByGoals(final String goals) {
        final List<Profile> listProfile = (List<Profile>)this.sessionFactory.getCurrentSession().createQuery("from Profile profile where :goals in (select goal.description from Goal as goal where profile.id=goal.profileID)").setString("goals", goals).list();
        return listProfile;
    }
    
    @Transactional(readOnly = false)
    public void updateProfile(final Profile profile) {
        this.sessionFactory.getCurrentSession().merge(profile);
    }
    
    @Transactional(readOnly = true)
    public File getFile(final Integer userProfile) {
        final List<File> listFiles = (List<File>)this.sessionFactory.getCurrentSession().createQuery("Select f from File as f where f.profile.id=:userProfile and f.fileName = 'profilePhoto' ").setInteger("userProfile", (int)userProfile).list();
        if (listFiles.size() == 1) {
            return listFiles.get(0);
        }
        return null;
    }
    
    @Transactional(readOnly = false)
    public void deleteProfile(final Profile profile) {
        this.sessionFactory.getCurrentSession().delete((Object)profile);
    }
    
    @Transactional(readOnly = true)
    public List<Profile> getAllProfiles() {
        final List<Profile> listProfiles = (List<Profile>)this.sessionFactory.getCurrentSession().createQuery("from Profile").list();
        return listProfiles;
    }
    
    @Transactional(readOnly = true)
    public List<Profile> searchProfile(String gender, String country, String state, String criteria) {
        List<Profile> profileList = new ArrayList<Profile>();
        criteria = (StringUtils.isEmpty(criteria) ? "%" : criteria);
        if (StringUtils.isEmpty(gender) || StringUtils.isEmpty(country)) {
            profileList = (List<Profile>)this.sessionFactory.getCurrentSession().createQuery("Select p from Profile as p where p.user.country like :criteria or p.user.userName like :criteria").setString("criteria", criteria).list();
        }
        else {
            country = (StringUtils.isEmpty(country) ? "%" : country);
            gender = ((gender.equals("both") || StringUtils.isEmpty(gender)) ? "%" : gender);
            state = (StringUtils.isEmpty(state) ? "%" : state);
            final Query query = this.sessionFactory.getCurrentSession().createQuery("Select p from Profile as p where p.user.gender like :gender and p.user.country like :country and p.user.state like :state and p.user.userName like :criteria");
            final Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("gender", gender);
            parameters.put("country", country);
            parameters.put("criteria", criteria);
            parameters.put("state", state);
            profileList = (List<Profile>)query.setProperties((Map)parameters).list();
        }
        return profileList;
    }
    
    @Transactional(readOnly = true)
    public Integer getUserId(final Integer userId) {
        final List<Integer> listFiles = (List<Integer>)this.sessionFactory.getCurrentSession().createQuery("select p.user.id from Profile as p where p.id=:id").setInteger("id", (int)userId).list();
        if (listFiles.size() == 1) {
            return listFiles.get(0);
        }
        return null;
    }
    
    @Transactional(readOnly = false)
    public void saveProfileInfo(final ProfileInfo profileInfo) {
        this.sessionFactory.getCurrentSession().merge((Object)profileInfo);
    }
    
    @Transactional(readOnly = false)
    public void saveProfileTestimony(final Testimonies testimonies) {
        this.sessionFactory.getCurrentSession().merge((Object)testimonies);
    }
    
    @Transactional(readOnly = true)
    public List<Profile> retrieveRecentProfiles(final Date date) {
        List<Profile> profileList = new ArrayList<Profile>();
        profileList = (List<Profile>)this.sessionFactory.getCurrentSession().createQuery("from Profile pr where  pr.profileCreation >=:creationDate").setMaxResults(11).setDate("creationDate", date).list();
        return profileList;
    }
    
    @Transactional(readOnly = true)
    public Profile findProfileByConfirmation(final long confirmation) {
        final Profile profile = (Profile)this.sessionFactory.getCurrentSession().createQuery("from Profile p where p.confirmationNumber =:confirmation").setLong("confirmation", confirmation).uniqueResult();
        return profile;
    }
    
    @Transactional(readOnly = false)
    public List<Testimonies> retrieveTestimonies() {
        return (List<Testimonies>)this.sessionFactory.getCurrentSession().createQuery("from Testimonies").list();
    }
    
    @Transactional(readOnly = true)
    public List<Profile> retrievePresenterProfile() {
        return (List<Profile>)this.sessionFactory.getCurrentSession().createQuery("from Profile pr where  pr.presenter =true").list();
    }
    
    public Profile saveProfile(final Profile profile) {
        return (Profile)this.sessionFactory.getCurrentSession().merge((Object)profile);
    }
    
    public void deleteProfileInfo(final ProfileInfo profileInfo) {
        this.sessionFactory.getCurrentSession().delete((Object)profileInfo);
    }
    
    public Profile getProfileByHuntId(final Integer huntId) {
        return (Profile)this.sessionFactory.getCurrentSession().createQuery("from Profile as p where p.profileInfo.peopleHuntId =:huntId").setInteger("huntId", (int)huntId).uniqueResult();
    }
    
    @Transactional(readOnly = false)
    public void addAllProfileNotifications(final Collection<Map<String, Object>> notifications) {
        final Session session = (Session)this.sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();
        for (final Map<String, Object> map : notifications) {
            final double profileId = (Double)map.get("profileid");
            final Profile theProfile = (Profile)session.load((Class)Profile.class, (Serializable)(int)profileId);
            final Notification notification = new Notification();
            final double senderId =  (Double)map.get("senderid");
            notification.setSenderId((int)senderId);
            final String matchText = (String)map.get("matchtext");
            final boolean shares = (Boolean)map.get("shares");
            if (theProfile.getNotifications().contains(notification)) {
                for (final Notification storedNotification : theProfile.getNotifications()) {
                    if (storedNotification.getSenderId() == (int)senderId) {
                        final Calendar theCal = Calendar.getInstance();
                        storedNotification.setNotificationTime(theCal.getTime());
                        notification.setMatchItem(matchText);
                        notification.setCallBack(shares);
                        break;
                    }
                }
            }
            else {
                final List<Notification> notificationArray = new ArrayList<Notification>();
                notification.setProfile(theProfile);
                notification.setMatchItem(matchText);
                notification.setCallBack(shares);
                final Calendar theCal2 = Calendar.getInstance();
                notification.setNotificationTime(theCal2.getTime());
                notificationArray.add(notification);
                theProfile.setNotifications((List)notificationArray);
                session.merge((Object)theProfile);
            }
        }
        tx.commit();
        session.close();
    }
    
    @Transactional(readOnly = false)
    public void addAllConnections(final Collection<Map<String, Object>> allConnections, final Integer profileId) {
        final Session session = (Session)this.sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();
        final Profile theProfile = (Profile)session.load((Class)Profile.class, (Serializable)(int)profileId);
        for (final Map<String, Object> map : allConnections) {
            final Connection connection = new Connection();
            final double senderId = (Double)map.get("profile_id");
            connection.setWhoConnected((int)senderId);
            final double status = (Double)map.get("status");
            if (theProfile.getConnections().contains(connection)) {
                for (final Connection storedConnection : theProfile.getConnections()) {
                    if (storedConnection.getWhoConnected() == (int)senderId) {
                        connection.setStatus((int)status);
                        break;
                    }
                }
            }
            else {
                final List<Connection> connectionArray = new ArrayList<Connection>();
                connection.setProfile(theProfile);
                connection.setStatus((int)status);
                final Calendar theCal = Calendar.getInstance();
                connectionArray.add(connection);
                theProfile.setConnections((List)connectionArray);
                session.merge((Object)theProfile);
            }
        }
        tx.commit();
        session.close();
    }
    
    @Transactional(readOnly = false)
    public List<Notification> retrieveProfileNotifications(final Integer profileId) {
        final Session session = (Session)this.sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();
        final Profile theProfile = (Profile)session.load((Class)Profile.class, (Serializable)profileId);
        final List<Notification> allNotifications = (List<Notification>)theProfile.getNotifications();
        for (final Notification theNotification : allNotifications) {
            final Profile otherProfile = (Profile)session.load((Class)Profile.class, (Serializable)theNotification.getSenderId());
            theNotification.setName(otherProfile.getUser().getLastName());
            theNotification.setImageurl(RestUtils.extractURLProfile(otherProfile, "http://images.crowdscanner.com/anon_nosmile.png"));
            theNotification.setNotificationTime(otherProfile.getLastOnline());
            final ScrollableResults messageScroll = session.createQuery("from AsyncMessage as asy where asy.sender =:recipient and asy.profile.id =:calledId order by asy.dateSent desc").setString("recipient", theProfile.getUser().getUserName()).setInteger("calledId", (int)otherProfile.getId()).scroll(ScrollMode.FORWARD_ONLY);
            if (messageScroll.next()) {
                final AsyncMessage asyncMessage = (AsyncMessage)messageScroll.get(0);
                theNotification.setLastMessage(asyncMessage.getEncodedContent());
            }
        }
        tx.commit();
        session.close();
        return allNotifications;
    }
    
    public List<Map<String, Object>> doMatchingProcess() {
        final Session session = (Session)this.sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();
        final ScrollableResults profileScroll = session.createQuery("from Profile as p where p.user.email is not null").scroll(ScrollMode.FORWARD_ONLY);
        final List<HunterProfile> allHunters = new ArrayList<HunterProfile>();
        while (profileScroll.next()) {
            final Profile profile = (Profile)profileScroll.get(0);
            final HunterProfile hunter = new HunterProfile();
            hunter.setName(profile.getUser().getLastName());
            hunter.setUsername(profile.getUser().getUserName());
            hunter.setNumber(profile.getUser().getEmail());
            hunter.setProfileId(profile.getId());
            hunter.getAsyncMessages().addAll(profile.getAsyncMessages());
            this.extractUserInfo(profile, hunter);
            allHunters.add(hunter);
        }
        final List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        for (HunterProfile hunterProfile : allHunters) {
            final Map<String, Object> mapData = new HashMap<String, Object>();
            mapData.put("name", hunterProfile.getName());
            mapData.put("email", hunterProfile.getNumber());
           
            final Multimap<Integer, HunterProfile> looking = ArrayListMultimap.create();
            final Multimap<Integer, HunterProfile> providing = ArrayListMultimap.create();
            final Map<Integer, HunterProfile> liveMatchedProfiles = new HashMap<Integer, HunterProfile>();
            for (final HunterProfile hunterProfileTemp : allHunters) {
                hunterProfileTemp.getMatchList().clear();
                for (final int feeler : hunterProfileTemp.getHelp().keySet()) {
                    providing.put(feeler, hunterProfileTemp);
                }
                for (final int feeler2 : hunterProfileTemp.getInterested().keySet()) {
                    looking.put(feeler2, hunterProfileTemp);
                }
            }
            String matchPart = null;
            for (int answer : hunterProfile.getHelp().keySet()) {
                if (looking.containsKey((Object)answer)) {
                    for (HunterProfile hunterProfile2 : looking.get(answer)) {
                        if (hunterProfile.getProfileId() != (int)hunterProfile2.getProfileId() && (matchPart = hunterProfile2.getInterested().get(answer)) != null) {
                            if (liveMatchedProfiles.containsKey(hunterProfile2.getProfileId())) {
                                liveMatchedProfiles.get(hunterProfile2.getProfileId()).addCommon(matchPart);
                                ProfileDaoImpl.logger.info((Object)(hunterProfile2.getName() + " " + matchPart.trim()));
                            }
                            else {
                                hunterProfile2.setMatchItem("wants: ");
                                ProfileDaoImpl.logger.info((Object)(hunterProfile2.getName() + " " + matchPart.trim()));
                                hunterProfile2.addCommon(matchPart);
                                liveMatchedProfiles.put(hunterProfile2.getProfileId(), hunterProfile2);
                            }
                        }
                    }
                }
            }
            for (int answer : hunterProfile.getInterested().keySet()) {
                if (providing.containsKey((Object)answer)) {
                    for (HunterProfile hunterProfile2 : providing.get(answer)) {
                        if (hunterProfile.getProfileId() != (int)hunterProfile2.getProfileId() && (matchPart = hunterProfile2.getHelp().get(answer)) != null) {
                            if (liveMatchedProfiles.containsKey(hunterProfile2.getProfileId())) {
                                liveMatchedProfiles.get(hunterProfile2.getProfileId()).addCommon(matchPart);
                            }
                            else {
                                hunterProfile2.setMatchItem("knows: ");
                                hunterProfile2.addCommon(matchPart);
                                liveMatchedProfiles.put(hunterProfile2.getProfileId(), hunterProfile2);
                            }
                        }
                    }
                }
            }
            final List<HunterProfile> noInteractions = new ArrayList<HunterProfile>();
            for (final HunterProfile hunterProfileRes : liveMatchedProfiles.values()) {
                boolean hasInteracted = false;
                for (final AsyncMessage asyncMessage : hunterProfile.getAsyncMessages()) {
                    if (asyncMessage.getSender().endsWith(hunterProfileRes.getUsername())) {
                        hasInteracted = true;
                        break;
                    }
                }
                if (!hasInteracted) {
                    final HunterProfile newHunter = new HunterProfile();
                    newHunter.setName(hunterProfileRes.getName());
                    newHunter.setMatchItem(hunterProfileRes.getMatchItem());
                    noInteractions.add(newHunter);
                }
            }
            mapData.put("no_interact", noInteractions);
            results.add(mapData);
        }
        tx.commit();
        session.close();
        return results;
    }
    
    private void extractUserInfo(final Profile theProfile, final HunterProfile hunterProfile) {
        for (final FoundTarget foundTarget : theProfile.getFilteredTargets()) {
            if (foundTarget.getStatusType("providing") != null) {
                final List<Feeler> theFeelers = foundTarget.getStatusType("providing").getFeelers();
                for (final Feeler feeler : theFeelers) {
                    hunterProfile.getHelp().put(feeler.getId(), feeler.getDescription());
                }
            }
            if (foundTarget.getStatusType("looking") != null) {
                final List<Feeler> theFeelers = foundTarget.getStatusType("looking").getFeelers();
                for (final Feeler feeler : theFeelers) {
                    hunterProfile.getInterested().put(feeler.getId(), feeler.getDescription());
                }
            }
        }
    }
    
    static {
        logger = Logger.getLogger((Class)TagCloudGraph.class);
    }
}