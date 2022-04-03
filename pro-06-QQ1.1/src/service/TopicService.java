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
    public Integer addTopic(Topic topic) {
        String sql = "insert into topic values (null, ?, ?, now(), ?)";
        return topicDAO.update(sql, topic.getTitle(), topic.getContent(), topic.getAuthor().getId());
    }
    
    // 删除日志，传入日志的 id
    public Integer delTopic(Integer id) {
        String sql = "delete from topic where id=?";
        return topicDAO.update(sql, id);
    }
    
    
    // 根据id获取特定日志信息
    public Topic getTopic(Integer id) {
        String sql = "select id, title, content, topicDate from topic where id=?";
        return topicDAO.querySingle(sql, Topic.class, id);
    }
    
    
}
