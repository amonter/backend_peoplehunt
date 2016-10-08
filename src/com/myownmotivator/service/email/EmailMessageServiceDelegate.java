package com.myownmotivator.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmailMessageServiceDelegate {

	@Autowired
	@Qualifier("userCreatedEmailService")
	private EmailMessageService userCreatedEmailService;
	
	@Autowired
	@Qualifier("welcomeUserEmailService")
	private EmailMessageService welcomeUserEmailService;	

	@Autowired
	@Qualifier("adminEmailService")
	private EmailMessageService adminEmailService;
	
	@Autowired
	@Qualifier("eventInvitationEmailService")
	private EmailMessageService eventInvitationEmailService;
	
	@Autowired
	@Qualifier("sendFeedbackEmailService")
	private EmailMessageService sendFeedbackEmailService;
	
	@Autowired
	@Qualifier("sendInboxMessageService")
	private EmailMessageService sendInboxMessageService;	
	
	@Autowired
	@Qualifier("userForgotPasswordService")
	private EmailMessageService userForgotPasswordService;
	
	@Autowired
	@Qualifier("paymentNotificationService")
	private EmailMessageService paymentNotificationService;
	
	@Autowired
	@Qualifier("exceptionsEmailService")
	private EmailMessageService exceptionsEmailService;
	
	
	@Autowired
	@Qualifier("crowdscannerUserCreated")
	private EmailMessageService crowdscannerUserCreated;
	
	@Autowired
	@Qualifier("huntBackgroundQueue")
	private EmailMessageService huntBackgroundQueue;
	

	public EmailMessageService getHuntBackgroundQueue() {
		return huntBackgroundQueue;
	}

	public EmailMessageService getExceptionsEmailService() {
		return exceptionsEmailService;
	}

	public EmailMessageService getPaymentNotificationService() {
		return paymentNotificationService;
	}

	public EmailMessageService getUserForgotPasswordService() {
		return userForgotPasswordService;
	}

	public EmailMessageService getSendInboxMessageService() {
		return sendInboxMessageService;
	}

	public EmailMessageService getSendFeedbackEmailService() {
		return sendFeedbackEmailService;
	}

	public EmailMessageService getAdminEmailService() {
		return adminEmailService;
	}	

	public EmailMessageService getUserCreatedEmailService() {
		return userCreatedEmailService;
	}

	public EmailMessageService getEventInvitationEmailService() {
		return eventInvitationEmailService;
	}
	
	public EmailMessageService getWelcomeUserEmailService() {
		return welcomeUserEmailService;
	}
	
	
	public EmailMessageService getCrowdscannerUserCreated() {
		return crowdscannerUserCreated;
	}
	
}
