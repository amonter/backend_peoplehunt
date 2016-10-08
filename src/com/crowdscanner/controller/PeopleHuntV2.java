package com.crowdscanner.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.crowdscanner.controller.utils.RestUtils;
import com.crowdscanner.grouping.UserBundleOperationsThread;
import com.google.gson.Gson;
import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.service.peoplehuntv2.FeelerService;
import com.myownmotivator.service.peoplehuntv2.GroupService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.peoplehuntv2.model.Feeler;
import com.peoplehuntv2.model.FoundTarget;
import com.peoplehuntv2.model.Group;
import com.peoplehuntv2.model.MemberShip;
import com.peoplehuntv2.model.Status;

import flexjson.JSONSerializer;

@Controller
public class PeopleHuntV2 {

	final static Logger logger = Logger.getLogger(PeopleHuntV2.class);
		
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private UserDao userService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private FeelerService feelerService;
	
	 @RequestMapping(value = { "/deletestatus" }, method = { RequestMethod.GET })
	    public void deleteStatus(@RequestParam("username") final String username, @RequestParam("feelerid") final Integer feelerid, @RequestParam("statustype") final String statustype, final HttpServletResponse response) {
	        final User theUser = this.userService.getByUserName(username);
	        final Profile theProfile = theUser.getProfile();
	        final List<FoundTarget> foundTargets = theProfile.getFilteredTargets();
	        FoundTarget targetToDelete = null;
	        for (final FoundTarget foundTarget : foundTargets) {
	            final Status theStatus = foundTarget.getStatusType(statustype);
	            if (theStatus != null) {
	                final List<Feeler> feelers = theStatus.getFeelers();
	                for (final Feeler feeler : feelers) {
	                    if (feeler.getId() == (int)feelerid) {
	                        targetToDelete = foundTarget;
	                    }
	                }
	            }
	        }
	        theProfile.getFoundTargets().remove(targetToDelete);
	        this.profileService.updateProfile(theProfile);
	        this.feelerService.deleteFoundTarget(targetToDelete);
	        new UserBundleOperationsThread(Integer.valueOf(3033), theProfile.getId(), Integer.valueOf(3)).start();
	        response.setStatus(200);
	    }
	    
	    @RequestMapping(value = { "/addstatus" }, method = { RequestMethod.GET })
	    public void addStatus(@RequestParam("username") final String username, @RequestParam("feelerid") final Integer feelerid, @RequestParam("statustype") final String statustype, final HttpServletResponse response) {
	        final User theUser = this.userService.getByUserName(username);
	        final Profile theProfile = theUser.getProfile();
	        final List<FoundTarget> foundTargets = new ArrayList<FoundTarget>();
	        final FoundTarget aFoundTarget = new FoundTarget();
	        aFoundTarget.getFoundProfiles().add(theProfile);
	        final Status theStatus = new Status();
	        theStatus.setFoundStatus(statustype);
	        theStatus.setFoundTarget(aFoundTarget);
	        aFoundTarget.getStatuses().add(theStatus);
	        final Feeler theFeeler = this.feelerService.getFeelerById(feelerid);
	        final Calendar theCal = Calendar.getInstance();
	        theFeeler.setDateCreated(theCal.getTime());
	        theStatus.getFeelers().add(theFeeler);
	        foundTargets.addAll(theProfile.getFoundTargets());
	        foundTargets.add(aFoundTarget);
	        theProfile.setFoundTargets(foundTargets);
	        this.profileService.updateProfile(theProfile);
	        new UserBundleOperationsThread(Integer.valueOf(3033), theProfile.getId(), Integer.valueOf(3)).start();
	        response.setStatus(200);
	    }
	    
