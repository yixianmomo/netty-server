package com.yixianbinbin.netty.myutils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2020/10/27.
 */
public class SocketUtil {


    public static byte[] int2Bytes(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    public static int bytes2Int(byte[] bytes) {
        int value = 0;
        value = ((bytes[3] & 0xff) << 24) |
                ((bytes[2] & 0xff) << 16) |
                ((bytes[1] & 0xff) << 8) |
                (bytes[0] & 0xff);
        return value;
    }


    public static byte[] genToPlaceBodyBytes(Integer webSocketId, byte[] content) {
        byte[] webSocketIdBytes = int2Bytes(webSocketId);
        byte[] t = new byte[content.length + webSocketIdBytes.length];
        System.arraycopy(webSocketIdBytes, 0, t, 0, webSocketIdBytes.length);
        if(content.length > 0) {
            System.arraycopy(content, 0, t, webSocketIdBytes.length, content.length);
        }
        return t;
    }


    public static void readBytes(InputStream in, byte[] bytes) throws IOException {
        int rRead = 0;
        int tLength = bytes.length;
        while (rRead < tLength) {
            int r = in.read(bytes, rRead, (tLength - rRead));
            if (r <= 0) {
                throw new IOException("当前状态不为连接");
            }
            rRead += r;
        }
    }


}
