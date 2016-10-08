package com.crowdscanner.grouping;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crowdscanner.controller.avatars.CharacterFactory;
import com.crowdscanner.controller.avatars.CharacterMaker;
import com.crowdscanner.controller.utils.RestUtils;
import com.crowdscanner.model.HunterProfile;
import com.crowdscanner.model.ProfileBuffer;
import com.myownmotivator.model.gaming.HuntRating;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.service.gaming.PeopleHuntService;
import com.myownmotivator.service.profile.ProfileService;
import com.peoplehuntv2.model.Feeler;
import com.peoplehuntv2.model.FoundTarget;

import flexjson.JSONSerializer;

@Controller
public class GamingInfoViewController {
	
	final static Logger logger = Logger.getLogger(GamingInfoViewController.class);
    
    private PeopleHuntService peopleHuntService;
    private ProfileService profileService;
    
    @RequestMapping(value = { "/updatelink" }, method = { RequestMethod.GET })
    public void retrieveAvatarGender(final HttpServletResponse response) {
        try {
            final Map<String, String> theMap = new HashMap<String, String>();
            theMap.put("url", "itms-apps://itunes.apple.com/us/app/peoplehunt/id579833763?ls=1&mt=8");
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)theMap);
            response.setContentType("text/plain; charset=UTF-8");
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
                GamingInfoViewController.logger.info((Object)"retrieveAvatarGender User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
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
    
    @RequestMapping(value = { "/retrievesocialstream/{bundleid}" }, method = { RequestMethod.GET })
    public void retrieveSocialStream(@PathVariable("bundleid") final Integer bundleid, final HttpServletResponse response) {
        try {
            List<ProfileBuffer> foundTargets = (List<ProfileBuffer>)this.peopleHuntService.retrieveFoundTargets(bundleid);
            Collections.sort(foundTargets, new Comparator<ProfileBuffer>() {
                public int compare(final ProfileBuffer one, final ProfileBuffer two) {
                    return two.getCreatedDate().compareTo(one.getCreatedDate());
                }
            });
            if (foundTargets.size() > 6) {
                foundTargets = foundTargets.subList(0, 5);
            }
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)foundTargets);
            final HuntRating huntRating = new HuntRating();
            huntRating.setBundleId(bundleid);
            final Calendar theCalendar = Calendar.getInstance();
            huntRating.setDateCalled(theCalendar.getTime());
            huntRating.setRating("streamcalled");
            huntRating.setTypeRating("streamcalled");
            huntRating.setProfile((Profile)null);
            this.peopleHuntService.mergeHuntRating(huntRating);
            response.setContentType("text/plain; charset=UTF-8");
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
                GamingInfoViewController.logger.info((Object)"retrieveSocialStream User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
   
    
    @RequestMapping(value = { "/dospecial" }, method = { RequestMethod.POST })
    public void trySpecialCharacter(@RequestParam("word") final String word, final HttpServletResponse response, final HttpServletRequest request) {
        try {
            System.out.println(request.getCharacterEncoding());
            final List<String> aWord = new ArrayList<String>();
            System.out.println("word " + word);
            aWord.add(word);
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)aWord);
            response.setContentType("text/plain; charset=UTF-8");
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
                GamingInfoViewController.logger.info((Object)"retrieveilikegraph User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    @RequestMapping(value = { "/dofacebookog" }, method = { RequestMethod.GET })
    public void doFacebookOg(@RequestParam("theid") final Integer bundleid, final HttpServletResponse response) {
        response.setContentType("text/html");
    }
    
    @RequestMapping(value = { "/retrieveavatarsinfo/{bundleid}" }, method = { RequestMethod.GET })
    public void processQuestionMatch(@PathVariable("bundleid") final Integer bundleid, final HttpServletResponse response) {
        try {
            final CharacterMaker characterMaker = CharacterFactory.retrieveBundleCharacters(bundleid);
            final List<HashMap<String, Object>> personArrays = (List<HashMap<String, Object>>)characterMaker.getAvatarsInfo();
            final String theResult = new JSONSerializer().exclude(new String[] { "*.class" }).serialize((Object)personArrays);
            response.setContentType("text/plain; charset=UTF-8");
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
                GamingInfoViewController.logger.info((Object)"retrieveilikegraph User Ex", (Throwable)e);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }   
   
}