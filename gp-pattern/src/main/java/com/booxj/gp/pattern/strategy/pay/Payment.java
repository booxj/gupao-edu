package com.booxj.gp.pattern.strategy.pay;


import com.booxj.gp.pattern.strategy.PayState;

public interface Payment {

    PayState pay(String uid, double amount);
}
