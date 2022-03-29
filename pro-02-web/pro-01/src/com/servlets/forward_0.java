package com.servlets;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 演示服务端内部转发，以及客户端重定向
public class forward_0 extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 演示服务端内部转发
        System.out.println("forward_0......");
        // 服务端内部转发，getRequestDispathcher("");这个方法中填的不是类名，而是web.xml中配置的servlet-name
        //req.getRequestDispatcher("forward1").forward(req, resp);


        // 客户端重定向
        resp.sendRedirect("forward1");

    }




}

