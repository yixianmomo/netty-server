package com.yixianbinbin.netty.user;

import io.netty.channel.ChannelHandlerContext;

import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2020/10/30.
 */
public class UserFactory {

    private static UserFactory userFactoryInstance = null;
    private int userSize = 0;
    private CopyOnWriteArraySet<SocketUser> users = new CopyOnWriteArraySet<>();
    private ReentrantLock addlock = new ReentrantLock();
    private ReentrantLock removelock = new ReentrantLock();

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

    public boolean addUser(SocketUser user) {
        addlock.lock();
        try {
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
        } catch (Exception e) {

        } finally {
            addlock.unlock();
        }
        return false;
    }


    public boolean removeUser(ChannelHandlerContext ctx) {
        removelock.lock();
        try {
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
        } catch (Exception e) {

        } finally {
            removelock.unlock();
        }
        return false;
    }


    public synchronized ChannelHandlerContext findPlaceSocket(int placeId) {
        Iterator<SocketUser> iter = users.iterator();
        SocketUser item = null;
        while (iter.hasNext()) {
            item = iter.next();
            if (TerminalType.PLACE_TERMINAL.getName().equals(item.getTerminal())) {
                ClientUser user = (ClientUser) item.getUser();
                if (placeId == user.getPlaceId()) {
                    return item.getSocket();
                }
            }
        }
        return null;
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
            if(ctx.equals(item.getSocket())){
                return item;
            }
        }
        return null;
    }

    public int getUserSize() {
        return userSize;
    }


}
