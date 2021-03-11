package com.yixianbinbin.netty.user;

import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Administrator on 2020/10/30.
 */
public class UserWrap implements SocketUser, Serializable {

    private ChannelHandlerContext socket = null;
    private Date lastHeartbeat = null;
    private String terminal;
    private int socketId;
    private boolean isBusy;
    private Object user = null;

    public UserWrap(ChannelHandlerContext socket) {
        this.socket = socket;
        this.lastHeartbeat = new Date();
    }

    public UserWrap(ChannelHandlerContext socket, Date lastHeartbeat) {
        this.socket = socket;
        this.lastHeartbeat = lastHeartbeat;
    }

    public UserWrap(ChannelHandlerContext socket, Date lastHeartbeat, String terminal) {
        this.socket = socket;
        this.lastHeartbeat = lastHeartbeat;
        this.terminal = terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public void setSocketId(int socketId) {
        this.socketId = socketId;
    }


    @Override
    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public void setSocket(ChannelHandlerContext socket) {
        this.socket = socket;
    }

    @Override
    public Object getUserDetail() {
        return user;
    }

    public void setUserDetail(Object user) {
        this.user = user;
    }

    public void setLastHeartbeat(Date lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }

    @Override
    public ChannelHandlerContext getSocket() {
        return socket;
    }

    @Override
    public Date getLastHeartbeat() {
        return lastHeartbeat;
    }

    @Override
    public String getTerminal() {
        return terminal;
    }

    @Override
    public int getSocketId() {
        return socketId;
    }



}
