package de.netview.dao.Impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.netview.dao.LocationDao;
import de.netview.model.Location;

@Repository
public class LocationDaoImpl implements LocationDao {

	@Autowired
	private SessionFactory sessionFactory;

	public LocationDaoImpl() {

	}

	public LocationDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<Location> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public Location get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void saveOrUpdate(Location location) {
		// TODO Auto-generated method stub

	}

	public void delete(int id) {
		// TODO Auto-generated method stub

	}

}
