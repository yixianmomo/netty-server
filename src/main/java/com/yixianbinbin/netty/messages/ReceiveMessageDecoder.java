package com.yixianbinbin.netty.messages;

import com.yixianbinbin.netty.myutils.SocketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Author:Caoyixian
 * Created by Administrator on 2019/12/23.
 */
public class ReceiveMessageDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(ReceiveMessageDecoder.class);
    private final static int HEAD_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() < HEAD_LENGTH){
            return;
        }
        // 标记下一个读取读
        byteBuf.markReaderIndex();
        // 读取长度
        byte[] l = new byte[HEAD_LENGTH];
        byteBuf.readBytes(l);
        int dataLength =  SocketUtil.bytes2Int(l);
//        int dataLength = byteBuf.readInt();
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
