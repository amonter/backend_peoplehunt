package com.myownmotivator.service.gaming;

import java.util.HashMap;
import java.util.List;

import com.crowdscanner.model.ProfileBuffer;
import com.myownmotivator.model.gaming.Connection;
import com.myownmotivator.model.gaming.HuntIdGuess;
import com.myownmotivator.model.gaming.HuntRating;
import com.myownmotivator.model.gaming.InteractionSession;
import com.myownmotivator.model.gaming.MatchObject;
import com.myownmotivator.model.gaming.PeopleHuntId;
import com.myownmotivator.model.gaming.PlayingTurns;
import com.myownmotivator.model.profile.Profile;

public interface PeopleHuntService {

	public InteractionSession mergeInteractionSession(InteractionSession interactionSession);
	
	public InteractionSession retrieveInteractionSessionByBundle(Integer bundleId);
	
	public void addGuess(HuntIdGuess huntidGuess);
	
	public List<ProfileBuffer> retrieveFoundTargets(Integer bundleId);
	
	public void deleteGuess(HuntIdGuess huntidGuess);
	
	public void addPeopleHuntId(PeopleHuntId peopleHuntId);
	
	public void deletePeopleHuntId(PeopleHuntId peopleHuntId);
	
	public HuntIdGuess checkForGuess(Integer interrogatorId, Integer interrogeeId, Integer bundleId);	
	
	public HuntIdGuess doesMyGuessExist(Integer interrogatorId, Integer interrogeeId, Integer bundleId);
	
	public PeopleHuntId findUserWithHuntId(Integer huntId, Integer bundleId);
	
	public List<HashMap<String, String>> retrieveValidatedGuesses(Integer profileId);
	
	public List<HuntIdGuess> retrieveBundleGuesses(Integer bundleId);
	
	public InteractionSession retrieveInteractionSessionByType(String type, Integer bundleId);
	
	public InteractionSession doQueueTransaction(String username, Integer bundleId);
	
	public void mergePlayerTurn(PlayingTurns playingTurns);
	
	public PlayingTurns checkForTurn(Integer myHuntId, Integer otherHuntId, Integer bundleId);
	
	public void mergeMatchObject(MatchObject matchObject);
	
	public MatchObject findMatchObject(String username, Integer otherHuntId, Integer bundleId);
	
	//Hunt Objects
	public void mergeHuntRating(HuntRating huntRating);
	
	public List<HuntRating> getHuntObjects(Integer bundleId);
	

	//Bundle
	public List<Profile> retrieveAllProfilesByBundle(Integer bundleId);	

	//Connections made
	public void addConnection(Connection connection);
	
	public List<Connection> retrieveConnectionsMade(Integer bundleId);

	
	public List<Connection> checkIfConnectionExists(Integer bundleId, Integer profileIdOne, Integer profileIdTwo);
	
	public List<ProfileBuffer> retrievePersonalTargets(Profile profile, Integer bundleId);
	
	
}
