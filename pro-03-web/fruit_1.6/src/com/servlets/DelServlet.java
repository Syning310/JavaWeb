package com.servlets;

import com.myspringmvc.ViewBaseServlet;
import com.service.FruitService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DelServlet extends ViewBaseServlet {

    private FruitService fruitService = new FruitService();
    
    
    
    // 只要不是指定了类似 method="post"    都可以理解成doGet
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 获取参数
        String name = req.getParameter("name");
        
        if (name != null) {
            int i = fruitService.delete(name);

            System.out.println(i > 0 ? "删除成功" : "删除失败");
        }
        
        // 必须使用客户端重定向
        resp.sendRedirect("app");
        
        
    }
}
