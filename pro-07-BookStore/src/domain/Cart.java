package domain;



import java.util.Map;
import java.util.Set;

// 此类不和数据库的表对应，而是作为当前登录用户的购物车

public class Cart {
    // 购物车中，购物车项的集合，这个 Map 集合中的 key 是 Book 的 id，value 是 一个购物项 ShoppingItem
    private Map<Integer, ShoppingItem> cartMap;    
    private Double totalMoney;    // 购物车的总金额
    private Integer totalCount;   // 购物车中购物项的总数量
    private Integer totalBookCount; // 购物车中书本的总数量，不是种类的总数量
    
    public Cart() {}


    public Map<Integer, ShoppingItem> getCartMap() {
        return cartMap;
    }

    public void setCartMap(Map<Integer, ShoppingItem> cartMap) {
        this.cartMap = cartMap;
    }

    public Double getTotalMoney() {
        totalMoney = 0D;
        if (cartMap != null) {
            Set<Map.Entry<Integer, ShoppingItem>> entries = cartMap.entrySet();
            for (Map.Entry<Integer, ShoppingItem> shoppingCartEntry : entries) {
                ShoppingItem shoppingCart = shoppingCartEntry.getValue();
                totalMoney += shoppingCart.getBook().getPrice() * shoppingCart.getBuyCount();   // 购物项类中的 书的价格 * 购物的数量
            }
        }
//        // 保留两位小数并四舍五入
//        BigDecimal bigDecimal = new BigDecimal(totalMoney);
//        totalMoney = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  // 2表示保留两位小数
        return totalMoney;
    }


    // 返回书种类的数量
    public Integer getTotalCount() {
        totalCount = 0;
        if (cartMap != null && cartMap.size() > 0) {
            totalCount = cartMap.size();   // 返回的是购买的书本种类的数量
        }
        return totalCount;
    }
    
    
    
    // 返回购买书的总数量
    public Integer getTotalBookCount() {
        totalBookCount = 0;
        if (cartMap != null && cartMap.size() > 0) {
            for (ShoppingItem shoppingItem : cartMap.values()) {
                totalBookCount += shoppingItem.getBuyCount();  // 累加每个书种类的数量
            }
        }
        return totalBookCount;
    }
    

    
    
    
    
    
}
