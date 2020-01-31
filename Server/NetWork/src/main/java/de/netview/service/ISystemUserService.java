package de.netview.service;

import de.netview.config.security.UserDetailsImpl;
import de.netview.data.LDAPUserData;
import de.netview.model.Systemuser;
import org.springframework.security.core.Authentication;

import javax.transaction.Transactional;

/**
 * Created by mf on 13.08.2016.
 */
public interface ISystemUserService {
    public Systemuser createUser(Systemuser systemuser);
    public Systemuser getSystemuserByUsername(String username);
    UserDetailsImpl checkAD(Authentication authentication);

    boolean tryConnectionToLDAP();

    boolean isUserInGroup(String group, String username);
}
