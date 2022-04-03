package bo;

import domain.UserBasic;
import service.UserBasicService;

import java.util.List;

public class UserBasicBO {
    
    private UserBasicService userBasicService = null;


    // BO 业务逻辑

    // 1、登录验证
    public UserBasic login(String loginId, String pwd) {
        return userBasicService.getUserBasic(loginId, pwd);
    }
    
    
    // 2、获取好友列表
    public List<UserBasic> getUserBasicFriendList(UserBasic userBasic) {
        return userBasicService.getUserBasicFriendList(userBasic);
    }
    
    
    // 3、根据 id 获取指定用户的信息
    public UserBasic getUserBasicByUserId(Integer id) {
        return userBasicService.getUserBasicByUserId(id);
    }
    
    
}
