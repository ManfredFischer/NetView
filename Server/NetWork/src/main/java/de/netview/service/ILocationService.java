package de.netview.service;

import java.util.List;

import de.netview.model.Location;

public interface ILocationService {

	public List getLocation();
	public Location getLocationById(Long lid);
	Location getLocationByCity(String city);
}
