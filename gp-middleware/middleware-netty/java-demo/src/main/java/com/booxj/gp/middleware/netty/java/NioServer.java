package com.booxj.gp.middleware.netty.java;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO 服务端demo
 *
 * @author booxj
 * @create 2019/7/9 13:52
 * @since
 */
public class NioServer {

    public static void main(String[] args) {
        try {
            // 新建一个 Channel
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);

            // 新建一个 ServerSocket
            ServerSocket socket = serverChannel.socket();
            InetSocketAddress address = new InetSocketAddress(8080);
            socket.bind(address);

            // 打开 Selector 来处理 Channel
            Selector selector = Selector.open();

            // 将 ServerSocket 注册到 Selector 以接收连接
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes(CharsetUtil.UTF_8));

            for (; ; ) {
                try {
                    selector.select();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }

                Set<SelectionKey> readyKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = readyKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    try {
                        if (key.isAcceptable()) {
                            // 接收客户端，并将它注册到选择器
                            ServerSocketChannel server = (ServerSocketChannel) key.channel();
                            SocketChannel client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());

                            System.out.println("Accepted connection from " + client);
                        }

                        if (key.isWritable()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            ByteBuffer buffer = (ByteBuffer) key.attachment();
                            while (buffer.hasRemaining()) {
                                if (client.write(buffer) == 0) {
                                    break;
                                }
                            }
                            client.close();
                        }
                    } catch (IOException e) {
                        key.cancel();
                        try {
                            key.channel().close();
                        } catch (IOException ex) {

                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
