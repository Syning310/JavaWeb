package com.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 演示session作用域(ssion02)
public class Sess02Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 取出在ssion01中保存在session保存作用域中的数据
        Object obj = req.getSession().getAttribute("name");

        System.out.println(obj);
        
        
    }
}
