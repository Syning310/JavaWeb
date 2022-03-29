package com.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

public class JdbcUtilityByDruid {

    private static DataSource ds;


    static{
        try {
            Properties pss = new Properties();
            //pss.load(new FileInputStream("druid.properties"));
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


    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void close(Connection cons) {
        try {
            if (cons != null) {
                cons.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
