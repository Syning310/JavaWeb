package com.demo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



// 演示application作用域(app01)
public class Application01 extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 1、向application保存作用域保存数据
        ServletContext application01 = req.getServletContext();
        application01.setAttribute("name", "青青");
        
        
        
    }
}
