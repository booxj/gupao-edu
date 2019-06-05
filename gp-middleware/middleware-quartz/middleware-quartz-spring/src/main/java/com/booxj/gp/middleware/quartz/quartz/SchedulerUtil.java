package com.booxj.gp.middleware.quartz.quartz;

import org.quartz.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/5 16:54
 * @since
 */
public class SchedulerUtil {

    private static Scheduler scheduler = MySchedulerFactory.getSchedulerInstance();

    public static void addJob(String jobName, String Group, String triggerName, String cron, Class<? extends Job> clazz) {
        try {
            Trigger trigger = newTrigger()
                    .withIdentity(triggerName, Group)
                    .withSchedule(cronSchedule(cron))
                    .build();

            JobDetail jobDetail = JobBuilder.newJob(clazz)
                    .withIdentity(jobName, Group)
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {

        }
    }

    public static void startJob() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {

        }
    }

    public static void pauseJob(String jobKey,String jobGroup ) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobKey,jobGroup));
    }

    public static void resumeJob(String jobKey,String jobGroup) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jobKey,jobGroup));
    }

    public static void deleteJob(String jobKey,String jobGroup) throws SchedulerException {
        scheduler.deleteJob(JobKey.jobKey(jobKey,jobGroup));
    }
}
