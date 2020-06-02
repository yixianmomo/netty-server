package com.yixianbinbin.netty.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * Author:Caoyixian
 * Created by Administrator on 2020/6/2.
 */
public interface TestMapper {

    HashMap<String,Object> selectTest(@Param(value = "name") String name);
}
