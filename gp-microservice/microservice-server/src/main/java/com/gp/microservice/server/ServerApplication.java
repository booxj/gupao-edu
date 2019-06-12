package com.gp.microservice.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 使用zookeeper实现服务的注册与发现
 * <p>
 *
 * @author booxj
 * @create 2019/6/12 8:48
 * @since
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
