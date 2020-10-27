package com.young.oceanisun.netty;

import com.young.common.util.JwtUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang.StringUtils;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static Map<Channel, String> nameMap = new HashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext chc, TextWebSocketFrame msg) throws Exception {
        Channel incoming = chc.channel();
        String m = msg.text();
        if (StringUtils.isNotEmpty(m) && m.startsWith("token:")) {
            m = m.substring(m.indexOf(":") + 1);
            String name = JwtUtil.getName(m);
            nameMap.put(incoming, name);
            channels.stream()
                    .filter(channel -> channel != incoming)
                    .forEach(channel -> channel.writeAndFlush(new TextWebSocketFrame("[" + name + "] - " + "加入")));
        } else {
            channels.forEach(channel -> {
                if (channel == incoming) {
                    channel.writeAndFlush(new TextWebSocketFrame("[You]:" + msg.text()));
                } else {
                    String name = nameMap.get(incoming);
                    channel.writeAndFlush(new TextWebSocketFrame("[" + name + "]:" + msg.text()));
                }
            });
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        String name = nameMap.remove(incoming);
        channels.forEach(channel -> channel.writeAndFlush(new TextWebSocketFrame("[" + name + "] - 离开")));
        channels.remove(incoming);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
    }
}
