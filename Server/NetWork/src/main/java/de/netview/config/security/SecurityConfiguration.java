package de.netview.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;

/**
 * Created by mf on 13.08.2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        return new ActiveDirectoryLdapAuthenticationProvider("rsvx.it", "ldap://10.128.0.5");
    }


    @Bean
    public AuthenticationProviderImpl defaultAuthenticationProvider() {

        AuthenticationProviderImpl defaultAuthenticationProvider = new AuthenticationProviderImpl();
        defaultAuthenticationProvider.setUserService(userDetailsService());
        defaultAuthenticationProvider.setAdAuthenticationProvider(activeDirectoryLdapAuthenticationProvider());

        return defaultAuthenticationProvider;

    }

    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider("O92QgEo0utOyJY8QV6dKPnSRAQHMvThQ");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

        auth.authenticationProvider(rememberMeAuthenticationProvider());
        auth.authenticationProvider(defaultAuthenticationProvider());

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.csrf().disable();
    	http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN));
    	http.authorizeRequests().antMatchers("/static/img/**").permitAll();
    	http.authorizeRequests().antMatchers("/static/js/login.js").permitAll();
    	http.authorizeRequests().antMatchers("/static/css/login.css").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/hardware").permitAll();
        http.authorizeRequests().antMatchers("/hardware/login").permitAll();
        http.authorizeRequests().antMatchers("/hardware/logout").permitAll();
        http.authorizeRequests().antMatchers("/**").authenticated();
    	
        http.formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/main", true);
    }
}