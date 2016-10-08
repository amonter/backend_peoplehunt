package com.myownmotivator.service.email;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.myownmotivator.model.User;
import com.myownmotivator.model.message.Message;
import com.myownmotivator.service.utils.MailBeanProperties;


@Service("sendFeedbackEmailFactory")
public class SendFeedbackMessageFactory implements EmailPropertyFactory, InitializingBean {

	private MailBeanProperties mailBeanEventProperties  = new MailBeanProperties();
	
	
	@Override
	public MimeMessagePreparator[] constructMimeMessage(Object modelObject) {
		
		final Message feedback = (Message) modelObject;
		MimeMessagePreparator message = processMessage(feedback);		
		MimeMessagePreparator [] messages = new MimeMessagePreparator[1];
		messages[0] = message;
		
		return messages;		
	}

	public MimeMessagePreparator processMessage(final Message feedback) {
		
		
		MimeMessagePreparator message = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper theMessage = new MimeMessageHelper(mimeMessage);
				theMessage.setTo("adrian.mont@gmail.com");
				if (StringUtils.isEmpty(feedback.getSenderName())) {
					
					feedback.setSenderName("No name");
				}
				if (feedback.getProfile().getUser() == null) {
					
					User user = new User();
					user.setEmail("feedback@meetforeal.com");
					feedback.getProfile().setUser(user);
				}
				theMessage.setFrom(feedback.getProfile().getUser().getEmail());
				theMessage.setSubject("Feedback from: " + feedback.getSenderName());				
				theMessage.setText(feedback.getContent(), false);			
			}
		};
		return message;
	}

	@Override
	public MailBeanProperties geMailBeanProperties() {
		// TODO Auto-generated method stub
		return mailBeanEventProperties;
	}

	@Override
	public String[] getEmailProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetMailValues() {		
				
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

}
