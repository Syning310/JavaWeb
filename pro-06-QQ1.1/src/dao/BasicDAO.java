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
            //System.out.println("cons = " + cons.hashCode());
            return qr.update(cons, sql, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    // 复杂查询
    public Object[] executeComplexQuery(String sql, Object... parameters) {
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            Connection cons = TransactionManager.getConnection();
            
            psmt = cons.prepareStatement(sql);
            
            // 设置进参数
            setParameters(psmt, parameters);
            // 取得结果集
            rs = psmt.executeQuery();
            
            // 通过rs可以获取结果集的元数据
            // 元数据：描述结果集数据的数据，简单来说，就是这个结果集有哪些列，什么类型等等
            
            ResultSetMetaData rsmd = rs.getMetaData();
            
            // 获取结果集的列数
            int columnCount = rsmd.getColumnCount();
            // 承载结果集中的数据
            Object[] columnValueArr = new Object[columnCount];
            
            // 解析rs 
            if (rs.next()) {
                for (int i = 0; i < columnCount; ++i) {
                    Object columnValue = rs.getObject(i + 1);
                    columnValueArr[i] = columnValue;
                }
            }
            
            // 不返回结果集的原因是，如果结果集的连接关闭，那么就无法查看数据了 
            return columnValueArr;
            
        } catch(SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
                psmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
   
    
    // 给预处理命令对象，设置参数
    private void setParameters(PreparedStatement psmt, Object...  parameters) throws SQLException {
        if (parameters !=null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; ++i) {
                psmt.setObject(i + 1, parameters[i]);
            }
        }
    }
    
    
    
    
}
