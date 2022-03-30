package com.controller;

import com.domain.Fruit;
import com.service.FruitService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


// 变成一个普通的类
public class FruitController {
    
    private FruitService fserv = new FruitService();
    private int DisplayOnce = 5;  // 一次显示最大页数

    
    private String index(String oper, String keyword, Integer pageNo, HttpServletRequest req) {
        
        if (pageNo == null) {
            pageNo = 1;
        }
        
        if (oper != null && "search".equals(oper)) {  

            pageNo = 1;

            if (keyword == null) {
                keyword = ""; 
            }
            
            req.getSession().setAttribute("keyword", keyword);
        } else {
    
            Object keywdObj = req.getSession().getAttribute("keyword"); 
            if (keywdObj != null) {
                keyword = (String)keywdObj;  
            } else {
                keyword = ""; 
            }
        }
        req.getSession().setAttribute("pageNo", pageNo);
        
        int fruitCount = fserv.getColNums(keyword);
        int pageCount = (fruitCount + 5 - 1) / 5;
        req.getSession().setAttribute("pageCount", pageCount);
        
        {
            List<Fruit> lst = fserv.getPageList(pageNo, keyword);
            // 保存到 session 作用域
            HttpSession session = req.getSession();
            session.setAttribute("fruitList", lst);  

        }

        return "index";
    }



    private String update(String name, Double price, Integer count, String remark, String upname) {
        
        int i = fserv.update(name, price, count, remark, upname);
        System.out.println(i > 0 ? "修改成功" : "修改失败");
  
        return "redirect:fruit.do";

    }

    private String add(String name, Double price, Integer count, String remark) {

        // 获取表单传来的参数，从别处传入
//        String name = req.getParameter("name");
//        Double price = Double.parseDouble(req.getParameter("price"));
//        Integer count = Integer.parseInt(req.getParameter("count"));
//        String remark = req.getParameter("remark");

        int i = fserv.insertSingle(name, price, count, remark);
        System.out.println(i > 0 ? "添加成功" : "添加失败");


        // 客户端重定向
        //resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";

    }

    private String del(String name) {

        // 获取参数
        //String name = req.getParameter("name");

        if (name != null) {
            int i = fserv.delete(name);

            System.out.println(i > 0 ? "删除成功" : "删除失败");
            
            
        }

        // 必须使用客户端重定向
        //resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";

    }

    private String edit(String name, HttpServletRequest req) {

        // 获取name，从别处传入
        //String name = req.getParameter("name");

        if (name != null) {
            Fruit fruit = fserv.querySingle(name);

            req.setAttribute("fruit", fruit);  // 将要修改的行放入会话中
            //super.processTemplate("edit", req, resp);
            return "edit";
        }
        return "error";
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        service(req, resp);
//    }
}
