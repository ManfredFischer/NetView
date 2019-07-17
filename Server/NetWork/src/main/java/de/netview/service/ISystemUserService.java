package de.netview.service;

import de.netview.data.LDAPUserData;
import de.netview.model.Systemuser;

/**
 * Created by mf on 13.08.2016.
 */
public interface ISystemUserService {
    public void createUser(Systemuser systemuser);
    public Systemuser getSystemuserByUsername(String username) ;
}
