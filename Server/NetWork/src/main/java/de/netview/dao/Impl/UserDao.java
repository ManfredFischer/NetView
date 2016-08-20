package de.netview.dao.Impl;

import de.netview.dao.IUserDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mf on 13.08.2016.
 */
@Repository("userDao")
public class UserDao extends AbstractDao<User> implements IUserDao {

    public List getAll() {
        return getSession().createQuery("select u from User u").list();
    }

    @Override
    public User getUserByUsername(String username) {
        List<User> userList = getSession().createQuery("select u from User u where username = :username").setParameter("username", username).list();
        if (userList == null) {
            return null;
        } else {
            if (userList.isEmpty()) {
                return null;
            } else {
                return userList.get(0);
            }
        }
    }
}
