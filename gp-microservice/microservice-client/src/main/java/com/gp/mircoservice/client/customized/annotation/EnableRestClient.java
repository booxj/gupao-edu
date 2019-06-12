package com.gp.mircoservice.client.customized.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RestClientsRegistrar.class)
public @interface EnableRestClient {

    Class<?>[] clients() default {};

}
