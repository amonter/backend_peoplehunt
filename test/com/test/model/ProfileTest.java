package com.test.model;

import java.io.File;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.myownmotivator.model.Goal;
import com.myownmotivator.model.Motivation;
import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.service.profile.ProfileDaoImpl;


import junit.framework.TestCase;

public class ProfileTest extends TestCase {
	
	
	
	
	
	ProfileDaoImpl profile =  new ProfileDaoImpl();
	
	protected void setUp() throws Exception {

		
		Configuration config = new Configuration();
		config.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
		config.setProperty(Environment.URL, "jdbc:mysql://localhost/motivate");
		config.setProperty(Environment.DIALECT, MySQLDialect.class.getName());
		config.setProperty(Environment.SHOW_SQL, "true");
		config.setProperty(Environment.USER, "mont");
		config.setProperty(Environment.PASS, "mont");
		config.addClass(User.class);
		config.addClass(Profile.class);
		config.addClass(Goal.class);
		config.addClass(com.myownmotivator.model.profile.File.class);
		  SessionFactory sessionFactory = 
	            config.buildSessionFactory();
	    
		HibernateTemplate hibernateTemplate = new HibernateTemplate();  
		hibernateTemplate.setSessionFactory(sessionFactory);  		
		
		//profile.setHibernateTemplate(hibernateTemplate);
		
		
	}
	
	
	
	public void testProfile (){
		
		
		
		List<Profile> listProfile  = profile.getProfileByGoals("i love goals");
		
		
		
		
		int b= 10;
		int a = b/=2;
		
		System.out.println(a);
		
		
	}

}
