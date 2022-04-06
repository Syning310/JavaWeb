package dao;

import filters.TransactionManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BasicDAO <T> {
    
    QueryRunner qr = new QueryRunner();
    
    
    // 查询表中的多行数据
    public List<T> queryMutil(String sql, Class<T> clazz, Object... parameters) {
        try {
            Connection cons = TransactionManager.getConnection();
            return qr.query(cons, sql, new BeanListHandler<>(clazz), parameters);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    // 查询表中的单行数据
    public T querySingle(String sql, Class<T> clazz, Object... parameters) {
        try {
            Connection cons = TransactionManager.getConnection();
            return qr.query(cons, sql, new BeanHandler<>(clazz), parameters);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    // 查询表中列数据
    public Object queryScalar(String sql, Object... parameters) {
        try {
            Connection cons = TransactionManager.getConnection();
            return qr.query(cons, sql, new ScalarHandler<>(), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    // 修改表中的数据
    public int update(String sql, Object... parameters) {
        try {
            Connection cons = TransactionManager.getConnection();
            return qr.update(cons, sql, parameters);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
}
