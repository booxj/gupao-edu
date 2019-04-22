package com.booxj.gp.pattern.strategy;


import com.booxj.gp.pattern.strategy.pay.PayType;

public class Order {

    private String uid;
    private String orderId;
    private double amount;


    public Order(String uid, String orderId, double amount) {
        this.uid = uid;
        this.orderId = orderId;
        this.amount = amount;
    }

    public PayState pay(PayType payType) {
        return payType.get().pay(this.uid, this.amount);
    }
}
