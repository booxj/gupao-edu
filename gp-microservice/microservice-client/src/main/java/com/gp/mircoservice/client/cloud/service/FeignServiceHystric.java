package com.gp.mircoservice.client.cloud.service;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/12 10:42
 * @since
 */
public class FeignServiceHystric implements FeignService {

    @Override
    public String hello(String name) {
        // TODO: 降级服务
        return "trigger hystric";
    }
}
