package com.booxj.gp.middleware.quartz.lesson6;

import org.quartz.Trigger;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * CronTrigger
 * <p>CronTrigger 基于日历的概念，即 cron 表达式
 * <p>Cron Expressions,由七个子表达式组成的字符串
 * 1.Seconds
 * 2.Minutes
 * 3.Hours
 * 4.Day-of-Month
 * 5.Month
 * 6.Day-of-Week
 * 7.Year (optional field)
 *
 * @author booxj
 * @create 2019/6/5 13:55
 * @since
 */
public class CronTriggerDemo {

    private static Trigger trigger;

    // 每天上午8点至下午5点之间,每隔一分钟执行一次
    public static void main(String[] args) {
        trigger = newTrigger()
                .withIdentity("trigger3", "group1")
                .withSchedule(cronSchedule("0 0/2 8-17 * * ?"))
                .forJob("myJob", "group1")
                .build();
    }
}
