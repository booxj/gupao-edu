package com.gp.mircoservice.client.cloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/12 10:02
 * @since
 */
@Component
@FeignClient(name = "zk-server", fallback = FeignServiceHystric.class)
public interface FeignService {

    @GetMapping("/hello")
    String hello(@RequestParam("name") String name);
}
