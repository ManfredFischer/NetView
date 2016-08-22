package de.netview.service.Impl;

import de.netview.dao.Impl.LocationDao;
import de.netview.dao.Impl.NetworkDao;
import de.netview.dao.Impl.UserDao;
import de.netview.model.Location;
import de.netview.model.Network;
import de.netview.model.User;
import de.netview.service.INetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by mf on 21.08.2016.
 */
@Service
public class NetworkService implements INetworkService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private NetworkDao networkDao;

    @Autowired
    private LocationDao locationDao;

    @Override
    public Network createNetwork(Network network, Long lid) {
        Location location = locationDao.getLocationById(lid);
        if (location != null){
            networkDao.save(network);
            location.addNetwork(network);
        }

        locationDao.save(location);

        return network;
    }

    @Override
    public Set<Network> getNetworksByLocation(Location location) {
        return location.getNetzworks();
    }

    @Override
    public List<Map> getNetworksByUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getUserByUsername(userDetails.getUsername());

        List<Map> result = new ArrayList<>();
        for (Location location : user.getLocations()){
            HashMap network = new HashMap<>();
            network.put("name", location.getName());
            network.put("network",location.getNetzworks());
            result.add(network);
        }
        return result;
    }

    @Override
    public void removeNetworks(List<Long> networks) {

    }

    @Override
    public void removeNetworkById(Long lid) {

    }

    @Override
    public void updateNetwork(Network Network) {

    }
}
