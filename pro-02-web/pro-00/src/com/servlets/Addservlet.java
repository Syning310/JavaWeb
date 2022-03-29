package com.servlets;


import com.service.FruitService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Addservlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // post方式下，设置编码，防止中文乱码
        // get方式目前不需要设置编码；
        request.setCharacterEncoding("UTF-8");


        // 客户端发来的请求被封装成对象，服务端接收(HttpServletRequest)
        String name = request.getParameter("name");
        Double price = Double.parseDouble(request.getParameter("price"));
        Integer count = Integer.parseInt(request.getParameter("count"));
        String remark = request.getParameter("remark");


//        System.out.println("name = " + name);
//        System.out.println("price = " + price);
//        System.out.println("count = " + count);
//        System.out.println("remark = " + remark);


        // 经过排查测试，德鲁伊的连接池无法使用
        FruitService furService = new FruitService();
        int update = furService.update(name, price, count, remark);
        System.out.println(update + " rows语句受影响");

    }


}
