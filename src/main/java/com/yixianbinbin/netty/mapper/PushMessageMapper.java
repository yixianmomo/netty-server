package com.yixianbinbin.netty.mapper;


import com.yixianbinbin.netty.entity.Place;
import org.apache.ibatis.annotations.Param;



/**
 * Author:Caoyixian
 * Created by Administrator on 2020/6/2.
 */
public interface PushMessageMapper {


    Place getPlaceInfo(@Param(value = "placeKey") String placeKey);


}
