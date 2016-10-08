package com.myownmotivator.service;

import com.myownmotivator.model.profile.File;

public interface FileDao {

	public File retrieveFilebyPrimaryKey(Integer primaryKey);

	public File retrieveFileByName(String fileName); 
	
	public File updateFile(File file);

	public void remove(File theFile);
	

}
