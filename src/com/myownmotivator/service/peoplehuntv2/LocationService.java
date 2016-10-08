package com.myownmotivator.service.peoplehuntv2;

import java.util.*;

import com.myownmotivator.model.profile.Profile;
import com.peoplehuntv2.model.*;

public interface LocationService
{
    List<Location> getAllLocations();
    
    Location getLocationById(Integer p0);
    
    Location addNewLocation(Location location);
    
    public void deleteLocation(Location location);   
    
    public void deleteLocationLink(Profile theProfile);
    
    public List<Location> getLocationsByLatLong (double lat, double lgt, double radius);
}