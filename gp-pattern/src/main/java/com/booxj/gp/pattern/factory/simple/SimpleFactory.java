package com.booxj.gp.pattern.factory.simple;

import com.booxj.gp.pattern.factory.Mengniu;
import com.booxj.gp.pattern.factory.Milk;
import com.booxj.gp.pattern.factory.Yili;

/**
 * 简单工厂，根据名称获取不同的实例
 */
public class SimpleFactory {

    public Milk getMilk(String name) {
        if ("蒙牛".equals(name)) {
            return new Mengniu();
        } else if ("伊利".equals(name)) {
            return new Yili();
        } else {
            System.out.println("不能生产您所需的产品");
            return null;
        }
    }
}
