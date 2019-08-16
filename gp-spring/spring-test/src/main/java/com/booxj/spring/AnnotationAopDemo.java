package com.booxj.spring;

import com.booxj.spring.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationAopDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.booxj.spring.service");
        UserService userService = context.getBean(UserService.class);
        userService.getUser();
    }
}
