package dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import transaction.TransactionManager;

import java.sql.*;
import java.util.List;

public class BasicDAO <T> {
    
    QueryRunner qr = new QueryRunner();
    
    
    // 获取表中的所有数据
    public List<T> queryMutil(String sql, Class<T> clazz, Object... parameters) {
        Connection cons = null;
        try {
            cons = TransactionManager.getConnection();
            return qr.query(cons, sql, new BeanListHandler<>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    // 查询单行的结果
    public T querySingle(String sql, Class<T> clazz, Object... parameters) {
        Connection cons = null;
        try {
            cons = TransactionManager.getConnection();
            return qr.query(cons, sql, new BeanHandler<>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    // 取得单个对象，也就是单行单列
    public Object queryScalar(String sql, Object... parameters) {
        try {
            Connection cons = TransactionManager.getConnection();
            return qr.query(cons, sql, new ScalarHandler<>(), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    // 修改表的数据
    public int update(String sql, Object... parameters) {
        Connection cons = null;
        try {
            cons = TransactionManager.getConnection();
            return qr.update(cons, sql, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    
}
