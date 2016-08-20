package de.netview.dao.Impl;

import de.netview.dao.IProfileDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Profile;
import org.springframework.stereotype.Repository;

/**
 * Created by mf on 13.08.2016.
 */
@Repository("profileDao")
public class ProfileDao extends AbstractDao<Profile> implements IProfileDao {
}
