package com.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class session_1 extends HttpServlet {

    // 演示从HttpSession保存的作用域中获取数据
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Object uname = req.getSession().getAttribute("uname");
        System.out.println(uname);

    }
}
