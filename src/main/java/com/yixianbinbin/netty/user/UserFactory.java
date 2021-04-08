package com.yixianbinbin.netty.user;

import io.netty.channel.ChannelHandlerContext;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Administrator on 2020/10/30.
 */
public class UserFactory {

    private static UserFactory userFactoryInstance = null;
    private int userSize = 0;
    private CopyOnWriteArraySet<SocketUser> users = new CopyOnWriteArraySet<>();

    private UserFactory() {
    }

    public static UserFactory getInstance() {
        if (null == userFactoryInstance) {
            synchronized (UserFactory.class) {
                if (null == userFactoryInstance) {
                    userFactoryInstance = new UserFactory();
                }
            }
        }
        return userFactoryInstance;
    }

    public synchronized boolean addUser(SocketUser user) {
        boolean check = false;
        Iterator<SocketUser> iter = users.iterator();
        SocketUser item = null;
        while (iter.hasNext()) {
            item = iter.next();
            if (item.getSocket().equals(user.getSocket())) {
                check = true;
                break;
            }
        }
        if (!check) {
            users.add(user);
            userSize++;
            return true;
        }
        return false;
    }


    public synchronized boolean removeUser(ChannelHandlerContext ctx) {
        Iterator<SocketUser> iter = users.iterator();
        SocketUser item = null;
        while (iter.hasNext()) {
            item = iter.next();
            if (ctx.equals(item.getSocket())) {
                users.remove(item);
                userSize--;
                return true;
            }
        }
        return false;
    }


    public synchronized UserWrap findPlaceFreeSocket(int placeId) {
        int i = 0;
        UserWrap placeUser;
        while (i < 3) {
            placeUser = __findPlaceFreeSocket(placeId);
            if (null != placeUser) {
                placeUser.setBusy(true);
                return placeUser;
            }
            wait(1);
            i++;
        }
        return null;
    }

    private void wait(int seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {

        }
    }


    private UserWrap __findPlaceFreeSocket(int placeId) {
        Iterator<SocketUser> iter = users.iterator();
        UserWrap item = null;
        while (iter.hasNext()) {
            item = (UserWrap) iter.next();
            if (TerminalType.PLACE_TERMINAL.getName().equals(item.getTerminal()) && !item.isBusy()) {
                PlaceTerminalUserDetail user = (PlaceTerminalUserDetail) item.getUserDetail();
                if (placeId == user.getPlaceId()) {
                    return item;
                }
            }
        }
        return null;
    }


    public synchronized void releasePlaceSocket(UserWrap userWrap) {
        if(null != userWrap) {
            userWrap.setBusy(false);
        }
    }


    public synchronized ChannelHandlerContext findWebSocket(int webSocketId) {
        Iterator<SocketUser> iter = users.iterator();
        SocketUser item = null;
        while (iter.hasNext()) {
            item = iter.next();
            if (TerminalType.WEBAPI_TERMINAL.getName().equals(item.getTerminal())) {
                if (webSocketId == item.getSocketId()) {
                    return item.getSocket();
                }
            }
        }
        return null;
    }


    public synchronized SocketUser getUser(ChannelHandlerContext ctx) {
        Iterator<SocketUser> iter = users.iterator();
        SocketUser item = null;
        while (iter.hasNext()) {
            item = iter.next();
            if (ctx.equals(item.getSocket())) {
                return item;
            }
        }
        return null;
    }

    public int getUserSize() {
        return userSize;
    }


}
