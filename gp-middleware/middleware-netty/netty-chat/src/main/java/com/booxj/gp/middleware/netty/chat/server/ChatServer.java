package com.booxj.gp.middleware.netty.chat.server;

import com.booxj.gp.middleware.netty.chat.server.handler.HttpHandler;
import com.booxj.gp.middleware.netty.chat.server.handler.WebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * 聊天室服务端
 */
public class ChatServer {

    private int port;

    public ChatServer(int port) {
        if (port == 0) {
            this.port = 80;
        } else {
            this.port = port;
        }
    }

    public static void main(String[] args) {
        new ChatServer(80).start();
    }

    private void start() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            //TCP参数设置
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    /** 解析自定义协议 */


                    /** 解析Http请求 */
                    pipeline.addLast(new HttpServerCodec());
                    // 主要是将同一个http请求或响应的多个消息对象变成一个 fullHttpRequest完整的消息对象
                    pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                    // 主要用于处理大数据流,比如一个1G大小的文件如果你直接传输肯定会撑暴jvm内存的 ,加上这个handler我们就不用考虑这个问题了
                    pipeline.addLast(new ChunkedWriteHandler());
                    pipeline.addLast(new HttpHandler());

                    /** 解析WebSocket请求 */
                    // 请求前缀
                    pipeline.addLast(new WebSocketServerProtocolHandler("/im"));
                    pipeline.addLast(new WebSocketHandler());
                }
            });

            ChannelFuture f = bootstrap.bind(this.port).sync();
            System.out.println("the chat server is start...");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
