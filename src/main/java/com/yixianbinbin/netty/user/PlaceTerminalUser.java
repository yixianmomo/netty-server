package com.yixianbinbin.netty.user;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/11/26.
 */
public class PlaceTerminalUser implements Serializable {

    private Integer placeId = 0;
    private String placeKey;

    public PlaceTerminalUser() {
    }

    public PlaceTerminalUser(Integer placeId, String placeKey) {
        this.placeId = placeId;
        this.placeKey = placeKey;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getPlaceKey() {
        return placeKey;
    }

    public void setPlaceKey(String placeKey) {
        this.placeKey = placeKey;
    }
}
