package de.netview.service.Impl;

import de.netview.config.security.UserDetailsImpl;
import de.netview.dao.Impl.SystemuserDao;
import de.netview.data.ADUserData;
import de.netview.model.Systemuser;
import de.netview.service.ILDAPService;
import de.netview.service.ISystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemUserService implements ISystemUserService {

    @Autowired
    private SystemuserDao userDao;

    @Autowired
    private ILDAPService LDAPService;

    @Override
    @Transactional
    public Systemuser createUser(Systemuser systemuser) {
        userDao.saveOrUpdate(systemuser);
        return systemuser;
    }


    @Override
    @Transactional
    public Systemuser getSystemuserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    @Transactional
    public UserDetailsImpl checkAD(Authentication authentication) {
        Systemuser systemuser = null;
        ADUserData ldapUserByName = LDAPService.getLDAPUserByName(authentication.getName());

        if (ldapUserByName != null) {
            systemuser = new Systemuser();
            systemuser.setUsername(authentication.getName());
            systemuser.setPassword(cryptWithMD5(authentication.getCredentials().toString()));
            userDao.saveOrUpdate(systemuser);

			return new UserDetailsImpl(systemuser);
        }

		throw new UsernameNotFoundException("Username not found");

    }

    @Override
    public boolean tryConnectionToLDAP(){
        return LDAPService.tryConnectionToLDAP();
    }

	@Override
	public boolean isUserInGroup(String group, String username) {
		if ("admin".equalsIgnoreCase(username)) return true;
		if (LDAPService.getLDAPUserGroups(username).contains(group)) return true;

		return false;
	}

	public static String cryptWithMD5(String pass){
		PasswordEncoder md5PasswordEncoder  = new BCryptPasswordEncoder();
		if (!pass.equals("")) {
			return md5PasswordEncoder.encode(pass);
		}
		return "";
	}



}
