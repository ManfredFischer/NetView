package de.netview.dao;

import java.util.List;

import de.netview.model.Location;

public interface ILocationDAO {
	
	public List getLocation();
	public Location getLocationById(Long lid);

}
