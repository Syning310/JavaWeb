package com.servlets;

import com.domain.Fruit;
import com.myspringmvc.ViewBaseServlet;
import com.service.FruitService;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class IndexServlet extends ViewBaseServlet {

    FruitService fserv = new FruitService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        
        // 获取页码
        Integer pageNo = 1;  // 默认显示第一页
        String pageNoStr = req.getParameter("pageNo");
        if (pageNoStr != null) {
            pageNo = Integer.parseInt(pageNoStr);
        }

        // 需要保证 pageNo 不等于0和不小于0，数据库没有从第 -1(负数) 行开始检索的
        // 将页码存放到会话作用域
        req.getSession().setAttribute("pageNo", pageNo);

        
        // 总记录数
        int fruitCount = fserv.getColNums();
        // 总页数 = (总记录数 + 5 - 1) / 5
        int pageCount = (fruitCount + 5 - 1) / 5;

        // 将总页数保存在会话作用域中
        req.getSession().setAttribute("pageCount", pageCount);
        
        
        
        // 这段代码是直接显示数据库全部数据
//        {
//            List<Fruit> lst = fserv.list();
//
//            // 保存到session作用域
//            HttpSession session = req.getSession();
//            session.setAttribute("fruitList", lst);
//        }
//      

        
        // 现在分页显示，默认从第一页开始显示，如果从request中获取的pageNo参数不为空，则按照参数显示
        {
            List<Fruit> lst = fserv.getPageList(pageNo);
            // 保存到 session 作用域
            HttpSession session = req.getSession();
            session.setAttribute("fruitList", lst);
            
        }
        
        

                
        

        // 处理模板，第一个参数是视图名称，thymeleaf会将这个 逻辑视图名称 对应到 物理视图名称 上去
        // 逻辑视图：index
        // 物理视图:    view-prefix + 逻辑视图名称 + view-suffix
        // 所以真实的视图名称是:  /       index         .html
        super.processTemplate("index", req, resp);
        
        
    }
}
