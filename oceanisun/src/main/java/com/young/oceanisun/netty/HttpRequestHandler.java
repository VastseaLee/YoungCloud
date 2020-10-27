package com.young.oceanisun.netty;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.SocketAddress;

@Slf4j
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;
    private static final File INDEX;

    static {
        try {
//            URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
//            String path = location.toURI() + "WebsocketChatClient.html";
//            path = path.contains("file:") ? path.substring(5) : path;
            String path = "/home/web/dist/index.html";
//             /E:/vas/target/classes/WebsocketChatClient.html
            INDEX = new File(path);
        } catch (Exception e) {
            log.error("Unable to locate index.html",e);
            throw new IllegalStateException("Unable to locate index.html", e);
        }
    }

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, FullHttpRequest request) throws Exception {
        if (wsUri.equalsIgnoreCase(request.uri())) {
            chc.fireChannelRead(request.retain());
        } else {
            if (HttpUtil.is100ContinueExpected(request)) {
                send100Continue(chc);
            }
            RandomAccessFile file = new RandomAccessFile(INDEX, "r");
            HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            boolean keepAlive = HttpUtil.isKeepAlive(request);
            if (keepAlive) {
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, file.length());
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            chc.write(response);

            if (chc.pipeline().get(SslHandler.class) == null) {
                chc.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                chc.write(new ChunkedNioFile(file.getChannel()));
            }

            ChannelFuture future = chc.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

            if (!keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE);
            }

            file.close();
        }
    }

    private void send100Continue(ChannelHandlerContext chc) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        chc.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext chc, Throwable cause) throws Exception {
        Channel incoming = chc.channel();
        SocketAddress socketAddress = incoming.remoteAddress();
        log.error("Client:" + socketAddress + "error");
        cause.printStackTrace();
        chc.close();
    }
}
