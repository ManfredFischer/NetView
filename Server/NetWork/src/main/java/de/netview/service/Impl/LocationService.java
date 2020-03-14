package de.netview.service.Impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.Impl.LocationDAO;
import de.netview.model.Location;
import de.netview.service.ILocationService;

@Service
@Transactional(rollbackOn=MappingException.class, dontRollbackOn=Exception.class)
public class LocationService implements ILocationService {

	@Autowired
	public LocationDAO locationDAO;
	
	@Override
	public List getLocation() {
		return locationDAO.getLocation();
	}
	
	@Override
	public Location getLocationById(Long lid) {
		return locationDAO.getLocationById(lid);
	}

	@Override
	public Location getLocationByCity(String city) {
		return locationDAO.getLocationByCity(city);
	}

}
