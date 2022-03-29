package com.utils;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;

public class Test {

    public static void main(String[] args) throws Exception {
        Connection con = JdbcUtility.getConnection();

        String sql = "insert into fruit values(?, ?, ?, ?)";

        QueryRunner qr = new QueryRunner();

        qr.update(con, sql, "宁", 20, 1, "18:40");

        con.close();


    }
}
