package controller;

import com.google.gson.Gson;
import domain.Cart;
import domain.User;
import service.ShoppingItemService;

import javax.servlet.http.HttpSession;

public class CartController {
    
    ShoppingItemService shoppingItemService;
    
    public String cart() {
        
        return "cart/cart";
    } 
    
    
    public String cartInfo(HttpSession session) {
        
        User user = (User)session.getAttribute("currUser");


        Cart cart = shoppingItemService.getCartByUser(user);
        user.setCart(cart);
        
        Gson gson = new Gson();
        String cartJsonStr = gson.toJson(cart);
        
        return "json:" + cartJsonStr;
    }
    
    
    
    
}
