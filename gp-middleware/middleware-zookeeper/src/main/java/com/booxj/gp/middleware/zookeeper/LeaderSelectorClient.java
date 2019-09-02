package com.booxj.gp.middleware.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * zookeeper可以用于其他中间件集群进行leader选举，如spark,kafka
 * <p>使用curator实现leader选择的demo</p>
 */
public class LeaderSelectorClient extends LeaderSelectorListenerAdapter implements Closeable {

    private String name;  //表示当前的进程
    private LeaderSelector leaderSelector;  //leader选举的API
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public LeaderSelectorClient(String name) {
        this.name = name;
    }

    public void setLeaderSelector(LeaderSelector leaderSelector) {
        this.leaderSelector = leaderSelector;
    }

    public void start() {
        leaderSelector.start(); //开始竞争leader
    }

    private static String CONNECTION_STR = "localhost:2181,localhost:2182,localhost:2183";

    public static void main(String[] args) throws IOException {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(CONNECTION_STR).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        curatorFramework.start();

        LeaderSelectorClient leaderSelectorClient = new LeaderSelectorClient("ClientA");
        LeaderSelector leaderSelector = new LeaderSelector(curatorFramework, "/leader", leaderSelectorClient);
        leaderSelectorClient.setLeaderSelector(leaderSelector);
        leaderSelectorClient.start(); //开始选举
        System.in.read();
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }

    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
        System.out.println(name + " -> 现在是leader了");
        countDownLatch.await(); //阻塞当前的进程防止leader丢失
    }
}
