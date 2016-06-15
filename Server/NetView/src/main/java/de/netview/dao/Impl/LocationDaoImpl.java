package de.netview.dao.Impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
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

	@Transactional(readOnly = true)
	public List<Location> list() {
		@SuppressWarnings("unchecked")
		List<Location> listLocation = (List<Location>) sessionFactory.getCurrentSession().createCriteria(Location.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return listLocation;
	}

	@Transactional(readOnly = true)
	public Location get(Long lid) {
		String hql = "from Location where id=" + lid;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		@SuppressWarnings("unchecked")
		List<Location> listUser = (List<Location>) query.list();

		if (listUser != null && !listUser.isEmpty()) {
			return listUser.get(0);
		}

		return null;
	}

	@Transactional(readOnly = false)
	public Location saveOrUpdate(Location location) {
		sessionFactory.getCurrentSession().saveOrUpdate(location);
		return location;
	}

	@Transactional(readOnly = false)
	public void delete(Long lid) {
		Location locationToDelete = new Location();
		locationToDelete.setLid(lid);
		sessionFactory.getCurrentSession().delete(locationToDelete);

	}

}
