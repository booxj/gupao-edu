package com.booxj.gp.pattern.proxy.cglib;

public class CglibTest {

    public static void main(String[] args) throws Throwable {

        ZhangSan obj = (ZhangSan) new CglibMeipo().getInstance(ZhangSan.class);

        obj.findLove();
    }
}
