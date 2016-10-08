package com.passandpoll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crowdscanner.controller.RestControllerUtil;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.service.questions.QuestionDao;

@Controller
public class PassAndPollNavigationController {

	@Resource
	private QuestionDao questionService;	
	
	private String frontPage = "pass_front";	

	private String profilePage = "pass_the_profile";
	

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String renderFrontPage(HttpServletRequest request, ModelMap model) {
		
		model.addAttribute("title", "Pass and Poll");
		List<Question> theQuestions = questionService.retrieveAllQuestions();	
		Set<Question> questionsSet = new HashSet<Question>();
		questionsSet.addAll(theQuestions);		
	
		List<Question> sortedQuestions = new ArrayList<Question>(questionsSet);			
		Collections.sort(sortedQuestions, new Comparator<Question>() {
			public int compare(Question question, Question question2) {

				return question.getCreationDate().compareTo(question2.getCreationDate());
			}
		});		
	
		Collections.reverse(sortedQuestions);
		model.addAttribute("theList", sortedQuestions.subList(0, 5));		
		
		
		return getFrontPage();		
	}	
	
	
	@RequestMapping(value="/aboutus", method=RequestMethod.GET)
	public String renderAboutus(ModelMap model) {
				
		return "pass_aboutus_crowd";		
	}	
	
	
	@RequestMapping(value="/howtouse", method=RequestMethod.GET)
	public String viewHowtoUse(ModelMap model) {
				
		return "pass_howtouse";		
	}	
	
	
	@RequestMapping(value="/howyoufeel", method=RequestMethod.GET)
	public String viewHowYouFeel(ModelMap model) {
				
		return "pass_howyoufeel";		
	}	
	
	
	@RequestMapping(value="/user/{questionid}/{username}", method=RequestMethod.GET)
	public String retrieveUserQuestion(@PathVariable("username") String username,  @PathVariable("questionid") Integer questionid,
			ModelMap model) {
		
		List<Question> theQuestions =questionService.findQuestionWithUsername(username, questionid);		
		if (theQuestions.size() > 0) {
			
			String colorPalette = RestControllerUtil.colorArray(theQuestions.get(0).getAnswers().size());
			model.addAttribute("palette", colorPalette);
			model.addAttribute("title", theQuestions.get(0).getQuestionDescription() +"| Crowdscanner");
			Profile profileReq =  theQuestions.get(0).getProfiles().get(0);
			profileReq.setProfileImageUrl(RestControllerUtil.setProfileImageURL(profileReq));
			
			Question topQuestion = new Question();
			topQuestion.setQuestionDescription(theQuestions.get(0).getQuestionDescription());
			topQuestion.setCreatedBy(theQuestions.get(0).getQuestionDescription());			
		
			List<Question> allQuestions = questionService.findQuestionWithPhoneId(questionid);			
			
			Map<String, Answer> answersMap = new HashMap<String, Answer>();	
			for (Question question : allQuestions) { 
				
				Profile theProfile = question.getProfiles().get(0);	
				String profileImageUrl = RestControllerUtil.setProfileImageURL(theProfile);			
				theProfile.setProfileImageUrl(profileImageUrl);						
				
				for (Answer answer : question.getAnswers()) {
					
					String theKey = answer.getTextualAnswer().trim().replaceAll("\\s", "");
					if (answersMap.containsKey(theKey)) {
						
						Answer theAnswerObject = (Answer)answersMap.get(theKey);
						int accumulated = theAnswerObject.getAnswerNumber() + answer.getAnswerNumber();
						theAnswerObject.setAnswerNumber(accumulated);
						answersMap.put(theKey, theAnswerObject);				
					}
					else {
						
						Answer insertAnswer = new Answer();
						insertAnswer.setTextualAnswer(answer.getTextualAnswer());
						insertAnswer.setAnswerNumber(answer.getAnswerNumber());
						answersMap.put(theKey, insertAnswer);
					}
				}				
			}			
			
			topQuestion.setAnswers(new ArrayList<Answer>(answersMap.values()));
			
			model.addAttribute("main_question", topQuestion);
			model.addAttribute("answered_questions", allQuestions);
		}
	
		return getProfilePage();		
	}	


	@RequestMapping(value="/slider/{page}", method=RequestMethod.GET)
	public String sliderFrontPage(@PathVariable("page") String thepage ,ModelMap model) {
		
		model.addAttribute("the_page", thepage);
		model.addAttribute("title", "CrowdScanner");
		return getFrontPage();		
	}		
	
	
	public String getFrontPage() {
		return frontPage;
	}


	public void setFrontPage(String frontPage) {
		this.frontPage = frontPage;
	}
	
	public String getProfilePage() {
		return profilePage;
	}


	public void setProfilePage(String profilePage) {
		this.profilePage = profilePage;
	}	
	
	
}
