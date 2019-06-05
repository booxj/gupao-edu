package com.booxj.gp.middleware.quartz.lesson7;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/5 14:15
 * @since
 */
public class TriggerListenerDemo implements TriggerListener {

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * (1) trigger 关联的 job 即将被执行
     *
     * @param trigger
     * @param context
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        System.out.println("triggerFired " + trigger.getJobKey().getName() + " : " + context.getFireTime());
    }

    /**
     * (2) 是否禁止执行, 返回TRUE 那么任务job会被终止
     *
     * @param trigger
     * @param context
     * @return
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        System.out.println("vetoJobExecution " + trigger.getJobKey().getName() + " : " + context.getFireTime());
        return false;
    }

    /**
     * (3) 当 Trigger 错过被激发时执行
     *
     * @param trigger
     */
    @Override
    public void triggerMisfired(Trigger trigger) {
        System.out.println("triggerMisfired" + trigger.getJobKey().getName() + " : " + trigger.getStartTime());
    }

    /**
     * (4) 任务完成时触发
     *
     * @param trigger
     * @param context
     * @param triggerInstructionCode
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        System.out.println("triggerComplete " + trigger.getJobKey().getName() + " : " + trigger.getStartTime());
    }
}
