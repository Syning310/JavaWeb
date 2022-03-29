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
            pss.load(new FileInputStream("D:\\Syning_GitHub\\IDEA_1\\pro-03-web\\fruit_1.3\\web\\druid.properties"));

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
