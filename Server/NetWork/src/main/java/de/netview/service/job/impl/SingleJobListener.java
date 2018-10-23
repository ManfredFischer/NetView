package de.netview.service.job.impl;

import java.util.Iterator;
import java.util.Set;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;


public class SingleJobListener implements JobListener {

    @Override
    public String getName() {
        return "SingleJobListener";
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext arg0) {
    	System.out.println("vetoed");
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext arg0) {
        System.out.println("tobeexecuted: " + arg0.getJobDetail().getKey().getName());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException arg1) {
        try {
            // We are done with a job which has been saved as durable because we intended to launch it later. Since we are now
            // done with the job we can force deletion
            if (jobExecutionContext.getJobDetail().isDurable()) {
                jobExecutionContext.getScheduler().deleteJob(jobExecutionContext.getJobDetail().getKey());
            }
            Set<JobKey> groupJobKeys = jobExecutionContext.getScheduler().getJobKeys(
                    GroupMatcher.jobGroupEquals(jobExecutionContext.getJobDetail().getKey().getGroup()));
            Iterator<JobKey> groupJobKeysIterator = groupJobKeys.iterator();

            JobKey nextRunningJob = null;
            Long minId = null;

            if (groupJobKeys.size() > 0) {
                // There are still jobs in this group left
                JobDetail jobDetail = null;
                while (groupJobKeysIterator.hasNext()) {
                    JobKey jobKey = groupJobKeysIterator.next();
                    jobDetail = jobExecutionContext.getScheduler().getJobDetail(jobKey);
                    Long id = (Long) jobExecutionContext.getScheduler().getJobDetail(jobKey).getJobDataMap().get("id");
                    // ignore job wth the same name
                    // choose job when was stored permanently (status = waitung)
                    // choose job when minId is not set or jobId is before minId found so far
                    if (!jobKey.getName().equalsIgnoreCase(jobExecutionContext.getJobDetail().getKey().getName())
                            && jobDetail.isDurable() && (minId == null || id < minId)) {
                        minId = id;
                        nextRunningJob = jobKey;
                    }
                }

                if (nextRunningJob != null) {
                    if (jobExecutionContext.getScheduler().checkExists(
                            new TriggerKey(nextRunningJob.getName(), nextRunningJob.getGroup()))) {
                        return;
                    }

                    Trigger trigger = org.quartz.TriggerBuilder.newTrigger()
                            .withIdentity(nextRunningJob.getName(), nextRunningJob.getGroup()).forJob(nextRunningJob)
                            .startNow().build();
                    jobExecutionContext.getScheduler().scheduleJob(trigger);

                }
            }
        } catch (Exception exp) {
            
        }
    }

}
