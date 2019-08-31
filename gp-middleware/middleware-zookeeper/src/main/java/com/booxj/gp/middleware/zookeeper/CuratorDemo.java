package com.booxj.gp.middleware.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorDemo {

    private static String CONNECTION_STR = "localhost:2181,localhost:2182,192.168.13.104:2183";

    public static void main(String[] args) {
        CuratorFramework curator = CuratorFrameworkFactory.builder()
                .connectString(CONNECTION_STR)      //连接地址
                .sessionTimeoutMs(5000)             //超时时间
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))  //重试策略
                // ExponentialBackoffRetry 衰减重试
                // RetryOneTime 只重试一次
                // RetryUntilElapsed
                // RetryNTimes
                .build();

        curator.start();
    }

    private static void create(CuratorFramework curatorFramework) throws Exception {
        curatorFramework.create()
                //zookeeper默认只能一层一层创建节点，creatingParentsIfNeeded()方法帮我们自动创建父节点
                .creatingParentsIfNeeded()
                //PERSISTENT    持久节点
                //PERSISTENT_SEQUENTIAL 持久有序节点
                //EPHEMERAL     临时节点
                //EPHEMERAL_SEQUENTIAL  临时有序节点
                //CONTAINER     容器节点（节点中最后一个子节点被删除，容器节点被删除）
                //PERSISTENT_WITH_TTL   持久定时节点
                //PERSISTENT_SEQUENTIAL_WITH_TTL    持久顺序定时节点
                .withMode(CreateMode.PERSISTENT)
                .forPath("/data/program", "test".getBytes());
    }

    private static void update(CuratorFramework curatorFramework) throws Exception {
        curatorFramework.setData().forPath("/data/program", "update".getBytes());
    }

    private static void delete(CuratorFramework curatorFramework) throws Exception {
        Stat stat = new Stat();
        String value = new String(curatorFramework.getData().storingStatIn(stat).forPath("/data/program"));
        curatorFramework.delete().withVersion(stat.getVersion()).forPath("/data/program");
    }
}
