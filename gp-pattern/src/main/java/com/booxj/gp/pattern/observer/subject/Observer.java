package com.booxj.gp.pattern.observer.subject;


import com.booxj.gp.pattern.observer.core.Event;


public class Observer {

    public void advice(Event e) {
        System.out.println("=========触发事件，打印日志========\n" + e);
    }
}
