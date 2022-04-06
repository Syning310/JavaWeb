package service;

import dao.OrderDAO;
import domain.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public class OrderService {
    
    private OrderDAO orderDAO;
    private OrderDetailsService orderDetailsService;
    private ShoppingItemService shoppingItemService;


    // 1、添加一条订单记录，传入订单对象
    public Integer addOrder(Order order) {
        String sql = "insert into `order` values (null, ?, ?, ?, ?, ?)";
        return orderDAO.update(sql, order.getOrderNo(), order.getOrderDate(),
                order.getOrderUser().getId(), order.getOrderMoney(), order.getOrderStatus());
    }
    
    
    
    // 2、添加一条订单记录，添加对应数量的订单详情记录，清空购物车
    public void addOrderAndDetailDelCart(Order order) {
        
        // 1、订单表添加一条记录
        // 2、订单详情表添加对应条记录
        // 3、购物车项表中需要删除对应的记录
        
        // 第一步: 添加一条订单记录
        addOrder(order);  
        
        // 第二步: 每一条将订单详情表添加到数据库(一个订单可能会买多本书，每一项书种类的购买都是一条订单详情)
        List<OrderDetails> orderDetailsList = order.getOrderDetailsList();
        for (OrderDetails orderDetails : orderDetailsList) {
            orderDetailsService.addOrderDetails(orderDetails);
        }
        
        // 第三步: 删除数据库中每一条购物车项，清空购物车
        Map<Integer, ShoppingItem> cartMap = order.getOrderUser().getCart().getCartMap();  // 获取订单用户的购物车的 Map 容器
        Collection<ShoppingItem> shoppingItems = cartMap.values();
        for (ShoppingItem shoppingItem : shoppingItems) {
            // 分别传入购物项的 id 删除数据库的对应的购物项
            shoppingItemService.delShoppingItemById(shoppingItem.getId());
        }
      
    }
    
    // 3、传入订单编号取得订单的 id
    public Object getOrderId(String uuid_orderNo) {
        String sql = "select id from order where orderNo=?";
        return orderDAO.update(sql, uuid_orderNo);
    }
    
    
    
    // 4、传入用户，获取该用户的订单列表
    private List<Order> getOrderListByUser(User user) {
        String sql = "select id, orderNo, orderDate, orderMoney, orderStatus from `order` where orderUser=?";
        return orderDAO.queryMutil(sql, Order.class, user.getId());
    }
    
    
    // 5、因为从数据库中存储的 orderUser 是 int 类型，这里需要详细描述，并给 orderDetail 补全
    public List<Order> getOrderList(User user) {
        
        List<Order> orderList = getOrderListByUser(user);
        
        // 因为是通过 user 获取的订单，所以用户就是传入的 user
        for (Order order : orderList) {
            order.setOrderUser(user);
            
            // 通过订单编号，取得所有的订单详情
            //String orderNo = order.getOrderNo();
        }
        
        return orderList;
    }
    
    
    
    
    
    
    
    
}
