package com.booxj.spring;

import com.booxj.spring.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlAopDemo {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext-aop.xml");
        UserService userService = (UserService) context.getBean("userService");
//        userService.getUser();
        userService.throwException();
    }
}
