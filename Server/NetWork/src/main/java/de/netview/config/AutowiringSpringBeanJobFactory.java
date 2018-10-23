package de.netview.config;

import org.quartz.Job;
import org.quartz.SchedulerContext;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AutowiringSpringBeanJobFactory extends SimpleJobFactory implements ApplicationContextAware {
	private ApplicationContext ctx;
	private SchedulerContext schedulerContext;

	@Override
	public void setApplicationContext(final ApplicationContext context) {
		this.ctx = context;
	}

	protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
		Job job = ctx.getBean(bundle.getJobDetail().getJobClass());
		BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(job);
		MutablePropertyValues pvs = new MutablePropertyValues();
		pvs.addPropertyValues(bundle.getJobDetail().getJobDataMap());
		pvs.addPropertyValues(bundle.getTrigger().getJobDataMap());
		if (this.schedulerContext != null) {
			pvs.addPropertyValues(this.schedulerContext);
		}
		bw.setPropertyValues(pvs, true);
		return job;
	}

	public void setSchedulerContext(SchedulerContext schedulerContext) {
		this.schedulerContext = schedulerContext;
	}

}
