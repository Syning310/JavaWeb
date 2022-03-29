package com.service;

import com.dao.FruitDAO;
import com.domain.Fruit;
import com.utils.JdbcUtilityByDruid;

import java.sql.Connection;
import java.util.List;

public class FruitService {
    
    FruitDAO dao = new FruitDAO();
    // 返回表所有数据
    public List<Fruit> list() {
        return dao.queryMutil("select * from fruit", Fruit.class);
    } 
    
    
    // 根据name返回一个Fruit对象
    public Fruit querySingle(String name) {
        return dao.querySingle("select * from fruit where name = ?", Fruit.class, name);
    }
    
    // 根据where名字，修改表数据
    public int update(String newName, Double price, Integer count, String remark, String oldName) {
        Connection cons = null;
        try {
            return dao.update("update fruit set name=?, price=?, count=?, remark=? where name=?",
                    newName, price, count, remark, oldName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtilityByDruid.close(cons);
        }
    }
    
    // 根据name删除，数据
    public int delete(String name) {
        Connection cons = null;
        try {
            return dao.update("delete from fruit where name=?", name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtilityByDruid.close(cons);
        }
    }
    
    // 添加单个数据
    public int insertSingle(String name, Double price, Integer count, String remark) {
        Connection cons = null;
        try {
            return dao.update("insert into fruit values (?, ?, ?, ?)", name, price, count, remark);
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtilityByDruid.close(cons);
        }
    }
    
    
}
