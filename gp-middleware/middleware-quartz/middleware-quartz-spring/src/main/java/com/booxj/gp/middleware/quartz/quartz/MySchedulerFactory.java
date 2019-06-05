package com.booxj.gp.middleware.quartz.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/5 16:50
 * @since
 */
public class MySchedulerFactory {

    private volatile static Scheduler scheduler;

    public static Scheduler getSchedulerInstance() {

        if (scheduler == null) {
            synchronized (MySchedulerFactory.class) {
                try {
                    scheduler = new StdSchedulerFactory().getScheduler();
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }
        }
        return scheduler;
    }
}
