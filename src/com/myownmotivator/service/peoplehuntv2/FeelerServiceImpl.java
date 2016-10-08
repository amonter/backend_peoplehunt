package com.myownmotivator.service.peoplehuntv2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myownmotivator.model.profile.Profile;
import com.peoplehuntv2.model.Feeler;
import com.peoplehuntv2.model.FoundTarget;
import com.peoplehuntv2.model.Status;

@Service("feelerService")
@Transactional
public class FeelerServiceImpl implements FeelerService {
	
	@Autowired
    private SessionFactory sessionFactory;
    
    @Transactional(readOnly = true)
    public Feeler getFeelerById(final Integer theId) {
        return (Feeler)sessionFactory.getCurrentSession().get(Feeler.class, theId);
    }
    
    @Transactional(readOnly = false)
    public void deleteFoundTarget(FoundTarget foundTarget) {
        sessionFactory.getCurrentSession().delete(foundTarget);
    }
    
    @Transactional(readOnly = false)
    public void deleteFoundTargetsBatch(Profile theProfile, String feelerMode){
    	
    	//final List<FoundTarget> foundTargets = theProfile.getFilteredTargets();
    	int profileId = theProfile.getId();
    	Session session = (Session)sessionFactory.openSession();
        Transaction tx = session.beginTransaction();   	
        Profile loadedProfile = (Profile)session.load(Profile.class, profileId);
        List<FoundTarget> foundTargets = loadedProfile.getFilteredTargets();
        //FoundTarget targetToDelete = null;        
        for (Iterator<FoundTarget> iter = foundTargets.iterator(); iter.hasNext(); ) {
        	FoundTarget foundTarget = iter.next();
        	List<Status> theStatus = foundTarget.getStatuses();
        	for (Status status : theStatus) {
				if (status.getFoundStatus().equals(feelerMode)){
					//targetToDelete = foundTarget;
					iter.remove();
					session.delete(foundTarget);
					break;
				}
			}       	
        }
        
        tx.commit();
        session.close();
    }
    
    
    
    
    @Transactional(readOnly = false)
    public Feeler addNewFeeler(final Feeler theFeeler) {
        return (Feeler)this.sessionFactory.getCurrentSession().merge((Object)theFeeler);
    }
    
    @Transactional(readOnly = true)
    public List<Feeler> getAllFeelers() {
        return (List<Feeler>)sessionFactory.getCurrentSession().createQuery("from Feeler as fee order by fee.dateCreated desc").list();
    }
    
    @Transactional(readOnly = true)
    public List<Feeler> getRankedLookingFor() {
        return (List<Feeler>)this.sessionFactory.getCurrentSession().createQuery("from Feeler as fee order by fee.lookingRank desc").list();
    }
    
    @Transactional(readOnly = true)
    public List<Feeler> getRankedProvidingFor() {
        return (List<Feeler>)this.sessionFactory.getCurrentSession().createQuery("from Feeler as fee order by fee.providingRank desc").list();
    }
    
