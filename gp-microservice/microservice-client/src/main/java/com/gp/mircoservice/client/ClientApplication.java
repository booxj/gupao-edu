package com.gp.mircoservice.client;

import com.gp.mircoservice.client.cloud.service.FeignService;
import com.gp.mircoservice.client.customized.annotation.EnableRestClient;
import com.gp.mircoservice.client.customized.service.RestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author booxj
 * @create 2019/6/12 9:19
 * @since
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(clients = FeignService.class) // 引入 @FeignClient
@EnableHystrixDashboard     // 开启 Hystrix 仪表盘
@EnableRestClient(clients = RestService.class) // 引入 @RestClient
@EnableScheduling
public class ClientApplication {

    /**
     * @see com.gp.mircoservice.client.cloud        整合hystrix，feign实现熔断和负载均衡
     * @see com.gp.mircoservice.client.customized   自己实现熔断和负载均衡
     */
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
