package com.booxj.gp.pattern.observer.subject;


import com.booxj.gp.pattern.observer.core.Event;

import java.lang.reflect.Method;

/**
 * 观察者模式，所有监听器持有观察者，在执行事件时通知观察者
 */
public class ObserverTest {

    public static void main(String[] args) {

        try {
            //观察者
            Observer observer = new Observer();
            Method advice = Observer.class.getMethod("advice", new Class<?>[]{Event.class});

            Subject subject = new Subject();
            subject.addLisenter(SubjectEventType.ON_ADD, observer, advice);
            subject.addLisenter(SubjectEventType.ON_EDIT, observer, advice);
            subject.addLisenter(SubjectEventType.ON_RMOVE, observer, advice);
            subject.addLisenter(SubjectEventType.ON_QUERY, observer, advice);

            subject.add();
            subject.remove();

            System.out.println("============================分割线===============================");
            //通过动态代理进行监控，减少代码入侵
            Subject proxy = (Subject) new SubjectProxy().getSubjectProxy(Subject.class);

            proxy.add();
            proxy.remove();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
