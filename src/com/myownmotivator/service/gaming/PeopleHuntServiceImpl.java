package com.myownmotivator.service.gaming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crowdscanner.controller.utils.RestUtils;
import com.crowdscanner.model.ProfileBuffer;
import com.crowdscanner.model.QueueElement;
import com.myownmotivator.model.gaming.Connection;
import com.myownmotivator.model.gaming.HuntIdGuess;
import com.myownmotivator.model.gaming.HuntRating;
import com.myownmotivator.model.gaming.InteractionSession;
import com.myownmotivator.model.gaming.MatchObject;
import com.myownmotivator.model.gaming.PeopleHuntId;
import com.myownmotivator.model.gaming.PlayingTurns;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Service("peopleHuntService")
@Transactional
public class PeopleHuntServiceImpl implements PeopleHuntService {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly=false)
	public void addGuess(HuntIdGuess huntidGuess) {
		sessionFactory.getCurrentSession().merge(huntidGuess);		
	}
	
	
	
	@Override
	@Transactional(readOnly=false)
	public void deleteGuess(HuntIdGuess huntidGuess) {
		sessionFactory.getCurrentSession().delete(huntidGuess);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public HuntIdGuess checkForGuess(Integer interrogatorId, Integer interrogeeId, Integer bundleId) {
		
	   HuntIdGuess theHuntIdGuess = null;
		
	   List<HuntIdGuess> allGuesses = sessionFactory.getCurrentSession().createQuery("from HuntIdGuess as g where g.interrogatorId =:interrogeeId and " +
		"g.interrogeeId =:interrogatorId and g.bundleId=:bundleId")
		.setInteger("interrogatorId", interrogatorId)
		.setInteger("bundleId", bundleId)
		.setInteger("interrogeeId", interrogeeId).list();
		
		if (allGuesses != null && allGuesses.size() > 0){
			
			theHuntIdGuess = allGuesses.get(0);
		}		
		
		return theHuntIdGuess;		
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<HashMap<String, String>> retrieveValidatedGuesses(Integer profileId){
		
		List<HashMap<String, String>> validatedList = new ArrayList<HashMap<String, String>>();
		Map<Integer, HashMap<String, String>> buffersHuntGuess = new HashMap<Integer, HashMap<String, String>>();
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		ScrollableResults huntIdScroll = session.createQuery("from HuntIdGuess as g where g.profile.id =:profileId")
		.setInteger("profileId", profileId).scroll(ScrollMode.FORWARD_ONLY);
		while (huntIdScroll.next() ) {
			HuntIdGuess theHuntIdGuess = (HuntIdGuess) huntIdScroll.get(0);
			ScrollableResults scrollValidatedHunt = sessionFactory.getCurrentSession().createQuery("from HuntIdGuess as g where g.interrogatorId =:interrogeeId and " +
			"g.interrogeeId =:interrogatorId and g.bundleId=:bundleId")
			.setInteger("interrogatorId", theHuntIdGuess.getInterrogatorId())
			.setInteger("bundleId", theHuntIdGuess.getBundleId())
			.setInteger("interrogeeId", theHuntIdGuess.getInterrogeeId()).scroll(ScrollMode.FORWARD_ONLY);
			
			if (scrollValidatedHunt.next()){
				HuntIdGuess huntIdGuess = (HuntIdGuess) scrollValidatedHunt.get(0);
				if (!buffersHuntGuess.containsKey(huntIdGuess.getInterrogatorId())){
					Profile huntedProfile = huntIdGuess.getProfile();
					HashMap<String, String> allGuesses = new HashMap<String, String>();					
					allGuesses.put("url_image", RestUtils.extractURLProfile(huntedProfile,  "http://images.crowdscanner.com/anon_nosmile.png"));
					allGuesses.put("name", huntedProfile.getUser().getLastName());
					buffersHuntGuess.put(huntIdGuess.getInterrogatorId(), allGuesses);
					
				}				
			}					
		}
		
		validatedList.addAll(buffersHuntGuess.values());
		tx.commit();
		session.close();
		
		return validatedList;
	}
	
	//peoplehuntv2
	@SuppressWarnings({ "unused", "unchecked" })
	@Transactional(readOnly=true)
	public List<ProfileBuffer> retrievePersonalTargets(Profile profile, Integer bundleId){
		
		Session session = sessionFactory.openSession();		
		Transaction tx = session.beginTransaction();		
		//int huntId = profile.getPeopleHuntIdByBundle(bundleId).getHuntId();
		int huntId = 0;
		
		List<Connection> connections = session.createQuery("from Connection as c where c.bundleId=:bundleId and (c.connectionOne =:user  or c.connectionTwo =:user )")
				.setInteger("bundleId", bundleId)
				.setInteger("user", huntId)				
				.list();
		
		Set<Integer> connectionsIds = new HashSet<Integer>();
		for (Connection connection : connections) {						
			if (connection.getConnectionOne().intValue() != huntId){			
				connectionsIds.add(connection.getConnectionOne());
			}
			
			if (connection.getConnectionTwo().intValue() != huntId){			
				connectionsIds.add(connection.getConnectionTwo());
			}
		}
		
		List<ProfileBuffer> buffers = new ArrayList<ProfileBuffer>();
		for (Integer theHuntid : connectionsIds) {
			
			ProfileBuffer profileBuffer = new ProfileBuffer();
			PeopleHuntId peopleHuntId = (PeopleHuntId) session.createQuery("from PeopleHuntId as ph where ph.huntId =:huntId and ph.bundleId =:bundleId")
					.setInteger("huntId", theHuntid)
					.setInteger("bundleId", bundleId).uniqueResult();
			
			Profile theProfile = peopleHuntId.getProfile();
			profileBuffer.setProfileId(theProfile.getId());
			profileBuffer.setName(theProfile.getUser().getLastName());
			profileBuffer.setUrlImage(RestUtils.extractURLProfile(theProfile, "http://images.crowdscanner.com/anon_nosmile.png"));		
			
			String talkAbout = null;
			String lookingFor = null;
			/*
			for (Question theQuestion : theProfile.getBundleQuestions(bundleId)) {
				
					if (theQuestion.getQuestionType().equals("talkabout")){
						talkAbout = theQuestion.getFormatedAnswerLabels();
					}
					if (theQuestion.getQuestionType().equals("lookingfor")){
						lookingFor = theQuestion.getFormatedAnswerLabels();
					}				
			}*/
			
			profileBuffer.setTopic(talkAbout);
			profileBuffer.setLookingFor(lookingFor);
			buffers.add(profileBuffer);
			
			//add linkedIn and twitter
			if (theProfile.getTwitterUserAuthentication() != null){
				profileBuffer.setTwitterHandle(theProfile.getTwitterUserAuthentication().getUsername().replace("@", ""));				
			}		
			
			if (theProfile.getLinkedInData() != null){
				profileBuffer.setLinkedInUrl(theProfile.getLinkedInData().getUrl());
			}
			
			
		}
		
		tx.commit();
		session.close();
		
		return buffers;		
	}
	
	//peoplehuntv2
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<ProfileBuffer> retrieveFoundTargets(Integer bundleId) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		List<ProfileBuffer> resultGraph = new ArrayList<ProfileBuffer>();	
		Set<String> pairedUsernames = new HashSet<String>();
		
		//Retrieve all attached profiles to the bundle
		ScrollableResults huntIdScroll = session.createQuery("select distinct prof from Question as q1 join q1.profiles as prof where q1.parent =FALSE and q1.questionBundle.id =:id")
		.setInteger("id", bundleId)
		.scroll(ScrollMode.FORWARD_ONLY);
		while (huntIdScroll.next()) {
			//Set up a profile buffer for all the profiles
			Profile theProfile = (Profile) huntIdScroll.get(0);
			ProfileBuffer profileBuffer = new ProfileBuffer();
			String myUsername = theProfile.getUser().getUserName();
			profileBuffer.setUserName(theProfile.getUser().getUserName());
			profileBuffer.setCreatedDate(theProfile.getProfileCreation());
			profileBuffer.setName(theProfile.getUser().getLastName());
			profileBuffer.setUrlImage(RestUtils.extractURLProfile(theProfile, "http://images.crowdscanner.com/anon_nosmile.png"));		
			//Retrieve all the found profiles for each profile
			//List<HuntIdGuess> foundProfiles = theProfile.getHuntIdGuessByBundle(bundleId);
			List<HuntIdGuess> foundProfiles = null;
			List<HashMap<String, String>> bumpedUserList = new ArrayList<HashMap<String,String>>();
			int countFounds = 0; //Count number of founds
			
			for (HuntIdGuess huntIdGuess : foundProfiles) {
				HashMap<String, String> bumpedUserHash = new HashMap<String, String>();
				//Retrieve all the info of the other player by the HuntObject
				PeopleHuntId peopleHuntId = (PeopleHuntId) session.createQuery("from PeopleHuntId as ph where ph.huntId =:huntId and ph.bundleId =:bundleId")
				.setInteger("huntId", huntIdGuess.getInterrogeeId())
				.setInteger("bundleId", bundleId).uniqueResult();
				
				if (peopleHuntId != null) {	// Get profile object from the HuntOject		
					boolean userStored = false;					
					Profile otherProfile = peopleHuntId.getProfile();			
					String otherUsername = otherProfile.getUser().getUserName();
					ProfileBuffer bufferClone = new ProfileBuffer(profileBuffer);					
					bufferClone.setCreatedDate(huntIdGuess.getGuessTime());
					
					for (ProfileBuffer aBuffer : resultGraph) { // To avoid non-repeat profile info check if the user
						if (aBuffer.getUserName().equals(otherProfile.getUser().getUserName())) { //checking if who I guessed is in the buffer
							for (HashMap<String, String> hashMap : aBuffer.getBumpedProfiles()) { //get profiles found
								if (hashMap.containsValue(theProfile.getUser().getUserName())){ //Check if my profile is redirected back from who I guessed
									userStored = true; // Has been redirected back do not store
								}
							}
						}								
					}
					
					
					if (!userStored) { // only store the bidirection once and add the found profile to the array						
						pairedUsernames.add(otherUsername);
						pairedUsernames.add(myUsername);
						bumpedUserHash.put("profileId", String.valueOf(peopleHuntId.getHuntId()));		
						bumpedUserHash.put("userNameTwo", otherProfile.getUser().getUserName());						
						bumpedUserHash.put("nameTwo", otherProfile.getUser().getLastName());						
						bumpedUserHash.put("urlImageTwo", RestUtils.extractURLProfile(otherProfile,  "http://images.crowdscanner.com/anon_nosmile.png"));						
						bumpedUserList.add(bumpedUserHash);	
					}
					//add the found profile but distributing the order to each time was found
					if (bumpedUserList.size() >countFounds) {
						//PeopleHuntId theHuntIdObj = theProfile.getPeopleHuntIdByBundle(bundleId);
						PeopleHuntId theHuntIdObj = null;
						HashMap<String, String> foundHash = bumpedUserList.get(countFounds);			
						int profileId = Integer.valueOf(foundHash.get("profileId"));
						List<Connection> connections = sessionFactory.getCurrentSession().createQuery("from Connection as c where c.bundleId=:bundleId and (c.connectionOne =:user or c.connectionOne =:userTwo) and " +
								"(c.connectionTwo =:user or c.connectionTwo =:userTwo)")
						.setInteger("bundleId", bundleId)
						.setInteger("user", profileId)
						.setInteger("userTwo", theHuntIdObj.getHuntId())
						.list();
						if (!connections.isEmpty()){
							String theTopic = connections.get(0).getTopic();
							bufferClone.setTopic(theTopic);
						}
						
						bufferClone.getBumpedProfiles().add(foundHash);
					}
					
					// add my profile
					resultGraph.add(bufferClone);
					countFounds++;
				}				
			}
			// add my profile only if user has just joined and retrieve looking for answer
			if (foundProfiles.size() == 0) {
				
				List<Question> questions = sessionFactory.getCurrentSession().createQuery("select qu from Question as qu join qu.profiles as prof where prof.id =:id and qu.questionBundle.id =:bundleId and " +
				"qu.questionType ='lookingFor'")
				.setParameter("id", theProfile.getId())
				.setParameter("bundleId", bundleId).list();				
				
				List<String> listAnswers = new ArrayList<String>();
				if (!questions.isEmpty()){
					for (Answer answer : questions.get(0).getAnswers()) {
						listAnswers.add(answer.getTextualAnswer());
					}
				}
				
				profileBuffer.setTopic(StringUtils.join(listAnswers, ", "));				
				resultGraph.add(profileBuffer);
			}
		}		
		
			
		tx.commit();
		session.close();
		
		//if a profile has found somebody delete him/her from the just joined status
		for (String pairedUsername : pairedUsernames) {
			for (Iterator<ProfileBuffer> theBuffer = resultGraph.iterator();theBuffer.hasNext();) {
				ProfileBuffer aBuffer = theBuffer.next();
				if (aBuffer.getBumpedProfiles().isEmpty()){
						if (aBuffer.getUserName().equals(pairedUsername)){
							theBuffer.remove();
						}
				}				
			}
		}
		
		
		return resultGraph;
	}
	
	
	
	@Override
	public HuntIdGuess doesMyGuessExist(Integer interrogatorId,
			Integer interrogeeId, Integer bundleId) {
		
		return (HuntIdGuess) sessionFactory.getCurrentSession().createQuery("from HuntIdGuess as g where g.interrogatorId =:interrogatorId and " +
				"g.interrogeeId =:interrogeeId and g.bundleId=:bundleId")
		.setInteger("interrogatorId", interrogatorId)
		.setInteger("bundleId", bundleId)
		.setInteger("interrogeeId", interrogeeId).uniqueResult();
	}

	@Override
	@Transactional(readOnly=true)
	public PeopleHuntId findUserWithHuntId(Integer huntId, Integer bundleId) {
		
		return (PeopleHuntId) sessionFactory.getCurrentSession().createQuery("from PeopleHuntId as ph where ph.huntId =:huntId and ph.bundleId =:bundleId")
		.setInteger("huntId", huntId)
		.setInteger("bundleId", bundleId).uniqueResult();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Profile> retrieveAllProfilesByBundle(Integer bundleId) {
		
		List<Profile> allProfiles = sessionFactory.getCurrentSession().createQuery("select distinct prof from Question as q1 join q1.profiles as prof where q1.parent =FALSE and q1.questionBundle.id =:id")
				.setInteger("id", bundleId)
				.list();
		return allProfiles;
	}
	
	
	@Override
	@Transactional(readOnly=false)
	public InteractionSession mergeInteractionSession(InteractionSession interactionSession) {
		return (InteractionSession)sessionFactory.getCurrentSession().merge(interactionSession);			
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HuntIdGuess> retrieveBundleGuesses(Integer bundleId) {
		
		return sessionFactory.getCurrentSession().createQuery("from HuntIdGuess as g where g.bundleId =:bundleId")
		.setInteger("bundleId", bundleId).list();
	}

	@Override
	@Transactional(readOnly=false)
	public void addPeopleHuntId(PeopleHuntId peopleHuntId) {
		sessionFactory.getCurrentSession().merge(peopleHuntId);			
	}

	@Override
	@Transactional(readOnly=true)
	public InteractionSession retrieveInteractionSessionByBundle(Integer bundleId) {
		
		return (InteractionSession) sessionFactory.getCurrentSession().createQuery("from InteractionSession as i where i.bundleId=:bundleId and i.type='graph'")
		.setInteger("bundleId", bundleId)	.uniqueResult();
		
	}

	@Transactional(readOnly=false)
	public InteractionSession doQueueTransaction(String username, Integer bundleId) {
		
		InteractionSession interactionSession = retrieveInteractionSessionByType("semaphore", bundleId);
		Queue<QueueElement> storedQueue = extractQueue(interactionSession); 	
		QueueElement queueElement = new QueueElement();
		queueElement.setElementId(username);
		storedQueue.offer(queueElement);
		String queueResult = new JSONSerializer().serialize(storedQueue);			
		interactionSession.setQueue(queueResult);			
	
		
		return mergeInteractionSession(interactionSession);		
	}
	
	
	private Queue<QueueElement> extractQueue(InteractionSession interactionSession) {
		
		Queue<QueueElement> storedQueue = new LinkedList<QueueElement>();
		if (interactionSession.getQueue() != null && !interactionSession.getQueue().equals("[]")) {
			List<QueueElement> listMapResult = new JSONDeserializer<ArrayList<QueueElement>>()
			.deserialize(interactionSession.getQueue());
			storedQueue = new LinkedList<QueueElement>(listMapResult);				
			
		}
		return storedQueue;
	}
	
	@Override
	@Transactional(readOnly=true)
	public InteractionSession retrieveInteractionSessionByType(String type, Integer bundleId) {
		
		return (InteractionSession) sessionFactory.getCurrentSession().createQuery("from InteractionSession as i where i.type=:type and i.bundleId=:bundleId")
		.setString("type", type)
		.setInteger("bundleId", bundleId)
		.uniqueResult();
	}

	@Override
	@Transactional(readOnly=false)
	public void mergePlayerTurn(PlayingTurns playingTurns) {
		sessionFactory.getCurrentSession().merge(playingTurns);		
	}

	@Override
	@SuppressWarnings("unchecked")
	public PlayingTurns checkForTurn(Integer myHuntId, Integer otherHuntId, Integer bundleId) {		
		 
		PlayingTurns playingTurn = null;
		List<PlayingTurns> playingTurns = sessionFactory.getCurrentSession().createQuery("from PlayingTurns as pl where pl.playerOne=:myHuntId or pl.playerTwo=:myHuntId and pl.bundleId=:bundleId")
		.setInteger("myHuntId", myHuntId)		
		.setInteger("bundleId", bundleId)
		.list();
		
		for (PlayingTurns thePlayingTurn : playingTurns) {
			if (thePlayingTurn.getPlayerOne().equals(otherHuntId) || thePlayingTurn.getPlayerTwo().equals(otherHuntId)) {
				playingTurn = thePlayingTurn;				
			}			
		}
		
		 
		 return playingTurn;
	}

	@Override
	@Transactional(readOnly=false)
	public void mergeMatchObject(MatchObject matchObject) {
		sessionFactory.getCurrentSession().merge(matchObject);	
	}

	@Override
	@Transactional(readOnly=true)
	public MatchObject findMatchObject(String username, Integer otherHuntId, Integer bundleId) {
	
		return (MatchObject) sessionFactory.getCurrentSession().createQuery("from MatchObject as mo where mo.profile.user.userName=:username and mo.matchedWithHuntId=:otherHuntId" +
				"and mo.bundleId=:bundleId")
		.setString("username", username)
		.setInteger("otherHuntId", otherHuntId)
		.setInteger("bundleId", bundleId)
		.uniqueResult();
	}

	@Override
	@Transactional(readOnly=false)
	public void mergeHuntRating(HuntRating huntRating) {
		sessionFactory.getCurrentSession().merge(huntRating);			
	}


	@Override
	@Transactional(readOnly=false)
	public void deletePeopleHuntId(PeopleHuntId peopleHuntId) {
		sessionFactory.getCurrentSession().delete(peopleHuntId);	
		
	}

	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<HuntRating> getHuntObjects(Integer bundleId) {
		
		List<HuntRating> huntRatings = sessionFactory.getCurrentSession().createQuery("from HuntRating as hr where hr.bundleId=:bundleId")
		.setInteger("bundleId", bundleId)
		.list();
		
		return huntRatings;
	}


	@Override
	@Transactional(readOnly=false)
	public void addConnection(Connection connection) {
		sessionFactory.getCurrentSession().merge(connection);
	}


	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Connection> retrieveConnectionsMade(Integer bundleid) {
		List<Connection> huntRatings = sessionFactory.getCurrentSession().createQuery("from Connection as c where c.bundleId=:bundleId")
		.setInteger("bundleId", bundleid)
		.list();
		
		return huntRatings;		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Connection> checkIfConnectionExists(Integer bundleId, Integer profileIdOne, Integer profileIdTwo) {
		
		List<Connection> connections = sessionFactory.getCurrentSession().createQuery("from Connection as c where c.bundleId=:bundleId and (c.connectionOne =:user or c.connectionOne =:userTwo) and " +
		"(c.connectionTwo =:user or c.connectionTwo =:userTwo)")
		.setInteger("bundleId", bundleId)
		.setInteger("user", profileIdOne)
		.setInteger("userTwo", profileIdTwo)
		.list();
		
		return connections;
	}
	
}