package com.myownmotivator.service.profile;

import java.util.List;

import com.myownmotivator.model.Plan;

public interface PlanDao {

	/**
	 * Returns all plans that will expire within the next five days
	 * (endDate-today) == 5
	 * @param days
	 * @return
	 */
	List<Plan> findPlansThatExpireIn(int days);
	
	/**
	 * Find all plans for which x number of days have elapsed
	 * (today - startDate) == 25 
	 * @param days
	 * @return
	 */
	List<Plan> findPlansWithElapsedDays(int days);
	
}