    @Transactional(readOnly = false)
    public void cleanFoundTarget() {
    	
    	Map<Integer, Set<Integer>> allData = new HashMap<Integer, Set<Integer>>();	
    	Session session = (Session)sessionFactory.openSession();
        Transaction tx = session.beginTransaction();        
        
        //int[] ids = {475, 472, 471, 465, 464, 447, 444};  		
        int[] ids = {275, 147, 117, 302, 356};
  		for (int i = 0; i < ids.length; i++) {		
  		
  			Feeler theFeeler = (Feeler) session.get(Feeler.class, ids[i]);			
  			Map<Integer, Map<Integer, List<String>>> sharingData = new HashMap<Integer,  Map<Integer, List<String>>>();	
  			Set<Integer> foundIds = new HashSet<Integer>();
  		 	        		
      		
  			for (Status aStatus : theFeeler.getStatuses()) {
  	        	//System.out.println(aStatus.getFoundStatus());
  	        	FoundTarget foundTarget =  aStatus.getFoundTarget();	        	
  	        	//Profile loadedProfile = null;
  	        	//System.out.println("loop here");
  	        	for (Profile profile : foundTarget.getFoundProfiles()) {
  	        		List<String> statusUser = new ArrayList<String>();
  	        		if (sharingData.containsKey(profile.getId())){
  	        			Map<Integer, List<String>> obj = sharingData.get(profile.getId());
  	        			if (obj.containsKey(ids[i])){
  	        				statusUser = obj.get(ids[i]);
  	        			}
  	        		}
  	        		
  	        		  	        		
  	        	
  	        		if (statusUser.contains(aStatus.getFoundStatus())){
  	        			//System.out.println("delete "+foundTarget.getId());
  	        			//deleting rubbish		 
  	        			//System.out.println("add contains"+foundTarget.getId());
  	        			foundIds.add(foundTarget.getId());       
  	        			 
  	        	        
  	        		} else {
  	        			//System.out.println("add new "+foundTarget.getId());
  	        			statusUser.add(aStatus.getFoundStatus());	        			
  	        		}
  	        		
  	        		//System.out.println("status "+statusUser+" "+theFeeler.getDescription());
  	        		Map<Integer, List<String>> theList = new HashMap<Integer, List<String>>();
  	        		theList.put(ids[i], statusUser); 
  	        		sharingData.put(profile.getId(), theList);
  	        		if (foundIds.size() > 0) {
  	        			if (allData.containsKey(profile.getId())){
  	        				foundIds.addAll(allData.get(profile.getId()));  	        			
  	        			} 
  	        			allData.put(profile.getId(), foundIds);
  	        		}
  	        		
  					//System.out.println("  "+aStatus.getId()+" "+aStatus.getFoundStatus()+" "+foundTarget.getId()+ " "+profile.getId());					
  					
  				}
  	        	
  	        	
  	        	
  			}    
  	        
  			//feelerService.deleteFeeler(theFeeler);   
          
  		}          
          
          
         tx.commit();
         session.close();
         
         
         System.out.println(allData);
         
         Session session2 = (Session)sessionFactory.openSession();
         Transaction tx2 = session2.beginTransaction(); 
         
         for (int profileId : allData.keySet()) {	
         
        	 Profile theProfile = (Profile) session2.get(Profile.class, profileId);
	         final List<FoundTarget> foundTargets = theProfile.getFilteredTargets();
	         FoundTarget targetToDelete = null;
	         for (Integer foundId : allData.get(profileId)) {			
				
		         for (FoundTarget foundTarget : foundTargets) {
		         	
		         	if(foundTarget.getId().intValue() == foundId){	
		         		targetToDelete = foundTarget;
		 				//System.out.println("delete "+foundId+" "+profileId);
		 				
		 			}
		         }
		        if (targetToDelete != null){
		        	theProfile.getFoundTargets().remove(targetToDelete);	          	 
		        	session2.merge(theProfile);
		        	session2.delete(targetToDelete);
		        }
	         }
         
         }
         tx2.commit();
         session2.close();
    }
    
    @Transactional(readOnly = true)
    public List<Feeler> getFeelersBySegment(final int segment) {
        int maxRes = 25;
        int firstRes = 0;
        if (segment == 2) {
            maxRes = 500000000;
            firstRes = 25;
        }
        final Query theQuery = this.sessionFactory.getCurrentSession().createQuery("from Feeler as fee order by fee.dateCreated desc");
        theQuery.setFirstResult(firstRes);
        theQuery.setMaxResults(maxRes);
        return (List<Feeler>)theQuery.list();
    }
    
    @Transactional(readOnly = true)
    public List<Feeler> getFeelersLike(String like) {
        like = "%" + like + "%";
        return (List<Feeler>)this.sessionFactory.getCurrentSession().createQuery("from Feeler as feel where feel.description like :theLike").setString("theLike", like).list();
    }

    @Transactional(readOnly = false)
	public void deleteFeeler(Feeler theFeeler) {
    	sessionFactory.getCurrentSession().delete(theFeeler);		
	}
}