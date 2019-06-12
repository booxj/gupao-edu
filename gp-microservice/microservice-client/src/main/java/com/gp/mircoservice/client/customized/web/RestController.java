package com.gp.mircoservice.client.customized.web;

import com.gp.mircoservice.client.customized.annotation.CircuitBreaker;
import com.gp.mircoservice.client.customized.annotation.CustomizedLoadBalanced;
import com.gp.mircoservice.client.customized.loadbalance.LoadBalanceRequestInterceptor;
import com.gp.mircoservice.client.customized.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/12 10:47
 * @since
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("rest")
public class RestController {

    @Autowired
    private RestService restService;

    @CircuitBreaker(timeout = 1000)
    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        return restService.hello(name);
    }

    /**
     * 定义 RestTemplate 拦截器，实现负载均衡
     * @return
     */
    @Bean
    public ClientHttpRequestInterceptor interceptor() {
        return new LoadBalanceRequestInterceptor();
    }

    /**
     * 定义注解 {@link CustomizedLoadBalanced}，以免注入 RestTemplate 时发生冲突
     * @param interceptor
     * @return
     */
    @Bean
    @CustomizedLoadBalanced
    public RestTemplate lbRestTemplate(ClientHttpRequestInterceptor interceptor) {
        RestTemplate restTemplate = new RestTemplate();

        // 增加拦截器
        restTemplate.setInterceptors(Arrays.asList(interceptor));
        return restTemplate;
    }
}
