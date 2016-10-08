package com.crowdscanner.controller.avatars;

import java.util.HashMap;
import java.util.List;

import com.myownmotivator.model.questions.Question;

public abstract class CharacterMaker {

	public abstract List<HashMap<String, Object>> getAvatarsInfo();
	
	public abstract List<HashMap<String, Object>> getAvatarsInfoByGender(String gender);
	
	public abstract HashMap<String, String> characterAlg(List<Question> theQuestions);	
	
	public abstract String searchAvatarURL(String avatar);
	
}
