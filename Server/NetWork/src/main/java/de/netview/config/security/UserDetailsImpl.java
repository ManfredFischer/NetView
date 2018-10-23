package de.netview.config.security;

import de.netview.model.Systemuser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class UserDetailsImpl implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;
    protected Systemuser systemuser;


    @Override
    public String toString() {
        if (systemuser != null) {
            return super.toString() + " Username: " + systemuser.getUsername();
        } else {
            return super.toString();
        }
    }

    public UserDetailsImpl(Systemuser systemuser) {
        this.systemuser = systemuser;
    }

    public String getPassword() {
        return systemuser.getPassword();
    }

    public Systemuser getSystemUser() {
        return systemuser;
    }

    public String getUsername() {
        return systemuser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashMap<Long, GrantedAuthority> authoritiesMap = new HashMap<Long, GrantedAuthority>();
        return authoritiesMap.values();
    }




}
