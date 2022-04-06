package utils;

import org.apache.commons.dbutils.QueryRunner;


import java.sql.Connection;

public class Test {

    public static void main(String[] args) throws Exception {

        QueryRunner qr = new QueryRunner();


        Connection cons = JdbcByDruid.getConnection();
        
        
        
        int update = qr.update(cons, "create table ppp (id int, name varchar(32))");


        System.out.println(update);
        
        
        
    }
    
}
