package com.booxj.gp.pattern.factory.abstr;

import com.booxj.gp.pattern.factory.Mengniu;
import com.booxj.gp.pattern.factory.Milk;
import com.booxj.gp.pattern.factory.Yili;


public class MilkFactory extends AbstractFactory{

    @Override
    Milk getYili() {
        return new Yili();
    }

    @Override
    Milk getMengniu() {
        return new Mengniu();
    }
}
