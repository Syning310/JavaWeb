package com.uitls;

import javax.sql.DataSource;

import java.sql.Connection;
import java.util.Properties;
import com.alibaba.druid.pool.DruidDataSourceFactory;


public class JdbcByDruid {

    private static DataSource ds;
    private static Properties pss = new Properties();


    static {
        try {
            pss.setProperty("driverClassName", "com.mysql.jdbc.Driver");
            pss.setProperty("url", "jdbc:mysql://localhost:3306/webtest?rewriteBatchedStatements=true");
            pss.setProperty("username", "root");
            pss.setProperty("password", "syning");
            pss.setProperty("initialSize", "10");
            pss.setProperty("minIdle", "5");
            pss.setProperty("maxActive", "50");
            pss.setProperty("maxWait", "5000");
            ds = DruidDataSourceFactory.createDataSource(pss);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    // 编写获取连接方法
    public static Connection getConnections() {
        try {
            return ds.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 关闭连接
    public static void close(Connection cons) {
        try {
            if (cons != null) {
                cons.close();  // 将连接对象还给连接池
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
