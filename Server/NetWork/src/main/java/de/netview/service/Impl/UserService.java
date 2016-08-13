package de.netview.service.Impl;

import de.netview.dao.Impl.UserDao;
import de.netview.model.Groups;
import de.netview.model.User;
import de.netview.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        Groups userInfo = new Groups();
        userInfo.setName("info");

        user.getGroups().add(userInfo);

        userDao.save(user);
    }
}
