package com.myownmotivator.service.message;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myownmotivator.model.message.Message;

@Service("messageService")
@Transactional
public class MessageDaoImpl implements MessageDao  {

	@Autowired
	private SessionFactory sessionFactory;
		
	@Transactional(readOnly=false)
	public void sendMessage(Message message) {
		
		message.setSendDate(Calendar.getInstance().getTime());
		sessionFactory.getCurrentSession().save(message);
		
	}

	@Transactional(readOnly=true)
	public List<Message> retrieveMessage(int profileId) {
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("retrieveMessages");
		query.setParameter("profileid", profileId);		
		
		return query.list();
	}	

	@Transactional(readOnly=false)
	public void setReadMessage(Integer messagePK) {
	
		Message message = (Message) sessionFactory.getCurrentSession().get(Message.class,messagePK);
		message.setReadMessage(true);		
		sessionFactory.getCurrentSession().saveOrUpdate(message);		
	}

	@Transactional(readOnly=false)
	public void deleteMessage(Integer messagePK) {
		
		Message message = new Message();
		message.setId(messagePK); 
		sessionFactory.getCurrentSession().delete(message);		
	}
	
	@Transactional(readOnly=true)
	public long retrieveUnreadMessages(int profileId) {
		
		return (Long) sessionFactory.getCurrentSession().createQuery("select count(*) from Message mg where mg.profile.id =:profileId  and mg.readMessage = false")
		.setInteger("profileId", profileId).uniqueResult();
		
	}
	
}
