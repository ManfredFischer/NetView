package de.netview.dao.config;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<T> {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}


	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	public void save(T entity) {
		getSession().save(entity);
	}


	public void delete(T entity) {
		getSession().delete(entity);
	}
	

}
