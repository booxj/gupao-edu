package com.booxj.gp.middleware.netty.tomcat.servlets;


import com.booxj.gp.middleware.netty.tomcat.http.Request;
import com.booxj.gp.middleware.netty.tomcat.http.Response;
import com.booxj.gp.middleware.netty.tomcat.http.Servlet;
import io.netty.handler.codec.http.HttpResponseStatus;

public class HelloServlet implements Servlet {


    @Override
    public void doGet(Request request, Response response) {
        doPost(request, response);
    }

    @Override
    public void doPost(Request request, Response response) {
        String name = request.getParameter("name");
        response.write("hello, " + name, HttpResponseStatus.OK);
    }
}
