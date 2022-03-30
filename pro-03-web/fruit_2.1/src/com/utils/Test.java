package com.utils;

import com.domain.Fruit;
import org.apache.commons.dbutils.QueryRunner;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception {

        Connection cons = JdbcUtilityByDruid.getConnection();

        QueryRunner qr = new QueryRunner();


        List<Fruit> lst = qr.query(cons, "select * from fruit", new BeanListHandler<>(Fruit.class));

        for (Fruit f : lst) {
            System.out.println(f);
        }



    }

}
