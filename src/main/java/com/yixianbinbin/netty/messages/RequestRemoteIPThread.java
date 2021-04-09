package com.yixianbinbin.netty.messages;

import com.yixianbinbin.netty.myutils.SocketUtil;
import com.yixianbinbin.netty.user.UserFactory;
import com.yixianbinbin.netty.user.UserWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2021/4/9.
 */
public class RequestRemoteIPThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestRemoteIPThread.class);
    private UserFactory userFactory = null;
    WebClientRequestBean clientBean = null;

    public RequestRemoteIPThread(UserFactory userFactory, WebClientRequestBean clientBean) {
        this.userFactory = userFactory;
        this.clientBean = clientBean;
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
                InetSocketAddress insocket = (InetSocketAddress) clientBean.getWebSocket().channel().remoteAddress();
                SendMessage sendMessage = new SendMessage(EventType.WEB_DATA.getId(), insocket.getAddress().getHostAddress().getBytes("UTF-8"));
                clientBean.getWebSocket().writeAndFlush(sendMessage);
                logger.info("Web端获取场所IP:tarPlaceId={},ip={}", clientBean.getTargetPlaceId(),insocket.getAddress().getHostAddress());
            }
        } catch (Exception e) {

        } finally {
            userFactory.releasePlaceSocket(mUser);
        }
    }
}
