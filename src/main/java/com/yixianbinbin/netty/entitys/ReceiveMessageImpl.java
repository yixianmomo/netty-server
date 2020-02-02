package com.yixianbinbin.netty.entitys;

import com.yixianbinbin.netty.myutils.CUtil;

/**
 * Author:Caoyixian
 * Created by Administrator on 2020/2/2.
 */
public class ReceiveMessageImpl implements ReceiveMessage {

    private int msgTypeLen = 4;

    private byte[] packageBateArr;

    public ReceiveMessageImpl(byte[] packageBateArr) {
        this.packageBateArr = packageBateArr;
    }

    @Override
    public int getMsgType() {
        byte[] msgTypeBytes = new byte[msgTypeLen];
        if (packageBateArr.length > msgTypeLen) {
            System.arraycopy(this.packageBateArr, 0, msgTypeBytes, 0, msgTypeBytes.length);
        }
        return CUtil.byteArrayToInt(msgTypeBytes);
    }


    @Override
    public String getMsgBody() {
        try {
            byte[] msgBodyBytes = new byte[this.packageBateArr.length - msgTypeLen];
            if (packageBateArr.length > msgTypeLen) {
                System.arraycopy(this.packageBateArr, msgTypeLen, msgBodyBytes, 0, msgBodyBytes.length);
            }
            return new String(msgBodyBytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
