	package com.crowdscanner.controller.peoplehunt;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.service.crowdmodule.QuestionBudleDao;
import com.myownmotivator.service.gaming.PeopleHuntService;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.questions.QuestionDao;

import flexjson.JSONSerializer;

@Controller
public class PairingHuntController {
	
	final static Logger logger = Logger.getLogger(PairingHuntController.class);	

	@Resource
	private QuestionDao questionService;
		
	@Autowired
	PeopleHuntService peopleHuntService;		
		
	@Autowired
	QuestionBudleDao questionBudleDao;
	
	@Autowired
	private ProfileService profileService;
	
	
	private String currentUsername;
	
	
	@RequestMapping(value="/friendrequest", method=RequestMethod.GET)
	public String  friendMentionPage(@RequestParam(value="name") String name, 
			@RequestParam(value="message") String message, @RequestParam(value="feeler") String feeler,
			HttpServletResponse response, ModelMap model) {	
		
		model.addAttribute("message", message);
		model.addAttribute("name", name);
		model.addAttribute("feeler", feeler);
		
		return "friendmention";		
	}
	
	
	@RequestMapping(value="/friendmention", method=RequestMethod.GET)
	public String  friendMention(@RequestParam(value="name") String name, 
			@RequestParam(value="message") String message, @RequestParam(value="feeler") String feeler,
			HttpServletResponse response, ModelMap model) {	
		
		model.addAttribute("title", message);
		model.addAttribute("url", "http://peoplehunt.me/friendmention?name="+name+"&message="+message+"&feeler="+feeler);
		model.addAttribute("message", message);
		model.addAttribute("name", name);
		model.addAttribute("feeler", feeler);
		
		return "request_og";
	}
	
	
	
	@RequestMapping(value="/retrievedesiredata/", method=RequestMethod.GET)
	public void  retrieveDesireData(@RequestParam(value="bundleid") Integer theBundleId, 
			HttpServletResponse response) { 			
		
		try {
			
			Long theCount = questionBudleDao.countBundleProfiles(theBundleId);
			
			//This method retrieves the template question 'providing for' or looking for- Not attached to any profile
			Question parentQuestion = questionService.findQuestionParentByType("lookingfor", theBundleId);
			
			//Now retrieve all the 'looking for' questions of all the profiles in this bundle to do ranking
			List<Question> allQuestions =  questionService.findQuestionWithPhoneId(parentQuestion.getQuestionPhoneId());
		
			//building stats
			List<Integer> count = new ArrayList<Integer>();
			List<String> answerText = new ArrayList<String>();
			List<Answer> allAnswers = new ArrayList<Answer>();
			int temp = -1;
			int pos = -1;
			for (Question question : allQuestions) {// building rankings
				
				//get the answers for each question
				// if the answer has been answered it will have the property answerNumber = 1
				for(Answer ans : question.getAnswers()){
					if(ans.getAnswerNumber()==1){
						if(answerText.contains(ans.getTextualAnswer())){
							//increment
							pos = answerText.indexOf(ans.getTextualAnswer());
							temp = count.get(pos)+1;
							count.set(pos, temp);
						}
						else{
							//add
							count.add(1);
							answerText.add(ans.getTextualAnswer());
							allAnswers.add(ans);
						}
					}
				}
				
			}
			
			
			//handy way of storing data associated with each rank
			Multimap<Integer,Answer> ranks = ArrayListMultimap.create();
			temp = count.size();
			for(int i=0; i<temp; ++i){
				ranks.put(count.get(i) , allAnswers.get(i));
			}
			
			List<Integer> leaderboard =  asSortedList(ranks.keySet());
			
			HashMap<Integer, String> answers = new HashMap<Integer, String>();
			int x = 15; //amount of answers you want
			temp = 0;	//reuse for counting
			boolean limitReached = false;
			for(int i : leaderboard){
				for(Answer ans : ranks.get(i)){
					answers.put(ans.getParentAnswerId(), ans.getURLEncodedAnswer());	//Note: putting the answers in a map will lose the ranking in this context
					if(++temp == x){
						limitReached = true;
						break;
					}
				}
				if(limitReached){
					break;
				}
			}
			
			
			//TODO leave this as it is......
			Map<String, Object> theRes = new HashMap<String, Object>();
			theRes.put("total_players", theCount);
			theRes.put("answers", answers);
			theRes.put("helpwith", parentQuestion.getHelpWith());
			theRes.put("huntingfor", parentQuestion.getHuntingFor());
			
			String theResult = new JSONSerializer().exclude("*.class").serialize(theRes);			
			
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
				logger.info("retrieveDesireData  Ex", e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}	
	
	//convenience method
	public static	<T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
		  List<T> list = new ArrayList<T>(c);
		  java.util.Collections.sort(list);
		  java.util.Collections.reverse(list);
		  return list;
	}	



	
}
