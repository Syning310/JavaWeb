package com.service;

import com.dao.FruitDAO;
import com.domain.Fruit;

import java.util.List;

public class FruitService {

    FruitDAO dao = new FruitDAO();
    // 获取多行
    public List<Fruit> list() {
        return dao.queryMutil("select * from fruit", Fruit.class);
    }


}
