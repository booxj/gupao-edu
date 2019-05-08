package com.booxj.gp.middleware.netty.chat.server.handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 处理 Http 请求
 */
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    //如果直接执行.class文件那么会得到当前class的绝对路径。
    //如果封装在jar包里面执行jar包那么会得到当前jar包的绝对路径。
    private URL baseURL = HttpHandler.class.getProtectionDomain().getCodeSource().getLocation();
    private final String webroot;
    private final String wsUri;

    public HttpHandler(String webroot, String wsUri) {
        this.webroot = webroot;
        this.wsUri = wsUri;
    }

    private File getResource(String fileName) throws URISyntaxException {
        String path = baseURL.toURI() + webroot + "/" + fileName;
        path = !path.contains("file:") ? path : path.substring(5);
        path.replaceAll("//", "/");
        return new File(path);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String uri = request.uri();
        RandomAccessFile file = null;

        if (wsUri.equalsIgnoreCase(uri)) {
            ctx.fireChannelRead(request.retain());
        } else {
            try {

                String page = uri.equals("/") ? "chat.html" : uri;
                /**
                 * r:只读
                 * rw:读写，如果文件不存在则创建
                 * rws:读写，还要求对文件的内容或元数据的每个更新都同步写入到底层存储设备
                 * rwd:通rws,但不修改文件的元数据
                 */
                file = new RandomAccessFile(getResource(page), "r");
            } catch (Exception e) {
                //如果请求了webSocket协议升级，则增加引用计数，并将它传递给下一个ChannelInboundHandler
                ctx.fireChannelRead(request.retain());
                return;
            }

            HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
            String contextType = "text/html;";

            if (uri.endsWith(".css")) {
                contextType = "text/css";
            } else if (uri.endsWith(".js")) {
                contextType = "text/javascript";
            } else if (uri.toLowerCase().matches("(jpg|png|git)")) {
                String ext = uri.substring(uri.lastIndexOf("."));
                contextType = "image/" + ext;
            }

            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, contextType + "charset=utf-8;");

            boolean keepAlive = HttpHeaders.isKeepAlive(request);
            if (keepAlive) {
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }

            //将response写到客户端
            ctx.write(response);

            //将chat.html写到客户端
            if (ctx.pipeline().get(SslHandler.class) == null) {
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }

            //写LastHttpContent并冲刷至客户端
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

            //如果不是长连接，则在写操作完成后关闭Channel
            if (!keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE);
            }

            file.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel client = ctx.channel();
        System.out.println("Client:" + client.remoteAddress() + "异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

}
