package de.netview.dao;

import java.util.List;

import de.netview.model.Location;

public interface LocationDao {
	public List<Location> list();

	public Location get(int id);

	public void saveOrUpdate(Location location);

	public void delete(int id);
}
