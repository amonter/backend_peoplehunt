package com.myownmotivator.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.myownmotivator.model.profile.File;

@Repository(value="FileService")
@Transactional
public class FileDaoImpl implements FileDao {
	
	private static final String getFileByPrimaryKey = "select f from File as f where f.id=:fileId";
	
	private static final String getFileByName = "select f from File as f where f.fileName=:fileName";

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public File retrieveFilebyPrimaryKey(Integer fileId) {

		List<File> file = sessionFactory.getCurrentSession().createQuery(
				getFileByPrimaryKey).setInteger("fileId", fileId).list();

		if (file != null) {

			return file.get(0);
		}

		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public File retrieveFileByName(String fileName) {

		List<File> file = sessionFactory.getCurrentSession().createQuery(
				getFileByName).setString("fileName", fileName).list();

		if (!file.isEmpty()) {

			return file.get(0);
		}

		return null;
	}	
	
	@Transactional(readOnly=false)	
	public File updateFile(File file) {
		return (File)sessionFactory.getCurrentSession().merge(file);
	}


	@Transactional
	public void remove(File theFile) {
		
		sessionFactory.getCurrentSession().delete(theFile);
	}
}
