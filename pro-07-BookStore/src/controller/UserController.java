package controller;

import domain.Cart;
import domain.User;
import service.ShoppingItemService;
import service.UserService;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class UserController {
    
    private UserService userService;
    private ShoppingItemService shoppingItemService;
    
    // 验证登录的用户
    public String login(String uname, String pwd, HttpSession session) {

        User user = userService.getUserByNamePwd(uname, pwd);
        
        if (user != null) {
            
            // 登录成功后，获取当前登录用户的购物车，并设置到当前用户上
            Cart cart = shoppingItemService.getCartByUser(user);
            user.setCart(cart);
            
            // 登录成功将当前用户保存到 session 保存作用域
            session.setAttribute("currUser", user);
            
            return "redirect:book.do?operate=index";
        }
        
        return "user/login";
    }
    
    
    
    
    // 注册用户
    public String regist(String name, String pwd, String pwdAgain, String email, String verification, HttpSession session, HttpServletResponse response) {
        
        // 获取验证码
        Object verifyCode = session.getAttribute("KAPTCHA_SESSION_KEY");
        
        // 先对比验证码是否正确，在进行下一步操作
        if (verifyCode == null || !verifyCode.equals(verification)) { 
            // 验证码错误
            try {
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("<script language='javascript'>alert('验证码不正确!');window.location.href='page.do?operate=page&page=user/regist';</script>");
                // 因为上面已经跳转了，所以在这里不需要return 
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (verifyCode.equals(verification)) {
                User user = new User();
                // 设置名字，密码，邮箱，角色
                user.setName(name);
                user.setPwd(pwd);
                user.setEmail(email);
                user.setRole(0);
                userService.addUser(user);
                
            }
        }
        
        return "user/login";
    }
    
    
    
    // 检查用户是否存在
    public String ckName(String name) {
        
        User user = userService.getUserByName(name);
        
        if (user != null) {
            // 用户名已经被占用，不允许注册
            return "json:{'name':'1'}";
        } else {
            // 用户名不存在，允许注册
            return "json:{'name':'0'}";
        }
        
        
    }
    
    
    
    
    
    
    
    
}
