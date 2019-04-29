package com.booxj.gp.middleware.netty.chat.protocol;


public class IMEncoder {

    public String encode(IMMessage msg) {
        if (null == msg) {
            return "";
        }

        String prex = "[" + msg.getCmd() + "]" + "[" + msg.getTime() + "]";
        if (IMP.LOGIN.getName().equals(msg.getCmd()) ||
                IMP.CHAT.getName().equals(msg.getCmd()) ||
                IMP.FLOWER.getName().equals(msg.getCmd())) {
            prex += ("[" + msg.getSender() + "]");
        } else if (IMP.SYSTEM.getName().equals(msg.getCmd())) {
            prex += ("[" + msg.getOnline() + "]");
        }
        if (!(null == msg.getContent() || "".equals(msg.getContent()))) {
            prex += (" - " + msg.getContent());
        }
        return prex;
    }
}
