package com.yixianbinbin.netty.messages;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Author:Caoyixian
 * Created by Administrator on 2020/7/30.
 */
public class MyCustomMessageEncoder extends MessageToByteEncoder<SendMessage> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SendMessage sendMessage, ByteBuf byteBuf) throws Exception {
         byte[] packBytes = sendMessage.getPackageBytes();
         byteBuf.writeInt(packBytes.length);
         byteBuf.writeBytes(packBytes);
    }
}
