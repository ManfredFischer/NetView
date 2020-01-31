package de.netview.service.job.impl;

import java.util.Calendar;
import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
public class JobMonitoring {

    private Scheduler scheduler;

    @Autowired
    public JobMonitoring(Scheduler scheduler) {
        this.scheduler = scheduler;
        startCheckHardware();  
        startCheckUPSConverter();
    }
    
    private void startCheckHardware() {

        Trigger trigger = null;

        try {

            Long creationTime = System.currentTimeMillis();
            String name = "JobMonitor_HardwareCheck";
            String group = "SystemJob";

            // We only add a new trigger if none exists
            if (!scheduler.checkExists(new TriggerKey(name, group))) {
                JobDetail job = JobBuilder.newJob(CheckHardwareJob.class).withIdentity(name, group)
                        .usingJobData("creationTime", creationTime).build();

                trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(1).repeatForever()) // Intervall
                        .startNow().build();
                
                scheduler.scheduleJob(job, trigger);
            }
        } catch (Exception exp) {
            try {
                scheduler.unscheduleJob(trigger.getKey());
            } catch (Exception exp2) {}
        }

    }
    
    private void startCheckUPSConverter() {

        Trigger trigger = null;

        try {

            Long creationTime = System.currentTimeMillis();
            String name = "JobMonitor_UPSConverter";
            String group = "SystemJob";

            // We only add a new trigger if none exists
            if (!scheduler.checkExists(new TriggerKey(name, group))) {
                JobDetail job = JobBuilder.newJob(CheckUPSConverterJob.class).withIdentity(name, group)
                        .usingJobData("creationTime", creationTime).build();

                trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).repeatForever())
                        .startNow().build();
                
                scheduler.scheduleJob(job, trigger);
            }
        } catch (Exception exp) {
            try {
                scheduler.unscheduleJob(trigger.getKey());
            } catch (Exception exp2) {}
        }

    }

}
