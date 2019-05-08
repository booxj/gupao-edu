package com.booxj.gp.middleware.netty.tomcat.servlets;


import com.booxj.gp.middleware.netty.tomcat.domain.User;
import com.booxj.gp.middleware.netty.tomcat.http.Request;
import com.booxj.gp.middleware.netty.tomcat.http.Response;
import com.booxj.gp.middleware.netty.tomcat.http.Servlet;
import com.google.gson.Gson;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * UserServlet:访问url http:{ip}:{port}/user?id=1
 */
public class UserServlet implements Servlet {

    private Gson gson = new Gson();

    private static Map<Integer, User> userRepository = new HashMap<>();

    static {
        userRepository.put(1, new User(1, "张三", 18));
        userRepository.put(2, new User(2, "李四", 18));
    }


    @Override
    public void doGet(Request request, Response response) {
        doPost(request, response);
    }

    @Override
    public void doPost(Request request, Response response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            User user = getUserById(id);
            if (user == null) {
                response.write("the user is not exist!", HttpResponseStatus.OK);
            } else {
                response.write(gson.toJson(user), HttpResponseStatus.OK);
            }
        } catch (Exception e) {
            response.write("", HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private User getUserById(int id) {
        return userRepository.get(id);
    }
}
