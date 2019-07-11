package com.booxj.gp.middleware.netty.java;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * OIO 服务端demo
 *
 * @author booxj
 * @create 2019/7/9 13:52
 * @since
 */
public class OioServer {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true) {
                final Socket client = serverSocket.accept();

                System.out.println("Accepted connection from " + client);

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            OutputStream out = client.getOutputStream();
                            out.write("Hi!\r\n".getBytes(CharsetUtil.UTF_8));
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                client.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
