package com.booxj.gp.pattern.singleton.lazy;

/**
 * 懒汉式单例
 * <p>
 * 它是在类首次调用的时候实例化
 * <p>
 * 没有加锁控制时，可能会出现多个实例
 */
public class LazyOne {

    private static LazyOne instance = null;

    private LazyOne() {
    }

    public static LazyOne getInstance() {
        if (instance == null) {
            instance = new LazyOne();
        }
        return instance;
    }
}
