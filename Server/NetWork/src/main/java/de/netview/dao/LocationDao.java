package de.netview.dao;

import java.util.List;

import de.netview.model.Location;

public interface LocationDao {
	public List<Location> list();

	public Location get(Long id);

	public Location saveOrUpdate(Location location);

	public void delete(Long id);
}
