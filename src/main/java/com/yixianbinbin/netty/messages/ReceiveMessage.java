package com.yixianbinbin.netty.messages;

/**
 * Author:Caoyixian
 * Created by Administrator on 2020/2/2.
 */
public interface ReceiveMessage {

    int getMsgType();

    String getMsgBody();
}
