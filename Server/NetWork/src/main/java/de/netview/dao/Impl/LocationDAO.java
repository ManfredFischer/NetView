package de.netview.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import de.netview.dao.ILocationDAO;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Location;

@Repository
public class LocationDAO extends AbstractDao<Location> implements ILocationDAO{

	@Override
	public List getLocation() {
		List<Location> locationList = getSession().createQuery("from Location").list();
        if (locationList == null) {
            return null;
        } else {
            return locationList;
        }
	}

	@Override
	public Location getLocationById(Long lid) {
		List<Location> locationList = getSession().createQuery("from Location where lid = :lid").setParameter("lid", lid).list();
        if (locationList == null) {
            return null;
        } else {
        	if (locationList.isEmpty()) {
        		return null;
        	}
            return locationList.get(0);
        }
	}

}
