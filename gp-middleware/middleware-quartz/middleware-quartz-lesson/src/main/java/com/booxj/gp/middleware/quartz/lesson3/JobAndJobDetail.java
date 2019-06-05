package com.booxj.gp.middleware.quartz.lesson3;

import com.booxj.gp.middleware.quartz.job.DumbJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

/**
 * Job与 JobDetail 介绍
 * <p>
 * JobDataMap: 如果需要给job实例增加属性或配置，需要使用 JobDataMap {@link DumbJob}，如果使用的是持久化的存储机制，需要注意序列化不一致的问题
 * Job实例: 可以创建一个 Job, 然后创建多个与该 Job 关联的 JobDetail 实例，每一个实例都有自己的属性集和 JobDataMap，最后所有的实例都加载到 scheduler 中
 * Job状态与并发:
 * <pre>
 *  {@link org.quartz.DisallowConcurrentExecution} 不要并发地执行同一个job定义
 *  {@link  org.quartz.PersistJobDataAfterExecution} 成功执行了job类的execute方法后,更新JobDetail中JobDataMap的数据
 *  以上两个注解都是针对 JobDetail，而且强烈建议不用或者同时使用
 * </pre>
 * Job的其它特性: Durability 和 RequestsRecovery
 * JobExecutionException:
 *
 * @author booxj
 * @create 2019/6/5 10:55
 * @since
 */
public class JobAndJobDetail {

    public static void main(String[] args) {
        // define the job and tie it to our DumbJob class
        JobDetail jobDetail = JobBuilder.newJob(DumbJob.class)
                .withIdentity("myJob", "group1")
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.14f)
                .build();
    }
}
