package com.dao;

import com.domain.Fruit;
import com.utils.JdbcUtilityByDruid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class FruitDAO extends BasicDAO<Fruit> {
    
    // 属于FruitDAO的特殊方法
    // 获得水果表的列数
    public int getColNums() {
        Connection cons = null;
        PreparedStatement pss = null;
        ResultSet set = null;
        try {
            cons = JdbcUtilityByDruid.getConnection();
            String sql = "select count(*) as count from fruit";
            pss = cons.prepareStatement(sql);
            set = pss.executeQuery();
            set.next();  // 结果集的第一次下标指向的是第一行的上一个，没有数据，需要next才能达到第一行
            return set.getInt("count");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtilityByDruid.close(set, pss, cons);
        }
    }
    
}
