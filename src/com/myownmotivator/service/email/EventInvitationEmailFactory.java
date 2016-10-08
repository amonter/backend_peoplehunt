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

import com.myownmotivator.service.utils.MailBeanProperties;
import com.myownmotivator.service.utils.ServiceUtils;

@Service("eventInvitationEmailFactory")
public class EventInvitationEmailFactory implements EmailPropertyFactory, InitializingBean {

	@Resource(name="velocityEngine")
	private VelocityEngine velocityEventEngine;	

	private MailBeanProperties mailBeanEventProperties  = new MailBeanProperties();
	
	@Resource(name="serviceUtils")
	private ServiceUtils serviceUtils;
	
	public void resetMailValues() {
		serviceUtils.setEmailValues("eventinvitation", this);
		if (mailBeanEventProperties.getFrom() == null) {
			
			serviceUtils.putDefaultFromAddress(this);
		}		
	}

	public MimeMessagePreparator[] constructMimeMessage(Object modelObject) {

		resetMailValues();
		/*
		final UserValue[] users = (UserValue[]) modelObject;
		MimeMessagePreparator[] messages = messages = new MimeMessagePreparator[users.length];
		for (int i = 0; i < users.length; i++) {

			final UserValue user = users[i];
			MimeMessagePreparator message = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {

					MimeMessageHelper theMessage = new MimeMessageHelper(
							mimeMessage);
					theMessage.setTo(user.getEmail());
					theMessage.setFrom(new InternetAddress(mailBeanEventProperties.getFrom()));
					theMessage.setSubject(mailBeanEventProperties.getSubject());
					Map model = new HashMap<String, Object>();
					model.put("user", user);
					String text = VelocityEngineUtils.mergeTemplateIntoString(
							velocityEventEngine, mailBeanEventProperties.getTemplate(), model);
					theMessage.setText(text, true);
				}
			};
			
			messages[i] = message;
		}
		*/
		return null;
		
	}


	public MailBeanProperties geMailBeanProperties() {
		// TODO Auto-generated method stub
		return mailBeanEventProperties;
	}

	public String[] getEmailProperties() {
		// TODO Auto-generated method stub
		return new String[] { "template", "subject", "from" };
	}


	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
