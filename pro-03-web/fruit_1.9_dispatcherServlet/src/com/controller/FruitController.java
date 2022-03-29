package com.controller;

import com.domain.Fruit;
import com.myspringmvc.ViewBaseServlet;
import com.service.FruitService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;


public class FruitController extends ViewBaseServlet {
    
    // 之前FruitServlet是一个组件，那么它的init方法一定会被调用
    // 之前init方法中内部是：super.init(); 而现在没有 /fruit.do 所以 ViewBaseServlet 中的init方法不会被调用导致空指针异常
    
    
    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) throws ServletException {
        this.servletContext = servletContext;
        super.init(servletContext);
    }
    
    
    
    private FruitService fserv = new FruitService();
    private int DisplayOnce = 5;  // 一次显示最大页数

    
    private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        // 1、设置编码
        //req.setCharacterEncoding("UTF-8");

        // 2、设置当前页，默认值是1
        Integer pageNo = 1;

        // 3、获取oper的值，如果oper有值，说明这次请求是从 查询表单的按钮而来的
        String oper = req.getParameter("oper");


        String keyword = null;
        if (oper != null && "search".equals(oper)) {  // 4、点击查询按钮而来的

            pageNo = 1;
            keyword = req.getParameter("keyword");  // 5、取得查询文本框中的关键字
            if (keyword == null) {
                keyword = "";  // 6、如果在查询文本框中没有输入任何内容，将查询关键字设为空字符串；否则查询时会拼接成 %null%  我们期望的是 %%
            }
            // 6、将关键字设置到会话作用域中，因为如果已经有关键字了，显示上一页，下一页应该以关键字搜索的标准来显示

            // 7、如果是点击查询按钮而来的，那么pageNo一定是从第1页开始查询
            req.getSession().setAttribute("keyword", keyword);
        } else {
            // 不是点击查询按钮的业务逻辑

            Object keywdObj = req.getSession().getAttribute("keyword");  // 1、不是点击查询按钮的话，那就基于先有的关键字进行查询
            if (keywdObj != null) {
                keyword = (String)keywdObj;  // 2、取出关键字
            } else {
                keyword = "";  // 3、如果关键字为空，设为空字符串
            }


            // 4、此处是点击上一页下一页，首页尾页，而来的，从pageNoStr中获取页面的参数
            String pageNoStr = req.getParameter("pageNo");
            if (pageNoStr != null) {
                pageNo = Integer.parseInt(pageNoStr);
            }
            // 5、如果获取不到，默认为1
        }


        req.getSession().setAttribute("pageNo", pageNo);



        int fruitCount = fserv.getColNums(keyword);
        // 总页数 = (总记录数 + 5 - 1) / 5
        int pageCount = (fruitCount + 5 - 1) / 5;

        // 将总页数保存在会话作用域中
        req.getSession().setAttribute("pageCount", pageCount);


        {
            List<Fruit> lst = fserv.getPageList(pageNo, keyword);
            // 保存到 session 作用域
            HttpSession session = req.getSession();
            session.setAttribute("fruitList", lst);  // 设置到会话作用域中，Thymeleaf会进行动态渲染

        }


        // 处理模板，第一个参数是视图名称，thymeleaf会将这个 逻辑视图名称 对应到 物理视图名称 上去
        // 逻辑视图：index
        // 物理视图:    view-prefix + 逻辑视图名称 + view-suffix
        // 所以真实的视图名称是:  /       index         .html
        super.processTemplate("index", req, resp);

    }



    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
        int i = fserv.update(name, price, count, remark, upname);
        System.out.println(i > 0 ? "修改成功" : "修改失败");

        // 4、资源跳转
        //super.processTemplate("index", req, resp);  // 这里的跳转，不会更新界面数据，因为只是跳转到index.html界面，而不是重新渲染，需要用url响应IndexServlet渲染数据
        
        resp.sendRedirect("fruit.do");   // 使用客户端重定向，目的是给IndexServlet类发请求，重新获取fruitList，

    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // 获取表单传来的参数
        String name = req.getParameter("name");
        Double price = Double.parseDouble(req.getParameter("price"));
        Integer count = Integer.parseInt(req.getParameter("count"));
        String remark = req.getParameter("remark");

        int i = fserv.insertSingle(name, price, count, remark);
        System.out.println(i > 0 ? "添加成功" : "添加失败");


        // 客户端重定向
        resp.sendRedirect("fruit.do");


    }

    private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取参数
        String name = req.getParameter("name");

        if (name != null) {
            int i = fserv.delete(name);

            System.out.println(i > 0 ? "删除成功" : "删除失败");
        }

        // 必须使用客户端重定向
        resp.sendRedirect("fruit.do");


    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取name
        String name = req.getParameter("name");

        if (name != null) {
            Fruit fruit = fserv.querySingle(name);

            req.setAttribute("fruit", fruit);
            super.processTemplate("edit", req, resp);

        }
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }
}
