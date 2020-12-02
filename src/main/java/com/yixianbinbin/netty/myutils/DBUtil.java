package com.yixianbinbin.netty.myutils;


import com.yixianbinbin.netty.entity.Place;
import com.yixianbinbin.netty.mapper.PushMessageMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Author:Caoyixian
 * Created by Administrator on 2020/6/2.
 */
public class DBUtil {
    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
    private static DBUtil dbUtil;
    private SqlSessionFactory factory = null;

    private DBUtil(SqlSessionFactory sqlSessionFactory) {
        this.factory = sqlSessionFactory;
    }

    public static DBUtil getInstance() {
        if (null == dbUtil) {
            synchronized (DBUtil.class) {
                if (null == dbUtil) {
                    try {
                        File cnfFile = new File("config/mybatis-config.xml");
                        logger.info(cnfFile.getAbsolutePath());
//                        InputStream resourceStream = Resources.getResourceAsStream("config/mybatis-config.xml");
                        InputStream resourceStream = new FileInputStream(cnfFile);
                        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(resourceStream);
                        dbUtil = new DBUtil(factory);
                        logger.info("数据库连接成功");
                    } catch (IOException ioe) {
                        throw new RuntimeException("错误");
                    }
                }
            }
        }
        return dbUtil;
    }


    public Place getPlaceInfo(String placeKey) {
        SqlSession session = factory.openSession();
        PushMessageMapper pushMessageMapper = session.getMapper(PushMessageMapper.class);
        Place info = pushMessageMapper.getPlaceInfo(placeKey);
        session.close();
        return info;
    }




}
