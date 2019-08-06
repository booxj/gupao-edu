package com.booxj.spring;

import com.booxj.spring.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AnnotationDemo {

    @Bean
    public User user(){
        User user = new User();
        user.setId(1);
        user.setName("booxj");
        return user;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.booxj.spring");
        User user = context.getBean(User.class);
        System.out.println(user);
    }
}
