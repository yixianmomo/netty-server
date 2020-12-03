package com.yixianbinbin.netty;

import com.yixianbinbin.netty.entity.Place;
import com.yixianbinbin.netty.messages.*;
import com.yixianbinbin.netty.myutils.DBUtil;
import com.yixianbinbin.netty.user.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Author:Caoyixian
 * Created by Administrator on 2019/12/23.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    private UserFactory userFactory = UserFactory.getInstance();
    private DBUtil dbUtil = DBUtil.getInstance();
    private static int socketId = 0;

    public synchronized int getAndAddSocketId()
    {
        socketId++;
        return socketId;
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("有连接注册成功");
        UserWrap<ClientUser> user = new UserWrap<ClientUser>(ctx);
        user.setSocketId(getAndAddSocketId());
        user.setLastHeartbeat(new Date());
        userFactory.addUser(user);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("有连接注册关闭");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("有连接激活成功");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        logger.info("有连接激活关闭");
        userFactory.removeUser(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ReceiveMessage receiveMessage = (ReceiveMessage) msg;
//        logger.info("type={},bodyLen={}", receiveMessage.getMsgType(), receiveMessage.getMsgBody().length);
        UserWrap<ClientUser> user = (UserWrap<ClientUser>) userFactory.getUser(ctx);
        if (EventType.PLACE_LOGIN.getId() == receiveMessage.getMsgType()) {
            // 场所登录
            user.setTerminal(TerminalType.PLACE_TERMINAL.getName());
            String placeKey = new String(receiveMessage.getMsgBody(), "UTF-8");
            Place placeInfo = dbUtil.getPlaceInfo(placeKey);
            if (null == placeInfo) {
                logger.info("场所端登录错误：placeKey={}不存在,准备关闭", placeKey);
                ctx.close();
                return;
            }
            // 查询数据库得到placeId ...
            user.setUser(new ClientUser(placeInfo.getPlaceID(), placeKey));
        } else if (EventType.PLACE_HEARTBEAT.getId() == receiveMessage.getMsgType()) {
            user.setLastHeartbeat(new Date());
        } else if (EventType.PLACE_DATA.getId() == receiveMessage.getMsgType()) {
            PlaceClientRequestBean placeClient = new PlaceClientRequestBean(receiveMessage.getMsgBody());
            placeClient.setPlaceId(user.getUser().getPlaceId());
            new Thread(new ResponseQueueThread(userFactory, placeClient)).start();
        } else if (EventType.WEB_LOGIN.getId() == receiveMessage.getMsgType()) {
            String localLoginKey = "acd1a3bf1uc7hhecdfbadcbe7cdhk3xj";
            String loginKey = new String(receiveMessage.getMsgBody(), "UTF-8");
            if (!localLoginKey.equals(loginKey)) {
                logger.info("Web端登录错误：loginKey={}不存在,准备关闭", loginKey);
                ctx.close();
                return;
            }
            user.setTerminal(TerminalType.WEBAPI_TERMINAL.getName());
        } else if (EventType.WEB_HEARTBEAT.getId() == receiveMessage.getMsgType()) {
            user.setLastHeartbeat(new Date());
        } else if (EventType.WEB_DATA.getId() == receiveMessage.getMsgType()) {
            WebClientRequestBean webClient = new WebClientRequestBean(receiveMessage.getMsgBody());
            webClient.setWebSocketId(user.getSocketId());
            webClient.setWebSocket(ctx);
            new Thread(new RequestQueueThread(userFactory, webClient)).start();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("有消息到达完成");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 心跳包处理
        boolean checkTimeout = false;
        UserWrap<ClientUser> user = (UserWrap<ClientUser>) userFactory.getUser(ctx);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
//                    logger.info("读空闲事件:" + LocalDateTime.now().toString());
                    if (LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8)) - user.getLastHeartbeat().toInstant().getEpochSecond() > 60) {
                        checkTimeout = true;
                    }
                    break;
                case WRITER_IDLE:
                    // 处理推送事件

                    break;
                case ALL_IDLE:
                    // 不处理
                    break;
            }
            if (checkTimeout) {
                logger.info("连接即将关闭,原因:60秒内无心跳");
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
        logger.info("未知异常,准备关闭连接");
        cause.getStackTrace();
        ctx.close();
    }
}
