package com.booxj.gp.middleware.quartz.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/5 17:02
 * @since
 */
public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(new Date());
    }
}
