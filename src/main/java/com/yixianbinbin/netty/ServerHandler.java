package com.yixianbinbin.netty;


import com.yixianbinbin.netty.messages.ReceiveMessage;
import com.yixianbinbin.netty.messages.SendMessage;
import com.yixianbinbin.netty.myutils.CUtil;
import com.yixianbinbin.netty.myutils.DBUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.HashMap;


/**
 * Author:Caoyixian
 * Created by Administrator on 2019/12/23.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private int readIdleTimes = 0;
    private long lastHeartBeatTimestamp = CUtil.getCurrTimestamp();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有连接注册成功");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有连接注册关闭");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有连接激活成功");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有连接激活关闭");
        HashMap<String,Object> test = DBUtil.getInstance().selectTest("admin");
        System.out.println(test);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ReceiveMessage receiveMessage = (ReceiveMessage) msg;
        System.out.println("有消息到达=" + receiveMessage.getMsgBody());
        // send
        SendMessage sendMessage = new SendMessage(1,"nihao");
        ctx.writeAndFlush(sendMessage);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有消息到达完成");
        lastHeartBeatTimestamp = CUtil.getCurrTimestamp();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 心跳包处理
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()){
                case READER_IDLE:
                    System.out.println("读空闲事件:" + readIdleTimes);
                    if(CUtil.getCurrTimestamp() - lastHeartBeatTimestamp > 60) {
                        readIdleTimes++;
                    }
                    break;
                case WRITER_IDLE:
                    // 处理推送事件

                    break;
                case ALL_IDLE:
                    // 不处理
                    break;
            }
            if (readIdleTimes > 0) {
                System.out.println("连接即将关闭,原因:60秒内无心跳");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("消息异常");
        cause.getStackTrace();
        ctx.close();
    }
}
