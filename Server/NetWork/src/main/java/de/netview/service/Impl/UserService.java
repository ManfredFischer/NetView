package de.netview.service.Impl;

import de.netview.dao.Impl.UserDao;
import de.netview.model.Profile;
import de.netview.model.User;
import de.netview.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by mf on 13.08.2016.
 */
@Service("userService")
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;


    @Override
    public void createUser(User user) {
        Profile userInfo = new Profile();
        userInfo.setName("user");

        user.getProfiles().add(userInfo);

        userDao.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public void prepareUser(User user) {
        user.setActive(true);
        user.setLastlogin(new Date().getTime());
        userDao.save(user);
    }
}
