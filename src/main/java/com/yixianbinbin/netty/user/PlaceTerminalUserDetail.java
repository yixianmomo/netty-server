package com.yixianbinbin.netty.user;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/11/26.
 */
public class PlaceTerminalUserDetail implements Serializable {

    private Integer placeId = 0;
    private String placeKey;
    private String placeName;

    public PlaceTerminalUserDetail() {
    }

    public PlaceTerminalUserDetail(Integer placeId, String placeKey,String placeName) {
        this.placeId = placeId;
        this.placeKey = placeKey;
        this.placeName = placeName;
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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
