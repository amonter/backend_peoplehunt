package com.myownmotivator.service.email;

public class EmailMessageServiceImpl extends EmailMessageService {

	private EmailPropertyFactory emailFactory;	
	

	public void setEmailFactory(EmailPropertyFactory userCreatedEmailFactory) {
		this.emailFactory = userCreatedEmailFactory;
	}

	@Override
	public void send(Object modelObject) {
	
		getMailSender().send(emailFactory.constructMimeMessage(modelObject));
	}

	@Override
	public EmailPropertyFactory getEmailPropertyFactory() {
		// TODO Auto-generated method stub
		return emailFactory;
	}
}
