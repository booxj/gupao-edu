package com.booxj.gp.pattern.observer.subject;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class SubjectProxy implements MethodInterceptor {

    public Object getSubjectProxy(Class<?> clazz) throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("通过动态代理执行方法:" + method.getName());
        System.out.println("doing something...");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println(method.getName() + "方法执行结束");
        return result;
    }
}
