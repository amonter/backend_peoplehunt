package com.myownmotivator.service.crowdmodule;

import java.util.List;

import com.myownmotivator.model.crowdmodule.Visual;

public interface VisualDao {
	
	Visual create(Visual newVisual);
	List<Visual> findAll(); 
	Visual findByName(String name);
	Visual findById(Integer id);
	Visual findByPermalink(String eventPermaLink);

}
