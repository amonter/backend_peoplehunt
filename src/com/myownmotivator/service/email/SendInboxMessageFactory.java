package com.myownmotivator.service.email;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.myownmotivator.model.message.Message;
import com.myownmotivator.service.utils.MailBeanProperties;
import com.myownmotivator.service.utils.ServiceUtils;

@Service("sendInboxMessageFactory")
public class SendInboxMessageFactory extends SendFeedbackMessageFactory {
	
	private MailBeanProperties mailBeanUserProperties = new MailBeanProperties();	
	
	@Resource(name="velocityEngine")
	private VelocityEngine velocityUserEngine;
		
	
	@Resource(name="serviceUtils")
	private ServiceUtils serviceUtils;
	
	
	public void resetMailValues() {
		serviceUtils.setEmailValues("inbox_message", this);
		if (mailBeanUserProperties.getFrom() == null) {
			
			serviceUtils.putDefaultFromAddress(this);
		}
	}	
	
	@Override
	public MimeMessagePreparator processMessage(final Message messageToSend) {
		
		resetMailValues();
		/*
		MimeMessagePreparator message = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper theMessage = new MimeMessageHelper(mimeMessage);
				theMessage.setTo(messageToSend.getProfile().getUser().getEmail());				
				
				InternetAddress address = new InternetAddress(mailBeanUserProperties.getFrom());
				address.setPersonal("Meetforeal");
				theMessage.setFrom(address);
				MessageFormat messageFormat = new MessageFormat(mailBeanUserProperties.getSubject());				
				theMessage.setSubject(messageFormat.format(new Object[]{ messageToSend.getSenderName()}));
				
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("message", messageToSend);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityUserEngine, mailBeanUserProperties.getTemplate(), model);				
				theMessage.setText(text,true);			
			}
		};
		*/
		return null;		
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
