package com.myownmotivator.service.email;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.myownmotivator.service.utils.MailBeanProperties;
import com.myownmotivator.service.utils.ServiceUtils;

@Service("userCreatedEmailFactory")
public class UserCreatedEmailFactory implements EmailPropertyFactory, InitializingBean {
	
	@Resource(name="velocityEngine")
	private VelocityEngine velocityUserEngine;
		
	
	@Resource(name="serviceUtils")
	private ServiceUtils serviceUtils;
	
	
	private MailBeanProperties mailBeanUserProperties = new MailBeanProperties();	
	
	
	public void afterPropertiesSet() throws Exception {
				
	}
	
	public void resetMailValues() {
		serviceUtils.setEmailValues("user_creation", this);
		if (mailBeanUserProperties.getFrom() == null) {
			
			serviceUtils.putDefaultFromAddress(this);
		}
	}

	@SuppressWarnings("unchecked")
	public MimeMessagePreparator[] constructMimeMessage(Object modelObject) {	
		
		resetMailValues();

		/*
		final User user = (User)modelObject;
		MimeMessagePreparator message = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper theMessage = new MimeMessageHelper(mimeMessage);
				theMessage.setTo(user.getEmail());
				InternetAddress address = new InternetAddress(mailBeanUserProperties.getFrom());
				address.setPersonal("Meetforeal");
				theMessage.setFrom(address);				
				theMessage.setSubject(mailBeanUserProperties.getSubject());
				
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("user", user);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityUserEngine, mailBeanUserProperties.getTemplate(), model);				
				theMessage.setText(text,true);			
			}
		};
		MimeMessagePreparator [] messages = new MimeMessagePreparator[1];
		messages[0] = message;
		*/

		final List<Map<String,String>> users = (List<Map<String,String>>)modelObject;
		MimeMessagePreparator [] messages = new MimeMessagePreparator[users.size()];
		for (int i =0; i < messages.length; i++) {
		
			final int count = i;
			MimeMessagePreparator message = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {

					MimeMessageHelper theMessage = new MimeMessageHelper(mimeMessage);
					theMessage.setTo(users.get(count).get("email"));
					InternetAddress address = new InternetAddress("ellen@peoplehunt.me");
					address.setPersonal("Ellen at PeopleHunt");
					theMessage.setFrom(address);				
					theMessage.setSubject("Your connections at the GSAS Orientation Session");
					
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("name", users.get(count).get("name"));
					model.put("url", "http://peoplehunt.crowdscanner.com/connectionsmade/"+users.get(count).get("profileid")+"/169");
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityUserEngine, "connections_made.vm", model);
					System.out.println(text);
					theMessage.setText(text,true);			
				}
			};	
			
			messages[i] = message;
			
		}	
		
		
		return messages;

	}


	public String[] getEmailProperties() {
		// TODO Auto-generated method stub
		return new String[]{"template","subject","from"};
	}


	public MailBeanProperties geMailBeanProperties() {
		// TODO Auto-generated method stub
		return mailBeanUserProperties;
	}

}
