package com.uitls;

import com.dao.BasicDAO;
import com.domain.Fruit;


import java.util.List;


public class Test {

    public static void main(String[] args) throws Exception {


        BasicDAO<Fruit> dao = new BasicDAO<>();

        List<Fruit> lst = dao.queryMutil("select * from fruit", Fruit.class);

        for (Fruit f : lst) {
            System.out.println(f);
        }

    }
}
