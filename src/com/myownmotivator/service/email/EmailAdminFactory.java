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

@Service("emailAdminFactory")
public class EmailAdminFactory implements EmailPropertyFactory,
		InitializingBean {

	@Resource(name="velocityEngine")
	private VelocityEngine velocityAdminEngine;

		
	private MailBeanProperties mailBeanAdminProperties  = new MailBeanProperties();

	@Resource(name="serviceUtils")
	private ServiceUtils serviceUtils;	

	public void afterPropertiesSet() throws Exception {

		// resetMailValues();
	}

	public void resetMailValues() {
		serviceUtils.setEmailValues("adminsend", this);
		if (mailBeanAdminProperties.getFrom() == null) {

			serviceUtils.putDefaultFromAddress(this);
		}
	}

	public MimeMessagePreparator[] constructMimeMessage(Object modelObject) {

		resetMailValues();
		/*
		final List<UserValue> users = (List<UserValue>) modelObject;
		MimeMessagePreparator[] messages = new MimeMessagePreparator[users
				.size()];
		for (int i = 0; i < users.size(); i++) {

			final UserValue user = (UserValue) users.get(i);
			MimeMessagePreparator message = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {

					MimeMessageHelper theMessage = new MimeMessageHelper(mimeMessage);
					theMessage.setTo(user.getEmail());
					InternetAddress address = new InternetAddress(mailBeanAdminProperties.getFrom());
					address.setPersonal("Meetforeal");
					theMessage.setFrom(address);
					theMessage.setSubject(mailBeanAdminProperties.getSubject());				
					Map model = new HashMap<String, Object>();
					model.put("user", user);
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityAdminEngine, mailBeanAdminProperties.getTemplate(), model);
					theMessage.setText(text, true);
				}
			};

			messages[i] = message;

		}
		*/
		return null;
	}

	public String[] getEmailProperties() {
		// TODO Auto-generated method stub
		return new String[] { "template", "subject", "from" };
	}

	public MailBeanProperties geMailBeanProperties() {
		// TODO Auto-generated method stub
		return mailBeanAdminProperties;
	}

}
