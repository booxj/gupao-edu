package com.booxj.gp.pattern.singleton.lazy;

/**
 * 懒汉式单例：通过加锁保证单例
 * synchronized是重锁，获得锁和释放锁时会比较消耗资源
 */
public class LazyTwo {

    private static LazyTwo instance = null;

    private LazyTwo() {
    }

    public static synchronized LazyTwo getInstance() {
        if (instance == null) {
            instance = new LazyTwo();
        }
        return instance;
    }
}
