package com.yixianbinbin.netty.messages;

import com.yixianbinbin.netty.user.UserFactory;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2020/12/2.
 */
public class ResponseQueueThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ResponseQueueThread.class);
    private UserFactory userFactory = null;
    PlaceClientRequestBean clientBean = null;

    public ResponseQueueThread(UserFactory userFactory, PlaceClientRequestBean clientBean) {
        this.userFactory = userFactory;
        this.clientBean = clientBean;
    }


    @Override
    public void run() {
        ChannelHandlerContext mSocket = userFactory.findWebSocket(clientBean.getWebSocketId());
        if (null != mSocket) {
            SendMessage sendMessage = new SendMessage(EventType.WEB_DATA.getId(),clientBean.getContent());
            mSocket.writeAndFlush(sendMessage);
            logger.info("placeClient消息响应:tarPlaceId={},bodyLen={},webSocketId={}",clientBean.getPlaceId(),clientBean.getContent().length, clientBean.getWebSocketId());
        }
    }
}
