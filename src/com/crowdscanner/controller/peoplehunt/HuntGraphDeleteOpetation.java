package com.crowdscanner.controller.peoplehunt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;
import com.myownmotivator.service.crowdmodule.QuestionBudleDao;
import com.myownmotivator.service.profile.ProfileService;
import com.myownmotivator.service.profile.UserDao;
import com.myownmotivator.service.questions.QuestionDao;


@Controller
public class HuntGraphDeleteOpetation {

	final static Logger logger = Logger.getLogger(HuntGraphDeleteOpetation.class);	
	
	@Autowired
	private QuestionBudleDao questionBudleDao;	
	
	@Resource
	private QuestionDao questionService;	
	
	@Autowired
	private ProfileService	profileService;
	
	
	@RequestMapping(value={"/deleteuserbundle/"}, method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void peoplehuntUser(@PathVariable("userid") Integer userid, @PathVariable("bundleid") Integer bundleId, 
			HttpServletRequest request, HttpServletResponse response) { 
		
		QuestionBundle questionBundle = questionBudleDao.findById(bundleId);		
		Profile theProfile = profileService.getUserProfile(userid);		
		List<Question> questions = Arrays.asList(new Question[questionBundle.getQuestions().size()]);
		Collections.copy(questions, questionBundle.getQuestions());
		
		
		//It's deleting the user in the view with jquery but the question has not been deleted in the database		
		for(Question question:  questions) {
			Profile foundProfile = null;
			for(Profile profile:question.getProfiles()) {			
				if (profile.getId().equals(theProfile.getId())) {
					foundProfile = profile;
				}
			}
			if (foundProfile != null) {		
				questionBundle.removeQuestion(question);				
				questionService.deleteQuestion(question);				
			}
		}		
	}	
	
}
