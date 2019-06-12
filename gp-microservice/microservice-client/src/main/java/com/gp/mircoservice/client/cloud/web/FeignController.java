package com.gp.mircoservice.client.cloud.web;

import com.gp.mircoservice.client.cloud.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/12 10:07
 * @since
 */
@RestController
@RequestMapping("feign")
public class FeignController {

    @Autowired
    private FeignService feignService;

    @GetMapping("/hello")
    public String hello(@RequestParam String message) {
        return feignService.hello(message);
    }
}
