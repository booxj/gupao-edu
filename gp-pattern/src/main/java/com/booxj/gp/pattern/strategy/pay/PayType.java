package com.booxj.gp.pattern.strategy.pay;

public enum  PayType {

    ALI_PAY(new AliPay()),
    WECHAT_PAY(new WechatPay()),
    UNION_PAY(new UnionPay());

    private Payment payment;

    PayType(Payment payment){
        this.payment = payment;
    }

    public Payment get(){ return  this.payment;}
}
