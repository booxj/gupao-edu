package com.booxj.gp.middleware.netty.tomcat.http;

public interface Servlet {

    void doGet(Request request, Response response);

    void doPost(Request request, Response response);
}
