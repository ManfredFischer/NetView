package de.netview.service;

import de.netview.model.Systemuser;
import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.exception.LdapException;

/**
 * Created by mf on 13.08.2016.
 */
public interface IUserService {
    public void createUser(Systemuser systemuser);
    public Systemuser getUserByUsername(String username) ;
}
