package com.yixianbinbin.netty.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/12/2.
 */
public class Place  implements Serializable{

    private Integer PlaceID;

    private String PlaceName;

    private String PlaceKey;

    public Place() {
    }

    public Integer getPlaceID() {
        return PlaceID;
    }

    public void setPlaceID(Integer placeID) {
        PlaceID = placeID;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public String getPlaceKey() {
        return PlaceKey;
    }

    public void setPlaceKey(String placeKey) {
        PlaceKey = placeKey;
    }
}
