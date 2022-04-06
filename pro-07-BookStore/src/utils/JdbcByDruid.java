package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcByDruid {
    
    private static DataSource ds;
    
    // 读取文件，初始化连接池
    static {
        try {
            Properties pps = new Properties();
            pps.load(new FileInputStream("D:\\Syning_GitHub\\IDEA_1\\pro-07-BookStore\\src\\utils\\druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(pps);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    // 获取连接
    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    // 关闭连接
    public void close(ResultSet set, PreparedStatement psmt, Connection cons) {
        try {
            if (set != null) {
                set.close();
            }
            if (psmt != null) {
                psmt.close();
            }
            if (cons != null) {
                cons.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    
}
