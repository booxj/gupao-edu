package com.gp.mircoservice.client.customized.service;

import com.gp.mircoservice.client.customized.annotation.RestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/12 10:57
 * @since
 */
@RestClient(name = "zk-server")
public interface RestService {

    @GetMapping("hello")
    String hello(@RequestParam("name") String name);
}
