package com.yixianbinbin.netty.messages;

import com.yixianbinbin.netty.myutils.ConvertUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Author:Caoyixian
 * Created by Administrator on 2020/7/30.
 */
public class SendMessage implements Serializable {

    private int type;
    private String content;

    public SendMessage() {
    }

    public SendMessage(int type, String content) {
        this.type = type;
        this.content = content;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getPackageBytes(){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(ConvertUtil.intToByteArray(this.type));
            baos.write(content.getBytes("UTF-8"));
            return baos.toByteArray();
        }catch (IOException e){
            throw new RuntimeException("IO异常");
        }
    }


}
