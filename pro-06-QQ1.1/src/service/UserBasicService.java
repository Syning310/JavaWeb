package service;

import dao.UserBasicDAO;
import domain.UserBasic;

import java.util.List;

public class UserBasicService {
    private UserBasicDAO userBasicDAO = null;
    
    
    // DAO 层
    
    // 1、根据账号和密码获取特定用户信息
    public UserBasic getUserBasic(String loginId, String pwd) {
        String sql = "select id, loginId, nickName, pwd, headImg from user_basic where loginId=? and pwd=md5(?)";
        return userBasicDAO.querySingle(sql, UserBasic.class, loginId, pwd);
    }
    
    
    // 2、根据账号，获取指定用户的所有好友列表
    public List<UserBasic> getUserBasicFriendList(UserBasic userBasic) {
        String sql = "select u2.id, u2.loginId, u2.nickName, u2.pwd, u2.headImg " +
                "from user_basic u1 left join friend on u1.id=friend.uid " +
                "inner join user_basic u2 on u2.id=friend.fid where u1.loginId=?";
        return userBasicDAO.queryMutil(sql, UserBasic.class, userBasic.getLoginid());
    }
    
 
    // 3、根据传入的 id 获取指定的用户
    public UserBasic getUserBasicByUserId(Integer id) {
        String sql = "select id, loginId, nickName, pwd, headImg from user_basic where id=?";
        return userBasicDAO.querySingle(sql, UserBasic.class, id);
    }
    
    
    
    
    
}
