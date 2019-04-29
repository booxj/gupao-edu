package com.booxj.gp.middleware.netty.chat.server.handler;

import com.booxj.gp.middleware.netty.chat.processor.WebSocketMsgProcessor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 处理 WebSocket 请求
 */
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private WebSocketMsgProcessor processor = new WebSocketMsgProcessor();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        processor.sendMsg(ctx.channel(), msg.text());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String addr = processor.getAddress(channel);
        System.out.println("WebSocket Client:" + addr + "上线");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel client = ctx.channel();
        String addr = processor.getAddress(client);
        System.out.println("WebSocket Client:" + addr + "加入");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel client = ctx.channel();
        processor.logout(client);
        System.out.println("WebSocket Client:" + processor.getNickName(client) + "离开");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel client = ctx.channel();
        String addr = processor.getAddress(client);
        System.out.println("WebSocket Client:" + addr + "掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel client = ctx.channel();
        String addr = processor.getAddress(client);
        System.out.println("WebSocket Client:" + addr + "异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
