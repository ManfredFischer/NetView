package de.netview.config;

import org.quartz.SchedulerContext;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * @author sseaman - mostly a cut/paste from the source class
 * 
 */
public class SpringBeanJobFactory extends org.springframework.scheduling.quartz.SpringBeanJobFactory {

    private String[] ignoredUnknownProperties;

    private SchedulerContext schedulerContext;

    @Override
    public void setIgnoredUnknownProperties(String... ignoredUnknownProperties) {
        super.setIgnoredUnknownProperties(ignoredUnknownProperties);
        this.ignoredUnknownProperties = ignoredUnknownProperties;
    }

    @Override
    public void setSchedulerContext(SchedulerContext schedulerContext) {
        super.setSchedulerContext(schedulerContext);
        this.schedulerContext = schedulerContext;
    }

 
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // XmlWebApplicationContext ctx = ((XmlWebApplicationContext)schedulerContext.get("applicationContext"));
        // Object job = ctx.getBean(bundle.getJobDetail().getJobClass().getName());
        Object job = Class.forName(bundle.getJobDetail().getJobClass().getName()).getConstructor().newInstance();
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(job);
        if (isEligibleForPropertyPopulation(bw.getWrappedInstance())) {
            MutablePropertyValues pvs = new MutablePropertyValues();
            if (schedulerContext != null) {
                pvs.addPropertyValues(schedulerContext);
            }
            pvs.addPropertyValues(bundle.getJobDetail().getJobDataMap());
            pvs.addPropertyValues(bundle.getTrigger().getJobDataMap());
            if (ignoredUnknownProperties != null) {
                for (String propName : ignoredUnknownProperties) {
                    if (pvs.contains(propName) && !bw.isWritableProperty(propName)) {
                        pvs.removePropertyValue(propName);
                    }
                }
                bw.setPropertyValues(pvs);
            } else {
                bw.setPropertyValues(pvs, true);
            }
        }
        return job;
    }

}
