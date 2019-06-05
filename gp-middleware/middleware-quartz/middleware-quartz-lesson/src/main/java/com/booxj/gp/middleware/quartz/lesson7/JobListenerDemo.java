package com.booxj.gp.middleware.quartz.lesson7;

import org.quartz.*;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/5 14:15
 * @since
 */
public class JobListenerDemo implements JobListener {


    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * jobDetail 即将被执行
     *
     * @param context
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("jobToBeExecuted :" + context.getFireTime());
    }

    /**
     * JobDetail 即将被执行，但被 TriggerListener 否决
     * @param context
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("jobExecutionVetoed :" + context.getFireTime());
    }

    /**
     * 在 JobDetail 被执行之后调用
     * @param context
     * @param jobException
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("jobWasExecuted :" + context.getFireTime());
    }
}
