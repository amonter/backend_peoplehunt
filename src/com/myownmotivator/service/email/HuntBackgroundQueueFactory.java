package com.myownmotivator.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.myownmotivator.service.utils.MailBeanProperties;

@Service("huntBackgroundQueueFactory")
public class HuntBackgroundQueueFactory implements EmailPropertyFactory, InitializingBean {

	private MailBeanProperties mailBeanEventProperties  = new MailBeanProperties();
	
	@Override
	public MimeMessagePreparator[] constructMimeMessage(Object modelObject) {
		final String exception = (String) modelObject;		
		MimeMessagePreparator message = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper theMessage = new MimeMessageHelper(mimeMessage);
				theMessage.setTo("ellen.dudley@gmail.com");			
				theMessage.setFrom("adrian@crowdscanner.com");
				theMessage.setSubject("More numbers");				
				theMessage.setText(exception, false);			
			}
		};
		MimeMessagePreparator [] messages = new MimeMessagePreparator[1];
		messages[0] = message;
		
		return messages;		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
