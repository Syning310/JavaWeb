package service;

import dao.TopicDAO;
import domain.Topic;
import domain.UserBasic;

import java.sql.ResultSet;
import java.util.List;

public class TopicService {
    
    private TopicDAO topicDAO = null;
    
    
    // 获取指定用户的日志列表  // 传入参数作者 id ,每个Topic都未设置 author
    public List<Topic> getTopicListByUserId(Integer userId) {
        String sql = "select id, title, content, topicDate from topic where author=?";  
        return topicDAO.queryMutil(sql, Topic.class, userId);
    }
    
    
    // 添加日志
    public void addTopic(Topic topic) {
        
        
    }
    
    // 删除日志
    public void delTopic(Topic topic) {
        
        
    }
    
    
    // 获取特定日志信息
    public Topic getTopic(Integer id) {
        
        return null;
    }
    
    
}
