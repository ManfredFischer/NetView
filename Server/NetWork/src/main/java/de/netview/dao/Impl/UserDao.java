package de.netview.dao.Impl;

import de.netview.dao.IUserDAO;
import de.netview.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by mf on 13.08.2016.
 */
@Repository("userDao")
public class UserDao extends AbstractDao<Integer, User> implements IUserDAO{
}
