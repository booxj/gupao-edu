package com.booxj.gp.pattern.factory.func;


import com.booxj.gp.pattern.factory.Mengniu;
import com.booxj.gp.pattern.factory.Milk;

public class MengniuFactory implements FuncFactory{

    @Override
    public Milk getMilk() {
        return new Mengniu();
    }
}
