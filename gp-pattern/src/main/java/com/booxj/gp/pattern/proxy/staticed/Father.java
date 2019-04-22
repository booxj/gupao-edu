package com.booxj.gp.pattern.proxy.staticed;


import com.booxj.gp.pattern.proxy.Person;

public class Father {

    Person person;

    public Father(Person person) {
        this.person = person;
    }

    // 目标对象的引用给拿到
    public void findLove() {
        System.out.println("father:根据你的要求物色");
        this.person.findLove();
        System.out.println("father:双方父母是不是同意");
    }

    // 目标对象的引用给拿到
    public void findJob() {
        System.out.println("father:根据你的要求物色");
        this.person.findJob();
        System.out.println("father:公司是否同意");
    }
}
