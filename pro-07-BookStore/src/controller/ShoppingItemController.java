package controller;

import domain.Book;
import domain.Cart;
import domain.ShoppingItem;
import domain.User;
import service.ShoppingItemService;

import javax.servlet.http.HttpSession;

public class ShoppingItemController {
    
    private ShoppingItemService shoppingItemService;
    
    
    // 接收图书的 id 将指定的图书，添加到当前用户的购物车中
    public String addShoppingItem(Integer bookId, HttpSession session) {
        
        User user = (User)session.getAttribute("currUser");
        
        // 将指定的图书添加到用户的购物车当中
        ShoppingItem shoppingItem = new ShoppingItem();
        
        // 获取的参数是图书的 id，所以利用 bookId 封装一个图书的类
        Book book = new Book();
        book.setId(bookId);
        
        // 给购物项赋值
        shoppingItem.setBook(book);
        shoppingItem.setBuyCount(1);   // 购买的数量，默认为 1
        shoppingItem.setUserBean(user);
        
        
        // 传入购物项，和当前登录用户的购物车
        shoppingItemService.addOrUpdateShoppingItem(shoppingItem, user.getCart());


        return "redirect:shoppingItem.do?operate=index";   // 令客户端重定向，也就会提交以上的事务
    }
    
    
    // 加载当前用户的购物车信息
    public String index(HttpSession session) {
        
        // 获取当前登录的用户
        User user = (User)session.getAttribute("currUser");
        
        // 传入当前用户，获取当前用户的购物车信息
        Cart cart = shoppingItemService.getCartByUser(user);
        
        // 设置给当前用户
        user.setCart(cart);
        
        return "index";
        
    }
    
    
    
    public String editShoppingItem(Integer shoppingItemId, Integer buyCount, HttpSession session) {
        
        // 因为渲染 cart.面时，html 页不是从数据库中取出列表，而是直接进入
        User user = (User)session.getAttribute("currUser");
        
        
        if (buyCount >= 1) {
            shoppingItemService.updateShoppingItem(shoppingItemId, buyCount);

            // 更新当前用户的购物车
            Cart cart = shoppingItemService.getCartByUser(user);

            // 设置给当前用户
            user.setCart(cart);
        }
        

        return "cart/cart";
    }
    
    
    
    
    
    
}
