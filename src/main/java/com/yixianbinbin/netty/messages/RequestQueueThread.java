package com.yixianbinbin.netty.messages;

import com.yixianbinbin.netty.myutils.SocketUtil;
import com.yixianbinbin.netty.user.UserFactory;
import com.yixianbinbin.netty.user.UserWrap;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by Administrator on 2020/12/2.
 */
public class RequestQueueThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestQueueThread.class);
    private UserFactory userFactory = null;
    WebClientRequestBean clientBean = null;

    public RequestQueueThread(UserFactory mUserFactory, WebClientRequestBean mClientBean) {
        this.userFactory = mUserFactory;
        this.clientBean = mClientBean;
    }


    @Override
    public void run() {
        UserWrap mUser = userFactory.findPlaceFreeSocket(clientBean.getTargetPlaceId());
        try {
            if (null == mUser) {
                // 没有在线用户
                SendMessage sendMessage = new SendMessage(EventType.WEB_DATA.getId(), "-1".getBytes("UTF-8"));
                clientBean.getWebSocket().writeAndFlush(sendMessage);
            } else {
                SendMessage sendMessage = new SendMessage(EventType.PLACE_DATA.getId(), SocketUtil.genToPlaceBodyBytes(clientBean.getWebSocketId(), clientBean.getContent()));
                mUser.getSocket().writeAndFlush(sendMessage);
                logger.info("webClient消息请求:tarPlaceId={},bodyLen={},webSocketId={}", clientBean.getTargetPlaceId(), clientBean.getContent().length, clientBean.getWebSocketId());
            }
        } catch (Exception e) {

        } finally {
            userFactory.releasePlaceSocket(mUser);
        }
    }
}
