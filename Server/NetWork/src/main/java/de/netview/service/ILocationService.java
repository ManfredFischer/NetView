package de.netview.service;

import de.netview.model.Location;

import java.util.List;

/**
 * Created by mf on 21.08.2016.
 */
public interface ILocationService {
    Location createLocation(Location location);
    List<Location> getLocations();
    List<Location> getLocationsByUser();
    void removeLocations(List<Long> locations);
    void removeLocationById(Long lid);
    void updateLocation(Location location);
}
