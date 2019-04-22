package com.booxj.gp.pattern.factory.abstr;

import com.booxj.gp.pattern.factory.Milk;

/**
 * 抽象工厂方法：
 */
public abstract class AbstractFactory {

    abstract Milk getYili();

    abstract Milk getMengniu();
}
