package com.yixianbinbin.netty.user;

/**
 * Created by Administrator on 2020/12/2.
 */
public enum TerminalType {

    PLACE_TERMINAL("PLACE_TERMINAL"),WEBAPI_TERMINAL("WEBAPI_TERMINAL");

    private String name;

    TerminalType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
