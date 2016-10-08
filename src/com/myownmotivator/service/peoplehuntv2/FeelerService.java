package com.myownmotivator.service.peoplehuntv2;

import com.myownmotivator.model.profile.Profile;
import com.peoplehuntv2.model.*;

import java.util.*;

public interface FeelerService
{
    Feeler getFeelerById(Integer p0);
    
    void deleteFoundTarget(FoundTarget p0);
    
    public void deleteFoundTargetsBatch(Profile theProfile, String feelerMode);
    
    void deleteFeeler(Feeler theFeeler);
    
    Feeler addNewFeeler(Feeler p0);
    
    List<Feeler> getFeelersLike(String p0);
    
    List<Feeler> getAllFeelers();
    
    List<Feeler> getFeelersBySegment(int p0);
    
    List<Feeler> getRankedLookingFor();
    
    List<Feeler> getRankedProvidingFor();
    
    public void cleanFoundTarget();
}