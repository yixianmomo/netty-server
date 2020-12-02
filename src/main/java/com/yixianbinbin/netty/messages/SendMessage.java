package com.yixianbinbin.netty.messages;

import com.yixianbinbin.netty.myutils.SocketUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Author:Caoyixian
 * Created by Administrator on 2020/7/30.
 */
public class SendMessage implements Serializable {

    private int type;
    private byte[] content;

    public SendMessage() {
    }

    public SendMessage(int type, byte[] content) {
        this.type = type;
        this.content = content;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getPackageBytes(){
        try {
            int fullLen = 4 + content.length;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(SocketUtil.int2Bytes(fullLen));
            baos.write(SocketUtil.int2Bytes(type));
            baos.write(content);
            baos.close();
            return baos.toByteArray();
        }catch (IOException e){
            throw new RuntimeException("IO异常");
        }
    }


}
