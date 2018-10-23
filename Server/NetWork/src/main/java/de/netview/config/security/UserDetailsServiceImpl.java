package de.netview.config.security;

import de.netview.model.Systemuser;
import de.netview.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mf on 20.08.2016.
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Systemuser systemuser = userService.getUserByUsername(username);
        System.out.println("Systemuser : "+ systemuser);
        if(systemuser == null){
            System.out.println("Systemuser not found");
            throw new UsernameNotFoundException("Username not found");
        }
        return new UserDetailsImpl(systemuser);
    }


    private List<GrantedAuthority> getGrantedAuthorities(Systemuser systemuser){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        /*for(Profile profile : systemuser.getProfiles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+profile.getName()));
        }*/
        return authorities;
    }

}
