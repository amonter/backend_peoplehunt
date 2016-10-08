package com.crowdscanner.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CrowdScannerNavigationMobile  {

	@RequestMapping(value="/mobile", method=RequestMethod.GET)
	public String renderFrontPageMobile(HttpServletRequest request, ModelMap model) { 		
		model.addAttribute("title", "CrowdScanner");		
		return "front_mobile";
	}
	
	
	@RequestMapping(value="/mobile/aboutus", method=RequestMethod.GET)
	public String renderAboutusMobile(ModelMap model) {				
		return "aboutus_mobile";		
	}	
	
	
	@RequestMapping(value="/mobile/howitworks", method=RequestMethod.GET)
	public String renderHowtoUse(ModelMap model) {				
		return "howitworks_mobile";		
	}	
	
	@RequestMapping(value="/mobile/howtouse", method=RequestMethod.GET)
	public String howtouse(ModelMap model) {				
		return "howtouse_mobile";		
	}	
	
	@RequestMapping(value="/mobile/jobs", method=RequestMethod.GET)
	public String jobs(ModelMap model) {				
		return "jobs_mobile";		
	}	
	
	@RequestMapping(value="/mobile/peoplehunt", method=RequestMethod.GET)
	public String peoplehunt(ModelMap model) {				
		return "peoplehunt_mobile";		
	}	
}
