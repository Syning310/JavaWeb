package bo;

import domain.HostReply;
import domain.Reply;
import service.ReplyService;

import java.util.Date;
import java.util.List;

public class ReplyBO {
    
    private ReplyService replyService = null;
    private HostReplyBO hostReplyBO = null;
    
    
    // 1、传入 topic 的 id 值，得到 List集合    其中回复的用户 和 针对的话题还未填充
    public List<Reply> getReplyListByTopicId(Integer id) {
        return replyService.getReplyListByTopicId(id);
    }
    
    
    // 2、传入 topic 的 id 值，得到相应的 topic 有多少条回复
    public Long getCountReplyByTopicId(Integer id) {
        return (Long)replyService.getCountReplyByTopicId(id);
    }

    
    // 3、根据 reply 的 id 返回这个 reply 对象的 author 的 id
    public Integer getAuthorByReplyId(Integer id) {
        return replyService.getAuthorByReplyId(id);
    }
    
    // 4、添加回复
    public Integer addReply(String content, Date date, Integer userId, Integer topicId) {
        return replyService.addReply(content, date, userId, topicId);
    }
    
    
    // 5、删除回复
    public Integer delReplyByReply(Reply reply) {
        // 删除回复之前，需要查看这个回复是否有主人回复，如果有的话，需要先删除主人回复，在删除回复
        // 1、取出这个回复中的HostReply
        HostReply hostReply = reply.getHostReply();
        // 2、传入这个主人回复的id将他删除
        if (hostReply != null) {
            int update = hostReplyBO.delHostReplyById(hostReply.getId());
            //System.out.println(update > 0 ? "主人回复删除成功" : "主人回复删除失败");
        }
        // 3、删除这条回复
        return replyService.delReplyByReplyId(reply.getId());
    }
    
    
}
