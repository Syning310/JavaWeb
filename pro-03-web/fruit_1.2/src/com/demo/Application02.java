package com.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 演示application作用域(app02)
public class Application02 extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        
        // 读取application作用域中的数据
        Object obj = req.getServletContext().getAttribute("name");

        System.out.println(obj);
        
        
    }
}
