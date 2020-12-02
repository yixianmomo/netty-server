package com.yixianbinbin.netty.messages;



import com.yixianbinbin.netty.myutils.SocketUtil;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.net.Socket;

/**
 * Created by Administrator on 2020/12/1.
 */
public class WebClientRequestBean implements Serializable {

    private byte[] packBody;

    private int targetPlaceId;
    private Integer webSocketId;
    private ChannelHandlerContext webSocket;
    private byte[] content;

    public WebClientRequestBean(byte[] packBody) {
        this.packBody = packBody;
        parse();
    }

    private void parse() {
        // 前4个字节 请求placeId
        byte[] t = new byte[4];
        System.arraycopy(packBody, 0, t, 0, t.length);
        this.targetPlaceId = SocketUtil.bytes2Int(t);
        content = new byte[packBody.length - t.length];
        System.arraycopy(packBody, t.length, content, 0, content.length);
    }


    public int getTargetPlaceId() {
        return targetPlaceId;
    }

    public void setTargetPlaceId(int targetPlaceId) {
        this.targetPlaceId = targetPlaceId;
    }

    public Integer getWebSocketId() {
        return webSocketId;
    }

    public void setWebSocketId(Integer webSocketId) {
        this.webSocketId = webSocketId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public ChannelHandlerContext getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(ChannelHandlerContext webSocket) {
        this.webSocket = webSocket;
    }




}
