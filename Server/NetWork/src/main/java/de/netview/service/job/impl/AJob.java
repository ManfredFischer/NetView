package de.netview.service.job.impl;

import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.springframework.security.core.userdetails.UserDetailsService;

public abstract class AJob implements Job {

    public static long startTime;
    
    public static void interruptThread(String msg) {
        
    }

    protected Long id;
    protected boolean jobExist;
    protected Long languageId;
    protected Long organisationUnitId;
    protected Long userId;
    private UserDetailsService userDetailsService;
    private JobKey jobKey;
    protected boolean interrupted;
    private volatile Thread jobThread;

    public AJob() {
        
    }

    public AJob(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected void prepareEnvironment(Long userId, Long organisationUnitId, Long languageId) {
   
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        jobThread = Thread.currentThread();
        jobKey = context.getJobDetail().getKey();
        prepareEnvironment(userId, organisationUnitId, languageId);
    }

   
}
