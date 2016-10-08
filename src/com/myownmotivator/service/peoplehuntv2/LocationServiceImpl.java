package com.myownmotivator.service.peoplehuntv2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crowdscanner.controller.RestControllerUtil;
import com.myownmotivator.model.profile.Profile;
import com.peoplehuntv2.model.Location;
import com.sun.xml.internal.rngom.digested.DInterleavePattern;

@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {
	
	@Autowired
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
	@Transactional(readOnly = true)	
    public List<Location> getAllLocations() {
        return (List<Location>)sessionFactory.getCurrentSession().createQuery("from Location as loc order by loc.ranking desc").list();
    }
    
    @Transactional(readOnly = true)
    public Location getLocationById(final Integer theId) {
        return (Location)sessionFactory.getCurrentSession().get(Location.class, theId);
    }

	@Override
	@Transactional(readOnly = false)
	public Location addNewLocation(Location location) {
		return (Location)sessionFactory.getCurrentSession().merge(location);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteLocation(Location location) {
		sessionFactory.getCurrentSession().merge(location);	
		
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteLocationLink(Profile theProfile) {
		
		Session session = (Session)sessionFactory.openSession();
        Transaction tx = session.beginTransaction(); 
		for (Iterator<Location> it = theProfile.getLocations().iterator(); it
				.hasNext();) {
			Location theLocation = (Location) it.next();
			it.remove();			
			theLocation.getProfiles().remove(theProfile);							
		}
		
		tx.commit();
	    session.close();
		
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Location> getLocationsByLatLong(double lat, double lgt, double radius) {		
		List<Location> theLocations = (List<Location>)sessionFactory.getCurrentSession().createQuery("from Location").list();
		List<Location> radiusLocations = new ArrayList<Location>();
		for (Location location : theLocations) {
			double finalDistance = RestControllerUtil.distance(Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude()), lat, lgt);
			if (finalDistance <= radius){
				radiusLocations.add(location);
			}					
		}		
		return radiusLocations;
	}	
}