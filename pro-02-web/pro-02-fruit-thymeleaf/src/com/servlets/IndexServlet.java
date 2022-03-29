package com.servlets;

import com.domain.Fruit;
import com.myspringmvc.ViewBaseServlet;
import com.service.FruitService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;



public class IndexServlet extends ViewBaseServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FruitService fserv = new FruitService();
        List<Fruit> lst = fserv.list();

        // 保存到session作用域
        HttpSession session = req.getSession();
        session.setAttribute("fruitList", lst);

        // 处理模板，第一个参数是视图名称，thymeleaf会将这个 逻辑视图名称 对应到 物理视图名称 上去
        // 逻辑视图：index
        // 物理视图:    view-prefix + 逻辑视图名称 + view-suffix
        // 所以真实的视图名称是:  /       index         .html
        super.processTemplate("index", req, resp);


    }
}
