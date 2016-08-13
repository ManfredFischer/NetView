package de.netview.rest;

import de.netview.model.User;
import de.netview.service.Impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by mf on 25.06.2016.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public@ResponseBody String addUser() {
        User user = new User();
        user.setUsername("mafischer");
        userService.createUser(user);
        return "login";
    }
}
