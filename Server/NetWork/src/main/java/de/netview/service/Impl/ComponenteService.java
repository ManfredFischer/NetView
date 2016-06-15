package de.netview.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.LocationDao;
import de.netview.model.Location;
import de.netview.service.IComponenteService;

@Service
public class ComponenteService implements IComponenteService {

	@Autowired
	private LocationDao locationDao;

	public List getLocationList(Long lid) {
		if (lid == null) {
			return locationDao.list();
		} else {
			Location location = locationDao.get(lid);
			List listLocation = new ArrayList<Location>();
			listLocation.add(location);
			return listLocation;
		}
	}

	public Location addLocation(Location location) {
		return locationDao.saveOrUpdate(location);
	}

	public void saveLocation(Location location) {
		locationDao.saveOrUpdate(location);
	}

	public void deleteLocation(Long lid) {
		locationDao.delete(lid);

	}

}
