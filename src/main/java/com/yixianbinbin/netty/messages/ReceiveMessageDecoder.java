package com.yixianbinbin.netty.messages;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Author:Caoyixian
 * Created by Administrator on 2019/12/23.
 */
public class ReceiveMessageDecoder extends ByteToMessageDecoder {

    private final static int HEAD_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() < HEAD_LENGTH){
            return;
        }
        // 标记下一个读取读
        byteBuf.markReaderIndex();
        // 读取长度
        int dataLength = byteBuf.readInt();
        if(dataLength < 0){
            channelHandlerContext.close();
        }
        // 消息体长度小于我们传送过来的长度
        if(byteBuf.readableBytes() < dataLength){
            byteBuf.resetReaderIndex();
            return;
        }
        // read
        byte[] packBytes = new byte[dataLength];
        byteBuf.readBytes(packBytes);
        // 将byte转化为我们需要的对象
        ReceiveMessage receiveMessage = new ReceiveMessage(packBytes);
        list.add(receiveMessage);
    }


}
