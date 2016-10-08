package com.myownmotivator.service.questions;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.myownmotivator.model.questions.Answer;
import com.myownmotivator.model.questions.Question;
import com.myownmotivator.model.questions.QuestionBundle;

public interface QuestionDao {
	
	
	public void insertAnswers(Collection<Answer> answers);	
	
	public Question saveQuestion(Question questionaire);
	
	public Question findById(Integer id);
	
	public void saveBatchQuestions(List<Question> questions);
	
	public List<Question> findQuestionWithPhoneId(Integer id);
	
	public List<Question> findQuestionWithUsername(String username, Integer questionPhoneId);
	
	public List<Question> findQuestionsByUsername(String username);
	
	public Question findQuestionParentByPhoneId(Integer questionPhoneId);
	
	public Question findQuestionParentByType(String questionType, Integer bundleId);
	
	public List<Question> retrieveAllQuestions();
	
	public List<Question> retrieveQuestionsAsc();
	
	public void deleteAnswers(Collection<Answer> answers);
	
	public List<Question> retrieveWeekQuestions(String startDate, String endDate);
	
	public void deleteQuestion(Integer questionId);
	
	public List<Question> retrievePagedQuestions(int pageNumber, int pageSize); 	
	
	public Map<Integer, List<Question>> retrieveBatchQuestionsStats(List<Question> theQuestions);	
	
	public List<Question> retrieveAllQuestionsByLoc();
	
	public List<Question> findVirtualInfluence(String username);
	
	public Question findBackendQuestionWithUsername(String username, Integer questionId);
	
	public Map<Integer, Map<String, Object>> retrieveAnswersByParentQuestionId(Integer bundleId);
	
	//====Bundle======================///	
	
	public List<Question> retrieveUserBundle(String username, Integer bundleId);
	
	public QuestionBundle retrieveQuestionBundle(Integer bundleId);
	
	public List<QuestionBundle> retrieveAllBundles();
	
	public List<QuestionBundle> retrieveAllBundlesHunt();

	public void deleteQuestion(Question question);

	public List<Question> retrieveParentQuestions(Integer bundleId);

}
