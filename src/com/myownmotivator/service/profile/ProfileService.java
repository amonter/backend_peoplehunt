package com.myownmotivator.service.profile;

import com.myownmotivator.model.profile.*;
import java.util.*;
import com.peoplehuntv2.model.*;

public interface ProfileService
{
    Profile saveProfile(Profile p0);
    
    Profile getUserProfile(Integer p0);
    
    void updateProfile(Profile p0);
    
    List<Profile> getProfileByGoals(String p0);
    
    List<Profile> getAllProfiles();
    
    void deleteProfile(Profile p0);
    
    void deleteProfileInfo(ProfileInfo p0);
    
    List<Profile> searchProfile(String p0, String p1, String p2, String p3);
    
    Integer getUserId(Integer p0);
    
    void saveProfileInfo(ProfileInfo p0);
    
    Profile findProfileByConfirmation(long p0);
    
    List<Profile> retrieveRecentProfiles(Date p0);
    
    void saveProfileTestimony(Testimonies p0);
    
    List<Testimonies> retrieveTestimonies();
    
    List<Profile> retrievePresenterProfile();
    
    Profile getProfileByHuntId(Integer p0);
    
    void addAllProfileNotifications(Collection<Map<String, Object>> p0);
    
    List<Notification> retrieveProfileNotifications(Integer p0);
    
    List<Map<String, Object>> doMatchingProcess();
    
    void addAllConnections(Collection<Map<String, Object>> p0, Integer p1);
}