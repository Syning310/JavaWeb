package domain;




// 购物车项，一条记录代表一条购物项，
public class ShoppingItem {
    private Integer id;      
    private Book book;           // 购物车中存放的什么书，一个书的种类
    private Integer buyCount;    // 这本书想要购买多少本
    private User userBean;       // 属于哪个用户


    public ShoppingItem() {}


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

    public User getUserBean() {
        return userBean;
    }

    public void setUserBean(User userBean) {
        this.userBean = userBean;
    }
}
