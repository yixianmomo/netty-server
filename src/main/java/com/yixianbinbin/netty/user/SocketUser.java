package com.yixianbinbin.netty.user;

import io.netty.channel.ChannelHandlerContext;

import java.net.Socket;
import java.util.Date;

/**
 * Created by Administrator on 2020/10/30.
 */
public interface SocketUser {

    ChannelHandlerContext getSocket();

    boolean isBusy();

    String getTerminal();

    int getSocketId();

    Date getLastHeartbeat();

    Object getUserDetail();

}
