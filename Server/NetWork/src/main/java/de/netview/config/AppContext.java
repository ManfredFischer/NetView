package de.netview.config;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;

public class AppContext {

    private static ApplicationContext ctx;

    private static ResourceLoader resourceLoader;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    public static ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public static void setResourceLoader(ResourceLoader resourceLoader) {
        AppContext.resourceLoader = resourceLoader;
    }

}
