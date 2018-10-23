package de.netview.service.Impl;

import de.netview.dao.Impl.UserDao;
import de.netview.model.Systemuser;
import de.netview.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by mf on 13.08.2016.
 */
@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;


    @Override
    public void createUser(Systemuser systemuser) {
        userDao.save(systemuser);
    }

    @Override
    public Systemuser getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

}
