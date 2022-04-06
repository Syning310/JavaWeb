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
    
    
    
    
    
}
