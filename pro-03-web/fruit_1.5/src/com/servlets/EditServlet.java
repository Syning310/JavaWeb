package com.servlets;

import com.domain.Fruit;
import com.myspringmvc.ViewBaseServlet;
import com.service.FruitService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditServlet extends ViewBaseServlet {

    private FruitService fruitDao = new FruitService();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 获取name
        String name = req.getParameter("name");
        
        if (name != null) {
            Fruit fruit = fruitDao.querySingle(name); 
            
            req.setAttribute("fruit", fruit);
            super.processTemplate("edit", req, resp);
            
        }
        
        
        
    }
}
