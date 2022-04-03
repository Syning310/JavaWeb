package bo;

import domain.HostReply;
import domain.Reply;
import domain.Topic;

import service.TopicService;


import java.util.List;

public class TopicBO {
    TopicService topicService = null;
    ReplyBO replyBO = null;
    HostReplyBO hostReplyBO = null;
    
    // BO 逻辑层
    
    // 1、根据用户id查询，用户的所有日志
    public List<Topic> getUserTopicList(Integer userId) {
        // 取得的 Topic 集合中的 author 是 null 的
        return topicService.getTopicListByUserId(userId);
    }
    
    
    // 2、根据日志 id，获取单个日志对象
    public Topic getTopicById(Integer id) {
        // 取得的日志是没有author的
        return topicService.getTopic(id);  
    }
    
    
    // 3、取得要删除的日志对象，先删除它的所有回复，如果回复中有主人回复，也要先删除
    public Integer delTopic(Topic topic) {
        // 1、传入话题的 id 取得 回复列表
        List<Reply> replyList = replyBO.getReplyListByTopicId(topic.getId());
        
        // 2、一个一个取出 replyList 中的元素，查看它们有没有对应的主人回复，如果有就先删除
        for (int i = 0; i < replyList.size(); ++i) {
            Reply reply = replyList.get(i);
            // 查看此条回复有没有对应的主人回复，如果有就先删除主人回复
            HostReply hostReply = hostReplyBO.getHostReplyListByReplyId(reply.getId());
            if (hostReply != null) {
                // 如果不为空，则删除这条主人回复
                hostReplyBO.delHostReplyById(hostReply.getId());
            }
            // 然后删除这条回复
            replyBO.delReplyByReply(reply);
        }
        // 2、再通过传入 topic 的 id 删除掉 topic
        return topicService.delTopic(topic.getId());
    }
    
    
    // 添加日志
    public Integer addTopic(Topic topic) {
        return topicService.addTopic(topic);
    }
    
    
}
