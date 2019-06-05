package com.booxj.gp.middleware.quartz;

import com.booxj.gp.middleware.quartz.job.HelloJob;
import com.booxj.gp.middleware.quartz.lesson7.JobListenerDemo;
import com.booxj.gp.middleware.quartz.lesson7.TriggerListenerDemo;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.CountDownLatch;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/5 14:47
 * @since
 */
public class ListenerTest {

    @Test
    public void Listener() throws SchedulerException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.getListenerManager().addJobListener(new JobListenerDemo());
        scheduler.getListenerManager().addTriggerListener(new TriggerListenerDemo());
        scheduler.getListenerManager().addJobListener(new JobListenerDemo());
        scheduler.start();
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("listener_job", "test_group")
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("test_trigger", "test_group")
                .withSchedule(cronSchedule("0/10 * * * * ?"))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

        latch.await();
    }
}
