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

    private FruitService fserv = new FruitService();
    
    private int DisplayOnce = 5;  // 一次显示最大页数
    
    // service替换成doGet也能够正常运行
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        Integer pageNo = 1;  // 默认显示第一页
        
        
        String oper = req.getParameter("oper");
        // 如果 oper != null，说明 通过表单的查询按钮点击而来的；如果oper为空，说明不是通过表单的查询按钮而来的
        
        String keyword = null;
        if (oper != null && "search".equals(oper)) {
            // 说明是点击关键字查询而来的请求
            // 此时，pageNo应该还原为1，keyword应该从请求参数中获取
            pageNo = 1;
            keyword = req.getParameter("keyword");
            if (keyword == null) {  // 从关键字查询按钮而来，但是value为空，说明只点击按钮，没有填入关键字
                keyword = "";
            }
            req.getSession().setAttribute("keyword", keyword);  // 将关键字设置到会话作用域中
        } else {

            // 说明此处不是点击关键字查询而来的请求 (如：点击上一页，下一页)
            // 那么此时keyword应该从session作用域中获取
            Object keywdObj = req.getSession().getAttribute("keyword");
            if (keywdObj != null) {
                keyword = (String)keywdObj;
            } else {
                keyword = "";
            }
            
            // 如果请求有 “pageNo” 这个参数，说明是点击 首页，上一页，下一页，尾页 而来的
            String pageNoStr = req.getParameter("pageNo");
            if (pageNoStr != null) {  // 如果页码为空，说明这次响应不是点击 首页，上一页，下一页，尾页 而来的; 则不进入语句
                pageNo = Integer.parseInt(pageNoStr); // 将应该显示的页码，按照逻辑设置
            }
            
        }
        
        

            

        // 需要保证 pageNo 不等于0和不小于0，数据库没有从第 -1(负数) 行开始检索的
        // 将页码存放到会话作用域
        req.getSession().setAttribute("pageNo", pageNo);

        
        // 总记录数，按照模糊关键字查询过后，得到的行数
        int fruitCount = fserv.getColNums(keyword);  // 传入模糊关键字
        // 总页数 = (总记录数 + 5 - 1) / 5
        int pageCount = (fruitCount + 5 - 1) / 5;

        // 将总页数保存在会话作用域中
        req.getSession().setAttribute("pageCount", pageCount);
        
        
        
        
        // 现在分页显示，默认从第一页开始显示，如果从request中获取的pageNo参数不为空，说明是点击上一页，下一页而来的，则按照参数显示
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


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        doGet(req, resp);
    }
}
