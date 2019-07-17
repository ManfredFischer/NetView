package de.netview.dao.Impl;

import de.netview.dao.IUserDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Systemuser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mf on 13.08.2016.
 */
@Repository
public class SystemuserDao extends AbstractDao<Systemuser> implements IUserDao {

    public List getAll() {
        return getSession().createQuery("select u from User u").list();
    }

    @Override
    public Systemuser getUserByUsername(String username) {
        List<Systemuser> systemuserList = getSession().createQuery("select u from Systemuser u where username = :username").setParameter("username", username).list();
        if (systemuserList == null) {
            return null;
        } else {
            if (systemuserList.isEmpty()) {
                return null;
            } else {
                return systemuserList.get(0);
            }
        }
    }
}
