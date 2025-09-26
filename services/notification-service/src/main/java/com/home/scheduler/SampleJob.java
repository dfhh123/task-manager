package com.home.scheduler;

import com.home.service.MailService;
import lombok.AllArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Sample scheduled job for sending email notifications.
 * 
 * <p>This job extends QuartzJobBean and can be scheduled to run
 * at specific intervals to send automated email notifications.
 */
@AllArgsConstructor
public class SampleJob extends QuartzJobBean {

    private final MailService mailService;

    /**
     * Executes the scheduled job.
     * 
     * @param context the job execution context
     * @throws JobExecutionException if job execution fails
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // Implementation for scheduled email sending
    }
}
