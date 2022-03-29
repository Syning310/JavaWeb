package com.servlets;


import com.domain.Fruit;
import com.myspringmvc.ViewBaseServlet;
import com.service.FruitService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateServlet extends ViewBaseServlet {

    private FruitService fruitService = new FruitService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 1、设置编码
        req.setCharacterEncoding("utf-8");
        
        // 2、获取参数
        String name = req.getParameter("name");
        Double price = Double.parseDouble(req.getParameter("price"));
        Integer count = Integer.parseInt(req.getParameter("count"));
        String remark = req.getParameter("remark");
        
        String upname = req.getParameter("upname");  // 需要修改的name
        
        //String upname = ((Fruit)req.getAttribute("fruit")).getName();  // 在会话级别的作用域中找Fruit，是找不到的;因为在EditServlet中跳转资源过了
        
        
        
        // 3、执行更新
        int i = fruitService.update(name, price, count, remark, upname);
        System.out.println(i > 0 ? "修改成功" : "修改失败");
        
        // 4、资源跳转
        //super.processTemplate("index", req, resp);  // 这里的跳转，不会更新界面数据，因为只是跳转到index.html界面，而不是重新渲染，需要用url响应IndexServlet渲染数据

        // 使用服务器内部跳转(或者客户端重定向)依然可以修改页面上的信息；最好是使用客户端重定向
        
        resp.sendRedirect("app");   // 使用客户端重定向，目的是给IndexServlet类发请求，重新获取fruitList，
                                    // 然后覆盖到原来session中的fruitList，这样首页上现实的session的数据才是最新的
        
        //req.getRequestDispatcher("app").forward(req, resp);  // 如果是服务器内部跳转，客户端那里不知道跳转了，
                                                               // 所以依然是update.do的状态；所以最好使用客户端重定向
        
        // 让客户端去响应 /app 的 url ，web.xml 配置文件中会找到 IndexServlet 类，重新访问fruit表，取出新的fruitList，覆盖原来保存在session作用域中的fruitList
        // 然后重新渲染index.html
        
        
        
    }
}
