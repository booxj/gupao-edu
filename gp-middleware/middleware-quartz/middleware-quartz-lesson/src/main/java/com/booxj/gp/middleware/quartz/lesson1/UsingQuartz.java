package com.booxj.gp.middleware.quartz.lesson1;

import com.booxj.gp.middleware.quartz.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 使用 Quartz,基于 2.3.0 版本
 * 官方推荐使用工厂方法创建 {@link JobDetail} 和 {@link Trigger}
 * <p>
 *
 * @author booxj
 * @create 2019/6/5 10:07
 * @since
 */
public class UsingQuartz {

    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();

        // define the job and tie it to our HelloJob class
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob", "group1")
                .build();

        // Trigger the job to run now, and then every 10 seconds
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
