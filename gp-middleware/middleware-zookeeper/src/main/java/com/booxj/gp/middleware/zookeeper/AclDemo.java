package com.booxj.gp.middleware.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

public class AclDemo {

    private static String CONNECTION_STR = "localhost:2181,localhost:2182,localhost:2183";

    public static void main(String[] args) {

    }

    private static void digestDemo() throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(CONNECTION_STR).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        curatorFramework.start();

        List<ACL> aclList = new ArrayList<>();
        ACL acl = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE, new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin")));
        aclList.add(acl);
        curatorFramework.create().withMode(CreateMode.PERSISTENT).withACL(aclList).forPath("/auth");
    }

}
