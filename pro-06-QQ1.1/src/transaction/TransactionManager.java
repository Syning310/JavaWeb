package transaction;

import utils.JdbcUtilsByDruid;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    
    
    // 本地线程变量
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    
    // 获取连接
    public static Connection getConnection() {
        // 从 ThreadLocal 容器 中中取出连接，如果为空，则从连接池中获取连接，然后放入 ThreadLocal 容器
        Connection cons = threadLocal.get();
        if (cons == null) {
            cons = JdbcUtilsByDruid.getConnection();
            threadLocal.set(cons);
        }
        return threadLocal.get();
    }
    
    // 关闭连接，将连接还给连接池
    public static void closeConnection() {
        Connection cons = threadLocal.get();
        // 获取本地线程变量，如果为空则直接返回
        if (cons == null) {
            return;
        }
        // 将连接关闭(还给连接池)，然后将本地线程变量置为空
        try {
            cons.close();
            threadLocal.set(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    // 开启事务，也就是关闭自动提交
    public static void beginTrans() {
        // 从 ThreadLocal 容器中取出一个连接，如果容器中没有连接，那么放入一个连接，本方法是关闭自动提交
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    // 提交事务
    public static void commit() {
        try {
            getConnection().commit();  // 提交事务
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();   // 关闭连接，将连接还给连接池
        }
    }
    
    // 回滚事务
    public static void rollback() {
        try {
            getConnection().rollback();   // 如果Service某个步骤出现了异常，则回滚到开启事务的时候
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();  // 关闭连接，将连接还给连接池
        }
    }
    
    
}
