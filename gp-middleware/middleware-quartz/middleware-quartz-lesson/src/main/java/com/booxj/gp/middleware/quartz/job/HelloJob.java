package com.booxj.gp.middleware.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/5 10:10
 * @since
 */
public class HelloJob implements Job {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 创建 JobDetail 的时候，会把 Job 的类名传给 JobDetail, 每当 scheduler 执行 job 时，在调用 execute(...) 方法前会创建一个新的实例；执行结束后对该实例的引用被丢弃
     * 所以， job 必须有一个无参的构造函数，且不应该定义有状态的数据属性，如果需要，可以使用 JobDataMap {@link org.quartz.JobDataMap}
     * @see DumbJob
     */
    public HelloJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println(Thread.currentThread().getName() + " : " + simpleDateFormat.format(new Date()) + " : execute HelloJob.execute()");
    }

    public static void main(String[] args) {
        int[] nums = {2,2,1};
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.get(nums[i])==null){
                map.put(nums[i],1);
            }else{
                System.out.println(i);
            }
        }
    }
}