	    @RequestMapping(value = { "/addnewfeeler" }, method = { RequestMethod.POST })
	    public void addNewFeeler(@RequestParam("username") final String username, @RequestParam("feelerdata") final String feelerdata, @RequestParam("looking") final Boolean looking, final HttpServletResponse response) {
	        try {
	            final User theUser = this.userService.getByUserName(username);
	            final Profile theProfile = theUser.getProfile();
	            final List<FoundTarget> foundTargets = new ArrayList<FoundTarget>();
	            final FoundTarget aFoundTarget = new FoundTarget();
	            aFoundTarget.getFoundProfiles().add(theProfile);
	            final Status theStatus = new Status();
	            theStatus.setFoundStatus("looking");
	            if (!looking) {
	                theStatus.setFoundStatus("providing");
	            }
	            theStatus.setFoundTarget(aFoundTarget);
	            aFoundTarget.getStatuses().add(theStatus);
	            final Feeler aNewFeeler = new Feeler();
	            final Calendar theCal = Calendar.getInstance();
	            aNewFeeler.setDateCreated(theCal.getTime());
	            aNewFeeler.setDescription(feelerdata);
	            final Feeler createdFeeler = this.feelerService.addNewFeeler(aNewFeeler);
	            theStatus.getFeelers().add(createdFeeler);
	            foundTargets.addAll(theProfile.getFoundTargets());
	            foundTargets.add(aFoundTarget);
	            theProfile.setFoundTargets(foundTargets);
	            this.profileService.updateProfile(theProfile);
	            final Map<String, Object> res = new HashMap<String, Object>();
	            res.put("feelerid", createdFeeler.getId());
	            res.put("status", looking);
	            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)res);
	            response.setHeader("Content-Encoding", "gzip");
	            final OutputStream out = (OutputStream)response.getOutputStream();
	            final GZIPOutputStream theGzip = new GZIPOutputStream(out);
	            theGzip.write(theResult.getBytes());
	            theGzip.flush();
	            theGzip.close();
	            out.close();
	        }
	        catch (Exception e) {
	            try {
	                response.sendError(500);
	                PeopleHuntV2.logger.info((Object)"addMeAsMember User Ex", (Throwable)e);
	            }
	            catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }
	    }
	    
	    @RequestMapping(value = { "/addmembership" }, method = { RequestMethod.GET })
	    public void addMeAsMember(@RequestParam("username") final String username, @RequestParam("groupid") final Integer groupId, final HttpServletResponse response) {
	        try {
	            final User theUser = this.userService.getByUserName(username);
	            final Profile theProfile = theUser.getProfile();
	            final Group theGroup = this.groupService.getGroupById(groupId);
	            if (theProfile.getMemberships().size() > 0) {
	                theGroup.getMemberships().add(theProfile.getMembershipType("core"));
	                theProfile.getMembershipType("core").getGroups().add(theGroup);
	            }
	            else {
	                final List<MemberShip> memberShips = new ArrayList<MemberShip>();
	                final MemberShip coreMember = new MemberShip();
	                coreMember.setMembershipType("core");
	                coreMember.setProfile(theProfile);
	                theGroup.getMemberships().add(coreMember);
	                coreMember.getGroups().add(theGroup);
	                memberShips.add(coreMember);
	                theProfile.setMemberships(memberShips);
	            }
	            this.profileService.updateProfile(theProfile);
	            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)"\"res\":\"done\"");
	            response.setHeader("Content-Encoding", "gzip");
	            final OutputStream out = (OutputStream)response.getOutputStream();
	            final GZIPOutputStream theGzip = new GZIPOutputStream(out);
	            theGzip.write(theResult.getBytes());
	            theGzip.flush();
	            theGzip.close();
	            out.close();
	        }
	        catch (Exception e) {
	            try {
	                response.sendError(500);
	                PeopleHuntV2.logger.info((Object)"addMeAsMember User Ex", (Throwable)e);
	            }
	            catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }
	    }
	    
	    @RequestMapping(value = { "/getindividualfeeler" }, method = { RequestMethod.POST })
	    public void retrieveIndividialFeeler(@RequestParam("jsondata") final String jsondata, final HttpServletResponse response) {
	        try {
	            final Gson gson = new Gson();
	            final Map<Integer, String> providersUrl = new HashMap<Integer, String>();
	            final Map<Integer, String> lookersUrl = new HashMap<Integer, String>();
	            final Map<String, Object> map = new HashMap<String, Object>();
	            final Map<String, Object> result = gson.fromJson(URLDecoder.decode(jsondata, "UTF-8"), map.getClass());
	            final double feelerId = (Double)result.get("id");
	            final Feeler aFeeler = feelerService.getFeelerById((int)feelerId);
	            final List<Status> allStatus = aFeeler.getStatuses();
	            for (final Status status : allStatus) {
	                if (status.getFoundStatus().equals("looking")) {
	                    final List<Profile> allProfiles = status.getFoundTarget().getFoundProfiles();
	                    for (final Profile profile : allProfiles) {
	                        lookersUrl.put(profile.getId(), RestUtils.extractURLProfile(profile, "http://images.crowdscanner.com/anon_nosmile.png"));
	                    }
	                }
	                if (status.getFoundStatus().equals("providing")) {
	                    final List<Profile> allProfiles = status.getFoundTarget().getFoundProfiles();
	                    for (final Profile profile : allProfiles) {
	                        providersUrl.put(profile.getId(), RestUtils.extractURLProfile(profile, "http://images.crowdscanner.com/anon_nosmile.png"));
	                    }
	                }
	            }
	            final Map<String, Object> allData = new HashMap<String, Object>();
	            allData.put("lookers", lookersUrl);
	            allData.put("providers", providersUrl);
	            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)allData);
	            response.setHeader("Content-Encoding", "gzip");
	            final OutputStream out = (OutputStream)response.getOutputStream();
	            final GZIPOutputStream theGzip = new GZIPOutputStream(out);
	            theGzip.write(theResult.getBytes());
	            theGzip.flush();
	            theGzip.close();
	            out.close();
	        }
	        catch (Exception e) {
	            try {
	                response.sendError(500);
	                PeopleHuntV2.logger.info((Object)"retrieveGroupProfilaData User Ex", (Throwable)e);
	            }
	            catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }
	    }
	    
	    @RequestMapping(value = { "/getmygroups" }, method = { RequestMethod.GET })
	    public void retrieveGroupProfilaData(@RequestParam("username") final String username, final HttpServletResponse response) {
	        try {
	            final User theUser = this.userService.getByUserName(username);
	            final Profile theProfile = theUser.getProfile();
	            List<Group> coreGroups = new ArrayList<Group>();
	            List<Group> accessGroups = new ArrayList<Group>();
	            if (theProfile.getMembershipType("core") != null) {
	                coreGroups = theProfile.getMembershipType("core").getGroups();
	            }
	            if (theProfile.getMembershipType("access") != null) {
	                accessGroups = theProfile.getMembershipType("access").getGroups();
	            }
	            final List<Group> theGroups = this.groupService.getGroupsByType("open");
	            final Iterator<Group> it = theGroups.iterator();
	            while (it.hasNext()) {
	                final Group group = it.next();
	                if (coreGroups.contains(group)) {
	                    it.remove();
	                }
	            }
	            final Map<String, Object> theObject = new HashMap<String, Object>();
	            theObject.put("access", accessGroups);
	            theObject.put("member", coreGroups);
	            theObject.put("open", theGroups);
	            final List<FoundTarget> targets = theProfile.getFoundTargets();
	            final int ownerFoundId = theProfile.getOwnerFoundTarget();
	            int connectionCount = 0;
	            for (final FoundTarget foundTarget : targets) {
	                if (foundTarget.getId() == ownerFoundId) {
	                    for (final Profile aProfile : foundTarget.getFoundProfiles()) {
	                        if (aProfile.getId() != (int)theProfile.getId()) {
	                            ++connectionCount;
	                        }
	                    }
	                    break;
	                }
	            }
	            theObject.put("connections", connectionCount);
	            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)theObject);
	            response.setHeader("Content-Encoding", "gzip");
	            final OutputStream out = (OutputStream)response.getOutputStream();
	            final GZIPOutputStream theGzip = new GZIPOutputStream(out);
	            theGzip.write(theResult.getBytes());
	            theGzip.flush();
	            theGzip.close();
	            out.close();
	        }
	        catch (Exception e) {
	            try {
	                response.sendError(500);
	                PeopleHuntV2.logger.info((Object)"retrieveGroupProfilaData User Ex", (Throwable)e);
	            }
	            catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }
	    }
	    
	    @RequestMapping(value = { "/getfeelerdata" }, method = { RequestMethod.GET })
	    public void retrieveFeelerData(@RequestParam(value = "username", required = false) final String username, 
	    		@RequestParam(value = "page", required = false) final Integer page, final HttpServletResponse response) {
	        try {
	            final Set<Integer> providingIds = new HashSet<Integer>();
	            final Set<Integer> lookingFords = new HashSet<Integer>();
	            if (!StringUtils.isBlank(username)) {
	                final User theUser = userService.getByUserName(username);
	                final Profile theProfile = theUser.getProfile();
	                for (final FoundTarget foundTarget : theProfile.getFilteredTargets()) {
	                    if (foundTarget.getStatusType("providing") != null) {
	                        final List<Feeler> theFeelers = foundTarget.getStatusType("providing").getFeelers();
	                        for (final Feeler feeler : theFeelers) {
	                            providingIds.add(feeler.getId());
	                        }
	                    }
	                    if (foundTarget.getStatusType("looking") != null) {
	                        final List<Feeler> theFeelers = foundTarget.getStatusType("looking").getFeelers();
	                        for (final Feeler feeler : theFeelers) {
	                            lookingFords.add(feeler.getId());
	                        }
	                    }
	                }
	            }
	            List<Feeler> backDataFeelers = new ArrayList<Feeler>();
	            backDataFeelers = feelerService.getAllFeelers();
	            final List<Feeler> feelerSort = new ArrayList<Feeler>(backDataFeelers);
	            Collections.sort(feelerSort, new Comparator<Feeler>() {
	                public int compare(final Feeler feeler, final Feeler feeler2) {
	                    return feeler2.getDateCreated().compareTo(feeler.getDateCreated());
	                }
	            });
	            final List<Map<String, Object>> feelerList = new ArrayList<Map<String, Object>>();
	            for (final Feeler feeler2 : feelerSort) {
	                final Map<String, Object> properties = new HashMap<String, Object>();
	                properties.put("id", feeler2.getId());
	                properties.put("URLEncodedAnswer", feeler2.getURLEncodedDescription());
	                final List<Status> theStatus = feeler2.getStatuses();
	                final List<Integer> providers = new ArrayList<Integer>();
	                Set<String> lookersData = new HashSet<String>();
	                for (final Status status : theStatus) {
	                    if (status.getFoundStatus().equals("providing")) {
	                        providers.add(status.getFoundTarget().getFoundProfiles().get(0).getId());
	                    }
	                    if (status.getFoundStatus().equals("looking")) {
	                        final List<Profile> allProfiles = status.getFoundTarget().getFoundProfiles();
	                        for (final Profile profile : allProfiles) {
	                            lookersData.add(RestUtils.extractURLProfile(profile, "http://images.crowdscanner.com/anon_nosmile.png"));
	                        } 
	                    }
	                }
	                properties.put("learning", lookersData.size());	               
	                properties.put("providers", providers);
	                feelerList.add(properties);
	            }
	            final Map<String, Object> feelerData = new HashMap<String, Object>();
	            feelerData.put("feelers", feelerList);
	            feelerData.put("providing", providingIds);
	            feelerData.put("looking", lookingFords);
	            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)feelerData);
	            response.setHeader("Content-Encoding", "gzip");
	            final OutputStream out = (OutputStream)response.getOutputStream();
	            final GZIPOutputStream theGzip = new GZIPOutputStream(out);
	            theGzip.write(theResult.getBytes());
	            theGzip.flush();
	            theGzip.close();
	            out.close();
	        }
	        catch (Exception e) {
	            try {
	                response.sendError(500);
	                PeopleHuntV2.logger.info((Object)"retrieveGroupProfilaData User Ex", (Throwable)e);
	            }
	            catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }
	    }
	    
	    @RequestMapping(value = { "/collectionaction" }, method = { RequestMethod.POST })
	    @ResponseStatus(HttpStatus.OK)
	    public void collectionAction(@RequestParam("collecteduser") final String collecteduser, @RequestParam("bundleid") final Integer bundleId, final HttpServletRequest request, @RequestParam("username") final String username, final HttpServletResponse response) {
	        try {
	            final User theUser = this.userService.getByUserName(username);
	            final Profile theProfile = theUser.getProfile();
	            final User otherUser = this.userService.getByUserName(collecteduser);
	            final Profile foundProfile = otherUser.getProfile();
	            final String theUrl = String.format("http://prod.crowdscanner.com/disablematch/?profileone=%1$d&profiletwo=%2$d&action=0", theProfile.getId(), foundProfile.getId(), bundleId);
	            final URL address = new URL(theUrl);
	            final URLConnection conn = address.openConnection();
	            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = rd.readLine()) != null) {
	                System.out.println(line);
	            }
	            rd.close();
	            final List<FoundTarget> targets = theProfile.getFoundTargets();
	            final int ownerFoundId = theProfile.getOwnerFoundTarget();
	            for (FoundTarget foundTarget : targets) {
	                if (foundTarget.getId() == ownerFoundId && !foundTarget.getFoundProfiles().contains(foundProfile)) {
	                    foundTarget.getFoundProfiles().add(foundProfile);
	                    break;
	                }
	            }
	            if (ownerFoundId == 0) {
	                final List<FoundTarget> foundTargets = new ArrayList<FoundTarget>();
	                final FoundTarget aFoundTarget = new FoundTarget();
	                aFoundTarget.setOwnerId(theProfile.getId());
	                aFoundTarget.getFoundProfiles().add(theProfile);
	                aFoundTarget.getFoundProfiles().add(foundProfile);
	                foundTargets.addAll(theProfile.getFoundTargets());
	                foundTargets.add(aFoundTarget);
	                theProfile.setFoundTargets(foundTargets);
	            }
	            this.profileService.updateProfile(theProfile);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @RequestMapping(value = { "/giveotheraccess" }, method = { RequestMethod.GET })
	    public void giveOtherAccess(@RequestParam("username") final String username, @RequestParam("other") final String otherUsername, final HttpServletResponse response) {
	        final User user = this.userService.getByUserName(username);
	        final List<Group> theGroups = user.getProfile().getMembershipType("core").getGroups();
	        final User otherUser = this.userService.getByUserName(otherUsername);
	        final Profile otherProfile = otherUser.getProfile();
	        final List<MemberShip> otherAllMemberships = otherProfile.getMemberships();
	        final List<Group> allOtherGroups = new ArrayList<Group>();
	        for (final MemberShip membership : otherAllMemberships) {
	            allOtherGroups.addAll(membership.getGroups());
	        }
	        final List<Group> groupsWithNoAccess = new ArrayList<Group>();
	        for (final Group group : theGroups) {
	            if (!allOtherGroups.contains(group)) {
	                groupsWithNoAccess.add(group);
	            }
	        }
	        MemberShip otherAccessMem = null;
	        if (otherProfile.getMembershipType("access") != null) {
	            otherAccessMem = otherProfile.getMembershipType("access");
	        }
	        else {
	            otherAccessMem = new MemberShip();
	            otherAccessMem.setMembershipType("access");
	            otherAccessMem.setProfile(otherProfile);
	        }
	        final List<MemberShip> memberShips = new ArrayList<MemberShip>();
	        memberShips.add(otherProfile.getMembershipType("core"));
	        for (final Group theGroup : groupsWithNoAccess) {
	            theGroup.getMemberships().add(otherAccessMem);
	            otherAccessMem.getGroups().add(theGroup);
	            memberShips.add(otherAccessMem);
	            PeopleHuntV2.logger.info((Object)("adding group " + theGroup.getId()));
	        }
	        otherProfile.setMemberships(memberShips);
	        this.profileService.updateProfile(otherProfile);
	        response.setStatus(200);
	    }
	  
}
