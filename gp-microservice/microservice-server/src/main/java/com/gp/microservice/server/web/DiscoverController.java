package com.gp.microservice.server.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/12 8:51
 * @since
 */
@RestController
public class DiscoverController {

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 服务列表
     * @return
     */
    @RequestMapping("services")
    public List services() {
        return discoveryClient.getServices();
    }

    /**
     * 服务列表
     * @param instance
     * @return
     */
    @RequestMapping("services/{instance}")
    public List instance(@PathVariable("instance") String instance) {
        return discoveryClient.getInstances(instance).stream()
                .map(s ->
                        {
                            Map<String, Object> map = new HashMap<>();
                            map.put("serviceId", s.getInstanceId());
                            map.put("host", s.getHost());
                            map.put("port", s.getPort());
                            return map;
                        }
                ).collect(Collectors.toList());
    }
}
