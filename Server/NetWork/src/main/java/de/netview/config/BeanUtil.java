package de.netview.config;

/**
 * Created by magruschinske on 3/17/16.
 */
public class BeanUtil {

    public static Object getBean(String beanName) {
        return AppContext.getApplicationContext().getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return AppContext.getApplicationContext().getBean(beanName, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return AppContext.getApplicationContext().getBean(requiredType);
    }

}
