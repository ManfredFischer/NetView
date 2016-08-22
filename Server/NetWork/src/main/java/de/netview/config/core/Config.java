package de.netview.config.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Properties;

/**
 * Created by mf on 21.08.2016.
 */
@Configuration
@PropertySource(value = {"classpath:de/netview/database/application.properties"})
public class Config {

    @Autowired
    private static Environment environment;

    public static String getURL(){
        return environment.getRequiredProperty("jdbc.url");
    }

    public static String getDriverClassName(){
        return environment.getRequiredProperty("jdbc.driverClassName");
    }

    public static String getUsername(){
       return environment.getRequiredProperty("jdbc.username");
    }

    public static String getPassword(){
        return environment.getRequiredProperty("jdbc.password");
    }

    public static String getDialect(){
        return environment.getRequiredProperty("hibernate.dialect");
    }

    public static String getSQLShow(){
       return environment.getRequiredProperty("hibernate.show_sql");
    }

    public static String getSQLFormat(){
        return environment.getRequiredProperty("hibernate.format_sql");
    }
}
