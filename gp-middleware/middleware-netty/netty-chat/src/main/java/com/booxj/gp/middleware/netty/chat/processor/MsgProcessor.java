package com.booxj.gp.middleware.netty.chat.processor;


import com.alibaba.fastjson.JSONObject;
import com.booxj.gp.middleware.netty.chat.protocol.IMDecoder;
import com.booxj.gp.middleware.netty.chat.protocol.IMEncoder;
import com.booxj.gp.middleware.netty.chat.protocol.IMMessage;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

public abstract class MsgProcessor {

    //记录在线用户
    protected static ChannelGroup onlineUsers = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //定义一些扩展属性
    protected final AttributeKey<String> NICK_NAME = AttributeKey.valueOf("nickName");
    protected final AttributeKey<String> IP_ADDR = AttributeKey.valueOf("ipAddr");
    protected final AttributeKey<JSONObject> ATTRS = AttributeKey.valueOf("attrs");

    //自定义解码器
    protected IMDecoder decoder = new IMDecoder();
    //自定义编码器
    protected IMEncoder encoder = new IMEncoder();

    public abstract void logout(Channel client);

    public abstract void sendMsg(Channel client, IMMessage msg);
}
