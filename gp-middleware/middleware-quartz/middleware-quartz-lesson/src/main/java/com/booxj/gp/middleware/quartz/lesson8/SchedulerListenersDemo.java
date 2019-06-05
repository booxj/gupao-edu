package com.booxj.gp.middleware.quartz.lesson8;

import org.quartz.*;

import java.util.Date;

/**
 * TODO description
 * <p>
 *
 * @see SchedulerListener
 * @author booxj
 * @create 2019/6/5 14:34
 * @since
 */
public class SchedulerListenersDemo implements SchedulerListener{

    @Override
    public void jobScheduled(Trigger trigger) {
        System.out.println("jobScheduled" + " : " + new Date());
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        System.out.println("jobUnscheduled" + " : " + new Date());
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        System.out.println("triggerFinalized" + " : " + new Date());
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        System.out.println("triggerPaused" + " : " + new Date());
    }

    @Override
    public void triggersPaused(String triggerGroup) {
        System.out.println("triggersPaused" + " : " + new Date());
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        System.out.println("triggerResumed" + " : " + new Date());
    }

    @Override
    public void triggersResumed(String triggerGroup) {
        System.out.println("triggersResumed" + " : " + new Date());
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        System.out.println("jobAdded" + " : " + new Date());
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        System.out.println("jobDeleted" + " : " + new Date());
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        System.out.println("jobPaused" + " : " + new Date());
    }

    @Override
    public void jobsPaused(String jobGroup) {
        System.out.println("jobsPaused" + " : " + new Date());
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        System.out.println("jobResumed" + " : " + new Date());
    }

    @Override
    public void jobsResumed(String jobGroup) {
        System.out.println("jobsResumed" + " : " + new Date());
    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {
        System.out.println("schedulerError" + " : " + new Date());
    }

    @Override
    public void schedulerInStandbyMode() {
        System.out.println("schedulerInStandbyMode" + " : " + new Date());
    }

    @Override
    public void schedulerStarted() {
        System.out.println("schedulerStarted" + " : " + new Date());
    }

    @Override
    public void schedulerStarting() {
        System.out.println("schedulerStarting" + " : " + new Date());
    }

    @Override
    public void schedulerShutdown() {
        System.out.println("schedulerShutdown" + " : " + new Date());
    }

    @Override
    public void schedulerShuttingdown() {
        System.out.println("schedulerShuttingdown" + " : " + new Date());
    }

    @Override
    public void schedulingDataCleared() {
        System.out.println("schedulingDataCleared" + " : " + new Date());
    }
}
