package de.netview.config.security;


import de.netview.service.ISystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.transaction.TransactionException;

public class AuthenticationProviderImpl implements AuthenticationProvider {

    private ActiveDirectoryLdapAuthenticationProvider adAuthenticationProvider;
    private PasswordEncoder md5PasswordEncoder;
    private UserDetailsService userService;

    @Autowired
    private ISystemUserService systemUserService;

    public AuthenticationProviderImpl() {
        super();
        md5PasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            UsernamePasswordAuthenticationToken token = null;
            UserDetailsImpl details = null;

            if (!systemUserService.tryConnectionToLDAP()){

            } else if (systemUserService.isUserInGroup("g_u_verwaltung", authentication.getName())) {
                try {
                    details = (UserDetailsImpl) userService.loadUserByUsername(authentication.getName());
                } catch (UsernameNotFoundException e) {
                }

                if (details == null) details = systemUserService.checkAD(authentication);

                if (details.getPassword() != null) {
                    if (md5PasswordEncoder.matches((String) authentication.getCredentials(), details.getPassword())) {
                        token = new UsernamePasswordAuthenticationToken(details, details.getPassword(),
                                details.getAuthorities());

                    } else {
                        throw new AuthenticationException("Login failed.") {
                            private static final long serialVersionUID = 1L;
                        };
                    }
                } else {
                    throw new AuthenticationException("The systemuser \"" + details.getUsername() + "\" has no password.") {
                        private static final long serialVersionUID = 1L;
                    };
                }
            }

            return token;

        } catch (TransactionException e) {
            throw new AuthenticationException("Database Exception", e) {
                private static final long serialVersionUID = 1L;
            };
        }
    }

    public void setAdAuthenticationProvider(ActiveDirectoryLdapAuthenticationProvider adAuthenticationProvider) {
        this.adAuthenticationProvider = adAuthenticationProvider;
    }

    public void setUserService(UserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
