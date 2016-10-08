package com.test.utils;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import com.myownmotivator.model.User;
import com.myownmotivator.model.profile.EfactorData;
import com.myownmotivator.model.profile.File;
import com.myownmotivator.model.profile.LinkedInData;
import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.profile.ProfileInfo;
import com.myownmotivator.model.profile.TwitterUserAuthentication;
import com.peoplehuntv2.model.AsyncMessage;
import com.peoplehuntv2.model.Feeler;
import com.peoplehuntv2.model.FoundTarget;
import com.peoplehuntv2.model.Group;
import com.peoplehuntv2.model.MemberShip;
import com.peoplehuntv2.model.Notification;
import com.peoplehuntv2.model.Status;


public class SchemaUpdateUtil {

	public static void main(String[] args) {

		Configuration cfg = new Configuration();		
		cfg.addAnnotatedClass(ProfileInfo.class);	
		cfg.addAnnotatedClass(Profile.class);
		cfg.addAnnotatedClass(File.class);
		cfg.addAnnotatedClass(TwitterUserAuthentication.class);	
		cfg.addAnnotatedClass(User.class);
		cfg.addAnnotatedClass(LinkedInData.class);
		cfg.addAnnotatedClass(EfactorData.class);
		cfg.addAnnotatedClass(MemberShip.class);
		cfg.addAnnotatedClass(Group.class);
		cfg.addAnnotatedClass(FoundTarget.class);
		cfg.addAnnotatedClass(Status.class);
		cfg.addAnnotatedClass(Feeler.class);
		cfg.addAnnotatedClass(AsyncMessage.class);
		cfg.addAnnotatedClass(Notification.class);
		cfg.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
		cfg.setProperty(Environment.URL, "jdbc:mysql://127.0.0.1:3307/languagehunt");
		cfg.setProperty(Environment.DIALECT, MySQLDialect.class.getName());
		cfg.setProperty(Environment.SHOW_SQL, "true");
		cfg.setProperty(Environment.USER, "firstuser");
		cfg.setProperty(Environment.PASS, "espana29");

		//SchemaExport update = new SchemaExport(cfg);
		//update.create(true, false);
		
		SchemaUpdate update = new SchemaUpdate(cfg);
		update.execute(true, false);

	}
}
