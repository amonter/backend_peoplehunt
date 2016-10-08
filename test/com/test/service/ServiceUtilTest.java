package com.test.service;

import junit.framework.TestCase;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.myownmotivator.model.conf.ConfigurationObject;
import com.myownmotivator.service.email.EmailAdminFactory;
import com.myownmotivator.service.email.EmailPropertyFactory;
import com.myownmotivator.service.utils.ServiceUtils;

public class ServiceUtilTest extends TestCase {

	
	ServiceUtils serviceUtils = new ServiceUtils();
	
	protected void setUp() throws Exception {

		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
		cfg.setProperty(Environment.URL, "jdbc:mysql://localhost/motivate");
		cfg.setProperty(Environment.DIALECT, MySQLDialect.class.getName());
		cfg.setProperty(Environment.SHOW_SQL, "true");
		cfg.setProperty(Environment.USER, "mont");
		cfg.setProperty(Environment.PASS, "mont");
		cfg.addAnnotatedClass(ConfigurationObject.class);
		
		SessionFactory sessionFactory = cfg.buildSessionFactory();		
		HibernateTemplate hibernateTemplate = new HibernateTemplate();
		hibernateTemplate.setSessionFactory(sessionFactory);

		//configService.setHibernateTemplate(hibernateTemplate);
		//serviceUtils.setConfigService(configService);

	}
	
	public void testService () {
	
		EmailPropertyFactory email = new EmailAdminFactory();
		serviceUtils.setEmailValues("createuser", email);
		System.out.println(email.geMailBeanProperties().getFrom());
		System.out.println(email.geMailBeanProperties().getSubject());
		System.out.println(email.geMailBeanProperties().getTemplate());
		
	}
}
