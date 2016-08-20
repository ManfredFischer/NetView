package de.netview.service;

import de.netview.model.User;

/**
 * Created by mf on 13.08.2016.
 */
public interface IUserService {
    public void createUser(User user);
    public User getUserByUsername(String username);
    public void prepareUser(User user);
}
