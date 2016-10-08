package com.myownmotivator.service.email;

import org.springframework.mail.javamail.JavaMailSender;

public abstract class EmailMessageService {

	private JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public abstract void send(Object modelObject);	
	
	public abstract EmailPropertyFactory getEmailPropertyFactory();
		
}
