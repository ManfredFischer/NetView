package de.netview.config.security;


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

    public AuthenticationProviderImpl() {
        super();
        md5PasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {

            UserDetailsImpl details;

            try {
                details = (UserDetailsImpl) userService.loadUserByUsername(authentication.getName());
            } catch (UsernameNotFoundException e) {
                throw new AuthenticationException(e.getMessage(), e) {
                    private static final long serialVersionUID = 1L;
                };
            }


            UsernamePasswordAuthenticationToken token = null;
            Authentication adAuthentication = null;
            Boolean ldapConnection = true;

            try {
                adAuthentication = adAuthenticationProvider.authenticate(authentication);
                if (adAuthentication.isAuthenticated()) {
                    token = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
                }else {
                    ldapConnection = false;
                }
            }catch(Exception ex){
                System.out.println("Nicht in der Domäne");
                ldapConnection = false;
            }

            if (!ldapConnection){
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

    public UserDetailsService getUserService() {
        return userService;
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
