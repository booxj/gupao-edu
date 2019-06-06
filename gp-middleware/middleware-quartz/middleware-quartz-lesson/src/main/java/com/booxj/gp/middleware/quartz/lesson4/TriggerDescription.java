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
 * <pre>
 *     CronTrigger
 *      - withMisfireHandlingInstructionDoNothing   不触发立即执行;等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
 *      - withMisfireHandlingInstructionIgnoreMisfires  以错过的第一个频率时间立刻开始执行;重做错过的所有频率周期后;当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
 *      - withMisfireHandlingInstructionFireAndProceed  以当前时间为触发频率立刻触发一次执行;然后按照Cron频率依次执行
 *     SimpleTrigger
 *      - withMisfireHandlingInstructionFireNow 以当前时间为触发频率立即触发执行;执行至FinalTIme的剩余周期次数,以调度或恢复调度的时刻为基准的周期频率，FinalTime根据剩余次数和当前时间计算得到;调整后的FinalTime会略大于根据starttime计算的到的FinalTime值
 *      - withMisfireHandlingInstructionIgnoreMisfires  以错过的第一个频率时间立刻开始执行;重做错过的所有频率周期;当下一次触发频率发生时间大于当前时间以后，按照Interval的依次执行剩下的频率;共执行RepeatCount+1次
 *      - withMisfireHandlingInstructionNextWithExistingCount   不触发立即执行;等待下次触发频率周期时刻，执行至FinalTime的剩余周期次数;以startTime为基准计算周期频率，并得到FinalTime;即使中间出现pause，resume以后保持FinalTime时间不变
 *      - withMisfireHandlingInstructionNowWithExistingCount    以当前时间为触发频率立即触发执行;执行至FinalTIme的剩余周期次数;以调度或恢复调度的时刻为基准的周期频率，FinalTime根据剩余次数和当前时间计算得到;调整后的FinalTime会略大于根据starttime计算的到的FinalTime值
 *      - withMisfireHandlingInstructionNextWithRemainingCount  不触发立即执行;等待下次触发频率周期时刻，执行至FinalTime的剩余周期次数;以startTime为基准计算周期频率，并得到FinalTime;即使中间出现pause，resume以后保持FinalTime时间不变
 *      - withMisfireHandlingInstructionNowWithRemainingCount   以当前时间为触发频率立即触发执行;执行至FinalTIme的剩余周期次数;以调度或恢复调度的时刻为基准的周期频率，FinalTime根据剩余次数和当前时间计算得到
 * </pre>
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
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(9, 30).withMisfireHandlingInstructionDoNothing()) // execute job daily at 9:30
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
