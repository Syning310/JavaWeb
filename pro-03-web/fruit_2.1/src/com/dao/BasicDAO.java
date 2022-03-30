package com.dao;

import com.utils.JdbcUtilityByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import trans.TransactionManager;

import java.sql.Connection;
import java.util.List;

public class BasicDAO <T> {

    QueryRunner qr = new QueryRunner();

    // 获取表的所有数据
    public List<T> queryMutil(String sql, Class<T> clazz, Object... pars) {
        Connection cons = null;
        try {
            cons = TransactionManager.getConnectionByDAO();

            System.out.println("cons = " + cons.hashCode());
            
            return qr.query(cons, sql, new BeanListHandler<>(clazz), pars);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //JdbcUtilityByDruid.close(cons);
        }
    }
    
    
    // 查询单行的结果
    public T querySingle(String sql, Class<T> clazz, Object... pars) {
        Connection cons = null;
        try {
            cons = TransactionManager.getConnectionByDAO();

            System.out.println("cons = " + cons.hashCode());
            
            return qr.query(cons, sql, new BeanHandler<>(clazz), pars);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //JdbcUtilityByDruid.close(cons);
        }
    }
    
    
    // 修改表的数据
    public int update(String sql, Object... pars) {
        Connection cons = null;
        try {
            cons = TransactionManager.getConnectionByDAO();

            System.out.println("cons = " + cons.hashCode());
            
            return qr.update(cons, sql, pars);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //JdbcUtilityByDruid.close(cons);
        }
    }

}
