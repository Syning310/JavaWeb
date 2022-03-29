package com.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



// 演示session作用域(ssion01)
public class Sess01Servlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 1、向session保存作用域中保存数据
        req.getSession().setAttribute("name", "月");
        
        // 2、客户端重定向
        resp.sendRedirect("ssion02");  // ok
        
        
        // 服务端内部转发
        //req.getRequestDispatcher("ssion02").forward(req, resp);  // ok
        
        
    }
}
