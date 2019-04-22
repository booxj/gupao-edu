package com.booxj.gp.pattern.observer.subject;


import com.booxj.gp.pattern.observer.core.EventListenter;

public class Subject extends EventListenter {

    //通常的话，采用动态代理来实现监控，避免了代码侵入
    public void add() {
        System.out.println("调用添加的方法");
        trigger(SubjectEventType.ON_ADD);
    }

    public void remove() {
        System.out.println("调用删除的方法");
        trigger(SubjectEventType.ON_RMOVE);
    }

}
