package com.booxj.gp.middleware.quartz.lesson5;

import com.booxj.gp.middleware.quartz.job.HelloJob;
import org.quartz.*;

import java.util.Date;

/**
 * Simple Trigger
 * <p>SimpleTrigger,可以指定在具体的时间点执行一次，或者在具体的时间点执行，并且以指定的间隔重复执行若干次
 *
 * @author booxj
 * @create 2019/6/5 13:25
 * @since
 */
public class SimpleTriggerDemo {

    public static void main(String[] args) {

        // define the job and tie it to our HelloJob class
        JobDetail myJob = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob", "group1")
                .build();

        // 指定时间开始触发，不重复
        Date myStartTime = new Date();
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(myStartTime)                     // some Date
                .forJob("job1", "group1")                 // identify job with name, group strings
                .build();

        // 指定时间触发，每隔10秒执行一次，重复10次
        Date myTimeToStartFiring = new Date();
        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger3", "group1")
                .startAt(myTimeToStartFiring)  // if a start time is not given (if this line were omitted), "now" is implied
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(10)) // note that 10 repeats will give a total of 11 firings
                .forJob(myJob) // identify job with handle to its JobDetail itself
                .build();

        // 5分钟以后开始触发，仅执行一次
        String myJobKey = myJob.getKey().getName();
        trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger5", "group1")
                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.MINUTE)) // use DateBuilder to create a date in the future
                .forJob(myJobKey) // identify job with its JobKey
                .build();

        // 立即触发，每个5分钟执行一次，直到22:00
        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger7", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(5)
                        .repeatForever())
                .endAt(DateBuilder.dateOf(22, 0, 0))
                .build();

        // 将在下一个小时的整点触发，然后每2小时重复一次
        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger8") // because group is not specified, "trigger8" will be in the default group
                .startAt(DateBuilder.evenHourDate(null)) // get the next even-hour (minutes and seconds zero ("00:00"))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(2)
                        .repeatForever())
                // note that in this example, 'forJob(..)' is not called which is valid
                // if the trigger is passed to the scheduler along with the job
                .build();

    }
}
