package de.netview.service;

import java.util.List;

import de.netview.model.Location;

public interface IComponenteService {

	public List getLocationList(Long lid);

	public Location addLocation(Location location);

	public void saveLocation(Location location);

	public void deleteLocation(Long lid);

}
