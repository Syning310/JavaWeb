package trans;

import com.utils.JdbcUtilityByDruid;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    
    // ThreadLocal 不是一个集合，它是一个携带这变量的线程：本地线程变量
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    
    // 给DAO提供连接，开启事务之后的连接
    public static Connection getConnectionByDAO() {
        return threadLocal.get();
    }
    
    // 获取连接
    public static Connection getConnection() {
        // 在集合中取出连接，如果为空
        // 则从连接池中获取，然后放入集合中维护
        Connection cons = threadLocal.get();
        if (cons == null) {
            cons = JdbcUtilityByDruid.getConnection();
            threadLocal.set(cons);
        }
        return threadLocal.get();
    }
    // 关闭连接
    public static void closeConnection() {
        Connection cons = threadLocal.get();
        // 获取线程变量(连接)，如果为空则直接返回
        if (cons == null) {
            return;
        }
        
        // 将连接关闭，然后将线程变量置为空
        try {
            cons.close();
            threadLocal.set(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    // 开启事务
    public static void beginTrans() throws SQLException {
        getConnection().setAutoCommit(false);  // 关闭自动提交
    }
    
    
    // 提交事务
    public static void commit() throws SQLException {
        getConnection().commit();  // 提交事务
        closeConnection();   // 关闭连接
    }
    
    // 回滚事务
    public static void rollback() throws SQLException {
        getConnection().rollback();  // 事务回滚
        closeConnection(); // 关闭连接
    }
    
    
}
