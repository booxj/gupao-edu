package com.booxj.gp.middleware.quartz.lesson2;

/**
 * Quartz API
 * <p>
 * Scheduler: 与调度程序交互的主要 API
 * Job: 被 scheduler 执行的任务需要继承这个 API
 * JobDetail: 用于定义 job 的实例
 * Trigger: 用于定义 scheduler 的计划
 * JobBuilder: 用于定义/构建 JobDetail 实例
 * TriggerBuilder: 用于定义/构建 Trigger 实例
 *
 * <p> Scheduler 的生命周期从被创建，一直到被执行 shutdown() 方法. Scheduler 被创建后可以 新增、删除、查询 Job 和 Trigger.
 * 但是只有 Scheduler 在调用 start() 方法后，才会真正的去触发 Trigger
 * <p> Quartz 提供了一些 “builder” 去实例化相关的类
 *
 * @author booxj
 * @create 2019/6/5 10:21
 * @see org.quartz.JobBuilder
 * @see org.quartz.TriggerBuilder
 * @see org.quartz.SimpleScheduleBuilder
 * @see org.quartz.CronScheduleBuilder
 * @see org.quartz.CalendarIntervalScheduleBuilder
 * @see org.quartz.DateBuilder
 * @since
 */
public class QuartzApi {

}
