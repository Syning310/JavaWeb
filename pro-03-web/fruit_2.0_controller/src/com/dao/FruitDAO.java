package com.dao;


import com.domain.Fruit;
import com.utils.JdbcUtilityByDruid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class FruitDAO extends BasicDAO<Fruit> {
    
    // 属于FruitDAO的特殊方法
    
    
    // 获得水果表的列数
    public int getColNums(String sql, Object... pars) {
        Connection cons = null;
        PreparedStatement pss = null;
        ResultSet set = null;
        try {
            cons = JdbcUtilityByDruid.getConnection();
            pss = cons.prepareStatement(sql);

            for (int i = 0; i < pars.length; ++i) {
                pss.setObject(i + 1, pars[i]);
            }

            set = pss.executeQuery();
            set.next();
            return set.getInt("count");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtilityByDruid.close(set, pss, cons);
        }
    }
    
    
    
}
