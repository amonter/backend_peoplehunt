package com.myownmotivator.service.email;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.myownmotivator.model.User;
import com.myownmotivator.service.utils.MailBeanProperties;
import com.myownmotivator.service.utils.ServiceUtils;


@Service("paymentNotificationEmailFactory")
public class PaymentNotificationEmailFactory implements EmailPropertyFactory, InitializingBean {

	final static Logger logger = Logger.getLogger(PaymentNotificationEmailFactory.class);
	
	@Resource(name="velocityEngine")
	private VelocityEngine velocityUserEngine;	
	
	@Resource(name="serviceUtils")
	private ServiceUtils serviceUtils;	
	
	private MailBeanProperties mailBeanProperties  = new MailBeanProperties();
	
	@Override
	public MimeMessagePreparator[] constructMimeMessage(Object modelObject) {
		
		resetMailValues();
		final User theUser = (User)modelObject;
		MimeMessagePreparator message = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {

				logger.info("aloha sending mail "+theUser.getEmail());
				MimeMessageHelper theMessage = new MimeMessageHelper(mimeMessage);
				String email = theUser.getEmail();
				theMessage.setTo(email);
				InternetAddress address = new InternetAddress(mailBeanProperties.getFrom());
				address.setPersonal("CrowdScanner");
				theMessage.setFrom(address);				
				theMessage.setSubject(mailBeanProperties.getSubject());
				theMessage.setBcc("ellen@crowdscanner.com");
				Map<String, Object> model = new HashMap<String, Object>();
				//model.put("plan", theUser.getProfile().getPlan());
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityUserEngine, mailBeanProperties.getTemplate(), model);				
				theMessage.setText(text,true);			
			}
		};
		MimeMessagePreparator [] messages = new MimeMessagePreparator[1];
		messages[0] = message;
		
		return messages;		
	}

	@Override
	public MailBeanProperties geMailBeanProperties() {
		
		return mailBeanProperties;
	}

	@Override
	public String[] getEmailProperties() {
		// TODO Auto-generated method stub
		return new String[] { "template", "subject", "from" };
	}

	@Override
	public void resetMailValues() {
		
		serviceUtils.setEmailValues("notify_order", this);
		if (mailBeanProperties.getFrom() == null) {
			
			serviceUtils.putDefaultFromAddress(this);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
