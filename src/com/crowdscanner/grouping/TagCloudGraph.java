package com.crowdscanner.grouping;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crowdscanner.model.HunterProfile;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.service.crowdmodule.QuestionBudleDao;
import com.myownmotivator.service.gaming.PeopleHuntService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;

import flexjson.JSONSerializer;

@Controller
public class TagCloudGraph {

	final static Logger logger = Logger.getLogger(TagCloudGraph.class);
	
	@Autowired
	private QuestionDao questionService;
	
	@Autowired
	private UserDao userService;	
	
	@Autowired
	private QuestionBudleDao questionBudleDao;
	
	@Autowired
	private PeopleHuntService peopleHuntService;
	
	@Autowired
	private ProfileService profileService;
		
	
	@RequestMapping(value="/retrievetagcloud/", method=RequestMethod.GET)
	public void processTagCloud(@RequestParam("bundleid") Integer budleId, 
			@RequestParam("parent") Integer parent, HttpServletResponse response) { 		
		
		try {
			
			Map<Integer, Map<String, Object>> provideForMap = questionService.retrieveAnswersByParentQuestionId(budleId);
			
			List<Question> lookingForList = questionService.findQuestionWithPhoneId(parent);
			Map<Integer, Map<String,Object>> lookingForMap = new HashMap<Integer, Map<String,Object>>();
			for (Question question : lookingForList) {
				if (!question.getParent()) {
					for (Answer theAnswer : question.getAnswers()) {
						if(!lookingForMap.containsKey(theAnswer.getParentAnswerId())){
							Map<String,Object> someAnswers = new HashMap<String,Object>();
							someAnswers.put("answer",theAnswer.getTextualAnswer());
							someAnswers.put("repeat",1);
							lookingForMap.put(theAnswer.getParentAnswerId(), someAnswers);
							
						} else {
							Map<String,Object> retrieveAnswers = lookingForMap.get(theAnswer.getParentAnswerId());
							int theRepeat = (Integer) retrieveAnswers.get("repeat") + 1;
							retrieveAnswers.put("repeat", theRepeat);
							lookingForMap.put(theAnswer.getParentAnswerId(), retrieveAnswers);
						}			
					}
				}
			}
			
			Map<String, Collection<Map<String,Object>>> resultMap = new HashMap<String, Collection<Map<String,Object>>>();
			resultMap.put("looking", lookingForMap.values());		
			resultMap.put("providing", provideForMap.values());
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(resultMap);
			
			response.setContentType("text/plain; charset=UTF-8");
			response.setHeader("Content-Encoding", "gzip");
			OutputStream out = response.getOutputStream();
			GZIPOutputStream theGzip = new GZIPOutputStream(out);
			theGzip.write(theResult.getBytes());			
			
			theGzip.flush();
			theGzip.close();
			out.close();			
				
			} catch (Exception e) {
			
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				logger.info("processTagCloud  Exception", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}			
	}	
	
	
	//profileService.doMatchingProcess();
	
	@RequestMapping(value="/001operation", method=RequestMethod.GET)
	public void processOperation(HttpServletResponse response) {
		
		List<Map<String, Object>> allresults = profileService.doMatchingProcess();
		for (Map<String, Object> theMap : allresults) {
			logger.info("\n\n"+theMap.get("name")+" : "+theMap.get("email"));
			List<HunterProfile> hunters = (List<HunterProfile>) theMap.get("no_interact");
			for (HunterProfile hunterProfile : hunters) {
				logger.info("  "+hunterProfile.getName()+" "+hunterProfile.getMatchItem().trim());
			}			
		}		
	}	
}
