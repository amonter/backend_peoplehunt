package com.crowdscanner.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myownmotivator.model.questions.Question;
import com.myownmotivator.service.questions.QuestionDao;

@Controller
public class ManageQuestionsAdmin {

	@Autowired
	private QuestionDao questionService;
	
	
	@RequestMapping(value="/retrievequestions.htm", method=RequestMethod.GET)
	public String retrieveQuestions(ModelMap model){	
		
		List<Question> theQuestions = questionService.retrieveAllQuestions();	
		Set<Question> questionsSet = new HashSet<Question>();
		questionsSet.addAll(theQuestions);
		model.addAttribute("theList", questionsSet);
		
		return "questionlist_view";
	}	
	
	
	
	@RequestMapping(value="/viewallquestions.htm", method=RequestMethod.GET)
	public String viewAllQuestion(@RequestParam("questionphoneid") Integer questionPhoneId, ModelMap model){	
		
		List<Question> theQuestions = questionService.findQuestionWithPhoneId(questionPhoneId);	
		
		model.addAttribute("questions", theQuestions);
		
		return "allquestionlist_view";
	}
	
	
	@RequestMapping(value="/deletethequestion.htm", method=RequestMethod.POST)
	public String deleteTheQuestion(@RequestParam("id") Integer theId, @RequestParam("questionPhoneId") Integer questionPhoneId,
			ModelMap model){	
		
		questionService.deleteQuestion(theId);		
		
		return "redirect:viewallquestions.htm?questionphoneid="+ questionPhoneId;
	}
	
	
}
