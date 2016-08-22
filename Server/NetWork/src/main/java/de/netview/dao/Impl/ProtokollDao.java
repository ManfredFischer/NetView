package de.netview.dao.Impl;

import de.netview.dao.IProtokoll;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Protokoll;
import org.springframework.stereotype.Repository;

/**
 * Created by mf on 21.08.2016.
 */
@Repository
public class ProtokollDao extends AbstractDao<Protokoll> implements IProtokoll {
}
