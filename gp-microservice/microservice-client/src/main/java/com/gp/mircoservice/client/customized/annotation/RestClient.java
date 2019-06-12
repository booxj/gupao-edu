package com.gp.mircoservice.client.customized.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestClient {

    /**
     * REST 服务应用名称
     * @return
     */
    String name();
}
