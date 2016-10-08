
package com.test.file;

import junit.framework.TestCase;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.service.FileDaoImpl;
import com.myownmotivator.service.profile.ProfileDaoImpl;



public class FileRetrieveTest extends TestCase{

	FileDaoImpl fileDaoImpl = new FileDaoImpl();
	
	ProfileDaoImpl profileDaoImpl = new ProfileDaoImpl();
	
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
		config.addClass(com.myownmotivator.model.profile.File.class);
		  SessionFactory sessionFactory = 
	            config.buildSessionFactory();
	    
		HibernateTemplate hibernateTemplate = new HibernateTemplate();  
		hibernateTemplate.setSessionFactory(sessionFactory);  		
		
		//fileDaoImpl.setHibernateTemplate(hibernateTemplate);
		
		//profileDaoImpl.setHibernateTemplate(hibernateTemplate);
	
	}
	
	public void testRetrieveFile(){
		
		
		File file = new File();
		file.setFileContent(new byte[]{12,23,45});
		//file.setUserProfile(4);
		

	}
	
	public void testProfile(){
	
	File file = new File();
	file.setFileContent(new byte[]{12,23,45});
	Profile profile = new Profile();
	profile.getFiles().add(file);
	
	//File fileresult = profileDaoImpl.updateProfile(profile);

	int b= 10;
	int a = b/=2;
	
	System.out.println(a);
	
		
	}
}
