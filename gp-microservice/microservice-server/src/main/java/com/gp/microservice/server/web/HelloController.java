package com.gp.microservice.server.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/12 10:04
 * @since
 */
@RestController
public class HelloController {

    @RequestMapping("hello")
    public Object hello(@RequestParam(value = "name", defaultValue = "booxj") String name) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3000);
        System.out.println(name);
        return "hello, " + name;
    }
}
