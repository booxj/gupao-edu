package com.booxj.gp.middleware.quartz.job;

import org.quartz.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/5 10:51
 * @since
 */
public class DumbJob implements Job {

    String jobSays;
    float myFloatValue;
    ArrayList state;

    public DumbJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();

        JobDataMap dataMap = context.getMergedJobDataMap();  // Note the difference from the previous example

        // 如果有对应的set方法，则不需要手动获取
//        String jobSays = dataMap.getString("jobSays");
//        float myFloatValue = dataMap.getFloat("myFloatValue");
//        ArrayList state = (ArrayList) dataMap.get("myStateData");
        state.add(new Date());
        System.err.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
    }

    public void setJobSays(String jobSays) {
        this.jobSays = jobSays;
    }

    public void setMyFloatValue(float myFloatValue) {
        myFloatValue = myFloatValue;
    }

    public void setState(ArrayList state) {
        state = state;
    }
}
