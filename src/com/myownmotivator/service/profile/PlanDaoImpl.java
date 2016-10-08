package com.myownmotivator.service.profile;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myownmotivator.model.Plan;

@Repository
public class PlanDaoImpl implements PlanDao {

	@Autowired
	private SessionFactory sessionFactory;

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Plan> findPlansThatExpireIn(int days) {
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Plan p where p.endDate = :date ")
				.setDate("date",add(new Date(),days) )
				.list();
	}

	private Date subtract(Date date,int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,-1*days);
		return cal.getTime();
	}

	private Date add(Date date,int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,days);
		return cal.getTime();
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<Plan> findPlansWithElapsedDays(int days) {
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Plan p where p.startDate = :date ")
				.setDate("date",subtract(new Date(),days) )
				.list();

	}

	
}
