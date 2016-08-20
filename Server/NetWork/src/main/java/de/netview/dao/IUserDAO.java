package de.netview.dao;

import de.netview.model.User;

import java.util.List;

/**
 * Created by mf on 13.08.2016.
 */
public interface IUserDao {
    public List getAll();
    public User getUserByUsername(String username);
}
