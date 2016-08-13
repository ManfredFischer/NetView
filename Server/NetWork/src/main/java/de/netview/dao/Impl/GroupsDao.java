package de.netview.dao.Impl;

import de.netview.dao.IGroupsDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Groups;
import org.springframework.stereotype.Repository;

/**
 * Created by mf on 13.08.2016.
 */
@Repository("groupsDao")
public class GroupsDao extends AbstractDao<Groups> implements IGroupsDao {
}
