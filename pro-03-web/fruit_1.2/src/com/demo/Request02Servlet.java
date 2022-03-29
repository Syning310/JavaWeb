package com.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 演示requst保存作用域(req02)
public class Request02Servlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 取出在demo01中request保存作用域，保存的数据，key为name
        Object obj = req.getAttribute("name");

        System.out.println("obj = " + obj);
        
    }
}
