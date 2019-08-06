package com.booxj.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlDemo {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        User user = context.getBean(User.class);
//        System.out.println(user);

        AClass a = context.getBean(AClass.class);
        BClass b = context.getBean(BClass.class);

        System.out.println(a + "\n" + b);
    }
}
