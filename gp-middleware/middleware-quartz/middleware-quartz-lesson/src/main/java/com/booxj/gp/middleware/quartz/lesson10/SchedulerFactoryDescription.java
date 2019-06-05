package com.booxj.gp.middleware.quartz.lesson10;

/**
 * SchedulerFactory Description
 * <p>Quartz 的几个主要组件
 * 线程池: 用于运行所有的job，如果 job 数量大于线程池大小，按照优先级(priority)执行 {@link org.quartz.simpl.SimpleThreadPool}
 * JobStore: {@link org.quartz.spi.JobStore}
 * DataSources（如有必要）
 * 计划程序本身: {@link  org.quartz.impl.StdSchedulerFactory} {@link  org.quartz.impl.DirectSchedulerFactory}
 *
 * @author booxj
 * @create 2019/6/5 15:15
 * @since
 */
public class SchedulerFactoryDescription {
}
