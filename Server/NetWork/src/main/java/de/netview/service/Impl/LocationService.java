package de.netview.service.Impl;

import de.netview.dao.Impl.LocationDao;
import de.netview.dao.Impl.UserDao;
import de.netview.model.Location;
import de.netview.model.User;
import de.netview.service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mf on 21.08.2016.
 */
@Service
public class LocationService implements ILocationService {

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public Location createLocation(Location location) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getUserByUsername(userDetails.getUsername());

        locationDao.save(location);
        user.addLocation(location);

        userDao.saveOrUpdate(user);
        return location;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getLocations() {
        return locationDao.getLocation();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getLocationsByUser() {
        List<Location> result = new ArrayList<>();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getUserByUsername(userDetails.getUsername());
        for (Location location : user.getLocations()){
            if (!location.getRemoved()){
                result.add(location);
            }
        }

        return result;
    }

    @Override
    @Transactional
    public void removeLocations(List<Long> locations) {
        Location location;
        for(Long lid : locations){
            locationDao.setLocationToRemovedById(lid);
        }
    }

    @Override
    public void removeLocationById(Long lid) {
        locationDao.setLocationToRemovedById(lid);
    }

    @Override
    public void updateLocation(Location location) {
        locationDao.saveOrUpdate(location);
    }


}
