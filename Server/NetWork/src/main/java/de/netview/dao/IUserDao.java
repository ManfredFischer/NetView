package de.netview.dao;

import de.netview.model.Systemuser;

/**
 * Created by mf on 13.08.2016.
 */
public interface IUserDao {
    Systemuser getUserByUsername(String username);
}
