package com.servlets;


import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Demo01 extends HttpServlet {

    @Override
    public void init() throws ServletException {
        ServletConfig fig = getServletConfig();

        System.out.println(fig.getInitParameter("hello"));

        ServletContext text = getServletContext();
        System.out.println(text.getInitParameter("contextConfigLocation"));
        
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletConfig fig = getServletConfig();

        System.out.println(fig.getInitParameter("hello"));

        ServletContext text = getServletContext();
        System.out.println(text.getInitParameter("contextConfigLocation"));



    }
}


