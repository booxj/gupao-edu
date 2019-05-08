package com.booxj.gp.middleware.netty.tomcat.http;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class Response {

    private ChannelHandlerContext ctx;
    private Request request;

    public Response(ChannelHandlerContext ctx, Request request) {
        this.ctx = ctx;
        this.request = request;
    }

    public void write(String outString, HttpResponseStatus status) {
        FullHttpResponse response = null;
        if (status == HttpResponseStatus.OK) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(outString.getBytes(CharsetUtil.UTF_8)));

        } else if (status == HttpResponseStatus.INTERNAL_SERVER_ERROR) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.INTERNAL_SERVER_ERROR,
                    Unpooled.copiedBuffer(HttpResponseStatus.INTERNAL_SERVER_ERROR.reasonPhrase().getBytes(CharsetUtil.UTF_8)));

        } else if (status == HttpResponseStatus.NOT_FOUND) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.NOT_FOUND,
                    Unpooled.copiedBuffer(HttpResponseStatus.NOT_FOUND.reasonPhrase().getBytes(CharsetUtil.UTF_8)));
        } else {
            //to do nothing
        }

        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/json");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaders.Names.EXPIRES, 0);

        if (request.isKeepAlive()) {
            response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        ctx.write(response);
        ctx.flush();
    }

}
