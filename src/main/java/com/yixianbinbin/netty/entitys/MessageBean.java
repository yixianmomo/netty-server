package com.yixianbinbin.netty.entitys;

import lombok.Data;

import java.io.Serializable;

/**
 * Author:Caoyixian
 * Created by Administrator on 2020/2/2.
 */
@Data
public class MessageBean implements Serializable {

    private int msgType;

    private String msgBody;

    public MessageBean() {
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }


}
