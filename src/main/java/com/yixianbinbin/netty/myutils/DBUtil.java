package com.yixianbinbin.netty.myutils;

import com.yixianbinbin.netty.mapper.TestMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Author:Caoyixian
 * Created by Administrator on 2020/6/2.
 */
public class DBUtil {
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
                        InputStream resourceStream = Resources.getResourceAsStream("mybatis-config.xml");
                        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(resourceStream);
                        dbUtil = new DBUtil(factory);
                    }catch (IOException ioe){
                        throw new RuntimeException("错误");
                    }
                }
            }
        }
        return dbUtil;
    }


    public HashMap<String,Object> selectTest(String name){
        SqlSession session = factory.openSession();
        TestMapper test = session.getMapper(TestMapper.class);
        HashMap<String, Object> info = test.selectTest("admin");
        session.close();
        return info;
    }
}
