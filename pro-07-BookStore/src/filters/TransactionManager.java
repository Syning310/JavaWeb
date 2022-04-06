package filters;


import utils.JdbcByDruid;

import java.sql.Connection;
import java.sql.SQLException;

// 开启事务，提交事务，回滚事务
public class TransactionManager {
    
    // 本地线程变量
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();



    // 获取连接
    public static Connection getConnection() {
        // 从 ThreadLocal 容器中，取出连接，如果为空，则从连接池中获取连接，然后放入容器中
        Connection cons = threadLocal.get();
        if (cons == null) {
            cons = JdbcByDruid.getConnection();
            threadLocal.set(cons);
        }
        return threadLocal.get();
    }
    
    
    // 关闭连接
    public static void closeConnection() {
        Connection cons = threadLocal.get();
        // 获取本地线程变量，如果为空直接返回
        if (cons == null) {
            return;   
        }
        // 将连接关闭，然后将本地线程变量置为空
        try {
            cons.close();
            threadLocal.set(null);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    // 开启事务，也就是关闭自动提交
    public static void beginTrans() {
        // 从 ThreadLocal 容器中取出连接，如果容器中没有连接，那么从连接池中取出放入容器中
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    // 提交事务
    public static void commit() {
        try {
            getConnection().commit();  // 从容器中取出连接，然后提交
        } catch(SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
    
    
    // 滚回事务
    public static void rollback() {
        try {
            getConnection().rollback();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    
    
    
    
    
    
    
    
    
    
}
