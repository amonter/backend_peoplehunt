package com.myownmotivator.service.email;

import java.util.HashMap;
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

import com.myownmotivator.model.User;
import com.myownmotivator.service.utils.MailBeanProperties;
import com.myownmotivator.service.utils.ServiceUtils;

@Service("crowdscannerUserCreatedEmailFactory")
public class CrowdscannerUserCreatedEmailFactory implements EmailPropertyFactory, InitializingBean{

	@Resource(name="velocityEngine")
	private VelocityEngine velocityUserEngine;
		
	
	@Resource(name="serviceUtils")
	private ServiceUtils serviceUtils;
	
	
	private MailBeanProperties mailBeanUserProperties = new MailBeanProperties();	
	
	
	public void afterPropertiesSet() throws Exception {
				
	}
	
	public void resetMailValues() {
		serviceUtils.setEmailValues("crowdscanner_usercreated", this);
		if (mailBeanUserProperties.getFrom() == null) {
			
			serviceUtils.putDefaultFromAddress(this);
		}
	}	
	
	
	@Override
	public MimeMessagePreparator[] constructMimeMessage(Object modelObject) {
		
		resetMailValues();
		final User user = (User)modelObject;
		MimeMessagePreparator message = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper theMessage = new MimeMessageHelper(mimeMessage);
				theMessage.setTo(user.getEmail());
				InternetAddress address = new InternetAddress(mailBeanUserProperties.getFrom());
				address.setPersonal("Crowdscanner");
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
		
		return messages;		
		
	}

	@Override
	public MailBeanProperties geMailBeanProperties() {
		// TODO Auto-generated method stub
		return mailBeanUserProperties;
	}

	@Override
	public String[] getEmailProperties() {
		// TODO Auto-generated method stub
		return new String[]{"template","subject","from"};
	}	

}
