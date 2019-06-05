package com.booxj.gp.middleware.quartz.controller;

import com.booxj.gp.middleware.quartz.quartz.MyJob;
import com.booxj.gp.middleware.quartz.quartz.SchedulerUtil;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/5 16:53
 * @since
 */
@RestController
public class SchedulerController {

    @RequestMapping("start")
    public void start() {
        SchedulerUtil.startJob();
    }

    @RequestMapping("add")
    public void addJob() {
        SchedulerUtil.addJob("jobName", "group", "triggerName", "0/5 * * * * ?", MyJob.class);
    }

    @RequestMapping("pause")
    public void pause() throws SchedulerException {
        SchedulerUtil.pauseJob("jobName", "group");
    }

    @RequestMapping("resume")
    public void resume() throws SchedulerException {
        SchedulerUtil.resumeJob("jobName", "group");
    }

    @RequestMapping("delete")
    public void delete() throws SchedulerException {
        SchedulerUtil.deleteJob("jobName", "group");
    }
}
