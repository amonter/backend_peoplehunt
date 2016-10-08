package com.myownmotivator.service.crowdmodule;

import java.util.List;
import java.util.Map;

import com.myownmotivator.model.profile.Profile;
import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.QuestionBundle;

public interface QuestionBudleDao {

	List<QuestionBundle> findAll();
	QuestionBundle findById(Integer id);
	void update(QuestionBundle questionBundle);
	void create(QuestionBundle questionBundle);
	public QuestionBundle findByPermalink(String eventPermaLink);
	public List<QuestionBundle> findBundleByUser(String username);
	public List<Profile> getBundleProfiles(Integer bundleId);
	public List<Profile> getBundleProfilesBySize(Integer bundleId, Integer maxSize);
	public Long countBundleProfiles(Integer bundleId);
	public void updateProfilesBatch(List<Profile> profiles);
	public Map<Integer, Integer> retrieveRepeatedProvidingFor(Integer bundleId, List<Answer> listAnswers);
}
