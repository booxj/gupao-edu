package com.booxj.gp.middleware.quartz.lesson4;

import org.quartz.*;
import org.quartz.impl.calendar.HolidayCalendar;

/**
 * Triggers 介绍
 *
 * <p>Triggers的公告属性
 * jobKey
 * startTime
 * endTime
 * <p>优先级(priority)
 * <p>错过触发(misfire Instructions)
 * <p>日历示例(calendar)
 *
 * @author booxj
 * @create 2019/6/5 11:25
 * @since
 */
public class TriggerDescription {

    public static void main(String[] args) throws SchedulerException {

        SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        // 自定义一个Calendar，排除所有的商业节日和自定义时间
        HolidayCalendar cal = new HolidayCalendar();
//        cal.addExcludedDate( someDate );
//        cal.addExcludedDate( someOtherDate );
        scheduler.addCalendar("myHolidays", cal, false, false);

        Trigger t = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger")
                .forJob("myJob")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(9, 30)) // execute job daily at 9:30
                .modifiedByCalendar("myHolidays") // but not on holidays
                .build();

        Trigger t2 = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger2")
                .forJob("myJob2")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(11, 30)) // execute job daily at 11:30
                .modifiedByCalendar("myHolidays") // but not on holidays
                .build();

    }
}
