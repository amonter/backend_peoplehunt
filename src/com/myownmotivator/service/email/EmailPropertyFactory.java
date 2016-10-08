package com.myownmotivator.service.email;

import org.springframework.mail.javamail.MimeMessagePreparator;

import com.myownmotivator.service.utils.MailBeanProperties;

public interface EmailPropertyFactory {
		
	public void resetMailValues();
	
	public MailBeanProperties geMailBeanProperties();
	
	public String[] getEmailProperties();
	
	public MimeMessagePreparator[] constructMimeMessage(Object modelObject);
}
