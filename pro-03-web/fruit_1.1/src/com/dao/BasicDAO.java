package com.dao;

import com.utils.JdbcUtilityByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.util.List;

public class BasicDAO <T> {

    private QueryRunner qr = new QueryRunner();

    // 获取表的所有数据
    public List<T> queryMutil(String sql, Class<T> clazz, Object... pars) {
        Connection cons = null;
        try {
            cons = JdbcUtilityByDruid.getConnection();
            return qr.query(cons, sql, new BeanListHandler<>(clazz), pars);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtilityByDruid.close(cons);
        }
        

    }


}
