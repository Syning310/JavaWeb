package service;

import dao.ReplyDAO;
import domain.Reply;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class ReplyService {
    private ReplyDAO replyDAO = null;
    
    
    // 1、获取指定话题的回复列表，其中回复的用户 和 针对的话题还未填充
    public List<Reply> getReplyListByTopicId(Integer id) {
        String sql = "select id, content, replyDate from reply where topic=?";
        return replyDAO.queryMutil(sql, Reply.class, id);
    } 
    
    // 2、根据 reply 的 id 返回这个 reply 对象的 author 和 topic 并放入一个 Object[] 中
    public Integer getAuthorByReplyId(Integer id) {
        String sql = "select author from reply where id=?";
        return (Integer)replyDAO.queryScalar(sql, id);
    }
    
    
    // 2、添加回复
    // 传入内容、日期、用户id、话题id，添加回复记录
    public Integer addReply(String content, Date date, Integer userId, Integer topicId) {
        String sql = "insert into reply values (null, ?, ?, ?, ?)";
        return replyDAO.update(sql, content, date, userId, topicId);
    }
    
    
    // 4、传入回复的 id ，删除回复
    public Integer delReplyByReplyId(Integer id) {
        String sql = "delete from reply where id=?";
        return replyDAO.update(sql, id);
    }
    
    
    // 3、传入一个 topic 的 id，返回这个 topic 的回复数量
    public Object getCountReplyByTopicId(Integer id) {
        String sql = "select count(*) from reply where topic=?";
        return replyDAO.queryScalar(sql, id);
    }
    
    
}
