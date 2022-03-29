package com.servlets;

import com.myspringmvc.ViewBaseServlet;
import com.service.FruitService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends ViewBaseServlet {

    FruitService fruitService = new FruitService();

    // 表单的 method = "post"
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        
        // 获取表单传来的参数
        String name = req.getParameter("name");
        Double price = Double.parseDouble(req.getParameter("price"));
        Integer count = Integer.parseInt(req.getParameter("count"));
        String remark = req.getParameter("remark");
        
        int i = fruitService.insertSingle(name, price, count, remark);
        System.out.println(i > 0 ? "添加成功" : "添加失败");
        
        
        // 客户端重定向
        resp.sendRedirect("app");
        
        
    }
}
