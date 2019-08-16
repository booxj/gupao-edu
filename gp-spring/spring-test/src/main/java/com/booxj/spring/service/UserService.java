package com.booxj.spring.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void getUser(){
        System.out.println("get user success");
    }

    public void throwException(){
        throw new RuntimeException("抛出一个异常");
    }

}
