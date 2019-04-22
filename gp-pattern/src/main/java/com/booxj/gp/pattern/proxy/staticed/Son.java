package com.booxj.gp.pattern.proxy.staticed;


import com.booxj.gp.pattern.proxy.Person;

public class Son implements Person {

    @Override
    public void findLove() {
        // 我没有时间
        System.out.println("son：找对象，肤白貌美大长腿");
    }

    @Override
    public void findJob() {
        // 我没有时间
        System.out.println("son:找工作，活少钱多");
    }
}
