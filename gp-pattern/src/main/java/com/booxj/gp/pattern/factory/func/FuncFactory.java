package com.booxj.gp.pattern.factory.func;

import com.booxj.gp.pattern.factory.Milk;

/**
 * 工厂方法模式：又称多态性工厂模式
 *
 * 核心的工厂类不再负责所有的产品的创建，而是将具体创建的工作交给子类去做。该核心类成为一个抽象工厂角色，仅负责给出具体工厂子类必须实现的接口，而不接触哪一个产品类应当被实例化这种细节
 *
 * 缺点：使用者不知道有几种实现类
 */
public interface FuncFactory {

    Milk getMilk();
}
