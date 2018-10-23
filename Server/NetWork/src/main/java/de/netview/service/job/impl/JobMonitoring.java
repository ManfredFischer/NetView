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
        startJobMonitoringJob();        
    }

    /**
     * Method is used to start a quartz job which monitors current joblist and launches jobs if necessary.
     * The job is then automatically rescheduled every 60 seconds.
     */
    private void startJobMonitoringJob() {

        Trigger trigger = null;

        try {

            Long creationTime = System.currentTimeMillis();
            String name = "JobMonitor_Hardware";
            String group = "SystemJob";

            // We only add a new trigger if none exists
            if (!scheduler.checkExists(new TriggerKey(name, group))) {
                JobDetail job = JobBuilder.newJob(CheckHardwareJob.class).withIdentity(name, group)
                        .usingJobData("creationTime", creationTime).build();

                trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(60).repeatForever()) // Intervall
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
