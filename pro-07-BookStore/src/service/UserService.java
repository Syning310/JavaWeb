package service;

import dao.UserDAO;
import domain.User;

public class UserService {
    
    private UserDAO userDAO;
    
    
    // 1、根据 用户名 密码 返回对象
    public User getUserByNamePwd(String name, String pwd) {
        String sql = "select id, name, pwd, email, role from `user` where name=? and pwd=MD5(?)";
        return userDAO.querySingle(sql, User.class, name, pwd);
    }
    
    
    // 2、注册用户
    public Integer addUser(User user) {
        String sql = "insert into `user` values (null, ?, md5(?), ?, ?)";
        return userDAO.update(sql, user.getName(), user.getPwd(), user.getEmail(), user.getRole());
    }
    
    // 3、检查用户是否存在
    public User getUserByName(String name) {
        String sql = "select id, name, pwd, email, role from `user` where name=?";
        return userDAO.querySingle(sql, User.class, name);
    }
    
    
}
