package com.booxj.gp.pattern.singleton.lazy;

/**
 * 主流单例模式：利用内部类优先加载的特性
 */
public class LazyThree {

    private boolean initialized = false;

    // 防止反序列化
    private LazyThree() {
        synchronized (LazyThree.class) {
            if (!initialized) {
                initialized = true;
            } else {
                throw new RuntimeException("单例已被侵犯");
            }
        }
    }

    public static final LazyThree getInstance() {
        // 在返回结果以前，一定会先加载内部类
        return LazyHolder.LAZY;
    }

    // 防止通过反序列化的方式创建新实例
    private Object readResolve(){
        return getInstance();
    }

    private static class LazyHolder {
        private static final LazyThree LAZY = new LazyThree();
    }
}
