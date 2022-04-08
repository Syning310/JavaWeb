package controller;

import domain.*;
import service.OrderService;

import javax.servlet.http.HttpSession;
import java.util.*;

public class OrderController {
    
    private OrderService orderService;
    
    // 结账页面
    public String checkout(HttpSession session) {
        
        User user = (User)session.getAttribute("currUser");
        
        // 生成一张订单
        // 生成 UUID 随机码，生成日期，结账用户 id，总金额，未发货状态
        String uuid_orderNo = UUID.randomUUID().toString();
        Order order = new Order();
        order.setOrderNo(uuid_orderNo);  
        order.setOrderDate(new Date());
        order.setOrderUser(user);
        order.setOrderMoney(user.getCart().getTotalMoney());  // 购物车的总价格
        order.setOrderStatus("未发货");
        
        // 订单详情，因为是还未付款的状态，所以订单详情是不在数据表中的，所以订单详情需要利用,用户的购物车来一个一个地设置
        Cart cart = user.getCart();
        Map<Integer, ShoppingItem> cartMap = cart.getCartMap();
        Collection<ShoppingItem> shoppingItems = cartMap.values();
                
        List<OrderDetails> orderDetailsList = new ArrayList<>();   // 承载订单详情，最后赋值给订单的 List<OrderDetails>
        for (ShoppingItem shoppingItem : shoppingItems) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setBook(shoppingItem.getBook());  // 购买什么书
            orderDetails.setBuyCount(shoppingItem.getBuyCount());   // 这本书购买多少本
            orderDetails.setOrderBean(order);  // 属于上面生成的订单
            orderDetailsList.add(orderDetails);  // 添加进集合
        }
        
        order.setOrderDetailsList(orderDetailsList);  // 设置给当前订单
        
        session.setAttribute("order", order);  // 将当前订单设置到会话作用域中
        
        // 将订单记录、每一条订单详情 添加到数据库，然后删除购物车的记录
        orderService.addOrderAndDetailDelCart(order);
        
        return "cart/checkout";
    }



    
    
    // 获取我的订单页面
    public String getOrderList(HttpSession session) {
        
        User user = (User)session.getAttribute("currUser");
        
        // 传入用户 id 获取订单列表
        List<Order> orderList = orderService.getOrderList(user);
        
        // 将取得的订单列表设置到用户中
        user.setOrderList(orderList);
        
        //session.setAttribute("currUser", user);
        
        return "order/order";
    }
    
    
    
    
    
    
    
}
