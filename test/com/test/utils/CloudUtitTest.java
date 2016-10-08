package com.test.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.myownmotivator.model.Goal;

public class CloudUtitTest extends TestCase{

	
	public void testCloudUtil(){
		
		
		List<Goal> goals = new ArrayList<Goal>();
		Goal goal = new Goal();
		Goal goal2 = new Goal();
		Goal goal3 = new Goal();
		Goal goal4 = new Goal();
		Goal goal5 = new Goal();
		Goal goal6 = new Goal();
		Goal goal6a = new Goal();
		Goal goal6b = new Goal();
		Goal goal7 = new Goal();
		Goal goal8 = new Goal();
		Goal goal9 = new Goal();
		Goal goala = new Goal();
		Goal goalb = new Goal();
		Goal goalc = new Goal();
		Goal goald = new Goal();
		Goal goles = new Goal();
		Goal goles1 = new Goal();
		goal.setDescription("running");	
		goal2.setDescription("swimming");
		goal3.setDescription("running");
		goal4.setDescription("swimming");
		goal5.setDescription("running");
		goal6.setDescription("eating");
		goal6a.setDescription("eating a lot");
		goal6b.setDescription("eating a lot");
		goal7.setDescription("jogging");
		goal8.setDescription("have fun");
		goal9.setDescription("drink");
		goala.setDescription("jumping");
		goalb.setDescription("traveling");
		goalc.setDescription("running");
		goald.setDescription("food");
		goles.setDescription("sex");
		goles1.setDescription("sex");
		goals.add(goal);
		goals.add(goal2);
		goals.add(goal3);
		goals.add(goal4);
		goals.add(goal5);
		goals.add(goal6);
		goals.add(goal6a);
		goals.add(goal6b);
		goals.add(goal7);
		goals.add(goal8);
		goals.add(goal9);
		goals.add(goala);
		goals.add(goalb);
		goals.add(goalc);
		goals.add(goald);
		goals.add(goles);
		goals.add(goles1);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			String XML = "<user> <profile_image_url>http://a3.twimg.com/profile_images/59762219/profilePhoto1_normal.JPG</profile_image_url></user>";
			//parse using builder to get DOM representation of the XML file
			Document dom  = db.parse(new InputSource(new StringReader(XML.trim())));
			
			NodeList nodes_i = dom.getDocumentElement().getChildNodes();
			Map<String, String> theXMLMap = new HashMap<String, String>();
			if(nodes_i != null && nodes_i.getLength() > 0) {
				for(int i = 0 ; i < nodes_i.getLength();i++) {
					
					String tagName = nodes_i.item(i).getNodeName();
					String tagValue = nodes_i.item(i).getTextContent();
				
					theXMLMap.put(tagName, tagValue);
				}
			}
			
			
		byte[] theByte = theXMLMap.get("profile_image_url").getBytes();
		
		

		}	catch(ParserConfigurationException pce) {
				pce.printStackTrace();
		}	catch(SAXException se) {
				se.printStackTrace();
		}	catch(IOException ioe) {
				ioe.printStackTrace();
		}

		
		
		
	}
	
	
	
	
	
	
}
