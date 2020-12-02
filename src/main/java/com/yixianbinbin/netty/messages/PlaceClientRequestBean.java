package com.yixianbinbin.netty.messages;


import com.yixianbinbin.netty.myutils.SocketUtil;

/**
 * Created by Administrator on 2020/12/2.
 */
public class PlaceClientRequestBean {


    private byte[] packBody;
    private Integer webSocketId;
    private byte[] content;
    private Integer placeId;


    public PlaceClientRequestBean(byte[] packBody) {
        this.packBody = packBody;
        parse();
    }

    private void parse() {
        // 前4个字节 请求webSocketId
        byte[] t = new byte[4];
        System.arraycopy(packBody, 0, t, 0, t.length);
        this.webSocketId = SocketUtil.bytes2Int(t);
        content = new byte[packBody.length - t.length];
        System.arraycopy(packBody, t.length, content, 0, content.length);
    }

    public Integer getWebSocketId() {
        return webSocketId;
    }

    public byte[] getContent() {
        return content;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }
}
