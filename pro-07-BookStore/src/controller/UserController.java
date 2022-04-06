package controller;

import domain.Cart;
import domain.User;
import service.ShoppingItemService;
import service.UserService;


import javax.servlet.http.HttpSession;

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
    
    
    
    
    
    
    
    
    
    
    
    
    
}
