package de.netview.service;

import de.netview.model.Location;
import de.netview.model.Network;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mf on 21.08.2016.
 */

public interface INetworkService {
    Network createNetwork(Network network, Long lid);
    Set<Network> getNetworksByLocation(Location location);
    List<Map> getNetworksByUser();
    void removeNetworks(List<Long> networks);
    void removeNetworkById(Long nid);
    void updateNetwork(Network network);
}
