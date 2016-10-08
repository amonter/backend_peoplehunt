package com.myownmotivator.service.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.myownmotivator.model.conf.ConfigurationObject;
import com.myownmotivator.service.email.EmailPropertyFactory;

public class ServiceUtils {

	/*
	private ConfigurationDAO configService;

	public void setConfigService(ConfigurationDAO configService) {
		this.configService = configService;
	}*/

	public void setEmailValues(String identifier,	EmailPropertyFactory propertyFactory) {

		String[] emailProperties = propertyFactory.getEmailProperties();
		for (int i = 0; i < emailProperties.length; i++) {

			String emailProperty = emailProperties[i];
			String property = identifier + "_" + emailProperties[i];
			//ConfigurationObject objectValues = configService.retrieveConfig(property);
			ConfigurationObject objectValues = null;
			
			if (objectValues != null) {
				try {

					BeanUtils.copyProperty(propertyFactory.geMailBeanProperties(), emailProperty,objectValues.getValue());

				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	
	
	public void putDefaultFromAddress(EmailPropertyFactory propertyFactory) {		
		
		
		//ConfigurationObject objectValues = configService.retrieveConfig("default_from");
		ConfigurationObject objectValues = null;
		if (objectValues == null) {
			
			propertyFactory.geMailBeanProperties().setFrom("support@meetforeal.com");
		}
		else {
			
			propertyFactory.geMailBeanProperties().setFrom(objectValues.getValue());
		}
		
	}
}
