package controller;

import BO.TopicBO;
import BO.UserBasicBO;
import domain.Topic;
import domain.UserBasic;

import javax.servlet.http.HttpSession;
import java.util.List;

public class UserController {
    
    
    private UserBasicBO userBO = null;
    private TopicBO topicBO = null;
    
    // 登录验证
    public String login(String loginId, String pwd, HttpSession session) {
        
        // 传入 账号，密码 取得用户对象； 如果为空则说明 账号或密码，或者 用户不存在
        UserBasic userBasic = userBO.login(loginId, pwd);
        
        // 如果不为空，则用户存在，且账号密码没有错误
        if (userBasic != null) {
            // 传入用户，取出用户的好友列表
            List<UserBasic> FriendList = userBO.getUserBasicFriendList(userBasic);
            userBasic.setFriendList(FriendList);  // 设置进用户对象的 好友列表中
            
            // 传入用户Id，取出用户的日志列表
            List<Topic> topicList = topicBO.getUserTopicList(userBasic.getId());
            userBasic.setTopicList(topicList);  // 设置进用户对象的 日志列表中
            
            // 因为取得的topic它的author属性是空的，所以需要将日志主人设置进去    
            for (Topic topic : topicList) {
                topic.setAuthor(userBasic);
            }
            
            
            // 将用户对象放入 会话作用域 中
            session.setAttribute("userBasic", userBasic);
            return "index";
            
            
        } else {
            return "login";
        }
    }
    
}
