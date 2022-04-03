package controller;

import bo.TopicBO;
import bo.UserBasicBO;
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
            
            session.setAttribute("friend", userBasic);  
            
            // userBasic 这个 key 映射的是登录者的 value
            // friend 这个 key 映射的是，当前进入的是谁的空间 
            
            
            return "index";
        } else {
            return "login";
        }
    }
    
    
    
    
    // 进入好友空间
    public String friendSpace(Integer id, HttpSession session) {
        
        // 根据 id 获取 session 中 userBasic 里的 friendList 里的好友；并给好友的 topicList 属性补上值
        UserBasic userBasic = (UserBasic)session.getAttribute("userBasic");  // 取出当前登录的用户

        // 如果接收到的 id 等于，当前登录用户的 id 则说明，是返回自己空间的逻辑。那么直接重新设置 key：friend 就可以
        if (userBasic.getId() == id) {
            session.setAttribute("friend", userBasic);
            
        } else {

            // 这里的逻辑是进入好友的空间
            
            // 1、取出对象中的好友集合
            List<UserBasic> friendList = userBasic.getFriendList();

            // 2、分别取出好友与 id 进行比较，相等的话，就给这个当前好友的 topicList 字段补上值
            for (UserBasic currentFriend : friendList) {
                if (id == currentFriend.getId()) {
                    // 传入好友的 id 取出他的日志列表
                    List<Topic> topicList = topicBO.getUserTopicList(currentFriend.getId());
                    currentFriend.setTopicList(topicList);

                    // 因为得到的 topic 它的author是空的，所以需要将日志主人手动设置进去
                    for (Topic topic : topicList) {
                        topic.setAuthor(currentFriend);
                    }

                    // 设置当前所在谁的空间
                    session.setAttribute("friend", currentFriend);

                    break;
                }
            }
            
        }
        
        return "index";
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
