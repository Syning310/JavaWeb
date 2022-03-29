package com.utils;

import java.sql.Connection;
import java.util.Properties;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.Statement;


public class JdbcUtility {

    private static DataSource ds;

    // 在静态代码块完成 ds初始化
    static {
        try {
            Properties pps = new Properties();
            pps.load(new FileInputStream("druid.properties"));

            ds = DruidDataSourceFactory.createDataSource(pps);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // 编写getConnection方法
    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 关闭连接，在数据库连接池技术中，close不是真正的断掉连接，而是把使用的Connection对象放回连接池
    public static void close(ResultSet set, Statement statement, Connection connection) {
        try {
            if (set != null) {
                set.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();  // 把连接对象还给连接池，不是真正断开和DBMS系统的连接
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
