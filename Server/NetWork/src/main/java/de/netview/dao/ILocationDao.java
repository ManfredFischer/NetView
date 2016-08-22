package de.netview.dao;

import de.netview.model.Location;

import java.util.List;

/**
 * Created by mf on 21.08.2016.
 */
public interface ILocationDao {
    Location getLocationById(Long lid);
    List<Location> getLocation();
    void setLocationToRemovedById(Long lid);
}
