package service;

import dao.ShoppingItemDAO;
import domain.Book;
import domain.Cart;
import domain.ShoppingItem;
import domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingItemService {
    
    private ShoppingItemDAO shoppingItemDAO;
    private BookService bookService;
    
    
    // 1、传入 购物车项，购物车项中有字段: 图书的 id 、购买的数量、用户的 id 
    // 一个 ShoppingItem 就是一本书的购物项记录
    public int addShoppingItem(ShoppingItem shoppingItem) {
        String sql = "insert into shopping_item values (null, ?, ?, ?)";
        return shoppingItemDAO.update(sql, shoppingItem.getBook().getId(),
                shoppingItem.getBuyCount(), shoppingItem.getUserBean().getId());
    }
    
    
    // 2、若登录的用户在购物车中已经有书，则修改购买的数量 +1，这个 +1 的动作在判断方法中已经实现了
    public int updateShoppingItem(ShoppingItem shoppingItem) {
        String sql = "update shopping_item set buyCount=? where book_id=?";  // 设置数量，过滤图书的 id
        return shoppingItemDAO.update(sql, shoppingItem.getBuyCount(), shoppingItem.getBook().getId());
    }
    
    
    // 3、判断当前用户的购物车中是否有这本书的 id (是否已经添加过了)，有的话调用 updateCartItem， 没有的话则 addCartItem
    public void addOrUpdateShoppingItem(ShoppingItem shoppingItem, Cart cart) {
        // 1、如果当前用户的购物车中，已经存在此图书了，那么直接将购物项表中这本图书的数量 +1
        // 2、否则，在我的购物车中新增一个这本图书的 CartItem ，数量是 1
        
        if (cart != null) {
            // 获取购物车的 Map 容器
            Map<Integer, ShoppingItem> cartMap = cart.getCartMap();
            
            // 因为购物车的 Map 容器 key 值是添加的图书的 id，value 值是图书
            // 所以购物车的 Map 容器中，key 如果已经有想要添加的图书的 id，说明之前添加过(本书在购物车中)，所以这次只需要将 购物项表 中的购买数量 +1 即可
            if (cartMap.containsKey(shoppingItem.getBook().getId())) {
                ShoppingItem tmp = cartMap.get(shoppingItem.getBook().getId());
                tmp.setBuyCount(tmp.getBuyCount() + 1);
                updateShoppingItem(tmp);
            } else {
                addShoppingItem(shoppingItem);
            }
            
        } else {
            // 如果连购物车都没有
            addShoppingItem(shoppingItem);
        }
    }
    
    
    // 4、传入用户的 id，返回该用户所有的购物项列表，但是每个购物项中只有 id 和 buyCount 有值，因为数据表的 图书 与 用户 存放的是它们的 id 值
    // 而 domain 中的购物项类(ShoppingItem)中的 图书 和 用户 字段是自定义数据类型
    public List<ShoppingItem> getShoppingItemListByUserId(Integer userId) {
        String sql = "select id, buyCount from shopping_item where userBean=?";
        return shoppingItemDAO.queryMutil(sql, ShoppingItem.class, userId);
    }
    
    
    
    // 5、返回指定用户的购物车列表的信息
    public List<ShoppingItem> getShoppingItemListByUser(User user) {  
        // 1、只能得到购物项的 id 和购买数量
        // 2、此时的购物项中，Book 和 User 还是为空，需要进一步设置
        List<ShoppingItem> shoppingItemList = getShoppingItemListByUserId(user.getId());
        
        // 3、从购物项集合中分别取出购物项，传入购物项的 id，得到图书的 id，然后再查找图书的完整信息赋值给购物项
        for (ShoppingItem shoppingItem : shoppingItemList) {
            // ① 获取购物项的 id
            Integer itemId = shoppingItem.getId();
            
            // ② 传入购物项的 id，获取这条购物项的图书的 id (因为在表中保存的图书是图书的 id，保存的用户是用户的 id)
            Integer bookId = (Integer)getBookId(itemId);  
            
            // ③ 利用 bookId 获取 book 的完整信息
            Book book = bookService.getBookByBookId(bookId);
            
            // ④ 将 book 的完整信息，设置到购物项中的 book 字段中
            shoppingItem.setBook(book);
            // ⑤ 因为这个购物项是属于传入的用户的，所以直接把用户设置到这条购物项中即可
            
            shoppingItem.setUserBean(user);
        }
        
        return shoppingItemList;
    }
    
    
    // 6、给定购物项的 id ，返回图书的 id 
    public Object getBookId(Integer id) {
        String sql = "select book_id from shopping_item where id=?";
        return shoppingItemDAO.queryScalar(sql, id);   
    }
    
    
    // 7、传入指定用户，返回指定用户的购物车
    public Cart getCartByUser(User user) {
        // 得到指定用户的购物车列表信息
        List<ShoppingItem> shoppingItemList = getShoppingItemListByUser(user);
        
        Map<Integer, ShoppingItem> cartMap = new HashMap<>();
        
        for (ShoppingItem shoppingItem : shoppingItemList) {
            // 购物车 map 中存放的 k-v 为： key 是图书的 id， value 是 购物项
            cartMap.put(shoppingItem.getBook().getId(), shoppingItem);
        }
        
        // 创建一个购物车
        Cart cart = new Cart();
        
        cart.setCartMap(cartMap);
        
        return cart;
    }
    
    
    // 8、传入指定用户 id、指定图书的 id，删除它的购物项的记录
    public Integer delShoppingItemByUserIdAndBookId(Integer userId, Integer bookId) {
        String sql = "delete from shopping_item where userBean=? and book_id=?";
        // 在购物项表中，存储的是用户的 id，和图书的 id
        return shoppingItemDAO.update(sql, userId, bookId);
    }
    
    
    // 9、传入指定的购物项 id，删除数据库中的对应的记录
    public Integer delShoppingItemById(Integer id) {
        String sql = "delete from shopping_item where id=?";
        return shoppingItemDAO.update(sql, id);
    }



    // 通过 购物项的 id ，修改购买的数量
    public int updateShoppingItem(Integer shoppingItemId, Integer buyCount) {
        String sql = "update shopping_item set buyCount=? where id=?";  // 设置数量，过滤图书的 id
        return shoppingItemDAO.update(sql, buyCount, shoppingItemId);
    }
    
}
