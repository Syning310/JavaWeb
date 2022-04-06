package service;

import dao.OrderDetailsDAO;
import domain.OrderDetails;
import domain.ShoppingItem;

import java.util.List;

public class OrderDetailsService {
    
    private OrderDetailsDAO orderDetailsDAO;
    
    // 1、添加一项购物项到订单详情表，传入购物项 和 订单的编号 orderNo
    public Integer addOrderDetails(OrderDetails orderDetails) {
        String sql = "insert into order_details values (null, ?, ?, ?)";
        // 传入的是，购书的 id、 购物的数量、 对应的订单 id
        return orderDetailsDAO.update(sql, orderDetails.getBook().getId(),
                orderDetails.getBuyCount(), orderDetails.getOrderBean().getOrderNo());   
    }
    
    
    
    // 2、传入订单编号，获取所有订单详情列表
    public List<OrderDetails> getOrderListByOrderNo(String orderNo) {
        String sql = "select id, buyCount from orderBean where orderBean=?";
        return orderDetailsDAO.queryMutil(sql, OrderDetails.class, orderNo);
    }
    
    
}
