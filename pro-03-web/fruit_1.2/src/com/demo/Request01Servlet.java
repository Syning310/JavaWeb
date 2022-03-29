package com.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 演示requst保存作用域(req01)
public class Request01Servlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 1、向request保存作用域，存储数据
        req.setAttribute("name", "ning");
        
        // 2、客户端重定向
        //resp.sendRedirect("demo02");
        
        
        
        // 将客户端重定向，修改成服务端内部转发
        req.getRequestDispatcher("demo02").forward(req, resp);
        
        
    }
}
