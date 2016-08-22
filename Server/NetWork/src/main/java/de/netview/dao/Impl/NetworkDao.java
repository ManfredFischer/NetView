package de.netview.dao.Impl;

import de.netview.dao.INetworkDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Network;
import org.springframework.stereotype.Repository;

/**
 * Created by mf on 21.08.2016.
 */
@Repository
public class NetworkDao extends AbstractDao<Network> implements INetworkDao {
}
