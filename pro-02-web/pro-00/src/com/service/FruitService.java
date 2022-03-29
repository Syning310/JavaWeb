package com.service;

import org.apache.commons.dbutils.QueryRunner;
import com.utils.JdbcUtility;

import java.sql.Connection;

public class FruitService {


    // 往数据库中添加数据
    public int update(String name, double price, int count, String remark) {

        Connection con = null;

        try {
//            Properties pss = new Properties();
//            pss.setProperty("user", "root");
//            pss.setProperty("password", "syning");
//
//            // 类加载
//            Class d = Class.forName("com.mysql.jdbc.Driver");
//            Driver dr = (Driver)d.newInstance();
//
//            // 获得链接
//            con = dr.connect("jdbc:mysql://localhost:3306/webtest?rewriteBatchedStatements=true", pss);

            con = JdbcUtility.getConnection();

            String sql = "insert into fruit values(?, ?, ?, ?)";


            QueryRunner qr = new QueryRunner();
            return qr.update(con, sql, name, price, count, remark);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}
