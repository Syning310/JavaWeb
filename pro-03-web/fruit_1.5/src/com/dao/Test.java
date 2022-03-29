package com.dao;


import com.utils.JdbcUtilityByDruid;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Test {
    public static void main(String[] args) throws Exception {

        Connection cons = JdbcUtilityByDruid.getConnection();

        String sql = "select count(*) as count from fruit";

        PreparedStatement pss = cons.prepareStatement(sql);

        ResultSet set = pss.executeQuery();


        set.next();

        int i = set.getInt("count");
        //System.out.println(i);

        System.out.println(new FruitDAO().getColNums());


        JdbcUtilityByDruid.close(set, pss, cons);
    }

}
