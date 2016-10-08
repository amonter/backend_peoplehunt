package com.crowdscanner.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author Ellen
 *
 */
@Controller
public class CrowdScannerNavigation {	


	@RequestMapping(value="/", method=RequestMethod.GET)
	public String renderFrontPage(ModelMap model) {
		
		model.addAttribute("title", "CrowdScanner");		
		return "front";		
	}	
	
	@RequestMapping(value="/jobs", method=RequestMethod.GET)
	public String jobs(ModelMap model) {				
		return "jobs";		
	}	
	
	
	@RequestMapping(value="/story/987643G5342fg87t", method=RequestMethod.GET)
	public String storyAboutUsPrivate(ModelMap model) {				
		return "crowdscanner_story";		
	}
	
	@RequestMapping(value="/casestudymindfield", method=RequestMethod.GET)
	public String theCaseStudy(ModelMap model) {				
		return "casestudy_mindfield";		
	}	
	
	
	@RequestMapping(value="/aboutus", method=RequestMethod.GET)
	public String renderAboutus(ModelMap model) {				
		return "aboutus_crowd";		
	}	
	
	
	@RequestMapping(value="/howitworks", method=RequestMethod.GET)
	public String viewHowtoUse(ModelMap model) {				
		return "how_itworks";		
	}	
	
	
	@RequestMapping(value="/orderbadges", method=RequestMethod.GET)
	public String orderBadges(ModelMap model) {				
		return "preview_orderbadges";		
	}	
	
	
	@RequestMapping(value={"/guessmypersona", "/mobile/guessmypersona"}, method=RequestMethod.GET)
	public String thePeopleHunt(ModelMap model) {				
		return "peoplehunt_page";		
	}	
	
	
	@RequestMapping(value={"/addyoureventsxsw", "/mobile/addyoureventsxsw"}, method=RequestMethod.GET)
	public String addYourEventSXSW(ModelMap model) {				
		return "addyoureventsxsw_page";		
	}	
	
	
	@RequestMapping(value={"/pricing","/mobile/pricing"}, method=RequestMethod.GET)
	public String pricing(ModelMap model) {				
		return "pricing";		
	}	
	
	
	@RequestMapping(value={"/freetrial","/mobile/freetrial"}, method=RequestMethod.GET)
	public String freeTrial(HttpServletRequest request, ModelMap model) {		
		String theView = "freetrial";
		if (StringUtils.contains(request.getRequestURL().toString(),"mobile")) theView = "mobile/freetrial";		
		return theView;		
	}	
	
	@RequestMapping(value={"/faq","/mobile/faq"}, method=RequestMethod.GET)
	public String faq(ModelMap model) {				
		return "faq";		
	}	
	
	@RequestMapping(value="/howtouse", method=RequestMethod.GET)
	public String howToUse(ModelMap model) {				
		return "howtouse";		
	}	
	
	@RequestMapping(value="/blog", method=RequestMethod.GET)
	public String viewHowYouFeel(ModelMap model) {				
		return "blog";		
	}	
	
}
