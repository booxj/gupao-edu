package com.booxj.gp.middleware.netty.tomcat.server.handler;


import com.booxj.gp.middleware.netty.tomcat.http.Request;
import com.booxj.gp.middleware.netty.tomcat.http.Response;
import com.booxj.gp.middleware.netty.tomcat.http.Servlet;
import com.booxj.gp.middleware.netty.tomcat.servlets.HelloServlet;
import com.booxj.gp.middleware.netty.tomcat.servlets.UserServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class TomcatHandler extends SimpleChannelInboundHandler {

    private static final Map<Pattern, Class<?>> servletMapping = new HashMap<>();

    static {
        servletMapping.put(Pattern.compile("\\/hello.+"), HelloServlet.class);
        servletMapping.put(Pattern.compile("\\/user.+"), UserServlet.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;

            Request request = new Request(httpRequest);
            Response response = new Response(ctx, request);

            boolean hasPattern = false;

            // 验证url
            for (Map.Entry<Pattern, Class<?>> entry : servletMapping.entrySet()) {
                if (entry.getKey().matcher(httpRequest.getUri()).matches()) {

                    Servlet servlet = (Servlet) entry.getValue().newInstance();
                    if ("get".equalsIgnoreCase(request.getMethod())) {
                        servlet.doGet(request, response);
                    } else {
                        servlet.doPost(request, response);
                    }

                    hasPattern = true;
                    break;
                }
            }

            // 分发到指定servlet
            if (!hasPattern) {
                response.write("", HttpResponseStatus.NOT_FOUND);
                return;
            }
        }


    }
}
