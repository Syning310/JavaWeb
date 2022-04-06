package domain;



// 订单详情表
public class OrderDetails {
    private Integer id;         
    private Book book;           // 书
    private Integer buyCount;    // 买了多少本
    private Order orderBean;     // 属于哪个订单的详情，可能多个清单详情都属于同一个订单
    
    
    public OrderDetails() {}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Order getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(Order orderBean) {
        this.orderBean = orderBean;
    }
}
