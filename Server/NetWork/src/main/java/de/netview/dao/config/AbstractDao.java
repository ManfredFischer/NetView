package de.netview.dao.config;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractDao<T> {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}


	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	public T save(T entity) {
		getSession().save(entity);
		return entity;
	}


	public void delete(T entity) {
		getSession().delete(entity);
	}
	

	public List<T> get(String entity){
		return getSession().createQuery("from "+entity).list();
	}

	public T getById(Long id, Class<T> type){
		return getSession().get(type,id);
	}
}
