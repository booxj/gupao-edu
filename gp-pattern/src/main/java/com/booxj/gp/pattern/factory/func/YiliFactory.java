package com.booxj.gp.pattern.factory.func;


import com.booxj.gp.pattern.factory.Milk;
import com.booxj.gp.pattern.factory.Yili;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-05-05 9:52
 * @version:
 **/
public class YiliFactory implements FuncFactory {

    @Override
    public Milk getMilk() {
        return new Yili();
    }
}
