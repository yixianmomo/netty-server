package com.yixianbinbin.netty.messages;

/**
 * Created by Administrator on 2020/12/2.
 */
public enum EventType {

     PLACE_LOGIN(1),PLACE_HEARTBEAT(2),PLACE_DATA(3),WEB_LOGIN(4),WEB_HEARTBEAT(5),WEB_DATA(6),WEB_GET_REMOTEIP(7);

    private int id;

    EventType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
