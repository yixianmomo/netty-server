package com.yixianbinbin.netty.messages;

import com.yixianbinbin.netty.myutils.SocketUtil;

import java.io.Serializable;

/**
 * Author:Caoyixian
 * Created by Administrator on 2020/2/2.
 */
public class ReceiveMessage implements Serializable {

    private int msgTypeLen = 4;

    private byte[] packageBateArr;

    public ReceiveMessage(byte[] packageBateArr) {
        this.packageBateArr = packageBateArr;
    }


    public int getMsgType() {
        byte[] msgTypeBytes = new byte[msgTypeLen];
        if (packageBateArr.length > msgTypeLen) {
            System.arraycopy(this.packageBateArr, 0, msgTypeBytes, 0, msgTypeBytes.length);
        }
        return SocketUtil.bytes2Int(msgTypeBytes);
    }


    public byte[] getMsgBody() {
        byte[] msgBodyBytes = new byte[this.packageBateArr.length - msgTypeLen];
        if (packageBateArr.length > msgTypeLen) {
            System.arraycopy(this.packageBateArr, msgTypeLen, msgBodyBytes, 0, msgBodyBytes.length);
        }
        return msgBodyBytes;
    }


}
