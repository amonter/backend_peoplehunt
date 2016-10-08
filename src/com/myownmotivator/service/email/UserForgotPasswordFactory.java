package com.myownmotivator.service.email;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.myownmotivator.service.utils.MailBeanProperties;
import com.myownmotivator.service.utils.ServiceUtils;

@Service("userForgotPasswordEmailFactory")
public class UserForgotPasswordFactory extends UserCreatedEmailFactory {

	@Resource(name="serviceUtils")
	private ServiceUtils serviceUtils;
	
	private MailBeanProperties mailBeanUserProperties = new MailBeanProperties();
	
	@Override
	public void resetMailValues() {
	
		serviceUtils.setEmailValues("forgot_password", this);
		if (mailBeanUserProperties.getFrom() == null) {
			
			serviceUtils.putDefaultFromAddress(this);
		}		
	}
}
